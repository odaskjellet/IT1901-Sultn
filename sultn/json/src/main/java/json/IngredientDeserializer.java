package json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NumericNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import core.Ingredient;
import java.io.IOException;

/**
 * Converts a JSON node containing an ingredient into an Ingredient object.
 */
public class IngredientDeserializer extends JsonDeserializer<Ingredient> {

  @Override
  public Ingredient deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    TreeNode treeNode = parser.getCodec().readTree(parser);
    return deserialize((JsonNode) treeNode);
  }

  /**
   * Gets nodes from JSON file.
   *
   * @param jsonNode - Node to deserialize.
   * @return - An Ingredient object if jsonNode contains one, otherwise null.
   * @throws IOException If format of ingredient is wrong.
   * @throws IllegalArgumentException If Ingredient is initialized with invalid arguments.
   */
  public Ingredient deserialize(JsonNode jsonNode) throws IOException, IllegalArgumentException {
    if (jsonNode instanceof ObjectNode objectnode) {
      // Get nodes from JSON file.
      JsonNode nameNode = objectnode.get("name");
      JsonNode amountNode = objectnode.get("amount");
      JsonNode unitNode = objectnode.get("unit");

      // Init Ingredient constructor args.
      String name = "";
      double amount = 0.0;
      String unit = "";

      // Define args. if nodes exists.
      if (nameNode instanceof TextNode) {
        name = nameNode.asText();
      }

      if (amountNode instanceof NumericNode) {
        amount = amountNode.asDouble();
      }

      if (unitNode instanceof TextNode) {
        unit = unitNode.asText();
      }

      return new Ingredient(name, amount, unit);
    } else {
      throw new IOException("Incorrect ingredient format.");
    }
  }

}
