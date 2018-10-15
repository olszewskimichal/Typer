package pl.michal.olszewski.typer.livescore.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "livescore")
@Component
@Getter
@Setter
class LivescoreApiProperties {

  private String key;
  private String secret;
  private String url;
  private String contentType;
}
