package ua.foxminded.javaspring.consoleMenu.databaseInitializer.generator.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import ua.foxminded.javaspring.consoleMenu.databaseInitializer.generator.DataConduct;
import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.model.StudentAtCourse;
import ua.foxminded.javaspring.consoleMenu.DataInitializer;
import ua.foxminded.javaspring.consoleMenu.util.MyRandom;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

class StudentToCourseGeneratorTest {
    @Mock
    DataConduct dataConduct;
    @Mock
    private MyRandom random;
    @InjectMocks
    private StudentToCourseGenerator studentToCourseGenerator;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generate_shouldReturnListOfStudentAtCourse_whenIsCorrect() {
        ReflectionTestUtils.setField(studentToCourseGenerator, "maxCountCoursesOfStudent", 3);

        DataInitializer dataInitializer = new DataInitializer();
        List<Student> students =  dataInitializer.studentsListInit();
        List<Course> courses = dataInitializer.coursesListInit();

        List<StudentAtCourse> expect = new ArrayList<>();
        expect.add(new StudentAtCourse(students.get(0), new Course(1L)));
        expect.add(new StudentAtCourse(students.get(0), new Course(2L)));
        expect.add(new StudentAtCourse(students.get(1), new Course(1L)));
        expect.add(new StudentAtCourse(students.get(2), new Course(1L)));
        expect.add(new StudentAtCourse(students.get(2), new Course(2L)));
        expect.add(new StudentAtCourse(students.get(2), new Course(3L)));

        when(dataConduct.getCourses()).thenReturn(courses);
        when(dataConduct.getStudents()).thenReturn(students);
        when(random.getInt(anyInt())).thenReturn(2).thenReturn(1).thenReturn(3);
        when(random.getLong(anyInt())).thenReturn(1L, 2L).thenReturn(1L).thenReturn(1L, 2L, 3L);

        List<StudentAtCourse> result = studentToCourseGenerator.generate();
        
        assertAll(
        		() -> assertThat(result).hasSize(6),
        		() -> assertThat(result).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expect)
        		);

        verify(dataConduct).getCourses();
        verify(dataConduct).getStudents();
        verify(random, times(3)).getInt(anyInt());
        verify(random, times(6)).getLong(courses.size());
    }
}
