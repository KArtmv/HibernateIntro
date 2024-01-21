package ua.foxminded.javaspring.consoleMenu.dao.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.consoleMenu.dao.StudentAtCourseDAO;
import ua.foxminded.javaspring.consoleMenu.dao.TablesDAO;
import ua.foxminded.javaspring.consoleMenu.databaseInitializer.tables.scripts.SQLQueryOfCreateTable;
import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.model.StudentAtCourse;
import ua.foxminded.javaspring.consoleMenu.rowmapper.StudentAtCourseMapper;

import java.util.List;
import java.util.Optional;

@Repository
@PropertySource("classpath:sqlQueries.properties")
public class StudentAtCourseRepo implements StudentAtCourseDAO, TablesDAO<StudentAtCourse> {

    private SQLQueryOfCreateTable queryOfCreateTable;
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_GET_ALL_STUDENT_FROM_COURSE = "select *\n"
            + "from studenttocourse sc\n"
            + "join students s on s.student_id = sc.student_id\n"
            + "join courses c on c.course_id = sc.course_id\n"
            + "where c.course_id=?";
    private static final String SQL_ADD_STUDENT_TO_COURSE = "insert into studenttocourse (student_id, course_id) values (?, ?)";
    private static final String SQL_REMOVE_STUDENT_FROM_COURSE = "delete from studenttocourse where enrollment_id=?";
    private static final String SQL_REMOVE_STUDENT_FROM_ALL_THEIR_COURSES = "delete from studenttocourse where student_id=?";
    private static final String SQL_CHECK_IS_STUDENT_TO_COURSE_TABLE_EMPTY = "SELECT COUNT(*) FROM studenttocourse";
    private static final String SQL_GET_ENROLLMENT_BY_ID = "select * from studenttocourse where enrollment_id=?";

    @Value("${sqlQuery.isExistTable}")
    private String sqlCheckIsTableExist;
    @Value("${table.enrollment}")
    private String studentToCourseTableName;

    @Autowired
    public StudentAtCourseRepo(SQLQueryOfCreateTable queryOfCreateTable, JdbcTemplate jdbcTemplate) {
        this.queryOfCreateTable = queryOfCreateTable;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<StudentAtCourse> allStudentsFromCourse(Course course) {
        return jdbcTemplate.query(SQL_GET_ALL_STUDENT_FROM_COURSE, new StudentAtCourseMapper(), course.getCourseID());
    }

    @Override
    public boolean addItem(StudentAtCourse studentAtCourse) {
        return jdbcTemplate.update(SQL_ADD_STUDENT_TO_COURSE, studentAtCourse.getStudent().getStudentID(), studentAtCourse.getCourse().getCourseID()) > 0;
    }

    @Override
    public Optional<StudentAtCourse> getItemByID(StudentAtCourse studentAtCourse) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_GET_ENROLLMENT_BY_ID, new StudentAtCourseMapper(), studentAtCourse.getEnrollmentID()));
    }

    @Override
    public boolean removeStudentFromCourse(StudentAtCourse studentAtCourse) {
        return jdbcTemplate.update(SQL_REMOVE_STUDENT_FROM_COURSE, studentAtCourse.getEnrollmentID()) > 0;
    }

    @Override
    public boolean removeStudentFromAllTheirCourses(Student student) {
        return jdbcTemplate.update(SQL_REMOVE_STUDENT_FROM_ALL_THEIR_COURSES, student.getStudentID()) > 0;
    }

    @Override
    public boolean isTableExist() {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(String.format(sqlCheckIsTableExist, studentToCourseTableName), Boolean.class));
    }

    @Override
    public void createTable() {
        jdbcTemplate.execute(queryOfCreateTable.getStudentToCourseTable());
    }

    @Override
    public boolean isTableEmpty() {
        return jdbcTemplate.queryForObject(SQL_CHECK_IS_STUDENT_TO_COURSE_TABLE_EMPTY, Integer.class) == 0;
    }
}
