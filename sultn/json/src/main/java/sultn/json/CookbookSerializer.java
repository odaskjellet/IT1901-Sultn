package sultn.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import sultn.core.Cookbook;
import sultn.core.Recipe;

/**
 * Converts a Cookbook object into a JSON node which can be stored as a file. Creates a node for
 * each Recipe in Cookbook.
 */
class CookbookSerializer extends JsonSerializer<Cookbook> {

  @Override
  public void serialize(Cookbook cookBook, JsonGenerator jsonGen,
      SerializerProvider serializerProvider) throws IOException {
    jsonGen.writeStartObject();

    jsonGen.writeArrayFieldStart("recipes");
    for (Recipe r : cookBook.getRecipes()) {
      jsonGen.writeObject(r);
    }
    jsonGen.writeEndArray();

    jsonGen.writeEndObject();
  }
}
