package project.everytime.client.lecturereview;

public enum Attendance {
    MULTI("복합적"), DIRECT("직접호명"), SEAT("지정좌석"), DIGIT("전자출결"), NONE("반영안함");

    private final String description;

    Attendance(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
