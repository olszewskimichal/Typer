package pl.michal.olszewski.typer.match.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import pl.michal.olszewski.typer.match.dto.command.CreateNewRound;

class MatchRoundCreatorTest {

  private MatchRoundCreator matchRoundCreator = new MatchRoundCreator();

  @Test
  void shouldThrowExceptionWhenCommandIsNull() {
    assertThrows(NullPointerException.class, () -> matchRoundCreator.from(null));
  }

  @Test
  void shouldCreateMatchRoundWhenCommandIsValid() {
    //given
    MatchRound expected = MatchRound.builder().name("name").build();

    CreateNewRound createNewMatch = CreateNewRound
        .builder()
        .name("name")
        .build();
    //when
    MatchRound from = matchRoundCreator.from(createNewMatch);
    //then
    assertThat(from).isNotNull();
    assertThat(from).isEqualToComparingFieldByField(expected);
  }

  @Test
  void shouldThrowExceptionWhenOneOfTeamsIsNull() {
    //given
    //when
    //then
    assertThrows(IllegalArgumentException.class, () -> matchRoundCreator.from(CreateNewRound.builder().build()));
  }

}