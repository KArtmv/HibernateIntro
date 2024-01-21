package ua.foxminded.javaspring.consoleMenu.databaseInitializer.generator.data;

import org.springframework.beans.factory.annotation.Autowired;
import ua.foxminded.javaspring.consoleMenu.databaseInitializer.generator.DataConduct;
import ua.foxminded.javaspring.consoleMenu.databaseInitializer.generator.filesourse.SourceFilesDatabaseData;
import ua.foxminded.javaspring.consoleMenu.model.Group;

import java.util.List;
import java.util.stream.Collectors;

public class GroupGenerator implements DataGenerator<Group> {

    private SourceFilesDatabaseData resourcesFiles;
    private DataConduct dataConduct;

    @Autowired
    public GroupGenerator(SourceFilesDatabaseData resourcesFiles, DataConduct dataConduct) {
        this.resourcesFiles = resourcesFiles;
        this.dataConduct = dataConduct;
    }

    @Override
    public List<Group> generate() {
        List<String> groupNames = resourcesFiles.getGroups();

        List<Group> groups = groupNames.stream()
                .map(Group::new)
                .collect(Collectors.toList());

        dataConduct.setGroups(groups);
        return groups;
    }
}
