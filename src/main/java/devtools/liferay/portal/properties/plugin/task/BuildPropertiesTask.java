package devtools.liferay.portal.properties.plugin.task;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BuildPropertiesTask extends DefaultTask {
    private String descFolderPath = "";
    private String originFolderPath = "";
    private String keysFolderPath = "";


    @TaskAction
    public void buildPropertiesTask() throws Exception{
        log("Build properties task..." + descFolderPath);
        printSettings();
        List<Path> originFiles = getFilesFromFolder(originFolderPath);
        List<Path> keysFiles = getFilesFromFolder(keysFolderPath);
        List<Path> destinationFolders = getFolderFromFolder(descFolderPath);

        keysFiles.stream().forEach(keysFile -> {
            parseEnvironment(getFileName(keysFile.getFileName().toString()), keysFile, originFiles, destinationFolders);
        });
    }

    private List<Path> getFilesFromFolder(String folderPath) throws IOException {
        final List<Path> collect = Files.list(Paths.get(getProject().getProjectDir() + "/" + folderPath)).filter(Files::isRegularFile).collect(Collectors.toList());
        return collect;
    }

    private List<Path> getFolderFromFolder(String folderPath) throws IOException {
        final List<Path> collect = Files.list(Paths.get(getProject().getProjectDir() + "/" +  folderPath)).filter(Files::isDirectory).collect(Collectors.toList());
        return collect;
    }

    private void parseEnvironment(String environment, Path keysFilePath, List<Path> originFiles, List<Path> destinationFolders){
        log("Parsing " + environment + " environment...");
        originFiles.stream().forEach(originFile -> {
            destinationFolders.stream().filter(destinationFolder -> {
                return destinationFolder.toString().endsWith(environment);
            }).forEach(destinationFolder -> {
                Path destinationFilePath = copyFile(originFile, destinationFolder);
                parseDestinationFile(keysFilePath, destinationFilePath);
            });
        });
    }

    private void parseDestinationFile(Path keysFilePath, Path destinationFilePath) {
        if(destinationFilePath != null){
            try {
                Properties keySet = new Properties();
                FileInputStream keysFileInputStream = null;
                keysFileInputStream = new FileInputStream(keysFilePath.toFile());
                if(keysFileInputStream != null){
                    keySet.load(keysFileInputStream);
                }
                Enumeration<String> keyNameEnum = (Enumeration<String>)keySet.propertyNames();
                while(keyNameEnum.hasMoreElements()){
                    String keyName = keyNameEnum.nextElement();
                    String keyNameSearched = "\\$\\{" + keyName + "\\}";
                    String keyValue = keySet.getProperty(keyName);
                    String content = new String(Files.readAllBytes(destinationFilePath), StandardCharsets.UTF_8);
                    content = content.replaceAll(keyNameSearched, keyValue);
                    Files.write(destinationFilePath, content.getBytes(StandardCharsets.UTF_8));
                }

                checkBrokenProperties(destinationFilePath, getFileName(keysFilePath.getFileName().toString()));



            }catch(IOException exception){
                error(null, exception);
            }
        }
    }

    private void checkBrokenProperties(Path destinationFilePath, String environment) throws IOException {
        String content = new String(Files.readAllBytes(destinationFilePath), StandardCharsets.UTF_8);
        Pattern brokenPropertyPattern = Pattern.compile("\\$\\{.*\\}");
        Matcher brokenPropertyMatcher = brokenPropertyPattern.matcher(content);
        while(brokenPropertyMatcher.find()){
            error("WARNING: Property not found in file " + destinationFilePath.getFileName().toString() + " on " + environment + " folder (" + brokenPropertyMatcher.group() + ")", null);
        }
    }


    private Path copyFile(Path originFilePath, Path destinationFolderPath){
        log("Copying " + originFilePath.toString() + " to " + destinationFolderPath.toString());
        Path destinationFilePath = Paths.get(destinationFolderPath.toString() + "/" + originFilePath.getFileName().toString());
        try {
            Files.copy(originFilePath, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            return destinationFilePath;
        }catch(IOException exception){
            error(null, exception);
            return null;
        }
    }

    private String getFileExtension(String file) {
        if (file == null) {
            return "";
        }
        int i = file.lastIndexOf('.');
        String ext = i > 0 ? file.substring(i + 1) : "";
        return ext;
    }

    private String getFileName(String file) {
        if (file == null) {
            return "";
        }
        int i = file.lastIndexOf('.');
        String ext = i > 0 ? file.substring(0, i) : "";
        return ext;
    }

    private void printSettings(){
        log("Settings: ");
        log("destination folder path: " + descFolderPath);
        log("origin folder path: " + originFolderPath);
        log("keys folder path: " + keysFolderPath);
    }

    public String getDescFolderPath() {
        return descFolderPath;
    }

    public void setDescFolderPath(String descFolderPath) {
        this.descFolderPath = descFolderPath;
    }

    public String getOriginFolderPath() {
        return originFolderPath;
    }

    public void setOriginFolderPath(String originFolderPath) {
        this.originFolderPath = originFolderPath;
    }

    public String getKeysFolderPath() {
        return keysFolderPath;
    }

    public void setKeysFolderPath(String keysFolderPath) {
        this.keysFolderPath = keysFolderPath;
    }

    private void log(String message){
        System.out.println(message);
    }

    private void error(String message, Exception exception){
        if(message != null && !message.isEmpty()) {
            System.err.println(message);
        }
        if(exception != null) {
            exception.printStackTrace();
        }
    }
}
