package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import core.Cookbook;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import json.SultnPersistence;


public class RecipeControllerTest extends ApplicationTest {

  private RecipeController recipeController;

  @Override
  public void start(Stage stage) throws IOException {
    final FXMLLoader loader = new FXMLLoader(getClass().getResource("RecipeTest.fxml"));
    final Parent root = loader.load();
    this.recipeController = loader.getController();
    stage.setScene(new Scene(root));
    stage.show();
  }

  private Cookbook cookbook;
  private static String saveFile = "cookbookTest.json";
  private SultnPersistence persistence = new SultnPersistence();
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
  }

  public void setUpRecipe() {
    recipeController.initData(cookbook, 0, saveFile);
  }


  /**
   * Testing controller
   */
  @Test
  public void testController() {
    assertNotNull(this.recipeController);
  }


  /**
   * Testing text fields
   */
  // @Test
  // public void testRecipeFields() {

  // // String recipeTitle = lookup(".label");
  // // assertEquals(recipeNames.get(0), recipeTitle, "Recipe title does not match recipe name");


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
  private Node findNode(Predicate<Node> nodeTest, int num) {
    int count = 0;
    for (Node node : lookup(nodeTest).queryAll()) {
      if (nodeTest.test(node) && count++ == num) {
        return node;
      }
    }
    return null;
  }

  /**
   * Clean up method after all tests are finished.
   */
  @AfterAll
  public static void clean() {
    File f = new File(System.getProperty("user.dir"), saveFile);
    f.delete();
  }


}
