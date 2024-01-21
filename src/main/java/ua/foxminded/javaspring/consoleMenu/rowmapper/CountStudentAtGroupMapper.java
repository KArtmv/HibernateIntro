package ua.foxminded.javaspring.consoleMenu.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ua.foxminded.javaspring.consoleMenu.model.CounterStudentsAtGroup;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountStudentAtGroupMapper implements RowMapper<CounterStudentsAtGroup> {

    @Override
    public CounterStudentsAtGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CounterStudentsAtGroup(rs.getInt("student_count"), rs.getString("group_name"));
    }
}
