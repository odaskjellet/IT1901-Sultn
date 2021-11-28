package sultn.json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import sultn.core.Cookbook;
import sultn.core.Ingredient;
import sultn.core.Recipe;

/**
 * Module for registering serializers and deserializers of classes Cookbook, Recipe and Ingredient.
 * Is registered with ObjectMapper in SultnPersistence.
 */
public class SultnModule extends SimpleModule {
  private static final String NAME = "SultnModule";

  /**
   * Constructs a SultnModule with added serializers and deserializers for classes Cookbook, Recipe
   * and Ingredient.
   */
  public SultnModule() {
    super(NAME, Version.unknownVersion());
    addSerializer(Ingredient.class, new IngredientSerializer());
    addSerializer(Recipe.class, new RecipeSerializer());
    addSerializer(Cookbook.class, new CookbookSerializer());

    addDeserializer(Ingredient.class, new IngredientDeserializer());
    addDeserializer(Recipe.class, new RecipeDeserializer());
    addDeserializer(Cookbook.class, new CookbookDeserializer());
  }
}
