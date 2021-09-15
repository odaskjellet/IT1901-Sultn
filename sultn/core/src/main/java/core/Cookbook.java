import java.util.Map;

public class Cookbook {
    
    private Map<Integer, Recipe> recipeMap = new HashMap<>();

    //variabel for id-ene til recipes
    private int counter = 0; 

    //Constructor
    public Cookbook(HashMap<Integer, Recipe> recipeMap) { 
        this.recipeMap = recipeMap;
    }
     

    public Collection<Integer> getIds() { //returnerer en liste med alle id-ene
        return new ArrayList<>(recipeMap.keySet());
    }

    public Collection<Recipe> getRecipes() { //returnerer en liste med alle oppskriftene 
        Collection<String> recipeCollection = new ArrayList<>();
		for(Integer id : ingredientMap.keySet()) {
		    recipeCollection.add(ingredientMap.get(id));
        }
        return recipeCollection;
    }

    public void makeNewRecipe(String name, List<String> instructions) { //oppretter en ny oppskrift
        int id = counter;
        
        Recipe newRecipe = new Recipe(name, instructions, id);
        //må ha beskrivelse, navn og ingredienser, og id. 

        addRecipe(newRecipe);
        counter++;
    }
    
    public void deleteRecipe(Recipe recipe) { //fjerner en oppskrift
    
        if(recipeMap.containsKey(recipe.getId())) {
            recipeMap.remove(recipe.getId());
        }
        else {
            throw new IllegalArgumentException("Recipe " + recipe.getName() " not found. Invalid id.");
        }
    }
    //forslag, la vær recipe få en unik id når man oprretter den, 
    //slik at man kan ha reciepes med samme navn, men fortsatt slette riktig. 
    //eksempel sjekke om navn er brukt fra før, og legge til et tall hvis det er brukt, slik at det blir unikt. (while-loop)
    

    public void addRecipe(Recipe recipe) {//legger til en oppskrift i HashMap
        int id = recipe.getId();
        recipeMap.put(id, recipe);
    }
    

    public Recipe editRecipe(Recipe recipe) {
        //denne burde kunne kalle på andre funksjoner slik at man kan endre på beskrivelsen og ingredienser.  

        recipe.edit(); //gjøres i recipe

        return recipe;
    }

    
    

}
