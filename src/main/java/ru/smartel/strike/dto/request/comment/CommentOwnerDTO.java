package ru.smartel.strike.dto.request.comment;

import ru.smartel.strike.entity.interfaces.HasComments;

public class CommentOwnerDTO<T extends HasComments> {
    private int id;
    private Class<T> clazz;

    public CommentOwnerDTO(int id, Class<T> clazz) {
        this.id = id;
        this.clazz = clazz;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }
}
