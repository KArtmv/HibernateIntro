package ua.foxminded.javaspring.consoleMenu.databaseInitializer.generator.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ua.foxminded.javaspring.consoleMenu.databaseInitializer.generator.DataConduct;
import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.model.StudentAtCourse;
import ua.foxminded.javaspring.consoleMenu.util.MyRandom;

import java.util.*;

public class StudentToCourseGenerator implements DataGenerator<StudentAtCourse> {
    @Value("${maxCountCoursesOfStudent}")
    private int maxCountCoursesOfStudent;
    private int countCourses;
    private DataConduct dataConduct;
    private MyRandom random;
    private Set<Long> INDICES_COURSES_OF_STUDENT = new HashSet<>();
    private List<StudentAtCourse> studentAtCourses = new ArrayList<>();

    @Autowired
    public StudentToCourseGenerator(DataConduct dataConduct, MyRandom random) {
        this.dataConduct = dataConduct;
        this.random = random;
    }

    @Override
    public List<StudentAtCourse> generate() {
        countCourses = dataConduct.getCourses().size();
        List<Student> students = dataConduct.getStudents();
        students.forEach(this::addToCourseByIndex);

        return studentAtCourses;
    }

    private void addToCourseByIndex(Student student) {
        getRandomlyCoursesIndex();

        INDICES_COURSES_OF_STUDENT.forEach(index ->
                studentAtCourses.add(new StudentAtCourse(student, new Course(index))));

        INDICES_COURSES_OF_STUDENT.clear();
    }

    private void getRandomlyCoursesIndex() {
        int amountStudentCourses = random.getInt(maxCountCoursesOfStudent + 1);

        while (INDICES_COURSES_OF_STUDENT.size() < amountStudentCourses) {
            INDICES_COURSES_OF_STUDENT.add(random.getLong(countCourses));
        }
    }
}
