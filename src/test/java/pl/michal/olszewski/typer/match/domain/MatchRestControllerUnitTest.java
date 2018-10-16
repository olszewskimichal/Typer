package pl.michal.olszewski.typer.match.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.time.Instant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import pl.michal.olszewski.typer.match.dto.MatchNotFoundException;
import pl.michal.olszewski.typer.match.dto.command.CancelMatch;
import pl.michal.olszewski.typer.match.dto.command.CreateNewMatch;
import pl.michal.olszewski.typer.match.dto.command.FinishMatch;
import pl.michal.olszewski.typer.match.dto.command.IntegrateMatchWithLivescore;
import pl.michal.olszewski.typer.match.dto.read.MatchInfo;

class MatchRestControllerUnitTest {

  private MatchRestController matchRestController;
  private MatchSaver matchSaver = new InMemoryMatchSaver();
  private MatchFinder matchFinder = new InMemoryMatchFinder();
  private MatchRoundFinder matchRoundFinder = new InMemoryMatchRoundFinder();
  private MatchRoundSaver matchRoundSaver = new InMemoryMatchRoundSaver();
  private MatchEventPublisher matchEventPublisher = mock(MatchEventPublisher.class);
  private MatchUpdater matchUpdater = new MatchUpdater(matchFinder, matchSaver, matchEventPublisher);

  @BeforeEach
  void setUp() {
    matchRestController = new MatchRestController(matchRoundFinder, matchFinder, matchSaver, matchUpdater);
  }

  @Test
  void shouldReturnNotFoundWhenMatchByIdNotExist() {
    assertThrows(MatchNotFoundException.class, () -> matchRestController.getMatchInfoById(254L));
  }

  @Test
  void shouldReturnMatchInfoWhenMatchByIdExists() {
    Match match = Match.builder().id(254L).awayTeamId(1L).homeTeamId(2L).matchStatus(MatchStatus.FINISHED).homeGoals(1L).awayGoals(2L).build();
    matchSaver.save(match);

    ResponseEntity<MatchInfo> betInfoById = matchRestController.getMatchInfoById(254L);
    assertThat(betInfoById.getStatusCodeValue()).isEqualTo(200);
    assertThat(betInfoById.getBody()).isNotNull();
    assertThat(betInfoById.getBody().getId()).isEqualTo(254L);

  }

  @Test
  void shouldCreateNewMatch() {
    matchRoundSaver.save(MatchRound.builder().id(1L).build());

    CreateNewMatch createNewMatch = CreateNewMatch.builder().homeTeamId(1L).awayTeamId(2L).roundId(1L).startDate(Instant.now()).build();
    ResponseEntity<String> responseEntity = matchRestController.createNewMatch(createNewMatch);

    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
    assertThat(responseEntity.getBody()).isNull();
  }

  @Test
  void shouldCancelMatch() {
    givenMatchs().buildAndSave(1L, MatchStatus.NEW);

    CancelMatch cancel = CancelMatch.builder().matchId(1L).build();
    ResponseEntity<String> responseEntity = matchRestController.cancelMatch(cancel);

    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
  }

  @Test
  void shouldFinishMatch() {
    givenMatchs().buildAndSave(1L, MatchStatus.NEW);

    FinishMatch finishMatch = FinishMatch.builder().homeGoals(1L).awayGoals(2L).matchId(1L).build();
    ResponseEntity<String> responseEntity = matchRestController.finishMatch(finishMatch);

    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
  }

  @Test
  void shouldIntegrateMatchWithLivescore() {
    givenMatchs().buildAndSave(1L, MatchStatus.NEW);

    IntegrateMatchWithLivescore integrateMatchWithLivescore = IntegrateMatchWithLivescore.builder().livescoreId(1L).livescoreLeagueId(2L).matchId(1L).build();
    ResponseEntity<String> responseEntity = matchRestController.integrateMatchWithLivescore(integrateMatchWithLivescore);

    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
  }

  @AfterEach
  void removeAll() {
    givenMatchs().deleteAll();
  }

  private MatchFactory givenMatchs() {
    return new MatchFactory(matchSaver);
  }

}