package ru.smartel.strike.exception;

import java.util.List;

public class BusinessRuleValidationException extends Exception {

    protected List<String> errors;

    public BusinessRuleValidationException(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
