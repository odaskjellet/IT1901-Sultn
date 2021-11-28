package sultn.springboot;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import org.springframework.stereotype.Service;
import sultn.core.Cookbook;
import sultn.core.Recipe;
import sultn.json.SultnPersistence;

/**
 * Class that configures the Sultn service. Operates and maintains the persistance layer and
 * provides the functionality for the API.
 */
@Service
public class SultnService {
  private SultnPersistence sultnPersistence;
  private Cookbook cookbook;

  /**
   * Constructor initializing persistence.
   */
  public SultnService() {
    this.sultnPersistence = new SultnPersistence();
  }

  /**
   * Setter for selected cookbook. Updates persistence and loads selected cookbook. Will load
   * default cookbook if file is not found.
   *
   * @param cookbookName - Name of cookbook to load. Need not have file ending.
   */
  public void setCookbookName(String cookbookName) {
    String saveFile = cookbookName;
    if (!cookbookName.endsWith(".json")) {
      saveFile += ".json";
    }
    this.sultnPersistence.setSaveFile(saveFile);

    File cookbookDir = Path.of(System.getProperty("user.home"), ".sultn").toFile();
    if (cookbookDir.exists() || cookbookDir.mkdir()) {
      // Checks for existing file to load or loads default.
      File cookbookFile = new File(cookbookDir, saveFile);
      if (cookbookFile.exists()) {
        try {
          this.cookbook = sultnPersistence.loadCookbook();
        } catch (IllegalStateException | IOException e) {
          this.cookbook = createDefault();
          e.printStackTrace();
        }
      } else {
        this.cookbook = createDefault();
      }
    }
  }

  /**
   * Gets the Cookbook-object.
   */
  public Cookbook getCookbook() {
    return this.cookbook;
  }

  public String[] getAllCookbookNames() {
    File cookbookDir = Path.of(System.getProperty("user.home"), ".sultn").toFile();
    return cookbookDir.list();
  }

  /**
   * Adds a new Recipe-object to a Cookbook-object.
   *
   * @param recipe - The object to be added
   */
  public void addRecipe(Recipe recipe) {
    cookbook.makeNewRecipe(recipe.getName(), recipe.getInstructions(), recipe.getIngredients(),
        recipe.getCategory());
  }

  /**
   * Gets a Recipe-object from a Cookbook-object by ID.
   *
   * @param id - Recipe-object ID to be obtained.
   * @return the Recipe-object requested.
   */
  public Recipe getRecipe(int id) {
    return cookbook.getRecipe(id);
  }

  /**
   * Creates a default Cookbook-object from a file with sample recipes. If unsuccessful in obtaining
   * the URL, it returns an empty Cookbook-object.
   *
   * @return the Cookbook-object
   */
  private static Cookbook createDefault() {
    File defaultFile =
        Path.of(System.getProperty("user.dir"), "src", "main", "resources", "default-cookbook.json")
            .toFile();

    if (defaultFile.exists()) {
      try {
        ObjectMapper mapper = SultnPersistence.createObjectMapper();
        return mapper.readValue(defaultFile, Cookbook.class);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return new Cookbook();
  }

  /**
   * Saves a Cookbook-object.
   */
  public void autoSaveCookbook() {
    try {
      sultnPersistence.saveCookbook(cookbook);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
