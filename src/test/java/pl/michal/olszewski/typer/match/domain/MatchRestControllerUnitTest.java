package pl.michal.olszewski.typer.match.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import pl.michal.olszewski.typer.match.dto.MatchNotFoundException;
import pl.michal.olszewski.typer.match.dto.command.CreateNewMatch;
import pl.michal.olszewski.typer.match.dto.read.MatchInfo;

class MatchRestControllerUnitTest {

  private MatchRestController matchRestController;
  private MatchSaver matchSaver = new InMemoryMatchSaver();
  private MatchFinder matchFinder = new InMemoryMatchFinder();
  private MatchRoundFinder matchRoundFinder = new InMemoryMatchRoundFinder();
  private MatchRoundSaver matchRoundSaver = new InMemoryMatchRoundSaver();

  @BeforeEach
  void setUp() {
    matchRestController = new MatchRestController(matchRoundFinder, matchFinder, matchSaver);
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

  @AfterEach
  void removeAll() {
    givenMatchs().deleteAll();
  }

  private MatchFactory givenMatchs() {
    return new MatchFactory(matchSaver);
  }

}