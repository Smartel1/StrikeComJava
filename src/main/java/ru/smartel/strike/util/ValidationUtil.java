package ru.smartel.strike.util;

import ru.smartel.strike.exception.ValidationException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ValidationUtil {

    //no need to instantiate this util class
    private ValidationUtil() {}

    public static void addErrorMessage(String field, String errorMessage, Map<String, List<String>> errorsCollection) {
        if (errorsCollection.containsKey(field)) {
            errorsCollection.get(field).add(errorMessage);
        } else {
            List<String> errors = new ArrayList<>();
            errors.add(errorMessage);
            errorsCollection.put(field, errors);
        }
    }

    public static void throwIfErrorsExist(Map<String, List<String>> errorsCollection) throws ValidationException {
        if (!errorsCollection.isEmpty()) {
            throw new ValidationException(errorsCollection);
        }
    }

    public static String notNull() {
        return "must not be null";
    }

    public static String required() {
        return "must be present";
    }

    public static String oneOf(Collection<?> availableItems) {
        return "must be one of: " + availableItems.toString();
    }

    public static String min(int min) {
        return "must be longer/more than " + min;
    }

    public static String max(int max) {
        return "must be shorter/less than " + max;
    }

    public static String numericCollection() {
        return "must contain only numeric elements or nulls";
    }
}
