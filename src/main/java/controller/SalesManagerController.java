package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import filesystem.FileWriter;
import filewatcher.FileExtension;
import filewatcher.FileWatcher;
import filewatcher.ObjectReader;
import report.Report;
import report.ReportStrategy;
import report.ReportStrategyContext;
import report.SalesReportStrategy;

public class SalesManagerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalesManagerController.class);

    private static final String APPLICATION_PATH = new File("").getAbsolutePath();
    private static final String DATA_PATH = APPLICATION_PATH + File.separator + "dados" + File.separator;
    private static final String DEFAULT_INPUT_FOLDER = DATA_PATH + "in" + File.separator;
    private static final String DEFAULT_OUTPUT_FOLDER = DATA_PATH + "out" + File.separator;

    public static void main(String[] args) throws IOException, InterruptedException, Exception {
        SalesManagerController controller = new SalesManagerController();

        controller.bootstrap();
    }

    private void bootstrap() throws IOException, InterruptedException {
        try {
            FileWatcher fileWatcher = new FileWatcher(DEFAULT_INPUT_FOLDER);

            fileWatcher.start();

            while (true) {
                String changedFileName = fileWatcher.getChangedFilesQueue().take();

                processSaleFile(changedFileName);
            }
        } catch (IOException e) {
            LOGGER.error("Error: ", e);
            throw e;
        }
    }

    private void processSaleFile(String file)
            throws UnsupportedEncodingException, FileNotFoundException, IOException {
        ObjectReader fileObjectReader = new ObjectReader();
        Map<String, Object> fileEntitiesMap = fileObjectReader.readFileEntities(file);
        File outputFile = writeReport(fileEntitiesMap, file);

        LOGGER.info("File processed: " + outputFile);
    }

    private File writeReport(Map<String, Object> entitiesMap, String filePath)
            throws UnsupportedEncodingException, FileNotFoundException, IOException {
        FileWriter fileWriter = new FileWriter();
        File file = new File(filePath);
        FileExtension extension = FileExtension.valueOfExtension(file.getName());
        String newFileName = file.getName().replace(extension.getExtension(), extension.getProcessedExtension());
        File outputFile = new File(DEFAULT_OUTPUT_FOLDER + newFileName);
        ReportStrategyContext reportStrategyContext = new ReportStrategyContext();
        ReportStrategy reportStrategy = reportStrategyContext.getStrategy(SalesReportStrategy.class);
        Report report = reportStrategy.buildReport(entitiesMap);

        fileWriter.writeSalesReport(report, outputFile);

        return outputFile;
    }

}
