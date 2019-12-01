package ru.smartel.strike.service;

/**
 * Доступные локали приложения. Приходят из пути запроса
 * В зависимости от выбранной локали в ответ попадают локализованные поля,
 * или происходит обновление полей на нужной локали
 * (например, передано title="foobar" при обновлении события. Если выбрана локаль EN, то обновится поле title_en)
 */
public enum Locale {
    RU, EN, ES, DE, ALL;

    public String getPascalCase() {
        switch (this) {
            case RU: return "Ru";
            case EN: return "En";
            case ES: return "Es";
            case DE: return "De";
            case ALL: return "All";
            default: throw new IllegalStateException("Unknown locale");
        }
    }
}
