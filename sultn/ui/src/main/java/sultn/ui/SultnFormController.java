package sultn.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Controller class for the scene which handles the form for adding recipes to the cookbook.
 */
public class SultnFormController extends AbstractFormController {
  @FXML
  private AnchorPane anchorPane;

  @Override
  public void setup() {
    comboBoxInitialize();
    iconBoxInitialize();
    addInstruction();
  }

  /**
   * Make new recipe and save to file, then clear the text fields. Also alerts the user that it was
   * successfull in making a new recipe.
   */
  public void addNewRecipe() {
    try {
      for (TextField text : instructionList.getItems()) {
        if (!text.getText().isEmpty()) {
          this.instructions.add(text.getText());
        }
      }

      restAccess.addRecipe(recipeNameField.getText(), getIconCategory(), tempIngrd, instructions);
      new AlertBox("New recipe added! \n Feel free to add more.", anchorPane);
      clearFormFields();

    } catch (Exception e) {
      new AlertBox("ERROR: Unable to create recipe.\n" + e.getLocalizedMessage(), anchorPane);
    }
  }
}
