package ru.smartel.strike.exception;

import java.util.LinkedList;
import java.util.List;

public class BusinessRuleValidationException extends Exception {

    protected List<String> errors;

    public BusinessRuleValidationException(String error) {
        this.errors = new LinkedList<>();
        errors.add(error);
    }

    public BusinessRuleValidationException(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
