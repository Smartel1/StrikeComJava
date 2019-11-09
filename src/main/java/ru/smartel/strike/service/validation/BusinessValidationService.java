package ru.smartel.strike.service.validation;

import org.springframework.stereotype.Service;
import ru.smartel.strike.exception.ValidationException;
import ru.smartel.strike.rules.BusinessRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BusinessValidationService {

    public void validate(BusinessRule... rules) {
        List<String> messages = new ArrayList<>();

        for (BusinessRule rule : rules) {
            if (rule.when) {
                if (!rule.passes()) {
                    messages.add(rule.message());
                }
            }
        }

        if (!messages.isEmpty()) {
            throw new ValidationException(Collections.singletonMap("errors", messages));
        }
    }
}
