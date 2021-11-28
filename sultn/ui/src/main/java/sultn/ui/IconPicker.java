package sultn.ui;

import javafx.scene.image.Image;
import sultn.core.Recipe.Category;

/**
 * IconPicker object. Used by other classes to get the icon of recipes.
 */
public class IconPicker {

  private Category category;

  /**
   * IconPicker constructor.
   *
   * @param category - category to get the icon for
   */
  IconPicker(Category category) {
    this.category = category;
  }


  /**
   * Method for getting the icon image.
   *
   * @return Icon as an image
   */
  public Image getIcon() {
    Image icon =
        new Image(this.getClass().getResourceAsStream(getIconPath()), 50, 50, false, false);
    return icon;
  }

  /**
   * Method for getting the corresponding icon to a category.
   *
   * @return Path to icon image.
   */
  public String getIconPath() {
    return "icons/food-icons/icon" + getIconNumber() + ".png";
  }

  /**
   * Method for getting the corresponding index of a category.
   *
   * @return Integer representing the icons index.
   */
  public int getIconNumber() {
    int iconNumber = 0;

    switch (category) {
      case FISH:
        iconNumber = 1;
        break;
      case CHICKEN:
        iconNumber = 2;
        break;
      case MEAT:
        iconNumber = 3;
        break;
      case VEGETARIAN:
        iconNumber = 4;
        break;
      case BREAD:
        iconNumber = 5;
        break;
      case PIZZA:
        iconNumber = 6;
        break;
      case DESSERT:
        iconNumber = 7;
        break;
      case OTHER:
      default:
        iconNumber = 0;
    }
    return iconNumber;
  }
}
