package ua.foxminded.javaspring.consoleMenu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import ua.foxminded.javaspring.consoleMenu.util.ReadResourcesFile;
import ua.foxminded.javaspring.consoleMenu.databaseInitializer.generator.DataConduct;
import ua.foxminded.javaspring.consoleMenu.databaseInitializer.generator.data.*;
import ua.foxminded.javaspring.consoleMenu.databaseInitializer.generator.filesourse.SourceFilesDatabaseData;
import ua.foxminded.javaspring.consoleMenu.model.Course;
import ua.foxminded.javaspring.consoleMenu.model.Group;
import ua.foxminded.javaspring.consoleMenu.model.Student;
import ua.foxminded.javaspring.consoleMenu.model.StudentAtCourse;
import ua.foxminded.javaspring.consoleMenu.util.MyRandom;

@Component
@PropertySource("classpath:amountLimits.properties")
public class DataConfigInitializer {

    @Bean
    public ReadResourcesFile readFile(ResourceLoader resourceLoader) {
        return new ReadResourcesFile(resourceLoader);
    }

    @Bean
    public DataConduct dataConduct() {
        return new DataConduct();
    }

    @Bean
    public DataGenerator<Student> studentGenerator(SourceFilesDatabaseData resourcesFiles, DataConduct dataConduct, MyRandom random) {
        return new StudentGenerator(random, resourcesFiles, dataConduct);
    }

    @Bean
    public DataGenerator<Course> courseGenerator(SourceFilesDatabaseData resourcesFiles, DataConduct dataConduct) {
        return new CourseGenerator(resourcesFiles, dataConduct);
    }

    @Bean
    public DataGenerator<Group> groupGenerator(SourceFilesDatabaseData resourcesFiles, DataConduct dataConduct) {
        return new GroupGenerator(resourcesFiles, dataConduct);
    }

    @Bean
    public DataGenerator<StudentAtCourse> studentToCourseGenerator(DataConduct dataConduct, MyRandom random) {
        return new StudentToCourseGenerator(dataConduct, random);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public MyRandom random(){
        return new MyRandom();
    }
}