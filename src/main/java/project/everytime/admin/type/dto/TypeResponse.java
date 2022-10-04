package project.everytime.admin.type.dto;

import lombok.Data;
import project.everytime.admin.type.Type;

@Data
public class TypeResponse {

    private Long id;
    private String name;

    public TypeResponse(Type type) {
        this.id = type.getId();
        this.name = type.getName();
    }
}
