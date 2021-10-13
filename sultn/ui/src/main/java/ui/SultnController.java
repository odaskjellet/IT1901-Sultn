package ui;

import java.util.ArrayList;
import java.util.Arrays;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import json.SultnPersistence;

public class SultnController {

    
    private static Cookbook cookbook;
    private static SultnPersistence persistence = new SultnPersistence();

    @FXML 
    BorderPane bPane;

    @FXML
    Pane recipePane;

    @FXML
    TextFlow ingredientField;

    @FXML
    TextArea rText;


    @FXML
    Button btnAddRecipe;
    @FXML Button finish;
    @FXML Button cancel;

    @FXML Label titleTitle;
    @FXML Label title;
    @FXML Label  ingredientTitle;
    @FXML Label instructionsTitle;
    
    @FXML TextField titleText;
    @FXML TextField ingredientText;
    @FXML TextField instructionsText;

    public void initialize(){
        persistence.setSaveFile("cookbook.json");

        try {
            cookbook = persistence.loadCookbook();
        } catch (Exception e) {
            cookbook = new Cookbook();
            System.out.println("No cookbook found. Creating a new one.");
            e.printStackTrace();
        }

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
        rText.clear();

        List<String> instructions = new ArrayList<>();
        List<Ingredient> ingredients = new ArrayList<>();

        List<Recipe> recipes = cookbook.getRecipes();

        for(int i = 0; i < recipes.size(); i++){
           
            instructions = recipes.get(i).getInstructions();
            ingredients = recipes.get(i).getIngredients();

            //Text title = new Text(recipes.get(i).getName());
            rText.appendText(recipes.get(i).getName() + '\n');

            for(int x = 0; x < ingredients.size(); x++) {
                //Text ingredient = new Text(ingredients.get(x).toString());

                Ingredient listIngr = ingredients.get(x);
           
                rText.appendText(listIngr.getIngredientName() + 
                "      Antall:  " + listIngr.getIngredientAmount() + ' ' + listIngr.getIngredientUnit() + '\n');
            }
            
            for(int y = 0; y < instructions.size(); y++){
                rText.appendText(instructions.get(y).toString() + '\n');
            }
            rText.appendText("----------------------------------\n");
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


    
   

    //sett i fxml
    //btnAddRecipe.setOnAction(addRecipe());
    //finish.setOnAction(addNewRecipe());
    //cancel.setOnAction(cancelNewRecipe());

    

    public void addRecipe() {

        btnAddRecipe.setVisible(false);
        titleTitle.setVisible(true);
        title.setVisible(true);
        titleText.setVisible(true);
        ingredientText.setVisible(true);
        ingredientTitle.setVisible(true);
        instructionsText.setVisible(true);
        instructionsTitle.setVisible(true);
        finish.setVisible(true);
        cancel.setVisible(true);
    }

    public void addNewRecipe() {

        String name = titleText.getText();

        //ingredient fields
        String[] iStr = ingredientText.getText().split(" ");
        String iName = iStr[0];
        Double iAmount = Double.parseDouble(iStr[1]);
        String iUnit = iStr[2];

        Ingredient newIngredient = new Ingredient(iName, iAmount, iUnit);

        List<Ingredient> newIngredients = new ArrayList<>();
        newIngredients.add(newIngredient);

        //instruction fields
        String[] newInstr = instructionsText.getText().split(", ");
        List<String> listInstr = Arrays.asList(newInstr);
        
        //makes new recipe
        cookbook.makeNewRecipe(name, listInstr, newIngredients);
        
        try {
            persistence.saveCookBook(cookbook);
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnAddRecipe.setVisible(true);
        titleTitle.setVisible(false);
        title.setVisible(false);
        titleText.setVisible(false);
        ingredientText.setVisible(false);
        ingredientTitle.setVisible(false);
        instructionsText.setVisible(false);
        instructionsTitle.setVisible(false);
        finish.setVisible(false);
        cancel.setVisible(false);

        //etter adding
        createRecipeList();  

    }

    public void cancelNewRecipe() {

        ingredientText.clear();
        instructionsText.clear();
        titleText.clear();

        btnAddRecipe.setVisible(true);
        titleTitle.setVisible(false);
        title.setVisible(false);
        titleText.setVisible(false);
        ingredientText.setVisible(false);
        ingredientTitle.setVisible(false);
        instructionsText.setVisible(false);
        instructionsTitle.setVisible(false);
        finish.setVisible(false);
        cancel.setVisible(false);
    }
}
