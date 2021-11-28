package sultn.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NumericNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import sultn.core.Ingredient;
import sultn.core.Recipe.Category;
import sultn.core.Recipe;

/**
 * Converts a JSON node containing a recipe into a Recipe object.
 */
class RecipeDeserializer extends JsonDeserializer<Recipe> {

  private IngredientDeserializer ingredientDeserializer = new IngredientDeserializer();

  @Override
  public Recipe deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    TreeNode treeNode = parser.getCodec().readTree(parser);
    return deserialize((JsonNode) treeNode);
  }

  /**
   * Deserializes Recipe nodes.
   *
   * @param jsonNode - Recipe node to be deserialized
   * @return A Recipe object if jsonNode contains one, otherwise null.
   * @throws IOException If recipe format is incorrect, or if it contains an illegal ingredient
   *         field.
   */
  public Recipe deserialize(JsonNode jsonNode) throws IOException {
    if (jsonNode instanceof ObjectNode objectnode) {

      JsonNode nameNode = objectnode.get("name");
      JsonNode idNode = objectnode.get("id");
      JsonNode ingredientsNode = objectnode.get("ingredients");
      JsonNode instructionsNode = objectnode.get("instructions");
      JsonNode categoryNode = objectnode.get("category");

      String name = "";
      int id = 0;
      Category category = Category.OTHER;
      Collection<Ingredient> ingredients = new ArrayList<Ingredient>();
      List<String> instructions = new ArrayList<String>();

      if (nameNode instanceof TextNode) {
        name = nameNode.asText();
      }

      if (idNode instanceof NumericNode) {
        id = idNode.asInt();
      }

      if (ingredientsNode instanceof ArrayNode) {
        for (JsonNode i : ((ArrayNode) ingredientsNode)) {
          try {
            Ingredient ingr = ingredientDeserializer.deserialize(i);
            ingredients.add(ingr);
          } catch (IllegalArgumentException e) {
            throw new IOException("Illegal ingredient field. " + e.getMessage());
          }
        }
      }

      if (instructionsNode instanceof ArrayNode) {
        for (JsonNode i : ((ArrayNode) instructionsNode)) {
          instructions.add(i.asText());
        }
      }

      if (categoryNode instanceof TextNode) {
        category = Category.valueOf(categoryNode.asText());
      }

      return new Recipe(name, id, ingredients, instructions, category);
    } else {
      throw new IOException("Incorrect recipe format.");
    }
  }
}
