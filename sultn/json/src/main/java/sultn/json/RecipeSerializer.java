package sultn.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import sultn.core.Ingredient;
import sultn.core.Recipe;

/**
 * Converts a Recipe object into a JSON node. Uses the IngredientSerializer to make ingredient
 * nodes.
 */
class RecipeSerializer extends JsonSerializer<Recipe> {
  @Override
  public void serialize(Recipe recipe, JsonGenerator jsonGen, SerializerProvider serializerProvider)
      throws IOException {
    jsonGen.writeStartObject();
    jsonGen.writeStringField("name", recipe.getName());
    jsonGen.writeNumberField("id", recipe.getId());
    jsonGen.writeStringField("category", recipe.getCategory().name());

    jsonGen.writeArrayFieldStart("ingredients");
    for (Ingredient i : recipe.getIngredients()) {
      jsonGen.writeObject(i);
    }
    jsonGen.writeEndArray();

    jsonGen.writeArrayFieldStart("instructions");
    for (String i : recipe.getInstructions()) {
      jsonGen.writeString(i);
    }
    jsonGen.writeEndArray();

    jsonGen.writeEndObject();
  }
}
