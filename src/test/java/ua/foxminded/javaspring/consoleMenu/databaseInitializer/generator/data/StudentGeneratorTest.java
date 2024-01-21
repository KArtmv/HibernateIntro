package ua.foxminded.javaspring.consoleMenu.databaseInitializer.generator.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import ua.foxminded.javaspring.consoleMenu.databaseInitializer.generator.DataConduct;
import ua.foxminded.javaspring.consoleMenu.databaseInitializer.generator.filesourse.SourceFilesDatabaseData;
import ua.foxminded.javaspring.consoleMenu.model.Group;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.DataInitializer;
import ua.foxminded.javaspring.consoleMenu.util.MyRandom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StudentGeneratorTest {
    @Mock
    private MyRandom random;
    @Mock
    DataConduct dataConduct;
    @Mock
    SourceFilesDatabaseData resourcesFiles;
    @InjectMocks
    private StudentGenerator studentGenerator;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generate_shouldReturnListOfStudents_whenIsOk() {
        ReflectionTestUtils.setField(studentGenerator, "maxCountOfStudents", 3);

        List<String> firstNames = Arrays.asList("firstName0", "firstName1", "firstName2");
        List<String> lastNames = Arrays.asList("lastName0", "lastName1", "lastName2", "lastName3");
        List<Group> groups = new DataInitializer().groupsListInit();

        List<Student> expect = new ArrayList<>();
        expect.add(new Student(1L, "firstName0", "lastName2", new Group(2L)));
        expect.add(new Student(2L, "firstName2", "lastName1", new Group(3L)));
        expect.add(new Student(3L, "firstName1", "lastName0", new Group(1L)));

        when(resourcesFiles.getFirstNames()).thenReturn(firstNames);
        when(resourcesFiles.getLastNames()).thenReturn(lastNames);
        when(dataConduct.getGroups()).thenReturn(groups);
        when(random.getInt(firstNames.size())).thenReturn(0).thenReturn( 2).thenReturn( 1);
        when(random.getInt(lastNames.size())).thenReturn(2).thenReturn( 1).thenReturn( 0);
        when(random.getLong(groups.size())).thenReturn(2L).thenReturn( 3L).thenReturn( 1L);

        List<Student> result = studentGenerator.generate();

        assertAll(
                () -> assertThat(result).hasSize(3),
                () -> assertThat(result).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expect)
        );

        verify(dataConduct).getGroups();
        verify(dataConduct).setStudents(result);
    }
}