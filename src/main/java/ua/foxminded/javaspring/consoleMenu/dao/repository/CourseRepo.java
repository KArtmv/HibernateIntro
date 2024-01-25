package ua.foxminded.javaspring.consoleMenu.dao.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ua.foxminded.javaspring.consoleMenu.dao.CourseDAO;
import ua.foxminded.javaspring.consoleMenu.model.Course;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class CourseRepo implements CourseDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseRepo.class);

    @PersistenceContext
    private EntityManager entityManager;

    private static final String GET_LIST_OF_COURSE = "SELECT c FROM Course c";

    @Override
    @Transactional
    public boolean addItem(Course course) {
        try {
            entityManager.persist(course);
            return true;
        } catch (PersistenceException e) {
            LOGGER.error("Failed persist: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<Course> getItemByID(Course course) {
        try {
            return Optional.of(entityManager.find(Course.class, course.getCourseID()));
        } catch (PersistenceException e) {
            LOGGER.error("Failed obtain: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Course> getAll() {
        TypedQuery<Course> query = entityManager.createQuery(GET_LIST_OF_COURSE, Course.class);
        return query.getResultList();
    }
}
