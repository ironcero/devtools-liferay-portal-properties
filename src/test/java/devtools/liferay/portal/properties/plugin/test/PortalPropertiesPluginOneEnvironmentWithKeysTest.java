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
import static devtools.liferay.portal.properties.plugin.test.PortalPropertiesPluginTestKeys.PORTAL_PROPERTIES_ONE_PROPERTY_WITH_KEY_AND_OTHERS_WITHOUT_KEYS.*;
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
 *      key1 = value1
 * }
 * - dest
 * -- dev
 *
 * After:
 * - Result: SUCCESS
 * - Warnings: NONE
 * - dest
 * -- dev
 * --- portal-ext.properties {
 *      property1 = value1
 *      property2 = value2
 * }
 */
public class PortalPropertiesPluginOneEnvironmentWithKeysTest {

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
        devEnvironmentKey.createNewFile();
        InputStream inputStreamDevKeysFile = new StringInputStream(KEYS_CONTENT);
        FileUtils.copyInputStreamToFile(inputStreamDevKeysFile, devEnvironmentKey);

        File portalProperties = new File(originFolder.getAbsolutePath() + "/" + PORTAL_PROPERTIES_FILE_NAME + "." + PROPERTIES_FILE_EXTENSION);
        portalProperties.createNewFile();
        InputStream inputStream = new StringInputStream(PORTAL_PROPERTIES_ORIGIN);
        FileUtils.copyInputStreamToFile(inputStream, portalProperties);

        File devEnvironmentDestFolder = new File(destFolder.getAbsolutePath() + "/" + DEV_ENVIRONMENT_NAME);
        devEnvironmentDestFolder.mkdir();
    }

    private boolean checkEmptyFolders(TemporaryFolder temporaryFolder) throws IOException{
        return temporaryFolder.getRoot().exists() && temporaryFolder.getRoot().isDirectory() &&
                Files.exists(Paths.get(temporaryFolder.getRoot().getAbsolutePath() + "/" + DESTINATION_FOLDER_NAME)) &&
                Files.exists(Paths.get(temporaryFolder.getRoot().getAbsolutePath() + "/" + DESTINATION_FOLDER_NAME + "/" + DEV_ENVIRONMENT_NAME + "/" + PORTAL_PROPERTIES_FILE_NAME + "." + PROPERTIES_FILE_EXTENSION)) &&
                FileUtils.readFileToString(Paths.get(temporaryFolder.getRoot().getAbsolutePath() + "/" + DESTINATION_FOLDER_NAME + "/" + DEV_ENVIRONMENT_NAME + "/" + PORTAL_PROPERTIES_FILE_NAME + "." + PROPERTIES_FILE_EXTENSION).toFile()).equals(PORTAL_PROPERTIES_DEST)
                ;
    }
}
