package ru.smartel.strike.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.smartel.strike.dto.exception.ApiErrorDTO;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.stream.Collectors;

@ControllerAdvice
public class Handler {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ApiErrorDTO processJsonSchemaValidationException(ValidationException ex) {
        return new ApiErrorDTO("Validation failed", ex.getErrors().entrySet().stream()
                .map((entry)-> entry.getKey() + ": " + String.join(",", entry.getValue()))
                .collect(Collectors.toList()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiErrorDTO processEntityNotFoundException(EntityNotFoundException ex) {
        return new ApiErrorDTO("Entity not found", ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ApiErrorDTO processException(AccessDeniedException ex) {
        return new ApiErrorDTO("Forbidden", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiErrorDTO processException(Exception ex) {
        return new ApiErrorDTO("Server error (sorry)", Collections.emptyList());
    }
}