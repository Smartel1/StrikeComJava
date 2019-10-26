package ru.smartel.strike.service;

import java.util.Map;
import java.util.Optional;

public class BaseDTOValidator {

    public CheckedField check(Object field, String fieldName, Map<String, String> errors) {
        return new CheckedField(field, fieldName, errors);
    }

    public static class CheckedField {
        Object fieldValue;
        String fieldName;
        boolean presentOptional; //If this field was Optional
        Map<String, String> errorsBag;
        boolean skipChecks; //sometimes we want to skip remaining checks if one failed

        @SuppressWarnings("unchecked")
        public CheckedField(Object fieldValue, String fieldName, Map<String, String> errorsBag) {
            if (fieldValue instanceof Optional) {
                this.fieldValue = ((Optional) fieldValue).orElse(null);
                presentOptional = true;
            } else  {
                this.fieldValue = fieldValue;
            }
            this.fieldName = fieldName;
            this.errorsBag = errorsBag;
        }


        /**
         * Checks required field. Call this method only when field is Optional!
         * If this check fails, remaining checks will never be precessed
         */
        public CheckedField requiredOptional() {
            if (skipChecks) return this;

            if (!presentOptional) {
                putError("must be specified", true);
            }
            return this;
        }

        /**
         * Checks if field value is not null
         */
        public CheckedField notNull() {
            return notNull(false);
        }

        /**
         * Checks if field value is not null
         */
        public CheckedField notNull(boolean skipRemainingIfFail) {
            if (skipChecks) return this;

            if (null == fieldValue) {
                putError("must not be null", skipRemainingIfFail);
            }
            return this;
        }

        /**
         * Checks if string field has length less then or equal to number (null always valid)
         */
        public CheckedField maxLength(int length ) {
            return maxLength(length, false);
        }

        /**
         * Checks if string field has length less then or equals to number (null always valid)
         */
        public CheckedField maxLength(int length, boolean skipRemainingIfFail) {
            if (skipChecks) return this;
            if (!(fieldValue instanceof String)) return this;

            if (((String)fieldValue).length() > length) {
                putError("limited to " + length + " characters", skipRemainingIfFail);
            }
            return this;
        }

        /**
         * Checks if string field has length greater then or equals to number (null always valid)
         */
        public CheckedField minLength(int length) {
            return minLength(length, false);
        }

        /**
         * Checks if string field has length greater then or equals to number (null always valid)
         */
        public CheckedField minLength(int length, boolean skipRemainingIfFail) {
            if (skipChecks) return this;
            if (!(fieldValue instanceof String)) return this;

            if (((String)fieldValue).length() < length) {
                putError("should be at least " + length + " characters", skipRemainingIfFail);
            }
            return this;
        }

        /**
         * Checks if numeric field is less then or equals to number (null always valid)
         */
        public CheckedField max(int max) {
            return max(max, false);
        }

        /**
         * Checks if numeric field is less then or equals to number (null always valid)
         */
        public CheckedField max(int max, boolean skipRemainingIfFail) {
            if (skipChecks) return this;
            if (!(fieldValue instanceof Number)) return this;

            if (((Number)fieldValue).floatValue() > max) {
                putError("must be less then or equals to " + max, skipRemainingIfFail);
            }
            return this;
        }

        /**
         * Checks if numeric field is more then or equals to number (null always valid)
         */
        public CheckedField min(int max) {
            return min(max, false);
        }

        /**
         * Checks if numeric field is more then or equals to number (null always valid)
         */
        public CheckedField min(int min, boolean skipRemainingIfFail) {
            if (skipChecks) return this;
            if (!(fieldValue instanceof Number)) return this;

            if (((Number)fieldValue).floatValue() < min) {
                putError("must be more then or equals to " + min, skipRemainingIfFail);
            }
            return this;
        }

        /**
         * Put error message to Map. Add message if any message is present already
         */
        protected void putError(String message, boolean skipRemainingChecks) {
            errorsBag.computeIfPresent(fieldName, (key, oldValue) -> oldValue + ", " + message);
            errorsBag.putIfAbsent(fieldName, message);
            this.skipChecks = skipRemainingChecks;
        }
    }
}
