package json;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.ObjectMapper;

import core.CookBook;

public class SultnPersistence {
    private ObjectMapper mapper;
    private Path saveFilePath = null;

    public SultnPersistence() {
        mapper = new ObjectMapper();
        mapper.registerModule(new SultnModule());
    }

    public void writeCookBook(CookBook cookBook, Writer writer) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(writer, cookBook);
    }

    public void saveCookBook(CookBook cookBook) throws IOException, IllegalStateException {
        if (saveFilePath == null) {
            throw new IllegalStateException("Save file path not set...");
        }
        try (Writer writer = new FileWriter(saveFilePath.toFile(), StandardCharsets.UTF_8)) {
            writeCookBook(cookBook, writer);
        }
    }
}