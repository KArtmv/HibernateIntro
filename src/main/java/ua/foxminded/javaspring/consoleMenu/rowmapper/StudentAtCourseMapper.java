package ua.foxminded.javaspring.consoleMenu.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.model.StudentAtCourse;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentAtCourseMapper implements RowMapper<StudentAtCourse> {

    @Override
    public StudentAtCourse mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new StudentAtCourse(rs.getLong("enrollment_id"),
                new Student(rs.getString("first_name"), rs.getString("last_name")),
                new Course(rs.getString("course_name"), rs.getString("course_description")));
    }
}