package pl.michal.olszewski.typer.livescore;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Slf4j
class LivescoreWebClientConfig {

  @Bean
  WebClient livescoreWebClient(LivescoreApiProperties properties) {
    return WebClient.builder()
        .baseUrl(properties.getUrl())
        .defaultHeader(HttpHeaders.CONTENT_TYPE, properties.getContentType())
        .filter(logRequest())
        .build();
  }

  private ExchangeFilterFunction logRequest() {
    return (clientRequest, next) -> {
      log.debug("Request: {} {}", clientRequest.method(), clientRequest.url());
      return next.exchange(clientRequest);
    };
  }

}
