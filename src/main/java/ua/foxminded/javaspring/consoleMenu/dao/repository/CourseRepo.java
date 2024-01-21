package ua.foxminded.javaspring.consoleMenu.dao.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.consoleMenu.dao.CourseDAO;
import ua.foxminded.javaspring.consoleMenu.dao.TablesDAO;
import ua.foxminded.javaspring.consoleMenu.databaseInitializer.tables.scripts.SQLQueryOfCreateTable;
import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.rowmapper.CourseMapper;

import java.util.List;
import java.util.Optional;

@Repository
@PropertySource("classpath:sqlQueries.properties")
public class CourseRepo implements TablesDAO<Course>, CourseDAO {

    private SQLQueryOfCreateTable queryOfCreateTable;
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_ADD_NEW_COURSE = "insert into courses (course_name, course_description) values (?, ?)";
    private static final String SQL_GET_COURSE_BY_ID = "select * from courses where course_id=?";
    private static final String SQL_CHECK_IS_COURSE_TABLE_EMPTY = "SELECT COUNT(*) FROM courses";
    private static final String SQL_GET_LIST_OF_COURSE = "select * from courses";

    @Value("${sqlQuery.isExistTable}")
    private String sqlCheckIsTableExist;
    @Value("${table.course}")
    private String courseTableName;

    @Autowired
    public CourseRepo(SQLQueryOfCreateTable queryOfCreateTable, JdbcTemplate jdbcTemplate) {
        this.queryOfCreateTable = queryOfCreateTable;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean addItem(Course course) {
        return jdbcTemplate.update(SQL_ADD_NEW_COURSE, course.getCourseName(), course.getCourseDescription()) > 0;
    }

    @Override
    public Optional<Course> getItemByID(Course course) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_GET_COURSE_BY_ID, new CourseMapper(), course.getCourseID()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean isTableExist() {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(String.format(sqlCheckIsTableExist, courseTableName), Boolean.class));
    }

    @Override
    public void createTable() {
        jdbcTemplate.execute(queryOfCreateTable.getCourseTable());
    }

    @Override
    public boolean isTableEmpty() {
        return jdbcTemplate.queryForObject(SQL_CHECK_IS_COURSE_TABLE_EMPTY, Integer.class) == 0;
    }

    @Override
    public List<Course> getAll() {
        return jdbcTemplate.query(SQL_GET_LIST_OF_COURSE, new CourseMapper());
    }
}
