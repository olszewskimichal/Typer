package pl.michal.olszewski.typer.match.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.michal.olszewski.typer.RepositoryTestBase;
import pl.michal.olszewski.typer.match.dto.MatchRoundNotFoundException;

class MatchRoundFinderTest extends RepositoryTestBase {

  @Autowired
  private MatchRoundFinder matchRoundFinder;

  @Test
  void shouldFindMatchRoundById() {
    //given
    MatchRound matchRound = MatchRound.builder().build();
    Long id = (Long) entityManager.persistAndGetId(matchRound);
    entityManager.flush();
    entityManager.clear();

    //when
    MatchRound founded = matchRoundFinder.findOneOrThrow(id);

    //then
    assertThat(founded).isNotNull().isEqualToComparingFieldByField(matchRound);
  }

  @Test
  void shouldThrowExceptionWhenMatchRoundNotFound() {
    //given
    //when
    //then
    assertThrows(MatchRoundNotFoundException.class, () -> matchRoundFinder.findOneOrThrow(1L));
  }

}