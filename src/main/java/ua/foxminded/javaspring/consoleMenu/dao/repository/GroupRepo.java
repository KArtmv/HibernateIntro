package ua.foxminded.javaspring.consoleMenu.dao.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.consoleMenu.dao.TablesDAO;
import ua.foxminded.javaspring.consoleMenu.dao.GroupDAO;
import ua.foxminded.javaspring.consoleMenu.databaseInitializer.tables.scripts.SQLQueryOfCreateTable;
import ua.foxminded.javaspring.consoleMenu.model.CounterStudentsAtGroup;
import ua.foxminded.javaspring.consoleMenu.model.Group;
import ua.foxminded.javaspring.consoleMenu.rowmapper.CountStudentAtGroupMapper;
import ua.foxminded.javaspring.consoleMenu.rowmapper.GroupMapper;

import java.util.List;
import java.util.Optional;

@Repository
@PropertySource("classpath:sqlQueries.properties")
public class GroupRepo implements GroupDAO, TablesDAO<Group> {

    private SQLQueryOfCreateTable queryOfCreateTable;
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_CHECK_IS_GROUP_TABLE_EMPTY = "SELECT COUNT(*) FROM groups";
    private static final String SQL_ADD_NEW_GROUP = "insert into groups (group_name) values (?)";
    private static final String SQL_COUNT_STUDENTS_BY_GROUPS = "SELECT g.group_name, COUNT(s.student_id) AS student_count\n"
            + "FROM groups g\n"
            + "LEFT JOIN students s ON g.group_id = s.group_id\n"
            + "GROUP BY g.group_id\n"
            + "HAVING COUNT(s.student_id) <=?";
    private static final String SQL_GET_GROUP_BY_ID = "select * from groups where group_id=?";
    private static final String SQL_GET_LIST_OF_GROUPS = "select * from groups";

    @Value("${sqlQuery.isExistTable}")
    private String sqlCheckIsTableExist;
    @Value("${table.group}")
    private String groupTableName;

    @Autowired
    public GroupRepo(SQLQueryOfCreateTable queryOfCreateTable, JdbcTemplate jdbcTemplate) {
        this.queryOfCreateTable = queryOfCreateTable;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CounterStudentsAtGroup> counterStudentsAtGroups(Integer count) {
        return jdbcTemplate.query(SQL_COUNT_STUDENTS_BY_GROUPS, new CountStudentAtGroupMapper(), count);
    }

    @Override
    public boolean addItem(Group group) {
        return jdbcTemplate.update(SQL_ADD_NEW_GROUP, group.getGroupName()) > 0;
    }

    @Override
    public Optional<Group> getItemByID(Group group) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_GET_GROUP_BY_ID, new GroupMapper(), group.getGroupID()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean isTableExist() {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(String.format(sqlCheckIsTableExist, groupTableName), Boolean.class));
    }

    @Override
    public void createTable() {
        jdbcTemplate.execute(queryOfCreateTable.getGroupTable());
    }

    @Override
    public boolean isTableEmpty() {
        return jdbcTemplate.queryForObject(SQL_CHECK_IS_GROUP_TABLE_EMPTY, Integer.class) == 0;
    }

    @Override
    public List<Group> getAll() {
        return jdbcTemplate.query(SQL_GET_LIST_OF_GROUPS, new GroupMapper());
    }
}
