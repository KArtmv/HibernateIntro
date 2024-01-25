package ua.foxminded.javaspring.consoleMenu.dao.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.javaspring.consoleMenu.dao.StudentDAO;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.model.StudentAtCourse;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepo implements StudentDAO {

    private final Logger LOGGER = LoggerFactory.getLogger(StudentRepo.class);

    @PersistenceContext
    private EntityManager entityManager;

    private static final String SQL_GET_LIST_COURSES_OF_STUDENT = "SELECT stc FROM StudentAtCourse stc WHERE stc.student=:student";

    @Override
    public Optional<Student> getItemByID(Student student) {
        try {
            return Optional.ofNullable(entityManager.find(Student.class, student.getStudentID()));
        } catch (IllegalArgumentException | PersistenceException e) {
            LOGGER.error("Failed to get student by ID: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public boolean removeStudent(Student student) {
        try {
            entityManager.remove(entityManager.find(Student.class, student.getStudentID()));
            return true;
        } catch (IllegalArgumentException | PersistenceException e) {
            LOGGER.error("Failed to remove student: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public List<StudentAtCourse> studentCourses(Student student) {
        TypedQuery<StudentAtCourse> query = entityManager.createQuery(SQL_GET_LIST_COURSES_OF_STUDENT, StudentAtCourse.class);
        query.setParameter("student", student);
        return query.getResultList();
    }

    @Override
    @Transactional
    public boolean addItem(Student student) {
        try {
            entityManager.persist(student);
            return true;
        } catch (PersistenceException e) {
            LOGGER.error("Failed to add student: {}", e.getMessage());
            return false;
        }
    }
}
