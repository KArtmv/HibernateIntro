package ua.foxminded.javaspring.consoleMenu.dao.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.javaspring.consoleMenu.dao.GroupDAO;
import ua.foxminded.javaspring.consoleMenu.dto.CounterStudentsAtGroup;
import ua.foxminded.javaspring.consoleMenu.model.Group;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class GroupRepo implements GroupDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupRepo.class);

    @PersistenceContext
    private EntityManager entityManager;

    private static final String GET_LIST_OF_GROUPS = "SELECT g FROM Group g";
    private static final String SQL_COUNT_STUDENTS_BY_GROUPS =
            "SELECT NEW ua.foxminded.javaspring.consoleMenu.dto.CounterStudentsAtGroup(g.groupName, COUNT(s))\n"
                    + "FROM Group g\n"
                    + "LEFT JOIN Student s ON g = s.group\n"
                    + "GROUP BY g\n"
                    + "HAVING COUNT(s) < :count";

    @Override
    public List<CounterStudentsAtGroup> counterStudentsAtGroups(Integer count) {
        Query query = entityManager.createQuery(SQL_COUNT_STUDENTS_BY_GROUPS);
        query.setParameter("count", count.longValue());
        return query.getResultList();
    }

    @Override
    @Transactional
    public boolean addItem(Group group) {
        try {
            entityManager.persist(group);
            return true;
        } catch (PersistenceException e) {
            LOGGER.error("Failed persist: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<Group> getItemByID(Group group) {
        try {
            return Optional.ofNullable(entityManager.find(Group.class, group.getGroupId()));
        } catch (PersistenceException e) {
            LOGGER.error("Failed obtain: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Group> getAll() {
        TypedQuery<Group> query = entityManager.createQuery(GET_LIST_OF_GROUPS, Group.class);
        return query.getResultList();
    }
}
