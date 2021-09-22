package json;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.ObjectMapper;

import core.Cookbook;

public class SultnPersistence {
    private ObjectMapper mapper;
    private Path saveFilePath = null;

    public SultnPersistence() {
        mapper = new ObjectMapper();
        mapper.registerModule(new SultnModule());
    }

    public void writeCookBook(Cookbook cookbook, Writer writer) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(writer, cookbook);
    }

    public void saveCookBook(Cookbook cookbook) throws IOException, IllegalStateException {
        if (saveFilePath == null) {
            throw new IllegalStateException("Save file path not set...");
        }
        try (Writer writer = new FileWriter(saveFilePath.toFile(), StandardCharsets.UTF_8)) {
            writeCookBook(cookbook, writer);
        }
    }
}