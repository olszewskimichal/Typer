package pl.michal.olszewski.typer.match.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import pl.michal.olszewski.typer.match.dto.MatchRoundNotFoundException;
import pl.michal.olszewski.typer.match.dto.command.CreateNewRound;
import pl.michal.olszewski.typer.match.dto.read.MatchRoundInfo;

class MatchRoundRestControllerUnitTest {

  private MatchRoundRestController matchRoundRestController;
  private MatchRoundSaver matchRoundSaver = new InMemoryMatchRoundSaver();
  private MatchRoundFinder matchRoundFinder = new InMemoryMatchRoundFinder();
  private MatchLeagueFinder matchLeagueFinder = new InMemoryMatchLeagueFinder();
  private MatchLeagueSaver matchLeagueSaver = new InMemoryMatchLeagueSaver();

  @BeforeEach
  void setUp() {
    matchRoundRestController = new MatchRoundRestController(matchRoundFinder, matchLeagueFinder, matchRoundSaver);
  }

  @Test
  void shouldReturnNotFoundWhenMatchRoundByIdNotExist() {
    assertThrows(MatchRoundNotFoundException.class, () -> matchRoundRestController.getMatchRoundInfoById(254L));
  }

  @Test
  void shouldReturnMatchRoundInfoWhenMatchByIdExists() {
    MatchRound matchRound = MatchRound.builder().id(254L).name("nazwa").build();
    matchRoundSaver.save(matchRound);

    ResponseEntity<MatchRoundInfo> betInfoById = matchRoundRestController.getMatchRoundInfoById(254L);
    assertThat(betInfoById.getStatusCodeValue()).isEqualTo(200);
    assertThat(betInfoById.getBody()).isNotNull();
    assertThat(betInfoById.getBody().getId()).isEqualTo(254L);

  }

  @Test
  void shouldCreateNewMatchRound() {
    matchLeagueSaver.save(MatchLeague.builder().id(1L).build());

    CreateNewRound createNewRound = CreateNewRound.builder().leagueId(1L).name("name").build();
    ResponseEntity<String> responseEntity = matchRoundRestController.createNewMatchRound(createNewRound);

    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
    assertThat(responseEntity.getBody()).isNull();
  }

  @AfterEach
  void removeAll() {
    matchRoundSaver.deleteAll();
    matchLeagueSaver.deleteAll();
  }


}