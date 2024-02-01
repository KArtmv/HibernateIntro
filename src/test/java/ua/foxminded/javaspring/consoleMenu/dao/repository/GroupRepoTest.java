package ua.foxminded.javaspring.consoleMenu.dao.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import ua.foxminded.javaspring.consoleMenu.dao.GroupDAO;
import ua.foxminded.javaspring.consoleMenu.model.Group;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {GroupDAO.class}))
@ActiveProfiles("test")
@Sql({"/sql/group/groupTable.sql", "/sql/group/groupData.sql"})
class GroupRepoTest {

    @Autowired
    private GroupDAO groupDAO;

    @Test
    @SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
    @Sql(statements = "INSERT INTO groups (group_name) values ('YS-28');")
    void getById_shouldReturnGroup_whenGivenIdIsExist(){
            assertThat(groupDAO.getItemByID(4L).get())
                    .usingRecursiveComparison()
                    .isEqualTo(new Group(4L, "YS-28"));
    }

    @Test
    void getById_shouldReturnOptionalEmpty_whenGivenIdIsNotExist(){
        assertThat(groupDAO.getItemByID(4L)).isNotPresent();
    }

    @Test
    void getAll_shouldReturnListOfAvailableGroups_whenItRequest(){
        assertThat(groupDAO.getAll()).hasSize(3);
    }

    @Test
    void addItem_shouldSaveInDatabaseNewGroup_whenItRun(){
        Group group = new Group("YS-28");

        assertAll(() -> {
            assertThat(groupDAO.addItem(group)).isTrue();

            List<Group> courses = groupDAO.getAll();
            assertThat(courses).hasSize(4);
            assertThat(courses.get(3)).usingRecursiveComparison().isEqualTo(new Group(4L, group.getGroupName()));
        });
    }
}