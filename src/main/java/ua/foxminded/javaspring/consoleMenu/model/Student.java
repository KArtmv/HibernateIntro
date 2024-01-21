package ua.foxminded.javaspring.consoleMenu.model;

public class Student {

    private Long studentID;
    private String firstName;
    private String lastName;
    private Group group;

    public Student() {
    }

    public Student(Long studentID) {
        this.studentID = studentID;
    }

    public Student(String firstName, String lastName, Group group) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
    }

    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(Long studentID, String firstName, String lastName) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(Long studentID, String firstName, String lastName, Group group) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
    }

    public Long getStudentID() {
        return studentID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setStudentID(Long studentID) {
        this.studentID = studentID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
