package ua.foxminded.javaspring.consoleMenu.model;

public class StudentAtCourse {

    private Long enrollmentID;
    private Student student;
    private Course course;

    public StudentAtCourse(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    public StudentAtCourse(Long enrollmentID, Student student, Course course) {
        this.enrollmentID = enrollmentID;
        this.student = student;
        this.course = course;
    }

    public StudentAtCourse(Long enrollmentID) {
        this.enrollmentID = enrollmentID;
    }

    public StudentAtCourse(Long enrollmentID, Student student) {
        this.enrollmentID = enrollmentID;
        this.student = student;
    }

    public StudentAtCourse() {}

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public Long getEnrollmentID() {
        return enrollmentID;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setEnrollmentID(Long enrollmentID) {
        this.enrollmentID = enrollmentID;
    }
}
