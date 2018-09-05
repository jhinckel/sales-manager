package filesystem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import report.Report;

public class FileWriter extends FileUtils {
    
    public void writeSalesReport(Report report, File file) 
            throws UnsupportedEncodingException, FileNotFoundException, IOException {
        
        this.createFolderIfNotExists(file.getParent());
        
        try (OutputStream os = new FileOutputStream(file.getPath())) {
            try (Writer outputStreamWriter = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
                try (Writer writer = new BufferedWriter(outputStreamWriter)) {
                    writer.write(report.getReportData());
                }
            }
        }
    }
    
}
