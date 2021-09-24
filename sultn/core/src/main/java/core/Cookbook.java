package core;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

/*
The cookbook class is a collection with recipes, and contains methods that add,
delete, edit and make new recipes. 
The constructor contains a hash map with recipes and their ids.
*/


public class Cookbook {
    
    private HashMap<Integer, Recipe> recipeMap = new HashMap<>();
    //variabel for id-ene til recipes
    private int counter = 0;  

    //Constructors
    public Cookbook() {

    }

    public Cookbook(HashMap<Integer, Recipe> recipeMap) { 
        this.recipeMap = recipeMap;
    }

    //Getters
    public int getCounter() {
        return this.counter;
    }

    public HashMap<Integer, Recipe> getRecipeMap() {
        return this.recipeMap;
    }


    //Setters
    public void setCounter(int newCounter) {
        this.counter = newCounter;
    }

     
    //returnerer en liste med alle id-ene
    public List<Integer> getIds() { 
        return new ArrayList<>(recipeMap.keySet());
    }

    //returnerer en liste med alle oppskriftene 
    public List<Recipe> getRecipes() {
        List<Recipe> allRecipes = new ArrayList<Recipe>(recipeMap.values());
        return allRecipes;
    }

    //oppretter en ny oppskrift og legger den til
    public void makeNewRecipe(String name, List<String> instructions, List<Ingredient> ingredients) { 
        int id = counter;
        Recipe newRecipe = new Recipe(name, id, ingredients, instructions);
        addRecipe(newRecipe);
        counter++;
    }
    
    //fjerner en oppskrift
    public void deleteRecipe(Recipe recipe) { 
        int id = recipe.getId();

        if(recipeMap.containsKey(id)) {
            recipeMap.remove(id); 
        }
        else {
            throw new IllegalArgumentException("Recipe " + recipe.getName() + " not found. Invalid id.");
        }
    }
        
    //legger til en oppskrift i HashMap
    public void addRecipe(Recipe recipe) {
        int id = recipe.getId();
        recipeMap.put(id, recipe);
    }
    
    //denne burde kunne kalle på andre funksjoner slik at man kan endre på beskrivelsen og ingredienser. 
    //public Recipe editRecipe(Recipe recipe) {  
        //recipe.edit(); //gjøres i recipe
        //return recipe;
    //}
        

}

