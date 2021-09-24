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

/*
This class handles reading and writing to file.
It uses the SutnModule to handle creating files.
This class should be invoked to save/load cookbook.
*/

public class SultnPersistence {
    private ObjectMapper mapper;
    private Path saveFilePath = null;

    public SultnPersistence() {
        mapper = new ObjectMapper();
        mapper.registerModule(new SultnModule());
    }

    public Cookbook readCookbook(Reader reader) throws IOException {
        return mapper.readValue(reader, Cookbook.class);
    }

    public void writeCookBook(Cookbook cookbook, Writer writer) throws IOException {
        // Writes cookbook as a formatted file (serializers provide json)
        mapper.writerWithDefaultPrettyPrinter().writeValue(writer, cookbook);
    }

    // Sets file location to working direcotry with the provided filename.
    public void setSaveFile(String saveFile) {
        this.saveFilePath = Paths.get(System.getProperty("user.dir"), saveFile);
    }


    public Cookbook loadCookbook() throws IOException, IllegalStateException {
        if (saveFilePath == null) {
            throw new IllegalStateException("Save file path not set...");
        }
        try (Reader reader = new FileReader(saveFilePath.toFile(), StandardCharsets.UTF_8)) {
            return readCookbook(reader);
        }
    }

    /*
    Call this class to save a cookbook to json in your working directory.
    Save path must be set beforehand.
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