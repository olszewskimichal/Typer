package pl.michal.olszewski.typer.livescore.domain;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.time.LocalDate;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.michal.olszewski.typer.livescore.dto.command.FinishLivescoreMatch;
import pl.michal.olszewski.typer.livescore.dto.read.MatchDTO;
import pl.michal.olszewski.typer.livescore.dto.read.PastMatchesDTO;
import pl.michal.olszewski.typer.livescore.dto.read.PastMatchesDataDTO;
import pl.michal.olszewski.typer.match.dto.command.CheckMatchResults;
import reactor.core.publisher.Mono;

class CheckMatchResultListenerTest {

  private CheckMatchResultListener commandListener;
  private LivescoreApiClient apiClient;
  private LivescoreMatchEventPublisher publisher;

  @BeforeEach
  void configureSystemUnderTests() {
    apiClient = mock(LivescoreApiClient.class);
    publisher = mock(LivescoreMatchEventPublisher.class);
    commandListener = new CheckMatchResultListener(apiClient, publisher);
  }

  @Test
  void shouldHandleFinishMatchEventFromQueue() {
    //given
    PastMatchesDataDTO pastMatchesDTO = new PastMatchesDataDTO(Collections.singletonList(MatchDTO.builder().score("3-2").id(3L).build()));
    PastMatchesDTO pastMatches = new PastMatchesDTO(true, pastMatchesDTO);
    given(apiClient.getPastMatchesForLeagueFromDate(1L, LocalDate.MIN)).willReturn(Mono.just(pastMatches));
    CheckMatchResults checkMatchResults = CheckMatchResults.builder().livescoreLeagueId(1L).date(LocalDate.MIN).livescoreIds(Collections.singletonList(3L)).build();

    //when
    commandListener.checkMatchCommandJMS(checkMatchResults);

    //then
    Mockito.verify(publisher, times(1)).sendMatchFinishedToJMS(Mockito.any(FinishLivescoreMatch.class));
    Mockito.verifyNoMoreInteractions(publisher);
  }

}