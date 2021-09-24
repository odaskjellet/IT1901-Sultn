package json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import core.Cookbook;
import core.Recipe;

public class CookBookDeserializer extends JsonDeserializer<Cookbook> {

    private RecipeDeserializer recipeDeserializer = new RecipeDeserializer();
    
    @Override
    public Cookbook deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        TreeNode treeNode = parser.getCodec().readTree(parser);
        return deserialize((JsonNode) treeNode);
    }

    public Cookbook deserialize(JsonNode jsonNode) {
        if (jsonNode instanceof ObjectNode objectnode) {
            Cookbook cookbook = new Cookbook();
            JsonNode recipes = objectnode.get("recipes");

            if (recipes instanceof ArrayNode) {
                for (JsonNode node : ((ArrayNode) recipes)) {
                    Recipe r = recipeDeserializer.deserialize(node);
                    cookbook.addRecipe(r);
                }
            }
            return cookbook;
        }
        return null;
    }
}
