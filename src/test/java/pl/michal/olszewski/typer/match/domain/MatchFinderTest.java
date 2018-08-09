package pl.michal.olszewski.typer.match.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.michal.olszewski.typer.RepositoryTestBase;
import pl.michal.olszewski.typer.match.dto.MatchNotFoundException;

class MatchFinderTest extends RepositoryTestBase {

  @Autowired
  private MatchFinder matchFinder;

  @Test
  void shouldFindMatchById() {
    //given
    Match match = Match.builder().build();
    Long id = (Long) entityManager.persistAndGetId(match);
    entityManager.flush();
    entityManager.clear();

    //when
    Match founded = matchFinder.findOneOrThrow(id);

    //then
    assertThat(founded).isNotNull().isEqualToComparingFieldByField(match);
  }

  @Test
  void shouldThrowExceptionWhenMatchNotFound() {
    //given
    //when
    //then
    assertThrows(MatchNotFoundException.class, () -> matchFinder.findOneOrThrow(1L));
  }

}