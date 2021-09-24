package json;

import com.fasterxml.jackson.core.Version;
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
}