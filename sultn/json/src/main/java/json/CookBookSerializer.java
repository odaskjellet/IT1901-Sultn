package json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import core.CookBook;

public class CookBookSerializer extends JsonSerializer<CookBook> {
    
    @Override
    public void serialize(CookBook cookBook, JsonGenerator jsonGen, SerializerProvider serializerProvider) throws IOException {
        jsonGen.writeStartObject();
        
        

        jsonGen.writeEndObject();
    }
}
