package ua.foxminded.javaspring.consoleMenu.dao.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import ua.foxminded.javaspring.consoleMenu.dao.CourseDAO;
import ua.foxminded.javaspring.consoleMenu.model.Course;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {CourseDAO.class}))
@ActiveProfiles("test")
@Sql({"/sql/course/courseTable.sql", "/sql/course/courseData.sql"})
class CourseRepoTest {

    @Autowired
    private CourseDAO courseDAO;

    @Test
    @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
    @Sql(statements = "INSERT INTO courses (course_name, course_description) values ('Introduction to Psychology', 'Learn the basics of psychology');")
    void getById_shouldReturnCourse_whenGivenIdIsExist(){
            assertThat(courseDAO.getItemByID(4L).get())
                    .usingRecursiveComparison()
                    .isEqualTo(new Course(
                            4L,
                            "Introduction to Psychology",
                            "Learn the basics of psychology"));
    }

    @Test
    void getById_shouldReturnOptionalEmpty_whenGivenIdIsNotExist(){
        assertThat(courseDAO.getItemByID(4L)).isNotPresent();
    }

    @Test
    void getAll_shouldReturnListOfAvailableCourses_whenItRequest(){
        assertThat(courseDAO.getAll()).hasSize(3);
    }

    @Test
    void addItem_shouldSaveInDatabaseNewCourse_whenItRun(){
        Course course = new Course("Introduction to Psychology", "Learn the basics of psychology");

        assertAll(() -> {
            assertThat(courseDAO.addItem(course)).isTrue();

            List<Course> courses = courseDAO.getAll();
            assertThat(courses).hasSize(4);
            assertThat(courses.get(3)).usingRecursiveComparison().isEqualTo(new Course(4L, course.getCourseName(), course.getCourseDescription()));
        });
    }
}

