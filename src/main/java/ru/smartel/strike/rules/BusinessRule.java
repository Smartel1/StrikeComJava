package ru.smartel.strike.rules;

public abstract class BusinessRule {

    public boolean when = true;

    /**
     * Определить, проходит ли правило
     */
    abstract public boolean passes();

    /**
     * Сообщение об ошибке
     */
    abstract public String message();

    /**
     * Условие, когда правило применяется
     */
    public BusinessRule when(boolean condition)
    {
        when = condition;
        return this;
    }
}
