package devtools.liferay.portal.properties.plugin.task.test;

import devtools.liferay.portal.properties.plugin.PortalPropertiesUtils;
import org.junit.Assert;
import org.junit.Test;

public class BuildPropertiesTaskTest {

    /**
     * getFileExtension: notFolders -> ""
     */
    @Test
    public void getFileExtensionNullTest(){
        String fileName = null;
        String result = PortalPropertiesUtils.getFileExtension(fileName);
        String resultExpected = "";
        Assert.assertEquals(resultExpected, result);
    }

    /**
     * getFileExtension: notFolders -> ""
     */
    @Test
    public void getFileExtensionBlankTest(){
        String fileName = "";
        String result = PortalPropertiesUtils.getFileExtension(fileName);
        String resultExpected = "";
        Assert.assertEquals(resultExpected, result);
    }

    /**
     * getFileExtension: fileNameWithoutExtension -> ""
     */
    @Test
    public void getFileExtensionNoExtensionTest(){
        String fileName = "fileNameWithoutExtension";
        String result = PortalPropertiesUtils.getFileExtension(fileName);
        String resultExpected = "";
        Assert.assertEquals(resultExpected, result);
    }

    /**
     * getFileExtension: fileName.extension -> ""
     */
    @Test
    public void getFileExtensionExtensionTest(){
        String fileName = "fileName.extension";
        String result = PortalPropertiesUtils.getFileExtension(fileName);
        String resultExpected = "extension";
        Assert.assertEquals(resultExpected, result);
    }
}
