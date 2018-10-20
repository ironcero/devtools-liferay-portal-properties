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

import java.io.*;

import static devtools.liferay.portal.properties.plugin.test.PortalPropertiesPluginTestKeys.BUILD_COMMAND;
import static devtools.liferay.portal.properties.plugin.test.PortalPropertiesPluginTestKeys.GRADLE_BUILD_FILE_NAME;

public class PortalPropertiesPluginNullTest {

    @Rule
    public final TemporaryFolder testProjectDir = new TemporaryFolder();
    private File buildFile;

    @Before
    public void setUp() throws IOException{
        buildFile = testProjectDir.newFile(GRADLE_BUILD_FILE_NAME);
    }

    @Test(expected = UnexpectedBuildFailure.class)
    public void testBuildPropertiesNull() throws Exception {
        InputStream inputStream = new StringInputStream(PortalPropertiesPluginTestKeys.BASE_BUILD_GRADLE_FILE_CONTENT);
        FileUtils.copyInputStreamToFile(inputStream, buildFile);
        GradleRunner.create().withProjectDir(testProjectDir.getRoot()).withArguments(BUILD_COMMAND).build();
    }
}
