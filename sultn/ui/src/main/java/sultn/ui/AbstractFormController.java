package sultn.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sultn.core.Ingredient;
import sultn.core.Recipe.Category;

/**
 * Specialized abstract controller class, specific for form type controllers. Provides boilerplate
 * code for more FXML elements.
 */
public abstract class AbstractFormController extends AbstractController {

  @FXML
  private AnchorPane anchorPane;
  @FXML
  protected TextField recipeNameField;
  @FXML
  protected ComboBox<Image> categoryIconField;
  @FXML
  protected TextField ingredientNameField;
  @FXML
  protected TextField ingredientAmountField;
  @FXML
  protected ComboBox<String> ingredientUnitField;
  @FXML
  protected ListView<String> ingredientList;
  @FXML
  protected ListView<TextField> instructionList;

  protected Collection<Ingredient> tempIngrd = new ArrayList<>();
  protected List<String> instructions = new ArrayList<>();
  protected static final List<String> units = Collections.unmodifiableList(Arrays.asList("l", "dl",
      "ml", "kg", "g", "tbsp", "tsp", "pinch", "pcs", "cup", "oz", "fl.oz"));

  /**
   * Add new ingredient to tempIngrd, and show on interface. Displays exceptions to the user to
   * ensure proper formatting.
   */
  public final void addIngredient() {
    try {
      String ingrName = ingredientNameField.getText();
      String ingrAmountStr = ingredientAmountField.getText();
      if (ingrAmountStr.isBlank()) {
        throw new Exception("Ingredient amount is empty. Must be a number. 1, 3.14, etc.");
      }
      Double ingrAmount;
      try {
        ingrAmount = Double.parseDouble(ingrAmountStr);
      } catch (Exception e) {
        throw new Exception("Ingredient amount is empty. Must be a number. 1, 3.14, etc.");
      }

      String ingrUnit = ingredientUnitField.getValue();

      String fullIngr = ingrName + " " + ingrAmount + " " + ingrUnit;
      Ingredient newIngr = new Ingredient(ingrName, ingrAmount, ingrUnit);
      tempIngrd.add(newIngr);
      ingredientList.getItems().add(fullIngr);

      clearIngredientFields();
    } catch (Exception e) {
      new AlertBox(e.getLocalizedMessage(), anchorPane);
    }
  }

  /**
   * Clears the ingredient fields of the form.
   */
  public final void clearIngredientFields() {
    ingredientNameField.clear();
    ingredientAmountField.clear();
    ingredientUnitField.valueProperty().set("");
  }

  /**
   * Adds instruction to a list.
   */
  public void addInstruction() {
    TextField newTextField = new TextField();
    newTextField.setPromptText("Write an instruction here");
    newTextField.setId("instruction-" + instructionList.getItems().size());
    instructionList.getItems().add(newTextField);
  }

  /**
   * Clears the fields of the form after passing to cookbook.
   */
  public final void clearFormFields() {
    tempIngrd.clear();
    recipeNameField.clear();
    ingredientAmountField.clear();
    ingredientUnitField.valueProperty().set("");
    ingredientNameField.clear();
    instructionList.getItems().clear();
    instructions.clear();
    ingredientList.getItems().clear();
    categoryIconField.getSelectionModel().clearSelection();
    addInstruction();
  }

  /**
   * Initializes the ComboBox with options from the list.
   */
  public final void comboBoxInitialize() {
    ingredientUnitField.getItems().addAll(units);
    ingredientUnitField.setValue("");
  }

  /**
   * Initializes the ComboBox with icon options.
   */
  public final void iconBoxInitialize() {
    final ObservableList<Image> icons = fetchIcons();
    categoryIconField.getItems().addAll(icons);
    categoryIconField.setButtonCell(new ImageListCell());
    categoryIconField.setCellFactory(listView -> new ImageListCell());
    categoryIconField.getSelectionModel().selectFirst();
  }

  /**
   * Method for fetching icons to be used in iconComboBox.
   *
   * @return List of icons. It can be empty if no files are found in food-icons folder.
   */
  private final ObservableList<Image> fetchIcons() {
    final ObservableList<Image> images = FXCollections.observableArrayList();
    final int imgWidth = 39;
    final int imgHeight = imgWidth;

    File folder =
        new File(System.getProperty("user.dir") + "/src/main/resources/sultn/ui/icons/food-icons");
    File[] listOfIcons = folder.listFiles();

    if (listOfIcons != null) {

      for (int i = 0; i < listOfIcons.length; i++) {
        if (listOfIcons[i].isFile()) {
          FileInputStream inputstream;

          try {
            inputstream = new FileInputStream(listOfIcons[i].getAbsolutePath());
            Image image = new Image(inputstream, imgWidth, imgHeight, true, true);
            images.add(image);
          } catch (FileNotFoundException e) {
            System.err.println(e);
            e.printStackTrace();
          }
        }
      }
    }
    return images;
  }

  /**
   * Method for getting the chosen icon and category.
   *
   * @return - Category of corresponding icon.
   */
  protected final Category getIconCategory() {

    Category selectedCategory = Category.OTHER;

    switch (categoryIconField.getSelectionModel().getSelectedIndex()) {
      case 1:
        selectedCategory = Category.FISH;
        break;
      case 2:
        selectedCategory = Category.CHICKEN;
        break;
      case 3:
        selectedCategory = Category.MEAT;
        break;
      case 4:
        selectedCategory = Category.VEGETARIAN;
        break;
      case 5:
        selectedCategory = Category.BREAD;
        break;
      case 6:
        selectedCategory = Category.PIZZA;
        break;
      case 7:
        selectedCategory = Category.DESSERT;
        break;
      case 0:
      default:
        selectedCategory = Category.OTHER;
        break;
    }
    return selectedCategory;
  }

  /**
   * Class for setting an ImageView in a Combobox. Has one method updateItem() that sets the
   * graphic/image in the ImageView.
   */
  static class ImageListCell extends ListCell<Image> {
    private final ImageView view;

    ImageListCell() {
      setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
      view = new ImageView();
    }

    @Override
    protected void updateItem(Image item, boolean empty) {
      super.updateItem(item, empty);
      if (item == null || empty) {
        setGraphic(null);
      } else {
        view.setImage(item);
        setGraphic(view);
      }
    }
  }
}
