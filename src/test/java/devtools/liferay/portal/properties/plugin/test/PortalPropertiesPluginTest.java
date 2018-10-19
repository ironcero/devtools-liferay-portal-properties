package devtools.liferay.portal.properties.plugin.test;

import org.gradle.internal.impldep.org.apache.commons.io.FileUtils;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.UnexpectedBuildFailure;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.NoSuchFileException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.gradle.testkit.runner.TaskOutcome.*;

public class PortalPropertiesPluginTest {

    @Rule
    public final TemporaryFolder testProjectDir = new TemporaryFolder();
    private File buildFile;

    @Before
    public void setUp() throws IOException{
        buildFile = testProjectDir.newFile("build.gradle");
    }

    @Test(expected = UnexpectedBuildFailure.class)
    public void testBuildPropertiesEmpty() throws Exception {
        FileUtils.copyInputStreamToFile(this.getClass().getClassLoader().getResourceAsStream("empty/build.gradle"), buildFile);


        BuildResult result = GradleRunner.create().withProjectDir(testProjectDir.getRoot()).withArguments("buildproperties").build();
        //assertTrue(result.getOutput().contains(""));
        //assertEquals(SUCCESS, result.task(":buildproperties").getOutcome());

    }


}
