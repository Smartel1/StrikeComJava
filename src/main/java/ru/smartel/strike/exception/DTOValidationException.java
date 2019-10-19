package ru.smartel.strike.exception;

import java.util.Map;

public class DTOValidationException extends Exception {
    private Map<String, String> errors;

    public DTOValidationException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
