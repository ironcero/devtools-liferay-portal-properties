package devtools.liferay.portal.properties.plugin;

public class PortalPropertiesUtils {
    public static final String TASK_NAME = "buildproperties";

    public static final String DESC_FOLDER_PATH_DEFAULT_VALUE = "configs";
    public static final String ORIGIN_FOLDER_PATH_DEFAULT_VALUE = "configs/common/origin";
    public static final String KEYS_FOLDER_PATH_DEFAULT_VALUE = "configs/common/keys";


    /**
     * This method return extension of file name
     * @param file file name witch extension will be returned
     * @return extension of file name
     */
    public static String getFileExtension(String file) {
        if (file == null) {
            return "";
        }
        int i = file.lastIndexOf('.');
        String ext = i > 0 ? file.substring(i + 1) : "";
        return ext;
    }

    /**
     * This method return file name without extension of file name
     * @param file file name witch file name without extension will be returned
     * @return file name without extension
     */
    public static String getFileName(String file) {
        if (file == null) {
            return "";
        }
        int i = file.lastIndexOf('.');
        String ext = i > 0 ? file.substring(0, i) : "";
        return ext;
    }

    /**
     * This method print on default console system the message
     * @param message message witch will be printed on default console
     */
    public static void log(String message){
        System.out.println(message);
    }

    /**
     * This method print on default error console system the message or exception. Exception will be print full stack trace.
     * @param message message witch will be printed on default error console
     * @param exception exception witch will be printed on default error console
     */
    public static void error(String message, Exception exception){
        if(message != null && !message.isEmpty()) {
            System.err.println(message);
        }
        if(exception != null) {
            exception.printStackTrace();
        }
    }

}
