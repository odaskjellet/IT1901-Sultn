package sultn.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import sultn.core.Cookbook;

/**
 * Class handles reading and writing to file. Uses the SultnModule to serialize object to be stored
 * and deserialize JSON to be loaded into application. Should be invoked to save/load Cookbook.
 */
public class SultnPersistence {
  private ObjectMapper mapper;
  private Path saveFilePath = null;

  /**
   * Default constructor.
   */
  public SultnPersistence() {
    mapper = new ObjectMapper();
    mapper.registerModule(new SultnModule());
  }

  public static ObjectMapper createObjectMapper() {
    return new ObjectMapper().registerModule(new SultnModule());
  }

  /**
   * Reads Cookbook from JSON file (deserializers provide objects).
   *
   * @param reader - Object to be read as character stream
   * @return Cookbook object parsed from json.
   */
  private Cookbook readCookbook(Reader reader) throws IOException {
    return mapper.readValue(reader, Cookbook.class);
  }

  /**
   * Writes Cookbook as a formatted file (serializers provide json).
   *
   * @param cookbook - Cookbook to be formatted
   * @param writer - Write to character streams
   */
  private void writeCookbook(Cookbook cookbook, Writer writer) throws IOException {
    mapper.writerWithDefaultPrettyPrinter().writeValue(writer, cookbook);
  }

  /**
   * Set path to working directory (user.dir) with filename.
   *
   * @param saveFile - Filename to be used. Must end with '.json'.
   */
  public void setSaveFile(String saveFile) {
    this.saveFilePath = Paths.get(System.getProperty("user.home"), ".sultn", saveFile);
  }

  /**
   * Load a cookbook from file.
   *
   * @throws IllegalStateException If savefile path is not set.
   */
  public Cookbook loadCookbook() throws IOException, IllegalStateException {
    if (saveFilePath == null) {
      throw new IllegalStateException("Save file path not set...");
    }
    try (Reader reader = new FileReader(saveFilePath.toFile(), StandardCharsets.UTF_8)) {
      return readCookbook(reader);
    }
  }

  /**
   * Save a Cookbook to JSON in your working directory. Path must be set prior to function call.
   *
   * @param cookbook - Cookbook to be saved
   * @throws IllegalStateException If save file path not set.
   */
  public void saveCookbook(Cookbook cookbook) throws IOException, IllegalStateException {
    if (saveFilePath == null) {
      throw new IllegalStateException("Save file path not set...");
    }
    try (Writer writer = new FileWriter(saveFilePath.toFile(), StandardCharsets.UTF_8)) {
      writeCookbook(cookbook, writer);
    }
  }
}
