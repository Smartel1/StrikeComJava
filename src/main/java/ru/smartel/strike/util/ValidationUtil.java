package ru.smartel.strike.util;

import ru.smartel.strike.exception.ValidationException;

import javax.validation.ConstraintViolation;
import java.util.*;

public class ValidationUtil {

    //no need to instantiate this util class
    private ValidationUtil() {}

    public static void addErrorMessage(String field, ErrorMessage errorType, Map<String, List<String>> errorsCollection) {
        if (errorsCollection.containsKey(field)) {
            errorsCollection.get(field).add(errorType.toString());
        } else {
            List<String> errors = new ArrayList<>();
            errors.add(errorType.toString());
            errorsCollection.put(field, errors);
        }
    }

    public static void throwIfErrorsExist(Map<String, List<String>> errorsCollection) throws ValidationException {
        if (!errorsCollection.isEmpty()) {
            throw new ValidationException(errorsCollection);
        }
    }

    public static void throwIfErrorsExist(Set<ConstraintViolation<Object>> validates) throws ValidationException {
        throwIfErrorsExist(convertSetErrorsToMap(validates));
    }

    private static <T> Map<String, List<String>> convertSetErrorsToMap(Set<ConstraintViolation<T>> validates) {
        Map<String, List<String>> result = new HashMap<>();

        validates.forEach(error -> result.put(
                error.getPropertyPath().toString(),
                Arrays.asList(error.getMessage())
        ));

        return result;
    }

    public interface ErrorMessage {
        String toString();
    }

    public static class NotNull implements ErrorMessage {
        @Override
        public String toString() {
            return "must not be null";
        }
    }

    public static class Required implements ErrorMessage {
        @Override
        public String toString() {
            return "must be present";
        }
    }

    public static class OneOf<T> implements ErrorMessage {
        Collection<T> availableItems;

        public OneOf(Collection<T> availableItems) {
            this.availableItems = availableItems;
        }

        @Override
        public String toString() {
            return "must be one of: " + availableItems.toString();
        }
    }

    public static class Min implements ErrorMessage {
        private int min;

        public Min(int min) {
            this.min = min;
        }

        @Override
        public String toString() {
            return "must be longer/more than " + min;
        }
    }

    public static class Max implements ErrorMessage {
        private int max;

        public Max(int max) {
            this.max = max;
        }

        @Override
        public String toString() {
            return "must be shorter/less than " + max;
        }
    }
}
