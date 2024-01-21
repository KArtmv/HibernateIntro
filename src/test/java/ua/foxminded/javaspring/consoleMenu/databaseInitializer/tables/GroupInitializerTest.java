package ua.foxminded.javaspring.consoleMenu.databaseInitializer.tables;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.foxminded.javaspring.consoleMenu.dao.TablesDAO;
import ua.foxminded.javaspring.consoleMenu.databaseInitializer.generator.data.DataGenerator;
import ua.foxminded.javaspring.consoleMenu.model.Group;
import ua.foxminded.javaspring.consoleMenu.DataInitializer;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GroupInitializerTest {

    @Mock
    private TablesDAO<Group> dao;
    @Mock
    private DataGenerator<Group> generateItems;

    @InjectMocks
    private TableInitializer<Group> initializer;
    private DataInitializer dataInitializer = new DataInitializer();

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void initialize_shouldCreateCourseAndInsertIntoDatabaseTable_whenGroupTableExist() {
        List<Group> groups = dataInitializer.groupsListInit();

        when(dao.isTableExist()).thenReturn(true);
        when(dao.isTableEmpty()).thenReturn(true);
        when(generateItems.generate()).thenReturn(groups);

        initializer.initialize();

        verify(dao).isTableExist();
        verify(dao).isTableEmpty();
        verify(generateItems).generate();
        verify(dao, times(groups.size())).addItem(any(Group.class));
    }

    @Test
    void initialize_shouldCreateTableCourseAndInsertIntoDatabaseTable_whenGroupTableNotExist() {
        List<Group> groups = dataInitializer.groupsListInit();

        when(dao.isTableExist()).thenReturn(false);
        when(generateItems.generate()).thenReturn(groups);

        initializer.initialize();

        verify(dao).isTableExist();
        verify(generateItems).generate();
        verify(dao, times(groups.size())).addItem(any(Group.class));
    }
}
