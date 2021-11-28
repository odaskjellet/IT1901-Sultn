package sultn.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import sultn.core.Cookbook;
import sultn.core.Ingredient;
import sultn.core.Recipe;

public class SultnModuleTest {

  public static ObjectMapper mapper;

  @BeforeEach
  public void setup() {
    mapper = new ObjectMapper();
    mapper.registerModule(new SultnModule());
  }

  @Test
  public void testEmptyIngredient() {
    String json = """
        {
          "name" : "Kjeks",
          "id" : 0,
          "ingredients" : [ {
            "unit" : "kg",
            "amount" : 1.0
          } ],
          "instructions" : [ "Hell kjeksstoff i kjelen", "kok", "feridg" ]
        }
        """;

    assertThrows(IOException.class,
        () -> mapper.readValue(json.replaceAll("\\s+", ""), Recipe.class),
        "Illegal ingredient field. Name has no content.");
  }

  @Test
  public void testIncorrectIngredientFormat() {
    String json = "[]";

    assertThrows(IOException.class, () -> mapper.readValue(json, Ingredient.class),
        "Incorrect ingredient format.");
  }

  @Test
  public void testIncorrectRecipeFormat() {
    String json = "[]";

    assertThrows(IOException.class, () -> mapper.readValue(json, Recipe.class),
        "Incorrect recipe format.");
  }

  @Test
  public void testBadRecipeInCookbook() {
    String json = """
        {
          "recipes" : [ [] ]
        }
        """;

    try {
      Cookbook c = mapper.readValue(json.replaceAll("\\s+", ""), Cookbook.class);

      List<Recipe> r = c.getRecipes();
      assertEquals(0, r.size());
    } catch (Exception e) {
      System.out.println(e);
    }

  }
}
