package ru.smartel.strike.util;

import ru.smartel.strike.exception.DTOValidationException;

import java.util.Map;

public class ValidationUtil {

    public static void addErrorMessage(String field, ErrorMessage errorType, Map<String, String> errorsCollection) {
        errorsCollection.computeIfPresent(field, (key, oldValue) -> oldValue + ", " + errorType.toString());
        errorsCollection.putIfAbsent(field, errorType.toString());
    }

    public static void throwIfErrorsExist(Map<String, String> errorsCollection) throws DTOValidationException {
        if (!errorsCollection.isEmpty()) {
            throw new DTOValidationException("validation errors", errorsCollection);
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
