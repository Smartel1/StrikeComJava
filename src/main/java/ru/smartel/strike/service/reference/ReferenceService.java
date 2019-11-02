package ru.smartel.strike.service.reference;

import ru.smartel.strike.service.Locale;

import java.util.List;
import java.util.Map;

public interface ReferenceService {

    /**
     * Get Map containing all references wrapped in appropriate DTO
     * @param locale locale
     * @return references collection
     */
    Map<String, List<?>> getAllReferences(Locale locale);

    /**
     * Get hash of all references
     * @return hash
     */
    String getChecksum();
}
