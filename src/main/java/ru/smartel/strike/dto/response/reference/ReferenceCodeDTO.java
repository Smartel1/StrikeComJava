package ru.smartel.strike.dto.response.reference;

import ru.smartel.strike.entity.interfaces.ReferenceWithCode;

public class ReferenceCodeDTO {

    private int id;
    private String code;

    public static ReferenceCodeDTO from(ReferenceWithCode entity) {
        ReferenceCodeDTO instance = new ReferenceCodeDTO();
        instance.setId(entity.getId());
        instance.setCode(entity.getCode());
        return instance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
