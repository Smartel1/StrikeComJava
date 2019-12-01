package ru.smartel.strike.dto.response;

public class DetailWrapperDTO <T> {
    private T data;

    public DetailWrapperDTO(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
