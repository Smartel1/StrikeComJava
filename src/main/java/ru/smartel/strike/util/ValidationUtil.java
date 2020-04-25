package ru.smartel.strike.util;

import ru.smartel.strike.exception.ValidationException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
