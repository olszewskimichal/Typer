package pl.michal.olszewski.typer.livescore;

import java.time.LocalDate;
import reactor.core.publisher.Mono;

interface LivescoreApiClient {

  Mono<PastMatchesDTO> getPastMatchesForLeagueFromDate(Long leagueId, LocalDate from);
}
