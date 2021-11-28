package sultn.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import sultn.core.Cookbook;
import sultn.core.Ingredient;
import sultn.core.Recipe;
import sultn.core.Recipe.Category;
import sultn.json.SultnPersistence;

/**
 * A class that provides UI with the connection to rest API through methods with CRUD operations.
 */
public class RestAccess {

  private String protocol = "http";
  private String host = "localhost";
  private int port = 8080;
  private String basePath = "/api";

  private ObjectMapper mapper;

  public RestAccess() {
    this.mapper = SultnPersistence.createObjectMapper();
  }

  /**
   * Setter for cookbook name. Should not have file ending. Used to access correct file on server.
   *
   * @param cookbookName - Name of the cookbook to work on.
   * @throws IllegalArgumentException If cookbook name is blank.
   */
  public void setCookbookName(String cookbookName) throws IllegalArgumentException {
    if (cookbookName.isBlank()) {
      throw new IllegalArgumentException("Cookbook name is blank.");
    }
    basePath = "/" + basePath.split("/")[1] + "/" + cookbookName;
  }

  /**
   * Retrieves the server cookbook.
   *
   * @return The server cookbook.
   * @throws Exception If it fails to retrieve the Cookbook.
   */
  public Cookbook getCookbook() throws Exception {
    String json = get(this.basePath, "application/json; utf-8");

    if (json == null) {
      throw new Exception("Server failed to return requested Cookbook. Returned null instead.");
    }

    return mapper.readValue(json, Cookbook.class);
  }

  /**
   * Retrieves all cookbook names found on server.
   *
   * @return List of cookbook names.
   * @throws IOException If it fails to retrieve cookbook names.
   */
  public List<String> getAllCookbookNames() throws IOException {
    String[] res = get(this.basePath + "/all", "text/plain").split(",");
    List<String> formatted = new ArrayList<String>();

    // Unpack string separated by commas.
    for (String s : res) {
      if (!s.isBlank()) {
        formatted.add(s.trim().replace(".json", ""));
      }
    }
    return formatted;
  }

  /**
   * Retrives the recipe with requested ID from the server.
   *
   * @param id - ID of the recipe to be retrieved.
   * @return The requested recipe.
   * @throws Exception If it fails to retrieve the Recipe.
   */
  public Recipe getRecipe(int id) throws Exception {
    String res = get(this.basePath + "/recipe/" + id, "application/json; utf-8");

    if (res == null) {
      throw new Exception("Server failed to return requested Recipe. Returned null instead.");
    } else if (res.matches(".*\\/d.*")) {
      throw new IOException("HTTP EXCEPTION: " + res);
    }

    return mapper.readValue(res, Recipe.class);
  }

  /**
   * Creates a new recipe based on input and adds it to the server.
   *
   * @param name - Name of the recipe.
   * @param category - Category of the recipe.
   * @param ingredients - Ingredient list for the recipe.
   * @param instructions - Instruction list for the recipe.
   * @throws Exception - If it fails to add a Recipe.
   */
  public void addRecipe(String name, Category category, Collection<Ingredient> ingredients,
      List<String> instructions) throws Exception {
    Recipe recipe = new Recipe(name, 0, ingredients, instructions, category);
    byte[] data = mapper.writeValueAsString(recipe).getBytes(StandardCharsets.UTF_8);
    int res =
        post(data, this.basePath + "/recipe", "application/json; utf-8", "application/json; utf-8");

    if (res != HttpURLConnection.HTTP_CREATED) {
      throw new IOException("HTTP EXCEPTION: " + res);
    }
  }

  /**
   * Deletes the recipe on the server with the provided ID.
   *
   * @param id - ID of the recipe to be deleted.
   * @throws Exception - If it fails to delete recipe.
   */
  public void deleteRecipe(int id) throws Exception {
    int res = delete(this.basePath + "/recipe/" + id);

    if (res != HttpURLConnection.HTTP_OK) {
      throw new IOException("HTTP EXCEPTION: " + res);
    }
  }

  /**
   * Updates the servers version of the recipe with the provided recipe.
   *
   * @param recipe - The recipe to replace server recipe.
   * @throws Exception - If unable to edit recipe and update server.
   */
  public void editRecipe(Recipe recipe) throws Exception {
    byte[] data = mapper.writeValueAsString(recipe).getBytes(StandardCharsets.UTF_8);
    int res =
        put(data, this.basePath + "/recipe", "application/json; utf-8", "application/json; utf-8");

    if (res != HttpURLConnection.HTTP_OK) {
      throw new IOException("HTTP EXCEPTION: " + res);
    }
  }

