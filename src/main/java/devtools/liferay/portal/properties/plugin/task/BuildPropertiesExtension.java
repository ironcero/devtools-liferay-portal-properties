package devtools.liferay.portal.properties.plugin.task;

import org.gradle.api.Project;

public class BuildPropertiesExtension {
    private String descFolderPath = "";

    public BuildPropertiesExtension(Project project){
        this.descFolderPath = project.getObjects().property(String.class).get();
    }

    public BuildPropertiesExtension(){

    }

    public String getDescFolderPath() {
        return descFolderPath;
    }

    public void setDescFolderPath(String descFolderPath) {
        this.descFolderPath = descFolderPath;
    }
}
