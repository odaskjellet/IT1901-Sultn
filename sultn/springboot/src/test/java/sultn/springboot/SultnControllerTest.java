package sultn.springboot;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.jupiter.api.Assertions.assertEquals;
import sultn.core.Cookbook;
import sultn.core.Ingredient;
import sultn.core.Recipe.Category;
import sultn.core.Recipe;
import sultn.json.SultnPersistence;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {SultnController.class, SultnService.class, SultnApplication.class})
@WebMvcTest
class SultnControllerTest {

  private Cookbook cookbook = new Cookbook();
  ObjectMapper objectMapper;
  String cookbookName = "TEST_CB";

  @Autowired
  private MockMvc mvc;

  @BeforeEach
  void setup() {
    File sultnDir = Path.of(System.getProperty("user.home"), ".sultn").toFile();
    if (!sultnDir.exists()) {
      sultnDir.mkdir();
    }

    File testCb = new File(sultnDir, cookbookName + ".json");
    if (testCb.exists()) {
      testCb.delete();
    }

    this.objectMapper = SultnPersistence.createObjectMapper();
    try {
      this.cookbook = objectMapper.readValue(
          new File(System.getProperty("user.dir") + "/src/test/resources/default-cookbook.json"),
          Cookbook.class);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @AfterEach
  public void clean() {
    File f =
        new File(System.getProperty("user.home") + String.format("/.sultn/%s.json", cookbookName));
    if (f.exists()) {
      f.delete();
    }
  }

  @Test
  void testGetCookbook() {
    Cookbook serverCookbook;
    try {
      RequestBuilder request = MockMvcRequestBuilders.get(String.format("/api/%s", cookbookName));
      MvcResult result = mvc.perform(request).andReturn();
      serverCookbook =
          objectMapper.readValue(result.getResponse().getContentAsString(), Cookbook.class);
    } catch (Exception e) {
      serverCookbook = new Cookbook();
      e.printStackTrace();
    }

    List<Recipe> origRecipes = cookbook.getRecipes();
    List<Recipe> restRecipes = serverCookbook.getRecipes();

    for (int i = 0; i < origRecipes.size(); i++) {
      Recipe origRecipe = origRecipes.get(i);
      Recipe restRecipe = restRecipes.get(i);

      assertEquals(restRecipe.getName(), origRecipe.getName());
      assertEquals(restRecipe.getId(), origRecipe.getId());
      assertEquals(restRecipe.getInstructions(), origRecipe.getInstructions());

      Collection<Ingredient> origIngs = origRecipe.getIngredients();
      Collection<Ingredient> restIngs = restRecipe.getIngredients();

      assertEquals(origIngs.size(), restIngs.size());
    }
  }

  @Test
  void testDeleteRecipe() {
    Ingredient i = new Ingredient("Broccoli", 2.0, "dl");
    Collection<Ingredient> ings = new ArrayList<Ingredient>();
    ings.add(i);

    List<String> inst = new ArrayList<String>();
    inst.add("Pull");
    inst.add("Push");

    Recipe r = new Recipe("Broccoli", 999, ings, inst, Category.BREAD);
    ObjectMapper mapper = SultnPersistence.createObjectMapper();

    try {
      String data = mapper.writeValueAsString(r);
      byte[] dataByte = data.getBytes();
      String dataEncoded = new String(dataByte, StandardCharsets.UTF_8);

      RequestBuilder request = MockMvcRequestBuilders
          .post(String.format("/api/%s/recipe", cookbookName)).content(dataEncoded)
          .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE);
      mvc.perform(request);
    } catch (Exception e) {
      e.printStackTrace();
    }

    Recipe serverRecipe;
    try {
      RequestBuilder request =
          MockMvcRequestBuilders.get(String.format("/api/%s/recipe/126", cookbookName));
      MvcResult result = mvc.perform(request).andReturn();
      serverRecipe =
          objectMapper.readValue(result.getResponse().getContentAsString(), Recipe.class);

      RequestBuilder request2 = MockMvcRequestBuilders
          .delete(String.format("/api/%s/recipe/", cookbookName) + serverRecipe.getId());
      MvcResult result2 = mvc.perform(request2).andReturn();
      int resCode = result2.getResponse().getStatus();
      assertEquals(200, resCode);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  void testGetRecipe() {
    Recipe serverRecipe;
    try {
      RequestBuilder request =
          MockMvcRequestBuilders.get(String.format("/api/%s/recipe/125", cookbookName));
      MvcResult result = mvc.perform(request).andReturn();
      serverRecipe =
          objectMapper.readValue(result.getResponse().getContentAsString(), Recipe.class);
    } catch (Exception e) {
      serverRecipe = null;
      e.printStackTrace();
    }

    Recipe origRecipe = cookbook.getRecipe(125);

    assertEquals(origRecipe.getName(), serverRecipe.getName());
    assertEquals(origRecipe.getId(), serverRecipe.getId());

    Collection<Ingredient> origIngs = origRecipe.getIngredients();
    Collection<Ingredient> serverIngs = serverRecipe.getIngredients();

    assertEquals(origIngs.size(), serverIngs.size());
  }

  @Test
  @Order(1)
  void testAddNewRecipe() {
    Ingredient i = new Ingredient("Broccoli", 2.0, "dl");
    List<Ingredient> ings = new ArrayList<Ingredient>();
    ings.add(i);

    List<String> inst = new ArrayList<String>();
    inst.add("Pull");
    inst.add("Push");

    Recipe r = new Recipe("Broccoli", 999, ings, inst, Category.BREAD);
    ObjectMapper mapper = SultnPersistence.createObjectMapper();

    try {
      String data = mapper.writeValueAsString(r);
      byte[] dataByte = data.getBytes();
      String dataEncoded = new String(dataByte, StandardCharsets.UTF_8);

      RequestBuilder request = MockMvcRequestBuilders
          .post(String.format("/api/%s/recipe", cookbookName)).content(dataEncoded)
          .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE);
      MvcResult result = mvc.perform(request).andReturn();
      int resCode = result.getResponse().getStatus();
      assertEquals(201, resCode);
    } catch (Exception e) {
      e.printStackTrace();
    }

    Ingredient i2 = new Ingredient("Broccoli2", 2.0, "dl");
    List<Ingredient> ings2 = new ArrayList<Ingredient>();
    ings2.add(i2);

    List<String> inst2 = new ArrayList<String>();
    inst2.add("Pull");
    inst2.add("Push");

    Recipe r2 = new Recipe("Broccoli2", 126, ings2, inst2, Category.BREAD);
    ObjectMapper mapper2 = SultnPersistence.createObjectMapper();

    try {
      String data = mapper2.writeValueAsString(r2);
      byte[] dataByte = data.getBytes();
      String dataEncoded = new String(dataByte, StandardCharsets.UTF_8);

      RequestBuilder request = MockMvcRequestBuilders
          .put(String.format("/api/%s/recipe", cookbookName)).content(dataEncoded)
          .contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE);
      MvcResult result = mvc.perform(request).andReturn();
      int resCode = result.getResponse().getStatus();
      assertEquals(200, resCode);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
