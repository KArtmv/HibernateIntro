package ua.foxminded.javaspring.consoleMenu.dao;

import ua.foxminded.javaspring.consoleMenu.model.Course;

import java.util.List;

public interface CourseDAO extends DAO<Course> {
    List<Course> getAll();
}
