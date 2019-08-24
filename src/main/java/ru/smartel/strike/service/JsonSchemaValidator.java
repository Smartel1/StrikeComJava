package ru.smartel.strike.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ListProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import org.springframework.stereotype.Service;
import ru.smartel.strike.exception.JsonSchemaValidationException;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class JsonSchemaValidator {

    /**
     * Проверить данные на соответствие схеме. В случае ошибок выбросить исключение
     *
     * @param data       Данные для проверки
     * @param schemaName Название файла из папки resources/json-schemas без расширения
     */
    public void validate(JsonNode data, String schemaName) throws IOException, ProcessingException, JsonSchemaValidationException {
        //Создаем фабрику схем
        final JsonSchemaFactory factory = JsonSchemaFactory.newBuilder().freeze();
        //Находим схему, имя которой пришло в параметрах
        final JsonSchema schema = factory.getJsonSchema("resource:/json-schemas/" + schemaName + ".schema.json");
        //Валидируем json, сохраняем в переменную report данные о валидации
        ListProcessingReport report = (ListProcessingReport) schema.validate(data);
        //Ошибки преобразуем в формат "поле":"ошибка"
        Map<String, String> errors = new LinkedHashMap<>();
        report.forEach(processingMessage -> errors.put(
                processingMessage.asJson().get("instance").get("pointer").asText(),
                processingMessage.getMessage()
        ));
        //Если были ошибки, то выбрасываем исключение
        if (!errors.isEmpty()) throw new JsonSchemaValidationException("json содержит неверные данные", errors);
    }
}
