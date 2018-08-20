package pl.michal.olszewski.typer.livescore;

import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
class LivescoreReactiveWebClient implements LivescoreApiClient {

  private final WebClient client;
  private final LivescoreApiProperties properties;

  public LivescoreReactiveWebClient(WebClient client, LivescoreApiProperties properties) {
    this.client = client;
    this.properties = properties;
  }


  @Override
  public Mono<PastMatchesDTO> getPastMatchesForLeagueFromDate(Long leagueId, LocalDate from) {
    return client.get()
        .uri(
            "scores/history.json?key={key}&secret={secret}&league={leagueId}&from={from}", properties.getKey(), properties.getSecret(), leagueId, from)
        .accept(MediaType.parseMediaType(properties.getContentType()))
        .retrieve()
        .bodyToMono(PastMatchesDTO.class);
  }
}
