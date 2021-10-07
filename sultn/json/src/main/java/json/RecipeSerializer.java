package json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import core.Ingredient;
import core.Recipe;

/**
 * Converts a Recipe object into a JSON node.
 * 
 */
public class RecipeSerializer extends JsonSerializer<Recipe> {
    @Override
    public void serialize(Recipe recipe, JsonGenerator jsonGen, SerializerProvider serializerProvider) throws IOException {
        jsonGen.writeStartObject();
        jsonGen.writeStringField("name", recipe.getName());
        jsonGen.writeNumberField("id", recipe.getId());

        // Write ingredients
        jsonGen.writeArrayFieldStart("ingredients");
        for (Ingredient i : recipe.getIngredients()) {
            jsonGen.writeObject(i); // Uses IngredientSerialiser.
        }
        jsonGen.writeEndArray();

        // Write instructions
        jsonGen.writeArrayFieldStart("instructions");
        for (String i : recipe.getInstructions()) {
            jsonGen.writeString(i);
        }
        jsonGen.writeEndArray();
        
        jsonGen.writeEndObject();
    }
}
