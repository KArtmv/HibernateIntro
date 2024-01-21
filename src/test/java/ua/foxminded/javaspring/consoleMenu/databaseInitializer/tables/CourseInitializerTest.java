package ua.foxminded.javaspring.consoleMenu.databaseInitializer.tables;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.foxminded.javaspring.consoleMenu.dao.TablesDAO;
import ua.foxminded.javaspring.consoleMenu.databaseInitializer.generator.data.DataGenerator;
import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.DataInitializer;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CourseInitializerTest {
    @Mock
    private TablesDAO<Course> dao;
    @Mock
    private DataGenerator<Course> generateItems;

    @InjectMocks
    private TableInitializer<Course> initializer;
    private DataInitializer dataInitializer = new DataInitializer();

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void initialize_shouldCreateCourseAndInsertIntoDatabaseTable_whenCourseTableExistAndEmpty() {
        List<Course> courses = dataInitializer.coursesListInit();

        when(dao.isTableExist()).thenReturn(true);
        when(dao.isTableEmpty()).thenReturn(true);
        when(generateItems.generate()).thenReturn(courses);

        initializer.initialize();

        verify(dao).isTableExist();
        verify(dao).isTableEmpty();
        verify(generateItems).generate();
        verify(dao, times(courses.size())).addItem(any(Course.class));
    }

    @Test
    void initialize_shouldCreateTableCourseAndInsertIntoDatabaseTable_whenCourseTableNotExist() {
        List<Course> courses = dataInitializer.coursesListInit();

        when(dao.isTableExist()).thenReturn(false);
        when(generateItems.generate()).thenReturn(courses);

        initializer.initialize();

        verify(dao).isTableExist();
        verify(generateItems).generate();
        verify(dao, times(courses.size())).addItem(any(Course.class));
    }
}
