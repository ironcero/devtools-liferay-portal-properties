package devtools.liferay.portal.properties.plugin.test;

public class PortalPropertiesPluginTestKeys {

    public static final String BUILD_COMMAND = "buildproperties";
    public static final String GRADLE_BUILD_FILE_NAME = "build.gradle";

    public static final String BASE_BUILD_GRADLE_FILE_CONTENT =
            " buildscript {\n " +
            "    repositories {\n" +
            "        mavenLocal()\n" +
            "        maven {\n" +
            "            url 'https://repository-cdn.liferay.com/nexus/content/groups/public'\n" +
            "        }\n" +
            "    }\n" +
            "    dependencies {\n" +
            "        classpath group: 'devtools.liferay', name: 'portal-properties', version: '1.1.1'\n" +
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

    public static final String DEV_ENVIRONMENT_NAME = "dev";
    public static final String UAT_ENVIRONMENT_NAME = "uat";
    public static final String PROD_ENVIRONMENT_NAME = "prod";

    public static final String PROPERTIES_FILE_EXTENSION = "properties";

    public static final String PORTAL_PROPERTIES_FILE_NAME = "portal-ext";

    public class PORTAL_PROPERTIES_ONE_PROPERTY{
        public static final String PORTAL_PROPERTIES_ORIGIN = "property1 = value1";

        public static final String PORTAL_PROPERTIES_DEST = "property1 = value1";
    }

    public class PORTAL_PROPERTIES_ONE_PROPERTY_WITH_KEY_AND_OTHERS_WITHOUT_KEYS{
        public static final String PORTAL_PROPERTIES_ORIGIN = "property1 = ${key1}\nproperty2 = value2";
        public static final String KEYS_CONTENT = "key1 = value1";

        public static final String PORTAL_PROPERTIES_DEST = "property1 = value1\nproperty2 = value2";
    }

    public class PORTAL_PROPERTIES_ONE_PROPERTY_WITH_KEY_AND_OTHERS_WITHOUT_KEYS_DEPEND_ON_ENVIRONMENT{
        public static final String PORTAL_PROPERTIES_ORIGIN = "property1 = ${key1}\nproperty2 = value2";
        public static final String KEYS_DEV_CONTENT = "key1 = valueDev1";
        public static final String KEYS_UAT_CONTENT = "key1 = valueUat1";
        public static final String KEYS_PROD_CONTENT = "key1 = valueProd1";

        public static final String PORTAL_PROPERTIES_DEST_DEV = "property1 = valueDev1\nproperty2 = value2";
        public static final String PORTAL_PROPERTIES_DEST_UAT = "property1 = valueUat1\nproperty2 = value2";
        public static final String PORTAL_PROPERTIES_DEST_PROD = "property1 = valueProd1\nproperty2 = value2";
    }

    public class PORTAL_PROPERTIES_COMPL_SETUP{
        public static final String PORTAL_PROPERTIES_ORIGIN = "property1 = ${key1}\nproperty2 = value2\nproperty3 = ${key2}";
        public static final String KEYS_DEV_CONTENT = "key1 = valueDev1\nkey2 = valueDev2";
        public static final String KEYS_UAT_CONTENT = "key1 = valueUat1\nkey2 = valueUat2";
        public static final String KEYS_PROD_CONTENT = "key1 = valueProd1\nkey2 = valueProd2";

        public static final String PORTAL_PROPERTIES_DEST_DEV = "property1 = valueDev1\nproperty2 = value2\nproperty3 = valueDev2";
        public static final String PORTAL_PROPERTIES_DEST_UAT = "property1 = valueUat1\nproperty2 = value2\nproperty3 = valueUat2";
        public static final String PORTAL_PROPERTIES_DEST_PROD = "property1 = valueProd1\nproperty2 = value2\nproperty3 = valueProd2";
    }

    public class PORTAL_PROPERTIES_COMPL_SETUP_WITH_WARNING{
        public static final String PORTAL_PROPERTIES_ORIGIN = "property1 = ${key1}\nproperty2 = value2\nproperty3 = ${key2}";
        public static final String KEYS_DEV_CONTENT = "key1 = valueDev1\nkey2 = valueDev2";
        public static final String KEYS_UAT_CONTENT = "key1 = valueUat1";
        public static final String KEYS_PROD_CONTENT = "key1 = valueProd1\nkey2 = valueProd2";

        public static final String PORTAL_PROPERTIES_DEST_DEV = "property1 = valueDev1\nproperty2 = value2\nproperty3 = valueDev2";
        public static final String PORTAL_PROPERTIES_DEST_UAT = "property1 = valueUat1\nproperty2 = value2\nproperty3 = ${key2}";
        public static final String PORTAL_PROPERTIES_DEST_PROD = "property1 = valueProd1\nproperty2 = value2\nproperty3 = valueProd2";
    }
}
