package ua.foxminded.javaspring.consoleMenu.dao;

public interface TablesDAO<T> extends DAO<T> {
    boolean isTableExist();

    void createTable();

    boolean isTableEmpty();
}
