package ua.foxminded.javaspring.consoleMenu;

import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class TestDatabaseConfiguration {

    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.1")
            .withDatabaseName("serviceLayerDB")
            .withUsername("admin")
            .withPassword("admin")
            .withInitScript("sql/dropTables.sql")
            .withReuse(true);

    static {
        postgreSQLContainer.start();
    }

    @BeforeAll
    static void beforeAll() {
        System.setProperty("spring.datasource.url", postgreSQLContainer.getJdbcUrl());
        System.setProperty("spring.datasource.password", postgreSQLContainer.getPassword());
        System.setProperty("spring.datasource.username", postgreSQLContainer.getUsername());
    }
}



