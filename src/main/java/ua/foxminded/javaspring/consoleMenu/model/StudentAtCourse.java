package ua.foxminded.javaspring.consoleMenu.model;

import javax.persistence.*;

@Entity
@Table(name = "studenttocourse",
uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"}))
public class StudentAtCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private Long enrollmentID;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "course_id")
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
