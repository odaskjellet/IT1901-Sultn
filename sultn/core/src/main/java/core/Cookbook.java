public class Cookbook {

    private Ingredient ingredients;
    private Recipe recipe;
    
    public Cookbook (Ingredient ingredients, Recipe recipe) {
        this.ingredients = ingredients;
        this.recipe = recipe;
    
    } 
    //enkel konstruktør
    //kan senere endres til å eks inneholde bilde  

    private validateIngredients(Ingredient ingredients) {
        if(ingredients.size() == 0) {
            throw new IllegalArgumentException("Ingredients can't be empty!");
        }
    }
    //sjekker om ingredients listen er tom
    //kan også allerede valideres i ingredients, slik at man ikke oppretter en tom ingredient

    private validateRecipe(Recipe recipe) {
        if(recipe.size() == 0) {
            throw new IllegalArgumentException("Recipe can't be empty!");
        }
    }
    //kan valideres i recipe, slik at man ikke oppretter en tom instans

}
