package json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;

import core.Cookbook;
import core.Ingredient;
import core.Recipe;

/**
 * Module for registering serializers and deserializers of classes Cookbook, Recipe and Ingredient.
 * Is registered with ObjectMapper in SultnPersistence.
 */
@SuppressWarnings("serial")
public class SultnModule extends SimpleModule {
    private static final String NAME = "SultnModule";

    public SultnModule(boolean deepSultnModelSerializer) {
        super(NAME, Version.unknownVersion());
        addSerializer(Ingredient.class, new IngredientSerializer());
        addSerializer(Recipe.class, new RecipeSerializer());
        addSerializer(Cookbook.class, new CookBookSerializer());

        addDeserializer(Ingredient.class, new IngredientDeserializer());
        addDeserializer(Recipe.class, new RecipeDeserializer());
        addDeserializer(Cookbook.class, new CookBookDeserializer());
    }

    public SultnModule() {
        this(true);
    }
}