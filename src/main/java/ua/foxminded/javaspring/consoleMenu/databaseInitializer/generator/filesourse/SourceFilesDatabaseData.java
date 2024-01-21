package ua.foxminded.javaspring.consoleMenu.databaseInitializer.generator.filesourse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ua.foxminded.javaspring.consoleMenu.databaseInitializer.ReadResourcesFile;

import java.util.List;

@Component
@PropertySource("classpath:sourceDataFilepath.properties")
public class SourceFilesDatabaseData {

    private ReadResourcesFile readFile;

    @Autowired
    public SourceFilesDatabaseData(ReadResourcesFile readFile) {
        this.readFile = readFile;
    }

    @Value("${filepath.groupFile}")
    private String groupsFile;

    @Value("${filepath.courseFile}")
    private String coursesFile;

    @Value("${filepath.firstNamesFile}")
    private String firstNameFile;

    @Value("${filepath.lastNamesFile}")
    private String lastNameFile;

    public List<String> getGroups() {
        return getSourceData(groupsFile);
    }

    public List<String> getCourses() {
        return getSourceData(coursesFile);
    }

    public List<String> getFirstNames() {
        return getSourceData(firstNameFile);
    }

    public List<String> getLastNames() {
        return getSourceData(lastNameFile);
    }

    private List<String> getSourceData(String filePath) {
        return readFile.getData(filePath);
    }
}
