package ua.foxminded.javaspring.consoleMenu.databaseInitializer.tables;

import org.springframework.beans.factory.annotation.Autowired;
import ua.foxminded.javaspring.consoleMenu.dao.TablesDAO;
import ua.foxminded.javaspring.consoleMenu.databaseInitializer.generator.data.DataGenerator;

import java.util.List;

public class TableInitializer<T> {

    private TablesDAO<T> dao;
    private DataGenerator<T> generateItems;

    @Autowired
    public TableInitializer(TablesDAO<T> dao, DataGenerator<T> generateItems) {
        this.dao = dao;
        this.generateItems = generateItems;
    }

    public void initialize() {
        if (dao.isTableExist()) {
            toFillIfEmpty();
        } else {
            dao.createTable();
            toFillTable();
        }
    }

    private void toFillIfEmpty() {
        if (dao.isTableEmpty()) {
            toFillTable();
        }
    }

    private void toFillTable() {
        List<T> items = generateData();
        items.forEach(dao::addItem);
    }

    private List<T> generateData() {
        return generateItems.generate();
    }
}
