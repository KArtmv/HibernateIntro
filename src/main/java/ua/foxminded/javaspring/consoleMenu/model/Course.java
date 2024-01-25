package ua.foxminded.javaspring.consoleMenu.model;

import javax.persistence.*;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseID;
    @Column(nullable = false, length = 50)
    private String courseName;
    @Column(nullable = false, length = 100)
    private String courseDescription;

    public Course(){
    }

    public Course(Long courseID) {
        this.courseID = courseID;
    }

    public Course(String courseName, String courseDescription) {
        this.courseName = courseName;
        this.courseDescription = courseDescription;
    }

    public Course(Long courseID, String courseName, String courseDescription) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
    }

    public Long getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseID(Long courseID) {
        this.courseID = courseID;
    }
}
