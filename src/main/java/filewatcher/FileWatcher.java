package filewatcher;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import filesystem.FileUtils;

public class FileWatcher extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileWatcher.class);

    private String inputFolderPath;
    private BlockingQueue<String> changedFilesQueue = new LinkedBlockingQueue<>();
    private Map<String, Long> processedFilesTimestampMap = Collections.synchronizedMap(new HashMap<>());

    public FileWatcher(String inputFolderPath) throws FileNotFoundException {
        if (FileUtils.isPathAFolder(inputFolderPath)) {
            this.inputFolderPath = inputFolderPath;
        } else {
            throw new FileNotFoundException("Invalid path!");
        }
    }

    @Override
    public void run() {
        LOGGER.info("Watching path: " + this.inputFolderPath);
        Path folderPath = Paths.get(this.inputFolderPath);
        FileSystem fs = folderPath.getFileSystem();

        // Add files present before started folder listener.
        addPreviousFiles(this.inputFolderPath);

        try (WatchService service = fs.newWatchService()) {
            WatchKey key = null;

            folderPath.register(service, ENTRY_CREATE, ENTRY_MODIFY);

            while (true) {
                key = service.take();

                for (WatchEvent<?> watchEvent : key.pollEvents()) {
                    if ((ENTRY_CREATE == watchEvent.kind()) || (ENTRY_MODIFY == watchEvent.kind())) {
                        Path dir = (Path) key.watchable();
                        Path fullPath = dir.resolve((Path) watchEvent.context());
                        String changedFile = fullPath.toString();

                        if (FileExtension.isExtensionAllowed(changedFile)) {
                            if (this.shouldProcessFile(changedFile)) {
                                LOGGER.info("New file created/modified: " + changedFile);
                                this.getChangedFilesQueue().add(changedFile);
                            }
                        }
                    }
                }
                if (!key.reset()) {
                    break;
                }
            }
        } catch (IOException ioe) {
            LOGGER.error("Error processing file changes event: ", ioe);
        } catch (InterruptedException ie) {
            LOGGER.error("Interrupted process: ", ie);
        }
    }

    private void addPreviousFiles(String inputFolderPath) {
        File folder = new File(inputFolderPath);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile() && FileExtension.isExtensionAllowed(file.getName())) {
                this.getChangedFilesQueue().add(file.getAbsolutePath());
            }
        }
    }

    private synchronized boolean shouldProcessFile(String changedFile) {
        File file = new File(changedFile);
        Long fileTimestamp = this.getProcessedFilesTimestampMap().get(changedFile);
        boolean shouldProcessChangedFile = false;

        if ((fileTimestamp == null) || (fileTimestamp.longValue() < file.lastModified())) {
            this.getProcessedFilesTimestampMap().put(changedFile, Long.valueOf(file.lastModified()));
            shouldProcessChangedFile = true;
        }
        return shouldProcessChangedFile;
    }

    public Map<String, Long> getProcessedFilesTimestampMap() {
        return processedFilesTimestampMap;
    }

    public synchronized BlockingQueue<String> getChangedFilesQueue() {
        return changedFilesQueue;
    }

}
