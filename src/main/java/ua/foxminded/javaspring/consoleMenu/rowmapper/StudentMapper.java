package ua.foxminded.javaspring.consoleMenu.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ua.foxminded.javaspring.consoleMenu.model.Group;
import ua.foxminded.javaspring.consoleMenu.model.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Student(rs.getLong("student_id"), rs.getString("first_name"), rs.getString("last_name"),
                new Group(rs.getString("group_name")));
    }
}
