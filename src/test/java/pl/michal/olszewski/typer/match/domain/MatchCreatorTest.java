package pl.michal.olszewski.typer.match.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import pl.michal.olszewski.typer.match.dto.command.CreateNewMatch;
import pl.michal.olszewski.typer.match.dto.IllegalMatchMemberException;

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
        .awayTeamId(2L)
        .homeTeamId(1L)
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
    //when
    //then
    assertThrows(IllegalMatchMemberException.class, () -> matchCreator.from(CreateNewMatch.builder().awayTeamId(null).homeTeamId(1L).build()));
    assertThrows(IllegalMatchMemberException.class, () -> matchCreator.from(CreateNewMatch.builder().awayTeamId(1L).homeTeamId(null).build()));
  }

  @Test
  void shouldThrowExceptionWhenHomeTeamIsTheSameAsAwayTeam() {
    //given
    CreateNewMatch createNewMatch = CreateNewMatch.builder().awayTeamId(1L).homeTeamId(1L).build();

    //when
    //then
    assertThrows(IllegalMatchMemberException.class, () -> matchCreator.from(createNewMatch));
  }

}