package pl.michal.olszewski.typer.match.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.michal.olszewski.typer.RepositoryTestBase;
import pl.michal.olszewski.typer.match.dto.MatchLeagueNotFoundException;

class MatchLeagueFinderTest extends RepositoryTestBase {

  @Autowired
  private MatchLeagueFinder matchLeagueFinder;

  @Test
  void shouldFindMatchLeagueById() {
    //given
    MatchLeague matchLeague = MatchLeague.builder().build();
    Long id = (Long) entityManager.persistAndGetId(matchLeague);
    entityManager.flush();
    entityManager.clear();

    //when
    MatchLeague founded = matchLeagueFinder.findOneOrThrow(id);

    //then
    assertThat(founded).isNotNull().isEqualToComparingFieldByField(matchLeague);
  }

  @Test
  void shouldFindAllMatchLeagues() {
    entityManager.persist(MatchLeague.builder().build());
    entityManager.persist(MatchLeague.builder().build());

    List<MatchLeague> all = matchLeagueFinder.findAll();

    assertThat(all).isNotEmpty().hasSize(2);
  }


  @Test
  void shouldThrowExceptionWhenMatchLeagueNotFound() {
    //given
    //when
    //then
    assertThrows(MatchLeagueNotFoundException.class, () -> matchLeagueFinder.findOneOrThrow(1L));
  }

}