package ru.smartel.strike.util;

import ru.smartel.strike.exception.ValidationException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ValidationUtil {

    //no need to instantiate this util class
    private ValidationUtil() {}

    public static void addErrorMessage(String field, ErrorMessage errorType, Map<String, List<String>> errorsCollection) {
        if (errorsCollection.containsKey(field)) {
            errorsCollection.get(field).add(errorType.toString());
        } else {
            errorsCollection.put(field, Arrays.asList(errorType.toString()));
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
