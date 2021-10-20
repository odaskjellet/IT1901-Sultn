// package ui;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.List;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.testfx.framework.junit5.ApplicationTest;
// import core.Cookbook;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.scene.control.ListView;
// import javafx.stage.Stage;
// import json.SultnPersistence;

// public class SultnFormControllerTest extends ApplicationTest {

// private SultnFormController sultnFormController;
// private SultnController sultnController;

// @Override
// public void start(Stage stage) throws IOException {
// final FXMLLoader loader = new FXMLLoader(getClass().getResource("SultnFormTest.fxml"));
// final Parent root = loader.load();
// this.sultnFormController = loader.getController();
// stage.setScene(new Scene(root));
// stage.show();
// }


// private Cookbook cookbook;
// private static String saveFile = "cookbookTest.json";
// private SultnPersistence persistence = new SultnPersistence();
// private List<String> ingredients = new ArrayList<>();
// private List<String> recipeNames = new ArrayList<>();

// /**
// * Method for setting up a json test file for the tests.
// */
// @BeforeAll
// public static void setupSaveFile() {


// String json =
// """
// "recipes" : [ {
// "name" : "Carbonara",
// "id" : 0,
// "ingredients" : [ {
// "name" : "Eggs",
// "unit" : "pcs",
// "amount" : 2.0
// } ],
// "instructions" : [ "Fry guanciale in a pan.", "Cook pasta.", "Make slurry of eggs, parmigiano,
// and pepper.", "Add al dente pasta to frying pan along with slurry", "Stir constantly", "Add salt
// to taste" ]
// }, {
// "name" : "Pizza",
// "id" : 1,
// "ingredients" : [ {
// "name" : "Mel",
// "unit" : "kg",
// "amount" : 1.0
// }, {
// "name" : "Vann",
// "unit" : "dl",
// "amount" : 5.0
// }, {
// "name" : "Topping",
// "unit" : "stk",
// "amount" : 5.0
// } ],
// "instructions" : [ "Bland mel og vann til en deig.", "La deigen heve.","Rull ut pizza.", "Ta på
// topping.", "Stek i 15 min" ]
// }]
// }
// """;

// File jsonFile = new File(System.getProperty("user.dir"), saveFile);

// Path path = jsonFile.toPath();

// try {
// Files.write(path, json.getBytes());

// } catch (Exception e) {
// System.out.println(e);
// }

// }

// /**
// * Sets up a Cookbook with stored Recipes from JSON.
// */
// @BeforeEach
// public void setupCookbook() {
// ingredients.add("Pepper, 2, kg");
// ingredients.add("Mel, 1, kg");
// recipeNames.add("Carbonara");
// recipeNames.add("Pizza");

// persistence.setSaveFile(saveFile);
// try {
// this.cookbook = persistence.loadCookbook();
// } catch (Exception e) {
// System.err.println(e);
// System.err.println("Couln't load cookbookTest.json");
// }
// assertNotNull(cookbook);

// this.sultnFormController =
// new SultnFormController(this.cookbook, this.sultnController, this.saveFile);
// burde teste items her...?
// se hallvard sin test
// }

// /**
// * Testing controller
// */
// @Test
// public void testController() {
// assertNotNull(this.sultnFormController);
// }


// /**
// * Test for opening adding a new recipe
// */
// @Test
// public void testAddNewRecipe() {

// String newTitleText = "Pepperkake";
// String newIngredient1 = "Pepper";
// String newIngredient2 = "Mel";
// String newAmount1 = "2";
// String newAmount2 = "1";
// String newUnit1 = "kg";
// String newUnit2 = "kg";

// String instruction1 = "Mål opp ingrediensene.";
// String instruction2 = "Bland sammen.";
// String instruction3 = "Stek";


// clickOn("#titleText").write(newTitleText);

// clickOn("#ingredientText").write(newIngredient1);
// clickOn("#ingredientAmnt").write(newAmount1);
// clickOn("#unitBox").write(newUnit1);
// clickOn("#addIngredient");

// clickOn("#ingredientText").write(newIngredient2);
// clickOn("#ingredientAmnt").write(newAmount2);
// clickOn("#unitBox").write(newUnit2);
// clickOn("#addIngredient");

// sjekker at listview stemmer
// checkIngredientListView();

// clickOn("#instructionsText").write(instruction1);
// clickOn("#instructionsText").write("\n");
// clickOn("#instructionsText").write(instruction2);
// clickOn("#instructionsText").write("\n");
// clickOn("#instructionsText").write(instruction3);

// clickOn("#finish");
// sjekke at den blir lagret
// sjekke at oppskriftsliten har blitt oppdatert
// kan denne kanskje splittes litt opp?
// }


// hjelpemetoder

// private void checkIngredientListView() {
// ListView<String> ingredientListView = lookup("#listIngredients").query();
// List<String> listView = ingredientListView.getItems();

// int i = 0; // ekstra sikkerhet

// for (String ingredient : ingredients) {
// String[] inputText = ingredient.split(", ");
// String[] listViewText = listView.get(i).split(" ");
// for (int x = 0; x < inputText.length; x++) {
// assertTrue(i < ingredients.size());

// assertEquals(inputText[x], listViewText[x], "Word in listview not the same as input");

// i++;
// }
// }
// assertTrue(i == ingredients.size());
// }

// /**
// * Clean up method after all tests are finished.
// */
// @AfterAll
// public static void clean() {
// File f = new File(System.getProperty("user.dir"), saveFile);
// f.delete();
// }

// }
