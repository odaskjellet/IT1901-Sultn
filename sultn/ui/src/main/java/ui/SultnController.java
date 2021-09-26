package ui;

import java.util.ArrayList;
import java.util.List;

import core.Cookbook;
import core.Ingredient;
import core.Recipe;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class SultnController {

    
    private static Cookbook cookbook;



    @FXML 
    BorderPane bPane;

    @FXML
    Pane recipePane;

    @FXML
    TextFlow ingredientField;

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
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Recipe.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle("Recipe");
                    stage.setScene(new Scene(root1));
                    stage.show();

                    //StackPane secondaryLayout = new StackPane();
                    //Scene scene = new Scene(secondaryLayout, 300, 467);
                    //Stage stage = new Stage();
                    //stage.setTitle("Hallo"); //cookbook.getRecipeMap().get(button.getId()).getName();
                    //stage.setScene(scene);
                    //stage.show();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

                
              //must put on an "OnAction" or an action event 

             this.getChildren().addAll(label, button);
        }
    }


   private void createRecipeList(){
       
        List<String> instructions = new ArrayList<>();
        List<Ingredient> ingredients = new ArrayList<>();


        List<Recipe> recipes = cookbook.getRecipes();

        for(int i = 0; i < recipes.size(); i++){
           
            instructions = recipes.get(i).getInstructions();
            ingredients = recipes.get(i).getIngredients();

            Text title = new Text(recipes.get(i).getName());

            recipePane.getChildren().add(title);


            for(int x = 0; x < ingredients.size(); x++) {
                Text ingredient = new Text(ingredients.get(x).toString());
                recipePane.getChildren().add(ingredient);
            }
            
            for(int y = 0; y < instructions.size(); y++){
                Text instruct = new Text(instructions.get(y));
                recipePane.getChildren().add(instruct);
            }
        }






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
