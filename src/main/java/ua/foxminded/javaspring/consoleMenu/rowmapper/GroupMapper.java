package ua.foxminded.javaspring.consoleMenu.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ua.foxminded.javaspring.consoleMenu.model.Group;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupMapper implements RowMapper<Group> {
    @Override
    public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Group(rs.getLong("group_id"), rs.getString("group_name"));
    }
}
