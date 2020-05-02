package ru.smartel.strike.dto.exception;

import java.util.Collections;
import java.util.List;

public class ApiErrorDTO {
    private String message;
    private List<String> errors;

    public ApiErrorDTO(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }

    public ApiErrorDTO(String message, String error) {
        this.message = message;
        errors = Collections.singletonList(error);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
