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

/**
 * Converts a JSON node containing a Cookbook into a Cookbook object.
 * 
 */
public class CookBookDeserializer extends JsonDeserializer<Cookbook> {

    private RecipeDeserializer recipeDeserializer = new RecipeDeserializer();
    
    @Override
    public Cookbook deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        TreeNode treeNode = parser.getCodec().readTree(parser);
        return deserialize((JsonNode) treeNode);
    }
    /**
     * Deserialize a JSON node
     * @param jsonNode - Node to be deserialized
     * @return A Cookbook object with all the recipes found in the JSON file (may be empty).
     */
    public Cookbook deserialize(JsonNode jsonNode) {
        
        // Init Cookbook.
        Cookbook cookbook = new Cookbook();
        
        if (jsonNode instanceof ObjectNode objectnode) {
            
            // Get all Recipe nodes.
            JsonNode recipes = objectnode.get("recipes");

            // Add each Recipe object to Cookbook if JSON nodes exists.
            if (recipes instanceof ArrayNode) {
                for (JsonNode node : ((ArrayNode) recipes)) {
                    try {
                        Recipe r = recipeDeserializer.deserialize(node);
                        cookbook.addRecipe(r);
                    } catch (IOException e) {
                        // If exception is thrown the recipe is skipped.
                        System.err.println(e);
                    }
                }
            }
        }
        return cookbook;
    }
}
