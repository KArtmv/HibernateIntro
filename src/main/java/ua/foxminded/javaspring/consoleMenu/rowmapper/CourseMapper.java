package ua.foxminded.javaspring.consoleMenu.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ua.foxminded.javaspring.consoleMenu.model.Course;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseMapper implements RowMapper<Course> {

    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Course(rs.getLong("course_id"), rs.getString("course_name"), rs.getString("course_description"));
    }
}
