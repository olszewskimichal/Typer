package pl.michal.olszewski.typer.match.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.michal.olszewski.typer.RepositoryTestBase;
import pl.michal.olszewski.typer.match.dto.MatchNotFoundException;

class MatchFinderTest extends RepositoryTestBase {

  @Autowired
  private MatchFinder matchFinder;

  @Autowired
  private MatchSaver matchSaver;

  @BeforeEach
  void setUp() {
    matchSaver.deleteAll();
  }

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
  void shouldFindAllMatches() {
    givenPersistedMatch().buildAndSave(null, MatchStatus.FINISHED);
    givenPersistedMatch().buildAndSave(null, MatchStatus.NEW);

    List<Match> all = matchFinder.findAll();

    assertThat(all).isNotEmpty().hasSize(2);
  }

  @Test
  void shouldFindMatchByStatusWithNotNullLivescoreId() {
    givenPersistedMatch()
        .buildLivescoreMatchAndSave(null, MatchStatus.FINISHED, 3L, null);
    givenPersistedMatch()
        .buildLivescoreMatchAndSave(null, MatchStatus.NEW, null, null);
    Match match = givenPersistedMatch()
        .buildLivescoreMatchAndSave(null, MatchStatus.NEW, 3L, null);

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