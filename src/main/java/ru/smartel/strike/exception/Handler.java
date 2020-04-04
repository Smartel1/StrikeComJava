package ru.smartel.strike.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.exsio.nestedj.ex.InvalidNodesHierarchyException;
import ru.smartel.strike.dto.exception.ApiErrorDTO;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;

import static java.util.stream.Collectors.toList;

@ControllerAdvice
public class Handler {
    private static final Logger logger = LoggerFactory.getLogger(Handler.class);

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ApiErrorDTO processJsonSchemaValidationException(ValidationException ex) {
        logger.info("ValidationException occurred: {}", ex.getErrors());
        return new ApiErrorDTO("Validation failed", ex.getErrors().entrySet().stream()
                .map((entry)-> entry.getKey() + ": " + String.join(",", entry.getValue()))
                .collect(toList()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiErrorDTO processEntityNotFoundException(EntityNotFoundException ex) {
        logger.info("EntityNotFoundException occurred: {}", ex.getMessage());
        return new ApiErrorDTO("Entity not found", ex.getMessage());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiErrorDTO processBindException(BindException ex) {
        logger.warn("BindException occurred: {}", ex.getMessage());
        return new ApiErrorDTO("Binding failed",
                ex.getBindingResult().getFieldErrors().stream()
                        .map(err -> "binding to '" + err.getField() + "' failed, rejected value: " + err.getRejectedValue())
                        .collect(toList()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ApiErrorDTO processException(AccessDeniedException ex) {
        logger.warn("AccessDeniedException occurred");
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
        logger.warn("Data violation exception occurred: {}", ex.getMessage());
        return new ApiErrorDTO("Data violation exception", ex.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public ApiErrorDTO processMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        logger.warn("Not supported method", ex);
        return new ApiErrorDTO("Request method not supported", Collections.emptyList());
    }

    @ExceptionHandler(InvalidNodesHierarchyException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ApiErrorDTO processMethodNotSupportedException(InvalidNodesHierarchyException ex) {
        logger.warn("Nodes hierarchy error: {}", ex.getMessage());
        return new ApiErrorDTO("Nodes hierarchy error", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiErrorDTO processException(Exception ex) {
        logger.warn("Exception occurred", ex);
        return new ApiErrorDTO("Server error (sorry)", Collections.emptyList());
    }
}