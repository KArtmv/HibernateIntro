package ua.foxminded.javaspring.consoleMenu.dao;

import java.util.Optional;

public interface DAO<T> {
    boolean addItem(T item);

    Optional<T> getItemByID(T t);
}
