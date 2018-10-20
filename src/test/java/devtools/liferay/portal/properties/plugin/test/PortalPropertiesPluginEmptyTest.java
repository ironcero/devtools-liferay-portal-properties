package devtools.liferay.portal.properties.plugin.test;

import org.apache.tools.ant.filters.StringInputStream;
import org.gradle.internal.impldep.org.apache.commons.io.FileUtils;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.UnexpectedBuildFailure;
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
import static org.gradle.testkit.runner.TaskOutcome.SUCCESS;
import static org.junit.Assert.assertEquals;


public class PortalPropertiesPluginEmptyTest {

    @Rule
    public final TemporaryFolder testProjectDir = new TemporaryFolder();
    private File buildFile;
    private BuildResult result;

    @Before
    public void setUp() throws IOException{
        buildFile = testProjectDir.newFile(GRADLE_BUILD_FILE_NAME);
        InputStream inputStream = new StringInputStream(PortalPropertiesPluginTestKeys.BASE_BUILD_GRADLE_FILE_CONTENT);
        FileUtils.copyInputStreamToFile(inputStream, buildFile);
        createEmptyFolders(testProjectDir);
        result = GradleRunner.create().withProjectDir(testProjectDir.getRoot()).withArguments(BUILD_COMMAND).build();
    }

    @Test
    public void testSuccess() {
        assertEquals(SUCCESS, result.task(":" + BUILD_COMMAND).getOutcome());
    }
    @Test
    public void testCorrectDestinationStructure() {
        assertEquals(true, checkEmptyFolders(testProjectDir));
    }
    @Test
    public void testWarningMessages() {
        assertEquals(false, result.getOutput().contains("WARNING"));
    }

    private void createEmptyFolders(TemporaryFolder temporaryFolder) throws IOException{
        temporaryFolder.newFolder(DESTINATION_FOLDER_NAME);
        temporaryFolder.newFolder(ORIGIN_FOLDER_NAME);
        temporaryFolder.newFolder(KEYS_FOLDER_NAME);
    }
    private boolean checkEmptyFolders(TemporaryFolder temporaryFolder){
        return temporaryFolder.getRoot().exists() && temporaryFolder.getRoot().isDirectory() &&
                Files.exists(Paths.get(temporaryFolder.getRoot().getAbsolutePath() + "/" + DESTINATION_FOLDER_NAME)) &&
                Files.exists(Paths.get(temporaryFolder.getRoot().getAbsolutePath() + "/" + ORIGIN_FOLDER_NAME)) &&
                Files.exists(Paths.get(temporaryFolder.getRoot().getAbsolutePath() + "/" + KEYS_FOLDER_NAME)) &&
                Paths.get(temporaryFolder.getRoot().getAbsolutePath() + "/" + DESTINATION_FOLDER_NAME).toFile().list().length == 0 &&
                Paths.get(temporaryFolder.getRoot().getAbsolutePath() + "/" + ORIGIN_FOLDER_NAME).toFile().list().length == 0 &&
                Paths.get(temporaryFolder.getRoot().getAbsolutePath() + "/" + KEYS_FOLDER_NAME).toFile().list().length == 0;
    }
}
