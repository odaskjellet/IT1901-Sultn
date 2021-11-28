package sultn.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sultn.core.Cookbook;
import sultn.core.Recipe;

/**
 * Class for the API-layer and service implementation.
 */
@RestController
@RequestMapping(path = "api/")
public class SultnController {

  private final SultnService sultnService;

  /**
   * The constructor for this class initializes the private service-object.
   *
   * @param sultnService - The service-object.
   */
  @Autowired
  SultnController(SultnService sultnService) {
    this.sultnService = sultnService;
  }

  /**
   * Saves the cookbook in sultnService.
   */
  private void autoSaveCookbook() {
    sultnService.autoSaveCookbook();
  }

  /**
   * Gets the a single Cookbook-object.
   *
   * @return the Cookbook-object requested.
   */
  @GetMapping(value = "{cookbook}", produces = {MediaType.APPLICATION_JSON_VALUE})
  public Cookbook getCookbook(@PathVariable("cookbook") String cookbook) {
    sultnService.setCookbookName(cookbook);
    autoSaveCookbook();
    return sultnService.getCookbook();
  }

  /**
   * Retrieves all cookbook names found on server.
   *
   * @return String of cookbook names separated by comma.
   */
  @GetMapping(value = "all", produces = {MediaType.TEXT_PLAIN_VALUE})
  public String getAllCookbooks() {
    String[] cookbookNames = sultnService.getAllCookbookNames();

    // Cookbook names as string separated by commas.
    String res = "";
    if (cookbookNames != null) {
      for (String s : cookbookNames) {
        res += s + ", ";
      }
    }
    return res;
  }

  /**
   * Queries a recipe by ID.
   *
   * @param id - ID of recipe to get.
   * @return the Recipe-object response
   */
  @GetMapping(value = "{cookbook}/recipe/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
  public Recipe getRecipe(@PathVariable("cookbook") String cookbook, @PathVariable("id") int id) {
    sultnService.setCookbookName(cookbook);
    autoSaveCookbook();
    return sultnService.getRecipe(id);
  }

  /**
   * Posts to add a new Recipe-object to restserver.
   *
   * @param recipe - The Recipe-object to be added to a Cookbook.
   * @return Http status.
   */
  @PostMapping(value = "{cookbook}/recipe", consumes = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<String> addNewRecipe(@PathVariable("cookbook") String cookbook,
      @RequestBody Recipe recipe) {
    sultnService.setCookbookName(cookbook);
    sultnService.addRecipe(recipe);
    autoSaveCookbook();
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  /**
   * Replaces an existing Recipe-object with new values.
   *
   * @param recipe - The Recipe-object to be replaced
   * @return true
   */
  @PutMapping(value = "{cookbook}/recipe", consumes = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<String> editRecipe(@PathVariable("cookbook") String cookbook,
      @RequestBody Recipe recipe) {
    sultnService.setCookbookName(cookbook);
    sultnService.getCookbook().editRecipe(recipe);
    autoSaveCookbook();
    return ResponseEntity.ok().build();
  }

  /**
   * Deletes a Recipe-object from the Cookbook-object.
   *
   * @param id - The ID of the Recipe-object to delete.
   * @return true
   */
  @DeleteMapping("{cookbook}/recipe/{id}")
  public ResponseEntity<String> deleteRecipe(@PathVariable("cookbook") String cookbook,
      @PathVariable("id") int id) {
    sultnService.setCookbookName(cookbook);
    sultnService.getCookbook().deleteRecipe(id);
    autoSaveCookbook();
    return ResponseEntity.ok().build();
  }
}
