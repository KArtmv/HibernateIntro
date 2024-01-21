package ua.foxminded.javaspring.consoleMenu.databaseInitializer.generator.data;

import org.springframework.beans.factory.annotation.Autowired;
import ua.foxminded.javaspring.consoleMenu.databaseInitializer.generator.DataConduct;
import ua.foxminded.javaspring.consoleMenu.databaseInitializer.generator.filesourse.SourceFilesDatabaseData;
import ua.foxminded.javaspring.consoleMenu.model.Course;

import java.util.List;
import java.util.stream.Collectors;

public class CourseGenerator implements DataGenerator<Course> {

    private SourceFilesDatabaseData resourcesFiles;
    private DataConduct dataConduct;

    @Autowired
    public CourseGenerator(SourceFilesDatabaseData resourcesFiles, DataConduct dataConduct) {
        this.resourcesFiles = resourcesFiles;
        this.dataConduct = dataConduct;
    }

    @Override
    public List<Course> generate() {
        List<String> sourceResult = resourcesFiles.getCourses();

        List<Course> courses = sourceResult.stream().map(s -> {
            String[] courseData = s.split("_");
            return new Course(courseData[0], courseData[1]);
        }).collect(Collectors.toList());

        dataConduct.setCourses(courses);
        return courses;
    }
}
