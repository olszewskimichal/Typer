package pl.michal.olszewski.typer.match.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.michal.olszewski.typer.RepositoryTestBase;
import pl.michal.olszewski.typer.match.dto.MatchNotFoundException;

class MatchFinderTest extends RepositoryTestBase {

  @Autowired
  private MatchFinder matchFinder;

  @Autowired
  private MatchSaver matchSaver;

  @Test
  void shouldFindMatchById() {
    //given
    Match match = givenPersistedMatch()
        .buildAndSave(null, MatchStatus.FINISHED);
    //when
    Match founded = matchFinder.findOneOrThrow(match.getId());

    //then
    assertThat(founded).isNotNull().isEqualToComparingFieldByField(match);
  }

  @Test
  void shouldFindMatchByStatusWithNotNulllivescoreId() {
    givenPersistedMatch()
        .buildAndSave(1L, MatchStatus.FINISHED, 3L);
    givenPersistedMatch()
        .buildAndSave(2L, MatchStatus.NEW, null);
    Match match = givenPersistedMatch()
        .buildAndSave(3L, MatchStatus.NEW, 3L);

    List<Match> matchList = matchFinder.findByStatusForLivescoreUpdate(MatchStatus.NEW);

    assertThat(matchList).isNotNull().isNotEmpty().hasSize(1).contains(match);
  }

  @Test
  void shouldThrowExceptionWhenMatchNotFound() {
    //given
    //when
    //then
    assertThrows(MatchNotFoundException.class, () -> matchFinder.findOneOrThrow(1L));
  }

  private MatchFactory givenPersistedMatch() {
    return new MatchFactory(matchSaver);
  }


}