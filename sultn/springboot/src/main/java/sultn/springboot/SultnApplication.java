package sultn.springboot;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sultn.json.SultnPersistence;

/**
 * The Spring application. Starts the restserver and provides implicit json mapping.
 */
@SpringBootApplication
public class SultnApplication {

  /**
   * Creates an ObjectMapper to be used with Bean. This allows us to pass objects in the body of our
   * requests by serializing and deserialize to JSON.
   *
   * @return The ObjectMapper
   */
  @Bean
  public ObjectMapper sultnObjectMapper() {
    return SultnPersistence.createObjectMapper();
  }

  public static void main(String[] args) {
    SpringApplication.run(SultnApplication.class, args);
  }
}
