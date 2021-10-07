package json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import core.Ingredient;

/**
 * Converts an Ingredient object into a JSON node.
 */
public class IngredientSerializer extends JsonSerializer<Ingredient> {
    
    @Override
    public void serialize(Ingredient ingredient, JsonGenerator jsonGen, SerializerProvider serializerProvider) throws IOException {
        jsonGen.writeStartObject();

        jsonGen.writeStringField("name", ingredient.getIngredientName());
        jsonGen.writeStringField("unit", ingredient.getIngredientUnit());
        jsonGen.writeNumberField("amount", ingredient.getIngredientAmount());

        jsonGen.writeEndObject();
    }
}