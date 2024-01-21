package ua.foxminded.javaspring.consoleMenu.databaseInitializer.generator.data;

import java.util.List;

public interface DataGenerator<T> {
    List<T> generate();
}
