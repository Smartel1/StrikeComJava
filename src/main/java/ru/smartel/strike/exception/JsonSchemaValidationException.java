package ru.smartel.strike.exception;

import java.util.Map;

public class JsonSchemaValidationException extends Exception {
    private Map<String, String> errors;

    public JsonSchemaValidationException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
