package project.everytime.admin.major.dto;

import lombok.Data;
import project.everytime.admin.major.Major;

@Data
public class MajorResponse {

    private Long id;
    private String name;
    private int order;
    private Long parentId;

    public MajorResponse(Major major) {
        this.id = major.getId();
        this.name = major.getName();
        this.order = major.getOrder();
        this.parentId = major.getParent().getId();
    }
}
