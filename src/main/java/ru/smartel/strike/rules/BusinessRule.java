package ru.smartel.strike.rules;

public abstract class BusinessRule {

    public boolean when = true;

    /**
     * Returns true if rule passes
     */
    abstract public boolean passes();

    /**
     * Forms error message
     */
    abstract public String message();

    /**
     * Rule apply condition
     */
    public BusinessRule when(boolean condition) {
        when = condition;
        return this;
    }
}
