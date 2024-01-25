package ua.foxminded.javaspring.consoleMenu.dao.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.javaspring.consoleMenu.dao.StudentAtCourseDAO;
import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.model.StudentAtCourse;
import ua.foxminded.javaspring.consoleMenu.rowmapper.StudentAtCourseMapper;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentAtCourseRepo implements StudentAtCourseDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentAtCourseRepo.class);

    @PersistenceContext
    private EntityManager entityManager;

    private static final String SQL_REMOVE_STUDENT_FROM_ALL_THEIR_COURSES = "DELETE FROM StudentAtCourse where student=:student";
    private static final String SQL_GET_ALL_STUDENT_FROM_COURSE = "SELECT stc FROM StudentAtCourse stc WHERE stc.course=:course";

    @Override
    public List<StudentAtCourse> allStudentsFromCourse(Course course) {
        TypedQuery<StudentAtCourse> query = entityManager.createQuery(SQL_GET_ALL_STUDENT_FROM_COURSE, StudentAtCourse.class);
        query.setParameter("course", course);
        return query.getResultList();
    }

    @Override
    @Transactional
    public boolean addItem(StudentAtCourse studentAtCourse) {
        try {
            entityManager.persist(studentAtCourse);
            return true;
        } catch (PersistenceException e) {
            LOGGER.error("Failed to persist: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<StudentAtCourse> getItemByID(StudentAtCourse studentAtCourse) {
        try {
            return Optional.ofNullable(entityManager.find(StudentAtCourse.class, studentAtCourse.getEnrollmentID()));
        } catch (IllegalArgumentException | PersistenceException e) {
            LOGGER.error("Failed to obtain: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public boolean removeStudentFromCourse(StudentAtCourse studentAtCourse) {
        try {
            entityManager.remove(entityManager.find(StudentAtCourse.class, studentAtCourse.getEnrollmentID()));
            return true;
        } catch (IllegalArgumentException | PersistenceException e) {
            LOGGER.error("Failed to remove student from course: {}", e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public boolean removeStudentFromAllTheirCourses(Student student) {
        try {
            Query query = entityManager.createQuery(SQL_REMOVE_STUDENT_FROM_ALL_THEIR_COURSES);
            query.setParameter("student", student);
            entityManager.remove(entityManager.find(StudentAtCourse.class, student.getStudentID()));
            query.executeUpdate();
            return true;
        } catch (IllegalArgumentException | PersistenceException e) {
            LOGGER.error("Failed to remove student from all their courses: {}", e.getMessage());
            return false;
        }
    }
}
