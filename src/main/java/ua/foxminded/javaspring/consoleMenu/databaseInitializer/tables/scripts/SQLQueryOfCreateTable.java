package ua.foxminded.javaspring.consoleMenu.databaseInitializer.tables.scripts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ua.foxminded.javaspring.consoleMenu.util.ReadResourcesFile;

@Component
@PropertySource("classpath:sqlScriptsPath.properties")
public class SQLQueryOfCreateTable {

    private ReadResourcesFile readResourcesFile;

    @Autowired
    public SQLQueryOfCreateTable(ReadResourcesFile readResourcesFile) {
        this.readResourcesFile = readResourcesFile;
    }

    @Value("${sqlQuery.createTable.student}")
    private String SQL_SCRIPT_STUDENT;

    @Value("${sqlQuery.createTable.group}")
    private String SQL_SCRIPT_GROUP;

    @Value("${sqlQuery.createTable.course}")
    private String SQL_SCRIPT_COURSE;

    @Value("${sqlQuery.createTable.SQL_studentAtCourse}")
    private String SQL_SCRIPT_STUDENT_TO_COURSE;

    public String getStudentTable() {
        return getScript(SQL_SCRIPT_STUDENT);
    }

    public String getGroupTable() {
        return getScript(SQL_SCRIPT_GROUP);
    }

    public String getCourseTable() {
        return getScript(SQL_SCRIPT_COURSE);
    }

    public String getStudentToCourseTable() {
        return getScript(SQL_SCRIPT_STUDENT_TO_COURSE);
    }

    private String getScript(String filePath) {
        return readResourcesFile.getScript(filePath);
    }
}