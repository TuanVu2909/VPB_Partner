package com.lendbiz.p2p.api.model.SavisResponse;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonDeserialize(using = Value.Deserializer.class)
@JsonSerialize(using = Value.Serializer.class)
public class Value {
    public Long integerValue;
    public String stringValue;

    static class Deserializer extends JsonDeserializer<Value> {
        @Override
        public Value deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException, JsonProcessingException {
            Value value = new Value();
            switch (jsonParser.currentToken()) {
                case VALUE_NUMBER_INT:
                    value.integerValue = jsonParser.readValueAs(Long.class);
                    break;
                case VALUE_STRING:
                    String string = jsonParser.readValueAs(String.class);
                    value.stringValue = string;
                    break;
                default:
                    throw new IOException("Cannot deserialize Value");
            }
            return value;
        }
    }

    static class Serializer extends JsonSerializer<Value> {
        @Override
        public void serialize(Value obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
                throws IOException {
            if (obj.integerValue != null) {
                jsonGenerator.writeObject(obj.integerValue);
                return;
            }
            if (obj.stringValue != null) {
                jsonGenerator.writeObject(obj.stringValue);
                return;
            }
            throw new IOException("Value must not be null");
        }
    }
}
