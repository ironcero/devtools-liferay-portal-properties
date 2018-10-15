package devtools.liferay.portal.properties.plugin;

import devtools.liferay.portal.properties.plugin.task.BuildPropertiesExtension;
import devtools.liferay.portal.properties.plugin.task.BuildPropertiesTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import static devtools.liferay.portal.properties.plugin.PortalPropertiesUtils.TASK_NAME;

public class PortalPropertiesPlugin implements Plugin<Project> {


    @Override
    public void apply(Project target) {
        target.getTasks().create(TASK_NAME, BuildPropertiesTask.class, (task) -> {
            task.setDescFolderPath("loquesea");
        });
    }
}
