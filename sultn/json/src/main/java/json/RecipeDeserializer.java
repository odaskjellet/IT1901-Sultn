package json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import core.Ingredient;
import core.Recipe;

public class RecipeDeserializer extends JsonDeserializer<Recipe> {

    private IngredientDeserializer ingredientDeserializer = new IngredientDeserializer();
    
    @Override
    public Recipe deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        TreeNode treeNode = parser.getCodec().readTree(parser);
        return deserialize((JsonNode) treeNode);
    }

    public Recipe deserialize(JsonNode jsonNode) {
        if (jsonNode instanceof ObjectNode objectnode) {
            JsonNode nameNode = objectnode.get("name");
            JsonNode idNode = objectnode.get("id");
            JsonNode ingredientsNode = objectnode.get("ingredients");
            JsonNode instructionsNode = objectnode.get("instructions");

            String name = "";
            int id = 0;
            List<Ingredient> ingredients = new ArrayList<Ingredient>();
            List<String> instructions = new ArrayList<String>();

            if (nameNode instanceof TextNode) {
                name = nameNode.asText();
            }

            if (idNode instanceof NumericNode) {
                id = idNode.asInt();
            }

            if (ingredientsNode instanceof ArrayNode) {
                for (JsonNode i : ((ArrayNode) ingredientsNode)) {
                    Ingredient ingr = ingredientDeserializer.deserialize(i);
                    if (ingr != null) {
                        ingredients.add(ingr);
                    }
                }
            }

            if (instructionsNode instanceof ArrayNode) {
                for (JsonNode i : ((ArrayNode) instructionsNode)) {
                    instructions.add(i.asText());
                }
            }
            return new Recipe(name, id, ingredients, instructions);
        }
        return null;
    }
}
