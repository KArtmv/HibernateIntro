package ua.foxminded.javaspring.consoleMenu.databaseInitializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.foxminded.javaspring.consoleMenu.databaseInitializer.tables.TableInitializer;
import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.model.Group;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.model.StudentAtCourse;
import ua.foxminded.javaspring.consoleMenu.menu.MenuInteraction;

import javax.annotation.PostConstruct;

@Component
public class DatabaseInitializer {
    private TableInitializer<Course> courseTableInitializer1;
    private TableInitializer<Group> groupTableInitializer;
    private TableInitializer<Student> studentTableInitializer;
    private TableInitializer<StudentAtCourse> studentAtCourseTableInitializer;
    private MenuInteraction menuInteraction;

    @Autowired
    public DatabaseInitializer(TableInitializer<Course> courseTableInitializer1, TableInitializer<Group> groupTableInitializer, TableInitializer<Student> studentTableInitializer, TableInitializer<StudentAtCourse> studentAtCourseTableInitializer, MenuInteraction menuInteraction) {
        this.courseTableInitializer1 = courseTableInitializer1;
        this.groupTableInitializer = groupTableInitializer;
        this.studentTableInitializer = studentTableInitializer;
        this.studentAtCourseTableInitializer = studentAtCourseTableInitializer;
        this.menuInteraction = menuInteraction;
    }

    @PostConstruct
    public void initializeTables() {
        groupTableInitializer.initialize();

        courseTableInitializer1.initialize();

        studentTableInitializer.initialize();

        studentAtCourseTableInitializer.initialize();

        menuInteraction.startMenu();
    }
}
