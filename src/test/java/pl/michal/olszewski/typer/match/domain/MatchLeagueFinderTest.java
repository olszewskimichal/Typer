package pl.michal.olszewski.typer.match.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.HashSet;
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
  void shouldThrowExceptionWhenMatchLeagueNotFound() {
    //given
    //when
    //then
    assertThrows(MatchLeagueNotFoundException.class, () -> matchLeagueFinder.findOneOrThrow(1L));
  }

  @Test
  void shouldFindLeagueIdForRounds() {
    MatchRound matchRound = MatchRound.builder().name("runda1").build();
    entityManager.persist(matchRound);
    MatchRound matchRound2 = MatchRound.builder().name("runda2").build();
    entityManager.persist(matchRound2);
    MatchLeague matchLeague = MatchLeague.builder().build();
    Long id = (Long) entityManager.persistAndGetId(matchLeague);
    matchLeague.addMatchRound(matchRound);
    matchLeague.addMatchRound(matchRound2);
    entityManager.flush();
    entityManager.clear();

    List<Long> leagueIdsForRounds = matchLeagueFinder.findLeagueIdsForRounds(new HashSet<>(Arrays.asList(matchRound.getId(), matchRound2.getId())));
    assertThat(leagueIdsForRounds).hasSize(1).contains(id);
  }

}