package ui;

import core.Cookbook;
import core.Ingredient;
import core.Recipe;
import java.io.IOException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.scene.Node;

/**
 * RecipeController class
 * 
 */
public class RecipeController {

    private Stage stage;
    private Scene scene;

    SultnController sultnController;

    @FXML
    Button btnRecipeBack;

    @FXML
    private Label lblRecipeName;

    @FXML
    private TextFlow ingredientField;

    @FXML
    private TextFlow directionField;

    @FXML
    private TextFlow unitFlow;

    public void initialize() {
    }

    /**
     * 
     * Method for passing data from sultnController to recipeController
     * 
     * @param cookbook - Cookbook-object to get recipe from
     * 
     * @param id       - id to the chosen recipe from sultnController, used to get
     *                 chosen recipe from cookbook
     */
    public void initData(Cookbook cookbook, int id) {
        Recipe loadRecipe = cookbook.getRecipeMap().get(id);
        lblRecipeName.setText(loadRecipe.getName());
        writeIngredientField(loadRecipe);
        writeDirectionField(loadRecipe);
        writeUnitField(loadRecipe);
    }

    /**
     * Method for showing the ingredients in the Recipe view
     * 
     * @param recipe - Recipe-object to get ingredients from
     */
    public void writeIngredientField(Recipe recipe) {
        List<Ingredient> ingredients = recipe.getIngredients();
        for (Ingredient ingredient : ingredients) {
            Text ingredientText = new Text(ingredient.getIngredientName() + "\n");
            ingredientText.setFont(Font.font("Helvetica", 14));
            ingredientField.getChildren().add(ingredientText);

        }
    }

    /**
     * Method for showing the units of ingredients in the Recipe view
     * 
     * @param recipe - Recipe-object to get ingredient units from
     */
    public void writeUnitField(Recipe recipe) {
        List<Ingredient> ingredients = recipe.getIngredients();
        for (Ingredient ingredient : ingredients) {
            Text unitText = new Text("" + ingredient.getIngredientAmount() + ingredient.getIngredientUnit() + "\n");
            unitText.setFont(Font.font("Helvetica", 14));
            unitFlow.getChildren().add(unitText);

        }
    }

    /**
     * Method for showing the instructions in the Recipe view
     * 
     * @param recipe - Recipe-object to get instructions from
     * 
     */
    public void writeDirectionField(Recipe recipe) {
        List<String> instructions = recipe.getInstructions();
        int i = 1;
        for (String instruction : instructions) {
            Text instructionText = new Text(i + ". " + instruction + "\n");
            instructionText.setFont(Font.font("Helvetica", 14));
            directionField.getChildren().add(instructionText);
            i++;
        }
    }

    /**
     * Switches scene back to Sultn menu
     * 
     */
    public void handRecipeBack(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Sultn.fxml"));
        loader.setController(sultnController);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    |}
}
