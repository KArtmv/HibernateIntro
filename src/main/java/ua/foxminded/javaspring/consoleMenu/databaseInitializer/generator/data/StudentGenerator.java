package ua.foxminded.javaspring.consoleMenu.databaseInitializer.generator.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ua.foxminded.javaspring.consoleMenu.databaseInitializer.generator.DataConduct;
import ua.foxminded.javaspring.consoleMenu.databaseInitializer.generator.filesourse.SourceFilesDatabaseData;
import ua.foxminded.javaspring.consoleMenu.model.Group;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.util.MyRandom;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class StudentGenerator implements DataGenerator<Student> {

    private static final AtomicLong ATOMIC_LONG = new AtomicLong(1);
    @Value("${maxCountOfStudent}")
    private int maxCountOfStudents;
    private MyRandom random;
    private SourceFilesDatabaseData resourcesFiles;
    private DataConduct dataConduct;
    private int countOfGroups;
    private Map<String, Student> studentMap = new HashMap<>();

    @Autowired
    public StudentGenerator(MyRandom random, SourceFilesDatabaseData resourcesFiles, DataConduct dataConduct) {
        this.random = random;
        this.dataConduct = dataConduct;
        this.resourcesFiles = resourcesFiles;
    }

    @Override
    public List<Student> generate() {
        countOfGroups = dataConduct.getGroups().size();
        List<String> firstNames = resourcesFiles.getFirstNames();
        List<String> lastNames = resourcesFiles.getLastNames();

        int countFirstNames = firstNames.size();
        int countLastNames = lastNames.size();

        while (studentMap.size() < maxCountOfStudents) {
            String firstName = firstNames.get(random.getInt(countFirstNames));
            String lastName = lastNames.get(random.getInt(countLastNames));

            String key = firstName + lastName;
            if (!firstName.equals(lastName) && !studentMap.containsKey(key)) {
                studentMap.put(key, new Student(
                                ATOMIC_LONG.getAndIncrement(),
                                firstName,
                                lastName,
                                new Group(random.getLong(countOfGroups))));
            }
        }
        List<Student> generatedStudents = new ArrayList<>(studentMap.values());

        dataConduct.setStudents(generatedStudents);

        return generatedStudents;
    }
}
