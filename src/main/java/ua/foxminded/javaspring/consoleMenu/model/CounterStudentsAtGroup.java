package ua.foxminded.javaspring.consoleMenu.model;

public class CounterStudentsAtGroup {

    private Integer studentsCount;
    private String groupName;

    public CounterStudentsAtGroup(Integer studentsCount, String groupName) {
        this.studentsCount = studentsCount;
        this.groupName = groupName;
    }

    public Integer getStudentsCount() {
        return studentsCount;
    }

    public String getGroupName() {
        return groupName;
    }
}
