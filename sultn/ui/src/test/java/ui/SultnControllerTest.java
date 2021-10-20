package ui;

import core.Cookbook;
import core.Ingredient;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import json.SultnPersistence;
import ui.SultnController.HBoxCell;

public class SultnControllerTest extends ApplicationTest {

  private SultnController sultnController;


  @Override
  public void start(Stage stage) throws IOException {
    final FXMLLoader loader = new FXMLLoader(getClass().getResource("SultnTest.fxml"));
    final Parent root = loader.load();
    this.sultnController = loader.getController();
    stage.setScene(new Scene(root));
    stage.show();
  }



  private Cookbook cookbook;
  private static String saveFile = "cookbookTest.json";
  private SultnPersistence persistence = new SultnPersistence();
  private List<String> ingredients = new ArrayList<>();
  private List<String> recipeNames = new ArrayList<>();

  /**
   * Method for setting up a json test file for the tests.
   */
  @BeforeAll
  public static void setupSaveFile() {


    String json =
        """
              "recipes" : [ {
                "name" : "Carbonara",
                "id" : 0,
                "ingredients" : [ {
                  "name" : "Eggs",
                  "unit" : "pcs",
                  "amount" : 2.0
                } ],
                "instructions" : [ "Fry guanciale in a pan.", "Cook pasta.", "Make slurry of eggs, parmigiano, and pepper.", "Add al dente pasta to frying pan along with slurry", "Stir constantly", "Add salt to taste" ]
              }, {
                "name" : "Pizza",
                "id" : 1,
                "ingredients" : [ {
                  "name" : "Mel",
                  "unit" : "kg",
                  "amount" : 1.0
                }, {
                  "name" : "Vann",
                  "unit" : "dl",
                  "amount" : 5.0
                }, {
                  "name" : "Topping",
                  "unit" : "stk",
                  "amount" : 5.0
                } ],
                "instructions" : [ "Bland mel og vann til en deig.", "La deigen heve.","Rull ut pizza.", "Ta på topping.", "Stek i 15 min" ]
              }]
            }
              """;

    File jsonFile = new File(System.getProperty("user.dir"), saveFile);

    Path path = jsonFile.toPath();

    try {
      Files.write(path, json.getBytes());

    } catch (Exception e) {
      System.out.println(e);
    }

  }



  /**
   * Sets up a Cookbook with stored Recipes from JSON.
   */
  @BeforeEach
  public void setupCookbook() {
    ingredients.add("Pepper, 2, kg");
    ingredients.add("Mel, 1, kg");
    recipeNames.add("Carbonara");
    recipeNames.add("Pizza");

    persistence.setSaveFile(saveFile);
    try {
      this.cookbook = persistence.loadCookbook();
    } catch (Exception e) {
      System.err.println(e);
      System.err.println("Couln't load cookbookTest.json");
    }
    assertNotNull(cookbook);
    // burde teste items her...?
    // se hallvard sin test
  }


  /**
   * Testing controller
   */
  @Test
  public void testController() {
    assertNotNull(this.sultnController);
  }

  /**
   * Test for the recipe view/list
   */
  // @Test
  // public void testRecipeView() {

  // ListView<HBoxCell> testRecipeView = lookup("#recipeView").query();
  // List<HBoxCell> recipeList = testRecipeView.getItems();

  // // String textLabelRecipe1 = lookup("#label0").getText();
  // // String textLabelRecipe2 = lookup("#label1").toString();

  // // assertEquals(recipeNames.get(0), textLabelRecipe1, "Recipe in list does not match.");
  // // assertEquals(recipeNames.get(1), textLabelRecipe2, "Recipe in list does not match.");

  // // sjekke at den inneholder de oppskriftene den skal
  // // lag en lignende hjelpemetode som kan kanskje brukes flere steder


  // }

  /**
   * Test for opening a recipe
   */
  // @Test
  // public void testOpenRecipe() {
  // // clickOn("#0"); // knappen til Carbonara
  // // sjekk at det åpner nytt vindu
  // // sjekke at det står riktig ting i textflowene
  // // teste ek riktig overskrift/navn

  // }


  /**
   * Test for opening add new recipe scene
   */
  // @Test
  // public void testOpenAddNewRecipe() {
  // // clickOn("#btnAddNewRecipe");

  // }

  /**
   * Test for delete recipe
   */
  // @Test
  // public void testDeleteRecipe() {
  // // gå inn på en oppskrift
  // // trykke på delete
  // // teste om det kommer dialog boks?
  // // trykk ok, og ut
  // // sjekke at oppskriftsliten har blitt opdatert
  // }

  // hjelpemetoder


  // private void checkRecipeListView() {
  // ListView<HBoxCell> testRecipeView = lookup("#recipeView").query();
  // List<HBoxCell> recipeList = testRecipeView.getItems();
  // int i = 0; // ekstra sikkerhet

  // for (String recipeName : recipeNames) {

  // for (int x = 0; x < recipeNames.size(); x++) {

  // assertTrue(i < recipeNames.size());


  // assertEquals(, ,
  // "Recipename in recipeListView not the same as input");

  // i++;
  // }
  // }
  // assertTrue(i == ingredients.size());

  // }

  /**
   * Clean up method after all tests are finished.
   */
  @AfterAll
  public static void clean() {
    File f = new File(System.getProperty("user.dir"), saveFile);
    f.delete();
  }



}
