package ua.foxminded.javaspring.consoleMenu.databaseInitializer.tables;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.foxminded.javaspring.consoleMenu.dao.TablesDAO;

import ua.foxminded.javaspring.consoleMenu.databaseInitializer.generator.data.DataGenerator;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.DataInitializer;

import java.util.List;

import static org.mockito.Mockito.*;

class StudentInitializerTest {
    @Mock
    private TablesDAO<Student> dao;
    @Mock
    private DataGenerator<Student> generateItems;

    @InjectMocks
    private TableInitializer<Student> initializer;

    private DataInitializer dataInitializer = new DataInitializer();

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void initialize_shouldCreateCourseAndInsertIntoDatabaseTable_whenGroupTableExist() {
        List<Student> students = dataInitializer.studentsListInit();

        when(dao.isTableExist()).thenReturn(true);
        when(dao.isTableEmpty()).thenReturn(true);
        when(generateItems.generate()).thenReturn(students);

        initializer.initialize();

        verify(dao).isTableExist();
        verify(dao).isTableEmpty();
        verify(generateItems).generate();
        verify(dao, times(students.size())).addItem(any(Student.class));
    }

    @Test
    void initialize_shouldCreateTableCourseAndInsertIntoDatabaseTable_whenGroupTableNotExist() {
        List<Student> students = dataInitializer.studentsListInit();

        when(dao.isTableExist()).thenReturn(false);
        when(generateItems.generate()).thenReturn(students);

        initializer.initialize();

        verify(dao).isTableExist();
        verify(generateItems).generate();
        verify(dao, times(students.size())).addItem(any(Student.class));
    }
}
