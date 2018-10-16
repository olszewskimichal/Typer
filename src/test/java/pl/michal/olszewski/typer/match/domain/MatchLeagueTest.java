package pl.michal.olszewski.typer.match.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import pl.michal.olszewski.typer.match.dto.command.CreateNewLeague;

class MatchLeagueTest {

  @Test
  void shouldThrowExceptionWhenCommandIsNull() {
    assertThrows(NullPointerException.class, () -> MatchLeagueCreator.from(null));
  }

  @Test
  void shouldCreateMatchWhenCommandIsValidAndSetStatusAsNEW() {
    //given
    MatchLeague expected = MatchLeague.builder().name("name").betTypePolicy(3L).build();

    CreateNewLeague createNewLeague = CreateNewLeague
        .builder()
        .name("name")
        .betTypePolicy(3L)
        .build();
    //when
    MatchLeague from = MatchLeagueCreator.from(createNewLeague);
    //then
    assertThat(from).isNotNull();
    assertThat(from).isEqualToComparingFieldByField(expected);
  }

  @Test
  void shouldThrowExceptionWhenNameIsNull() {
    //given
    CreateNewLeague createNewLeague = CreateNewLeague
        .builder()
        .betTypePolicy(3L)
        .build();
    //when
    //then
    assertThrows(IllegalArgumentException.class, () -> MatchLeagueCreator.from(createNewLeague));
  }

  @Test
  void shouldThrowExceptionWhenBetTypePolicyIsNull() {
    //given
    CreateNewLeague createNewLeague = CreateNewLeague
        .builder()
        .name("aaa")
        .build();
    //when
    //then
    assertThrows(IllegalArgumentException.class, () -> MatchLeagueCreator.from(createNewLeague));
  }

}