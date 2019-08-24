package ru.smartel.strike.service;

import org.springframework.stereotype.Service;
import ru.smartel.strike.exception.BusinessRuleValidationException;
import ru.smartel.strike.rules.BusinessRule;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusinessValidationService {

    public BusinessValidationService validate(BusinessRule... rules) throws BusinessRuleValidationException {
        List<String> messages = new ArrayList<>();

        for (BusinessRule rule : rules) {
            if (rule.when) {
                if (!rule.passes()) {
                    messages.add(rule.message());
                }
            }
        }

        if (!messages.isEmpty()) {
            throw new BusinessRuleValidationException(messages);
        }

        return this;
    }
}
