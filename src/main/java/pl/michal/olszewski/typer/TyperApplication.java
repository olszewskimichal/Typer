package pl.michal.olszewski.typer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.michal.olszewski.typer.file.FileStorageProperties;

@SpringBootApplication
@EnableJms
@EnableScheduling
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class TyperApplication {

  public static void main(String[] args) {
    SpringApplication.run(TyperApplication.class, args);
  }

  @Bean // Serialize message content to json using TextMessage
  public MessageConverter jacksonJmsMessageConverter() {
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setTargetType(MessageType.TEXT);
    converter.setTypeIdPropertyName("_type");
    ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    converter.setObjectMapper(mapper);
    return converter;
  }
}
