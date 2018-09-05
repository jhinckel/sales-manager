package filesystem;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class FileUtils {
    
    protected boolean isFolderExists(String path) {
        Path filePath = Paths.get(path);
        
        if (Files.exists(filePath, LinkOption.NOFOLLOW_LINKS)) {
            return true;
        }
        return false;
    }
    
    protected void createFolderIfNotExists(String path) throws IOException {
        if (!isFolderExists(path)) {
            Path dirPathObj = Paths.get(path);
            Files.createDirectories(dirPathObj);
        }
    }
    
    public static boolean isPathAFolder(String folderPath) {
        boolean isPathValid = false;
        
        try {
            Path path = Paths.get(folderPath);
            Boolean isFolder = (Boolean) Files.getAttribute(path, "basic:isDirectory", NOFOLLOW_LINKS);
            
            if (!isFolder) {
                throw new IllegalArgumentException("Path: " + path + " is not a folder");
            }
            isPathValid = true;
        } catch (IOException ioe) {
            throw new IllegalArgumentException("Path does not exists: " + folderPath, ioe);
        }
        return isPathValid;
    }

}
