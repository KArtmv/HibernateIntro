package ua.foxminded.javaspring.consoleMenu.databaseInitializer.generator;

import org.junit.jupiter.api.Test;
import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.model.Group;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.DataInitializer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DataConductTest {

    public static final DataInitializer INITIALIZE_OBJECT = new DataInitializer();
    public static final DataConduct DATA_CONDUCT = new DataConduct();

    @Test
    void getStudents_shouldReturnListOfStudent_whenIsSet() {
        List<Student> students = INITIALIZE_OBJECT.studentsListInit();
        DATA_CONDUCT.setStudents(students);

        assertThat(DATA_CONDUCT.getStudents()).isEqualTo(students);
    }

    @Test
    void getCourses_shouldReturnListOfCourses_whenIsSet() {
        List<Course> courses = INITIALIZE_OBJECT.coursesListInit();
        DATA_CONDUCT.setCourses(courses);

        assertThat(DATA_CONDUCT.getCourses()).isEqualTo(courses);
    }

    @Test
    void getGroups() {
        List<Group> groups = INITIALIZE_OBJECT.groupsListInit();
        DATA_CONDUCT.setGroups(groups);

        assertThat(DATA_CONDUCT.getGroups()).isEqualTo(groups);
    }
}