package pl.michal.olszewski.typer.match.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import pl.michal.olszewski.typer.match.dto.CreateNewMatch;

class MatchCreatorTest {

  private MatchCreator matchCreator = new MatchCreator();

  @Test
  void shouldThrowExceptionWhenCommandIsNull() {
    assertThrows(NullPointerException.class, () -> matchCreator.from(null));
  }

  @Test
  void shouldCreateMatchWhenCommandIsValid() {
    //given
    Match expected = Match.builder().homeTeamId(1L).awayTeamId(2L).build();

    CreateNewMatch createNewMatch = CreateNewMatch
        .builder()
        .awayTeamId(1L)
        .homeTeamId(2L)
        .build();
    //when
    Match from = matchCreator.from(createNewMatch);
    //then
    assertThat(from).isNotNull();
    assertThat(from).isEqualToComparingFieldByField(expected);
  }

  @Test
  void shouldThrowExceptionWhenOneOfTeamsIsNull() {
    //given
    CreateNewMatch createNewMatch = CreateNewMatch.builder().awayTeamId(null).homeTeamId(1L).build();

    //when
    //then
    assertThrows(Exception.class, () -> matchCreator.from(createNewMatch));
  }

  @Test
  void shouldThrowExceptionWhenHomeTeamIsTheSameAsAwayTeam() {
    //given
    CreateNewMatch createNewMatch = CreateNewMatch.builder().awayTeamId(1L).homeTeamId(1L).build();

    //when
    //then
    assertThrows(Exception.class, () -> matchCreator.from(createNewMatch));
  }

}