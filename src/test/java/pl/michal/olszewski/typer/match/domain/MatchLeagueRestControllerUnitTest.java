package pl.michal.olszewski.typer.match.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import pl.michal.olszewski.typer.match.dto.MatchLeagueNotFoundException;
import pl.michal.olszewski.typer.match.dto.command.CreateNewLeague;
import pl.michal.olszewski.typer.match.dto.read.MatchLeagueInfo;

class MatchLeagueRestControllerUnitTest {

  private MatchLeagueRestController matchLeagueRestController;
  private MatchLeagueSaver matchLeagueSaver = new InMemoryMatchLeagueSaver();
  private MatchLeagueFinder matchLeagueFinder = new InMemoryMatchLeagueFinder();

  @BeforeEach
  void setUp() {
    matchLeagueRestController = new MatchLeagueRestController(matchLeagueFinder, matchLeagueSaver);
  }

  @Test
  void shouldReturnNotFoundWhenMatchLeagueByIdNotExist() {
    assertThrows(MatchLeagueNotFoundException.class, () -> matchLeagueRestController.getMatchLeagueInfoById(254L));
  }

  @Test
  void shouldReturnMatchLeagueInfoWhenMatchByIdExists() {
    MatchLeague matchLeague = MatchLeague.builder().id(254L).name("nazwa").build();
    matchLeagueSaver.save(matchLeague);

    ResponseEntity<MatchLeagueInfo> betInfoById = matchLeagueRestController.getMatchLeagueInfoById(254L);
    assertThat(betInfoById.getStatusCodeValue()).isEqualTo(200);
    assertThat(betInfoById.getBody()).isNotNull();
    assertThat(betInfoById.getBody().getId()).isEqualTo(254L);

  }

  @Test
  void shouldCreateNewMatchLeague() {
    CreateNewLeague createNewLeague = CreateNewLeague.builder().betTypePolicy(1L).name("name").build();
    ResponseEntity<String> responseEntity = matchLeagueRestController.createNewMatchLeague(createNewLeague);

    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
    assertThat(responseEntity.getBody()).isNull();
  }

  @AfterEach
  void removeAll() {
    matchLeagueSaver.deleteAll();
  }


}