package ua.foxminded.javaspring.consoleMenu.dao.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import ua.foxminded.javaspring.consoleMenu.dao.StudentDAO;
import ua.foxminded.javaspring.consoleMenu.model.Group;
import ua.foxminded.javaspring.consoleMenu.model.Student;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {StudentDAO.class}))
@ActiveProfiles("test")
@Sql({"/sql/student/studentTable.sql", "/sql/student/studentData.sql"})
class StudentRepoTest {

    @Autowired
    private StudentDAO studentDAO;

    @Test
    @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
    @Sql(statements = "INSERT INTO students (first_name, last_name, group_id) values ('Ava', 'Rodriguez', 3)")
    void getById_shouldReturnStudent_whenIdIsExist() {
        assertThat(studentDAO.getItemByID(6L).get()).usingRecursiveComparison()
                .isEqualTo(new Student(6L, "Ava", "Rodriguez", new Group(3L, "QM-09")));
    }

    @Test
    void getById_shouldReturnEmptyOptional_whenIdIsNotExist() {
        assertThat(studentDAO.getItemByID(6L)).isNotPresent();
    }

    @Test
    void addItem_shouldSuccessfullySavaNewStudent_whenIsRun(){
        Student student = new Student("Ava", "Rodriguez");
        student.setGroup(new Group(3L));

        assertAll(() -> {
            assertThat(studentDAO.addItem(student)).isTrue();
            assertThat(studentDAO.getItemByID(6L).get()).usingRecursiveComparison().isEqualTo(
              new Student(6L, student.getFirstName(), student.getLastName(), student.getGroup()));
        });
    }

    @Test
    void removeStudent_shouldSuccessfullyRemoveStudent_whenItRun(){
        Student student = new Student(1L);

        assertAll(() -> {
            assertThat(studentDAO.getItemByID(student.getId())).isPresent();
            assertThat(studentDAO.removeStudent(student)).isTrue();
            assertThat(studentDAO.getItemByID(student.getId())).isNotPresent();
        });
    }

    @Test
    @SqlMergeMode(SqlMergeMode.MergeMode.OVERRIDE)
    @Sql({"/sql/studentAtCourse/tables.sql", "/sql/studentAtCourse/data.sql"})
    void studentCourses_shouldReturnListCoursesRelateToStudent_whenItRun() {
        Student student = new Student(1L);

        assertThat(studentDAO.studentCourses(student)).hasSize(3);
    }
}