package ua.foxminded.javaspring.consoleMenu.model;

public class Course {

    private Long courseID;
    private String courseName;
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
