package ui;

import java.util.ArrayList;
import java.util.List;

import core.Cookbook;
import core.Ingredient;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class SultnController {

    
    private Cookbook cookbook;
    
    @FXML BorderPane bPane;

    public void initialize(){
        cookbook = new Cookbook();
        //cookbook.makeNewRecipe(saveHandler.load());


        // Mistenker at vi ikke kan launche med tom kokebok, så her er litt dummykode slik at vi har en recipe
        List<Ingredient> testIngredients = new ArrayList<>();
        List<String> testInstructions = new ArrayList<>();
        Ingredient testTomat = new Ingredient("tomat", 2, "stk");
        testIngredients.add(testTomat);
        testInstructions.add("kok tomat");
        cookbook.makeNewRecipe("Test", testInstructions, testIngredients);
        // -------------------------------------


        createRecipeList();
    }

    public static class HBoxCell extends HBox {
        Label label = new Label();
        Button button = new Button();
        
        HBoxCell(String recipeName, int id) {
             super();

             label.setText(recipeName);
             label.setMaxWidth(Double.MAX_VALUE);
             HBox.setHgrow(label, Priority.ALWAYS);

             button.setText("Open");
             button.setId("" + id); //should be the same as recipe id.

            // button.setOnAction(value); //must put on an "OnAction" or an action event 

             this.getChildren().addAll(label, button);
        }
   }


   private void createRecipeList(){
       
        List<HBoxCell> list = new ArrayList<>();

        /*for(int key : cookbook.getRecipeMap().keySet()) {
            list.add(new HBoxCell(cookbook.getRecipeMap().get(key).getName(), 
            cookbook.getRecipeMap().get(key).getId()));
        }*/

        list.add(new HBoxCell("Test", 0)); // Bruker denne til vi finner ut hvorfor løkken over ikke funker

        ListView<HBoxCell> recipeView = new ListView<HBoxCell>();
        ObservableList<HBoxCell> observableList = FXCollections.observableList(list);
        recipeView.setItems(observableList);

        bPane.setCenter(recipeView);

        /*ListView recipeView = new ListView(); 
        List<Recipe> recipeList = cookbook.getRecipes(); //we assume this works
        for(Recipe recipe: recipeList) {
            recipeView.getItems().add(recipe);
        }*/


    }
}
