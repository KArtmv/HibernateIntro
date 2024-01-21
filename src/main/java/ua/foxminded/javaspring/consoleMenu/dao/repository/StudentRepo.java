package ua.foxminded.javaspring.consoleMenu.dao.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.consoleMenu.dao.StudentDAO;
import ua.foxminded.javaspring.consoleMenu.dao.TablesDAO;
import ua.foxminded.javaspring.consoleMenu.databaseInitializer.tables.scripts.SQLQueryOfCreateTable;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.model.StudentAtCourse;
import ua.foxminded.javaspring.consoleMenu.rowmapper.StudentAtCourseMapper;
import ua.foxminded.javaspring.consoleMenu.rowmapper.StudentMapper;

import java.util.List;
import java.util.Optional;

@Repository
@PropertySource("classpath:sqlQueries.properties")
public class StudentRepo implements StudentDAO, TablesDAO<Student> {

    private SQLQueryOfCreateTable queryOfCreateTable;
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_ADD_NEW_STUDENT = "insert into students (first_name, last_name, group_id) values (?, ?, ?)";
    private static final String SQL_GET_STUDENT_BY_ID = "select s.student_id, s.first_name, s.last_name, g.group_name\n" +
            "from students s join groups g on s.group_id = g.group_id\n" +
            "where student_id=?";
    private static final String SQL_DELETE_STUDENT_BY_ID = "delete from students where student_id=?";
    private static final String SQL_GET_LIST_COURSES_OF_STUDENT = "select sc.enrollment_id, s.first_name, s.last_name, c.course_name, c.course_description\n" +
            "from students s join studenttocourse sc on s.student_id = sc.student_id\n" +
            "join courses c on sc.course_id = c.course_id\n" +
            "where s.student_id=?";
    private static final String SQL_CHECK_IS_STUDENT_TABLE_EMPTY = "SELECT COUNT(*) FROM students";

    @Value("${sqlQuery.isExistTable}")
    private String sqlCheckIsTableExist;
    @Value("${table.student}")
    private String studentTableName;

    @Autowired
    public StudentRepo(SQLQueryOfCreateTable queryOfCreateTable, JdbcTemplate jdbcTemplate) {
        this.queryOfCreateTable = queryOfCreateTable;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Student> getItemByID(Student student) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_GET_STUDENT_BY_ID, new StudentMapper(),
                    student.getStudentID()));
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public boolean removeStudent(Student student) {
        return jdbcTemplate.update(SQL_DELETE_STUDENT_BY_ID, student.getStudentID()) > 0;
    }

    @Override
    public List<StudentAtCourse> studentCourses(Student student) {
        return jdbcTemplate.query(SQL_GET_LIST_COURSES_OF_STUDENT, new StudentAtCourseMapper(), student.getStudentID());
    }

    @Override
    public boolean addItem(Student student) {
        return jdbcTemplate.update(SQL_ADD_NEW_STUDENT, student.getFirstName(), student.getLastName(),
                student.getGroup().getGroupID()) > 0;
    }

    @Override
    public boolean isTableExist() {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(String.format(sqlCheckIsTableExist, studentTableName), Boolean.class));
    }

    @Override
    public void createTable() {
        jdbcTemplate.execute(queryOfCreateTable.getStudentTable());
    }

    @Override
    public boolean isTableEmpty() {
        return jdbcTemplate.queryForObject(SQL_CHECK_IS_STUDENT_TABLE_EMPTY, Integer.class) == 0;
    }
}
