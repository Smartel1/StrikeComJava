package ru.smartel.strike.exception;

public class UnauthtenticatedException extends Exception {

    public UnauthtenticatedException() {
        super();
    }
    public UnauthtenticatedException(String message) {
        super(message);
    }
}
