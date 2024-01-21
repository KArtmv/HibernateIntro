package ua.foxminded.javaspring.consoleMenu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ua.foxminded.javaspring.consoleMenu.dao.TablesDAO;
import ua.foxminded.javaspring.consoleMenu.databaseInitializer.generator.data.DataGenerator;
import ua.foxminded.javaspring.consoleMenu.databaseInitializer.tables.TableInitializer;
import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.model.Group;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.model.StudentAtCourse;

@Component
public class TablesConfigInitializer {

    @Bean
    public TableInitializer<Course> courseTableInitializer(TablesDAO<Course> dao, DataGenerator<Course> studentGenerator) {
        return new TableInitializer<>(dao, studentGenerator);
    }

    @Bean
    public TableInitializer<Group> groupTableInitializer(TablesDAO<Group> dao, DataGenerator<Group> groupGenerator) {
        return new TableInitializer<>(dao, groupGenerator);
    }

    @Bean
    public TableInitializer<Student> studentTableInitializer(TablesDAO<Student> dao, DataGenerator<Student> studentGenerator) {
        return new TableInitializer<>(dao, studentGenerator);
    }

    @Bean
    public TableInitializer<StudentAtCourse> studentAtCourseTableInitializer(TablesDAO<StudentAtCourse> dao, DataGenerator<StudentAtCourse> studentToCourseGenerator) {
        return new TableInitializer<>(dao, studentToCourseGenerator);
    }
}
