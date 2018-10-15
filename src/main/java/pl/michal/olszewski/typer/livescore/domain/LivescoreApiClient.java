package pl.michal.olszewski.typer.livescore.domain;

import java.time.LocalDate;
import pl.michal.olszewski.typer.livescore.read.PastMatchesDTO;
import reactor.core.publisher.Mono;

interface LivescoreApiClient {

  Mono<PastMatchesDTO> getPastMatchesForLeagueFromDate(Long leagueId, LocalDate from);
}
