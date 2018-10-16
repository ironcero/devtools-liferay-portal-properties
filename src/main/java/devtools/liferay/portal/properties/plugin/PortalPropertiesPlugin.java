package devtools.liferay.portal.properties.plugin;

import devtools.liferay.portal.properties.plugin.task.BuildPropertiesTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import static devtools.liferay.portal.properties.plugin.PortalPropertiesUtils.TASK_NAME;
import static devtools.liferay.portal.properties.plugin.PortalPropertiesUtils.DESC_FOLDER_PATH_DEFAULT_VALUE;
import static devtools.liferay.portal.properties.plugin.PortalPropertiesUtils.ORIGIN_FOLDER_PATH_DEFAULT_VALUE;
import static devtools.liferay.portal.properties.plugin.PortalPropertiesUtils.KEYS_FOLDER_PATH_DEFAULT_VALUE;

public class PortalPropertiesPlugin implements Plugin<Project> {


    @Override
    public void apply(Project target) {
        target.getTasks().create(TASK_NAME, BuildPropertiesTask.class, (task) -> {
            task.setDescFolderPath(DESC_FOLDER_PATH_DEFAULT_VALUE);
            task.setOriginFolderPath(ORIGIN_FOLDER_PATH_DEFAULT_VALUE);
            task.setKeysFolderPath(KEYS_FOLDER_PATH_DEFAULT_VALUE);
        });
    }
}
