package ru.smartel.strike.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
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
    private static final Logger logger = LoggerFactory.getLogger(Handler.class);

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ApiErrorDTO processJsonSchemaValidationException(ValidationException ex) {
        logger.warn("ValidationException occurred", ex);
        return new ApiErrorDTO("Validation failed", ex.getErrors().entrySet().stream()
                .map((entry)-> entry.getKey() + ": " + String.join(",", entry.getValue()))
                .collect(Collectors.toList()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiErrorDTO processEntityNotFoundException(EntityNotFoundException ex) {
        logger.warn("EntityNotFoundException occurred", ex);
        return new ApiErrorDTO("Entity not found", ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ApiErrorDTO processException(AccessDeniedException ex) {
        logger.warn("AccessDeniedException occurred", ex);
        return new ApiErrorDTO("Forbidden", ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ApiErrorDTO processException(DataIntegrityViolationException ex) {
        if (null != ex.getCause() && ex.getCause() instanceof ConstraintViolationException) {
            if (null != ex.getCause().getCause() && ex.getCause().getCause() instanceof PSQLException) {
                String message = ex.getCause().getCause().getMessage();
                logger.warn("Constraint violated: {}", message);
                return new ApiErrorDTO("Constraint violated", message);
            }
        }
        logger.warn("Exception occurred", ex);
        return new ApiErrorDTO("Server error (sorry)", Collections.emptyList());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiErrorDTO processException(Exception ex) {
        logger.warn("Exception occurred", ex);
        return new ApiErrorDTO("Server error (sorry)", Collections.emptyList());
    }
}