  /**
   * Creates a HTTP connection based on provided specifications.
   *
   * @param slug - URL slug to extend base URL with.
   * @param method - HTTP method to be used.
   * @param contentType - Content type for the http request.
   * @param accept - Accept type for the http request.
   * @param doesOutput - Determines if the request provides output.
   * @return A new HTTP connection.
   * @throws IOException - If it fails to create a Http Connection.
   */
  private HttpURLConnection createHttpConnection(String slug, String method, String contentType,
      String accept, boolean doesOutput) throws IOException {
    URL url = new URL(this.protocol, this.host, this.port, slug);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod(method);
    if (method.equals("POST") || method.equals("PUT")) {
      connection.setRequestProperty("Content-Type", contentType);
    }
    connection.setRequestProperty("Accept", accept);
    connection.setDoOutput(doesOutput);

    return connection;
  }

  /**
   * Does a HTTP GET request on the provided slug.
   *
   * @param slug - URL slug to be used.
   * @param accept - Accept type for the http request.
   * @return Requested resource string on success or response code as string on fail.
   * @throws IOException If it fails to retrieve from server.
   */
  private String get(String slug, String accept) throws IOException {
    HttpURLConnection connection = createHttpConnection(slug, "GET", null, accept, false);

    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
      BufferedReader inBuf = new BufferedReader(
          new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
      StringBuffer response = new StringBuffer();
      String inLine;
      while ((inLine = inBuf.readLine()) != null) {
        response.append(inLine);
      }
      inBuf.close();

      return response.toString();
    }
    return Integer.toString(connection.getResponseCode());
  }

  /**
   * Does a HTTP POST request with the input data on the provided slug.
   *
   * @param data - Data to be added to the server.
   * @param slug - URL slug to be used.
   * @param contentType - Content type for the http request.
   * @param accept - Accept type for the http request.
   * @return Http response code. 201 Created on success.
   * @throws IOException If it fails to write to server.
   */
  private int post(byte[] data, String slug, String contentType, String accept) throws IOException {
    HttpURLConnection connection = createHttpConnection(slug, "POST", contentType, accept, true);

    try (OutputStream out = connection.getOutputStream()) {
      out.write(data, 0, data.length);
      out.flush();
      out.close();
    }

    int resCode = connection.getResponseCode();
    connection.disconnect();
    return resCode;
  }

  /**
   * Does a HTTP PUT request with the input data on the provided slug.
   *
   * @param data - Data to update the server with.
   * @param slug - URL slug to be used.
   * @param contentType - Content type for the http request.
   * @param accept - Accept type for the http request.
   * @return Http response code. 200 OK on success.
   * @throws IOException If it fails to update the server.
   */
  private int put(byte[] data, String slug, String contentType, String accept) throws IOException {
    HttpURLConnection connection = createHttpConnection(slug, "PUT", contentType, accept, true);

    try (OutputStream out = connection.getOutputStream()) {
      out.write(data, 0, data.length);
      out.flush();
      out.close();
    }

    int resCode = connection.getResponseCode();
    connection.disconnect();
    return resCode;
  }

  /**
   * Does a HTTP DELETE request on the provided slug.
   *
   * @param slug URL slug to be used. Slug should end with ID of recipe to be deleted.
   * @return Http response code. 202 Accepted on success.
   * @throws IOException If it fails to delete the recipe.
   */
  private int delete(String slug) throws IOException {
    String[] slugComponents = slug.split("/");
    int id = Integer.parseInt(slugComponents[slugComponents.length - 1]); // Last components is ID

    HttpURLConnection connection = createHttpConnection(slug, "DELETE",
        "application/x-www-form-urlencoded", "application/x-www-form-urlencoded", true);

    try (OutputStream out = connection.getOutputStream()) {
      byte[] data = ByteBuffer.allocate(id).putInt(id).array();
      out.write(data, 0, id);
      out.flush();
    }

    int resCode = connection.getResponseCode(); // Critical: executes deletion.
    connection.disconnect();
    return resCode;
  }
}
