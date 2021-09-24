package json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import core.Cookbook;
import core.Ingredient;
import core.Recipe;

@SuppressWarnings("serial")
public class SultnModule extends SimpleModule {
    private static final String NAME = "SultnModule";

    public SultnModule(boolean deepSultnModelSerializer) {
        super(NAME, Version.unknownVersion());
        addSerializer(Ingredient.class, new IngredientSerializer());
        addSerializer(Recipe.class, new RecipeSerializer());
        addSerializer(Cookbook.class, new CookBookSerializer());
    }

    public SultnModule() {
        this(true);
    }

    public static void main(String[] args) {
        Ingredient ing = new Ingredient("Eggs", 2.0, "pcs");
        List<Ingredient> ings = new ArrayList<Ingredient>(Arrays.asList(ing));
        List<String> instr = new ArrayList<String>(Arrays.asList( 
            "Fry guanciale in a pan.",
            "Cook pasta.",
            "Make slurry of eggs, parmigiano, and pepper.",
            "Add al dente pasta to frying pan along with slurry",
            "Stir constantly",
            "Add salt to taste"));
        
        Recipe recipe = new Recipe("Carbonara", 123, ings, instr);
        Cookbook cookbook = new Cookbook();
        cookbook.addRecipe(recipe);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new SultnModule());

        try {
            System.out.println(mapper.writeValueAsString(cookbook));
        } catch (JsonProcessingException e) {
            System.err.println(e);
        }
    }
}