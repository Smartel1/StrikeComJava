package ru.smartel.strike.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class Handler {

    @ExceptionHandler(value = {UnauthtenticatedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    protected String handleConflict(Exception ex, WebRequest request) {
        return "Проблемы с аутентификацией: " + ex.getMessage();
    }

    @ExceptionHandler(DTOValidationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public Map<String, String> processJsonSchemaValidationException(DTOValidationException ex) {
        return ex.getErrors();
    }

    @ExceptionHandler(BusinessRuleValidationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public Map<String, List<String>> processBusinessRuleValidationError(BusinessRuleValidationException ex) {
        return Collections.singletonMap("error", ex.getErrors());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Map<String, String> processEntityNotFoundException(EntityNotFoundException ex) {
        return Collections.singletonMap("error", ex.getMessage());
    }
}