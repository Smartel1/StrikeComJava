package ru.smartel.strike.jsonSerializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.smartel.strike.model.Event;

import java.io.IOException;

public class EventListSerializer extends StdSerializer<Event> {

    private String lang;

    public void setLang(String lang) {
        this.lang = lang;
    }

    public EventListSerializer() {
        this(null);
    }
    public EventListSerializer(Class<Event> t) {
        super(t);
    }

    @Override
    public void serialize(Event event, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", event.getId());
        if (lang.equals("ru")) {
            jgen.writeStringField("title", event.getTitleRu());
        }
        if (lang.equals("en")) {
            jgen.writeStringField("title", event.getTitleEn());
        }
        if (lang.equals("es")) {
            jgen.writeStringField("title", event.getTitleEs());
        }
        if (lang.equals("all")) {
            jgen.writeStringField("title_ru", event.getTitleRu());
            jgen.writeStringField("title_en", event.getTitleEn());
            jgen.writeStringField("title_es", event.getTitleEs());
        }
        jgen.writeEndObject();
    }
}
