package json;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;

import core.Cookbook;

/**
* Class handles reading and writing to file.
* Uses the SultnModule to serialize object to be stored and deserialize
* JSON to be loaded into application.
* Should be invoked to save/load Cookbook.
* 
*/
public class SultnPersistence {
    private ObjectMapper mapper;
    private Path saveFilePath = null;

    /**
     * Default constructor
     * 
     */
    public SultnPersistence() {
        mapper = new ObjectMapper();
        mapper.registerModule(new SultnModule());
    }

    /**
     * Reads Cookbook from JSON file (deserializers provide objects)
     * 
     * @param reader - Object to be read as character stream
     * @return mapper
     * @throws IOException
     */
    private Cookbook readCookbook(Reader reader) throws IOException {
        // mapper provides deserializers via the registered SultnModule.
        return mapper.readValue(reader, Cookbook.class);
    }

    /**
     * Writes Cookbook as a formatted file (serializers provide json)
     * 
     * @param cookbook - Cookbook to be formatted
     * @param writer - Write to character streams
     * @throws IOException
     */
    private void writeCookBook(Cookbook cookbook, Writer writer) throws IOException {
        // mapper provides serializers via the registered SultnModule.
        mapper.writerWithDefaultPrettyPrinter().writeValue(writer, cookbook);
    }

    /**
     * Set path to working directory with filename.
     * 
     * @param saveFile - Filename
     */
    public void setSaveFile(String saveFile) {
        // user.dir is the current working directory. 
        // Will change depending on where the code is run from.
        this.saveFilePath = Paths.get(System.getProperty("user.dir"), saveFile);
    }

    /**
    * Load a cookbook from file.
    * 
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
     * @throws IOException
     * @throws IllegalStateException
     */
    public void saveCookBook(Cookbook cookbook) throws IOException, IllegalStateException {
        if (saveFilePath == null) {
            throw new IllegalStateException("Save file path not set...");
        }
        try (Writer writer = new FileWriter(saveFilePath.toFile(), StandardCharsets.UTF_8)) {
            writeCookBook(cookbook, writer);
        }
    }
}