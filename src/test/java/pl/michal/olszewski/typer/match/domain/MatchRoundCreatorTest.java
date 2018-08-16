package pl.michal.olszewski.typer.match.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import pl.michal.olszewski.typer.match.dto.MatchLeagueNotFoundException;
import pl.michal.olszewski.typer.match.dto.command.CreateNewRound;

class MatchRoundCreatorTest {

  private MatchLeagueFinder matchLeagueFinder = new InMemoryMatchLeagueFinder();
  private MatchLeagueSaver matchLeagueSaver = new InMemoryMatchLeagueSaver();
  private MatchRoundCreator matchRoundCreator = new MatchRoundCreator(matchLeagueFinder);

  @Test
  void shouldThrowExceptionWhenCommandIsNull() {
    assertThrows(NullPointerException.class, () -> matchRoundCreator.from(null));
  }

  @Test
  void shouldCreateMatchRoundWhenCommandIsValid() {
    //given
    MatchLeague matchLeague = MatchLeague.builder().id(1L).name("name").betTypePolicy(2L).build();
    matchLeagueSaver.save(matchLeague);
    MatchRound expected = MatchRound.builder().name("name").matchLeague(matchLeague).betTypePolicy(2L).build();

    CreateNewRound createNewMatch = CreateNewRound
        .builder()
        .name("name")
        .leagueId(1L)
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

  @Test
  void shouldThrowExceptionWhenLeagueIdIsNull() {
    //given
    CreateNewRound createNewMatch = CreateNewRound
        .builder()
        .name("name")
        .build();
    //when
    //then
    assertThrows(MatchLeagueNotFoundException.class, () -> matchRoundCreator.from(createNewMatch));
  }

  @Test
  void shouldThrowExceptionWhenLeagueNotFoundInDb() {
    //given
    CreateNewRound createNewMatch = CreateNewRound
        .builder()
        .name("name")
        .leagueId(2L)
        .build();
    //when
    //then
    assertThrows(MatchLeagueNotFoundException.class, () -> matchRoundCreator.from(createNewMatch));
  }

}