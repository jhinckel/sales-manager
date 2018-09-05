package filesystem;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;

public class FileReader extends FileUtils {
    
    public static List<String> readFileContent(Path path) throws IOException {
        List<String> fileLines = new LinkedList<>();
        
        try (Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8)) {
            stream.forEach(line -> {
                if (StringUtils.isNotBlank(line)) {
                    fileLines.add(line);       
                }
            });
        } catch (IOException e) {
            throw e;
        }
        return fileLines;
    }
    
}
