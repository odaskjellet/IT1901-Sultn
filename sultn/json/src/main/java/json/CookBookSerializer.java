package json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.Cookbook;
import core.Recipe;
import java.io.IOException;

/**
 * Converts a Cookbook object into a JSON node which can be stored as a file. Creates a node for
 * each Recipe in Cookbook.
 */
public class CookBookSerializer extends JsonSerializer<Cookbook> {

  @Override
  public void serialize(Cookbook cookBook, JsonGenerator jsonGen,
      SerializerProvider serializerProvider) throws IOException {
    jsonGen.writeStartObject();

    jsonGen.writeArrayFieldStart("recipes");
    for (Recipe r : cookBook.getRecipes()) {
      jsonGen.writeObject(r); // Uses RecipeSerializer.
    }
    jsonGen.writeEndArray();

    jsonGen.writeEndObject();
  }
}
