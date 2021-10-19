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
   * @param cookbook - Cookbook from SultnController
   * @param sultnController - Controller from the home screen
   * @param saveFile - saveFile from the controller
   */
  public SultnFormController(Cookbook cookbook, SultnController sultnController, String saveFile) {
    this.cookbook = cookbook;
    this.sultnController = sultnController;
    persistence.setSaveFile(saveFile);
  }

  /**
   * Add new ingredient to tempIngrd, and show them on interface.
   */
  public void addIngredient() {
    String ingrName = ingredientText.getText();
    Double ingrAmount = Double.parseDouble(ingredientAmnt.getText());
    String ingrUnit = unitBox.getText();
    String fullIngr = ingrName + " " + ingrAmount + " " + ingrUnit;
    Ingredient newIngr = new Ingredient(ingrName, ingrAmount, ingrUnit);
    tempIngrd.add(newIngr);
    listIngredients.getItems().add(fullIngr);

    ingredientText.clear();
    ingredientAmnt.clear();
    unitBox.clear();
  }

  /**
   * Make new recipe and save to file, then clear the text fields.
   */
  public void addNewRecipe() {
    String[] newInstr = instructionsText.getText().split(", ");
    List<String> listInstr = Arrays.asList(newInstr);
    cookbook.makeNewRecipe(titleText.getText(), listInstr, tempIngrd);

    try {
      persistence.saveCookBook(cookbook);
    } catch (Exception e) {
      e.printStackTrace();
    }

    tempIngrd.clear();
    titleText.clear();
    ingredientAmnt.clear();
    unitBox.clear();
    instructionsText.clear();
    ingredientText.clear();
    listIngredients.getItems().clear();

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
    stage.show();
  }
}
