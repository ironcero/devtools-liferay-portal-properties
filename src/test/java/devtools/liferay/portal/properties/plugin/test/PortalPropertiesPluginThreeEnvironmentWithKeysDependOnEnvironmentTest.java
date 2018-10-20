package devtools.liferay.portal.properties.plugin.test;

import org.apache.tools.ant.filters.StringInputStream;
import org.gradle.internal.impldep.org.apache.commons.io.FileUtils;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static devtools.liferay.portal.properties.plugin.test.PortalPropertiesPluginTestKeys.*;
import static devtools.liferay.portal.properties.plugin.test.PortalPropertiesPluginTestKeys.PORTAL_PROPERTIES_ONE_PROPERTY_WITH_KEY_AND_OTHERS_WITHOUT_KEYS_DEPEND_ON_ENVIRONMENT.*;
import static org.gradle.testkit.runner.TaskOutcome.SUCCESS;
import static org.junit.Assert.assertEquals;

/**
 * Before:
 * - origin
 * -- portal-ext.properties {
 *      property1 = ${key1}
 *      property2 = value2
 * }
 * - keys
 * -- dev.properties {
 *      key1 = valueDev1
 * }
 * -- uat.properties {
 *      key1 = valueUat1
 * }
 * -- prod.properties {
 *      key1 = valueProd1
 * }
 * - dest
 * -- dev
 * -- uat
 * -- prod
 *
 * After:
 * - Result: SUCCESS
 * - Warnings: NONE
 * - dest
 * -- dev
 * --- portal-ext.properties {
 *      property1 = valueDev1
 *      property2 = value2
 * }
 * -- aut
 * --- portal-ext.properties {
 *      property1 = valueUat1
 *      property2 = value2
 * }
 * -- prod
 * --- portal-ext.properties {
 *      property1 = valueProd1
 *      property2 = value2
 * }
 */
public class PortalPropertiesPluginThreeEnvironmentWithKeysDependOnEnvironmentTest {

    @Rule
    public final TemporaryFolder testProjectDir = new TemporaryFolder();
    private File buildFile;
    private BuildResult result;

    @Before
    public void setUp() throws IOException{
        buildFile = testProjectDir.newFile(GRADLE_BUILD_FILE_NAME);
        InputStream inputStream = new StringInputStream(BASE_BUILD_GRADLE_FILE_CONTENT);
        FileUtils.copyInputStreamToFile(inputStream, buildFile);
        createFolders(testProjectDir);
        result = GradleRunner.create().withProjectDir(testProjectDir.getRoot()).withArguments(BUILD_COMMAND).build();
    }

    @Test
    public void testSuccess() {
        assertEquals(SUCCESS, result.task(":" + BUILD_COMMAND).getOutcome());
    }
    @Test
    public void testCorrectDestinationStructure() throws Exception {
        assertEquals(true, checkEmptyFolders(testProjectDir));
    }
    @Test
    public void testWarningMessages() {
        assertEquals(false, result.getOutput().contains("WARNING"));
    }


    private void createFolders(TemporaryFolder temporaryFolder) throws IOException {
        File destFolder = temporaryFolder.newFolder(DESTINATION_FOLDER_NAME);
        File originFolder = temporaryFolder.newFolder(ORIGIN_FOLDER_NAME);
        File keysFolder = temporaryFolder.newFolder(KEYS_FOLDER_NAME);

        File devEnvironmentKey = new File(keysFolder.getAbsolutePath() + "/" + DEV_ENVIRONMENT_NAME + "." + PROPERTIES_FILE_EXTENSION);
        File uatEnvironmentKey = new File(keysFolder.getAbsolutePath() + "/" + UAT_ENVIRONMENT_NAME + "." + PROPERTIES_FILE_EXTENSION);
        File prodEnvironmentKey = new File(keysFolder.getAbsolutePath() + "/" + PROD_ENVIRONMENT_NAME + "." + PROPERTIES_FILE_EXTENSION);
        devEnvironmentKey.createNewFile();
        uatEnvironmentKey.createNewFile();
        prodEnvironmentKey.createNewFile();
        InputStream inputStreamDevKeysFile = new StringInputStream(KEYS_DEV_CONTENT);
        InputStream inputStreamUatKeysFile = new StringInputStream(KEYS_UAT_CONTENT);
        InputStream inputStreamProdKeysFile = new StringInputStream(KEYS_PROD_CONTENT);
        FileUtils.copyInputStreamToFile(inputStreamDevKeysFile, devEnvironmentKey);
        FileUtils.copyInputStreamToFile(inputStreamUatKeysFile, uatEnvironmentKey);
        FileUtils.copyInputStreamToFile(inputStreamProdKeysFile, prodEnvironmentKey);

        File portalProperties = new File(originFolder.getAbsolutePath() + "/" + PORTAL_PROPERTIES_FILE_NAME + "." + PROPERTIES_FILE_EXTENSION);
        portalProperties.createNewFile();
        InputStream inputStream = new StringInputStream(PORTAL_PROPERTIES_ORIGIN);
        FileUtils.copyInputStreamToFile(inputStream, portalProperties);


        File devEnvironmentDestFolder = new File(destFolder.getAbsolutePath() + "/" + DEV_ENVIRONMENT_NAME);
        File uatEnvironmentDestFolder = new File(destFolder.getAbsolutePath() + "/" + UAT_ENVIRONMENT_NAME);
        File prodEnvironmentDestFolder = new File(destFolder.getAbsolutePath() + "/" + PROD_ENVIRONMENT_NAME);
        devEnvironmentDestFolder.mkdir();
        uatEnvironmentDestFolder.mkdir();
        prodEnvironmentDestFolder.mkdir();
    }

    private boolean checkEmptyFolders(TemporaryFolder temporaryFolder) throws IOException{
        return temporaryFolder.getRoot().exists() && temporaryFolder.getRoot().isDirectory() &&
                Files.exists(Paths.get(temporaryFolder.getRoot().getAbsolutePath() + "/" + DESTINATION_FOLDER_NAME)) &&
                Files.exists(Paths.get(temporaryFolder.getRoot().getAbsolutePath() + "/" + DESTINATION_FOLDER_NAME + "/" + DEV_ENVIRONMENT_NAME + "/" + PORTAL_PROPERTIES_FILE_NAME + "." + PROPERTIES_FILE_EXTENSION)) &&
                FileUtils.readFileToString(Paths.get(temporaryFolder.getRoot().getAbsolutePath() + "/" + DESTINATION_FOLDER_NAME + "/" + DEV_ENVIRONMENT_NAME + "/" + PORTAL_PROPERTIES_FILE_NAME + "." + PROPERTIES_FILE_EXTENSION).toFile()).equals(PORTAL_PROPERTIES_DEST_DEV) &&
                FileUtils.readFileToString(Paths.get(temporaryFolder.getRoot().getAbsolutePath() + "/" + DESTINATION_FOLDER_NAME + "/" + UAT_ENVIRONMENT_NAME + "/" + PORTAL_PROPERTIES_FILE_NAME + "." + PROPERTIES_FILE_EXTENSION).toFile()).equals(PORTAL_PROPERTIES_DEST_UAT) &&
                FileUtils.readFileToString(Paths.get(temporaryFolder.getRoot().getAbsolutePath() + "/" + DESTINATION_FOLDER_NAME + "/" + PROD_ENVIRONMENT_NAME + "/" + PORTAL_PROPERTIES_FILE_NAME + "." + PROPERTIES_FILE_EXTENSION).toFile()).equals(PORTAL_PROPERTIES_DEST_PROD)
                ;
    }
}
