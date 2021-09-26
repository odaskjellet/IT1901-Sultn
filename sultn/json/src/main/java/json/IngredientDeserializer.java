package json;

import java.io.IOException;

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

public class IngredientDeserializer extends JsonDeserializer<Ingredient> {

    @Override
    public Ingredient deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        TreeNode treeNode = parser.getCodec().readTree(parser);
        return deserialize((JsonNode) treeNode);
    }
    
    public Ingredient deserialize(JsonNode jsonNode) {
        if (jsonNode instanceof ObjectNode objectnode) {
            JsonNode nameNode = objectnode.get("name");
            JsonNode amountNode = objectnode.get("amount");
            JsonNode unitNode = objectnode.get("unit");

            String name = "";
            double amount = 0.0;
            String unit = "";

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
        }

        return null;
    }
    
}
