package pl.michal.olszewski.typer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(Channels.class)
public class TyperApplication {

  public static void main(String[] args) {
    SpringApplication.run(TyperApplication.class, args);
  }
}
