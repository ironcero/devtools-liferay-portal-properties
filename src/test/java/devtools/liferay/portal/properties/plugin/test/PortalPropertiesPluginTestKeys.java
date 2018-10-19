package devtools.liferay.portal.properties.plugin.test;

public class PortalPropertiesPluginTestKeys {

    public static final String BASE_BUILD_GRADLE_FILE_CONTENT =
            " buildscript {\n " +
            "    repositories {\n" +
            "        mavenLocal()\n" +
            "        maven {\n" +
            "            url 'https://repository-cdn.liferay.com/nexus/content/groups/public'\n" +
            "        }\n" +
            "    }\n" +
            "    dependencies {\n" +
            "        classpath group: 'devtools.liferay', name: 'portal-properties', version: '1.1.0'\n" +
            "    }\n" +
            " }\n" +
            " apply plugin: 'devtools-liferay-portal-properties'\n" +
            " buildproperties {\n" +
            "    descFolderPath = 'dest'\n" +
            "    originFolderPath = 'origin'\n" +
            "    keysFolderPath = 'keys'\n" +
            " }";
    public static final String DESTINATION_FOLDER_NAME = "dest";
    public static final String ORIGIN_FOLDER_NAME = "origin";
    public static final String KEYS_FOLDER_NAME = "keys";
}
