package core;

import java.util.HashMap;
import java.util.Map;

public class Cookbook {
    
    private Map<Integer, Recipe> recipeMap = new HashMap<>();
    private int counter = 0;  //variabel for id-ene til recipes

    //Constructor
    public Cookbook(HashMap<Integer, Recipe> recipeMap) { 
        this.recipeMap = recipeMap;
    }
     
    //returnerer en liste med alle id-ene
    public Collection<Integer> getIds() { 
        return new ArrayList<>(recipeMap.keySet());
    }

    //returnerer en liste med alle oppskriftene 
    public Collection<Recipe> getRecipes(HashMap<Integer, Recipe> ingredientMap) { 
        Collection<String> recipeCollection = new ArrayList<>();
		for(Integer id : ingredientMap.keySet()) {
		    recipeCollection.add(ingredientMap.get(id));
        }
        return recipeCollection;
    }

    //oppretter en ny oppskrift og legger den til
    public void makeNewRecipe(String name, String[] instructions, Ingredient[] ingredients) { 
        int id = counter;
        Recipe newRecipe = new Recipe(name, id, ingredients, instructions);
        addRecipe(newRecipe);
        counter++;
    }
    
    //fjerner en oppskrift
    public void deleteRecipe(Recipe recipe) { 
    
        if(recipeMap.containsKey(recipe.getId())) {
            recipeMap.remove(recipe.getId());
        }
        else {
            throw new IllegalArgumentException("Recipe " + recipe.getName() " not found. Invalid id.");
        }
    }
    
    public void addRecipe(Recipe recipe) {//legger til en oppskrift i HashMap
        int id = recipe.getId();
        recipeMap.put(id, recipe);
    }
    

    //denne burde kunne kalle på andre funksjoner slik at man kan endre på beskrivelsen og ingredienser. 
    //public Recipe editRecipe(Recipe recipe) {  
        //recipe.edit(); //gjøres i recipe
        //return recipe;
    }

    
    

}
