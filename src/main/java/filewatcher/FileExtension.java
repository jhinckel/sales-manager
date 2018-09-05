package filewatcher;

public enum FileExtension {
    
    DAT(".dat", ".dat.proc");
    
    private String extension;
    private String processedExtension;
       
    private FileExtension(String extension, String processedExtension) {
        this.extension = extension;
        this.processedExtension = processedExtension;
    }
    
    public String getExtension() {
        return this.extension;
    }

    public String getProcessedExtension() {
        return this.processedExtension;
    }

    public static FileExtension valueOfExtension(String fileName) {
        if (fileName != null) {
            for (FileExtension extension : FileExtension.values()) {
                if (fileName.endsWith(extension.getExtension())) {
                    return extension;
                }
            }
        }
        return null;                
    }
    
    public static boolean isExtensionAllowed(String fileName) {
        FileExtension extension = valueOfExtension(fileName);
        
        return extension != null;
    }

}
