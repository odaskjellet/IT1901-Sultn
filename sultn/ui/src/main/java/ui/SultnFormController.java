package ui;

import core.Cookbook;
import core.Ingredient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import json.SultnPersistence;

/**
 * SultnFormController class.
 */
public class SultnFormController {
  @FXML
  private SultnController sultnController;

  private Stage stage;
  private Scene scene;

  private List<Ingredient> tempIngrd = new ArrayList<>();
  private static SultnPersistence persistence = new SultnPersistence();

  @FXML
  private TextField ingredientText;
  @FXML
  private TextField ingredientAmnt;
  @FXML
  private TextField txtIngredientUnit;
  @FXML
  private TextArea instructionsText;
  @FXML
  private Button addIngredient;
  @FXML
  private Button finish;
  @FXML
  private Button handleCancel;
  @FXML
  private TextField titleText;
  @FXML
  private TextField unitBox;
  @FXML
  private ListView<String> listIngredients;

  private Cookbook cookbook;

  /**
   * Constructor.
   *
   * @param cookbook        - Cookbook from SultnController
   * @param sultnController - Controller from the home screen
   * @param saveFile        - saveFile from the controller
   */
  public SultnFormController(Cookbook cookbook, SultnController sultnController, String saveFile) {
    this.cookbook = cookbook;
    this.sultnController = sultnController;
    persistence.setSaveFile(saveFile);
  }

  /**
   * Add new ingredient to tempIngrd, and show on interface.
   * 
   */
  public void addIngredient() {
    try {
      String ingrName = ingredientText.getText();
      if (ingrName.isBlank()) {
        throw new Exception("Ingredient name is empty.");
      }

      Double ingrAmount = Double.parseDouble(ingredientAmnt.getText());
      if (ingrAmount.isNaN()) {
        throw new Exception("Ingredient amount must be a number. E.g. '1.2', '3.14', etc.");
      }
      String ingrUnit = unitBox.getText();
      if (ingrUnit.isBlank()) {
        throw new Exception("The unit is empty. Must be 'dl', 'l', etc.");
      }

      String fullIngr = ingrName + " " + ingrAmount + " " + ingrUnit;
      Ingredient newIngr = new Ingredient(ingrName, ingrAmount, ingrUnit);
      tempIngrd.add(newIngr);
      listIngredients.getItems().add(fullIngr);
      clearIngredientFields();
    } catch (Exception e) {
      exceptionHandling(e.getLocalizedMessage());
    }
  }

  /**
   * Clears the ingredient fields of the form.
   * 
   */
  public void clearIngredientFields() {
    ingredientText.clear();
    ingredientAmnt.clear();
    unitBox.clear();
  }

  /**
   * Clears the fields of the form after passing to cookbook.
   */
  public void clearFormFields() {
    tempIngrd.clear();
    titleText.clear();
    ingredientAmnt.clear();
    unitBox.clear();
    instructionsText.clear();
    ingredientText.clear();
    listIngredients.getItems().clear();

  }

  /**
   * Pops up a dialog box for the user, displaying what went wrong.
   */
  public void exceptionHandling(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR, message);
    alert.showAndWait();
  }

  /**
   * Make new recipe and save to file, then clear the text fields.
   */
  public void addNewRecipe() {
    try {
      cookbook.setCounter(cookbook.getLargestKey() + 1);
      String[] newInstr = instructionsText.getText().split(". ");
      List<String> listInstr = Arrays.asList(newInstr);
      cookbook.makeNewRecipe(titleText.getText(), listInstr, tempIngrd);
      try {
        persistence.saveCookBook(cookbook);
        clearFormFields();
      } catch (Exception e) {
        e.printStackTrace();
      }
    } catch (Exception e) {
      exceptionHandling(e.getMessage());
    }
  }

  /**
   * Switches scene back to Sultn menu.
   */
  public void handleCancel(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("Sultn.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(loader.load());
    stage.setScene(scene);
    stage.setResizable(false);
    sultnController.updateView();
    stage.show();
  }
}
