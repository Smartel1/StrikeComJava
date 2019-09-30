package ru.smartel.strike.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.smartel.strike.exception.BusinessRuleValidationException;
import ru.smartel.strike.exception.JsonSchemaValidationException;
import ru.smartel.strike.exception.UnauthtenticatedException;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@ControllerAdvice
public class Handler {

    @ExceptionHandler(value = {UnauthtenticatedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    protected String handleConflict(Exception ex, WebRequest request) {
        return "Проблемы с аутентификацией: " + ex.getMessage();
    }

    @ExceptionHandler(JsonSchemaValidationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public Map<String, String> processJsonSchemaValidationException(JsonSchemaValidationException ex) {
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