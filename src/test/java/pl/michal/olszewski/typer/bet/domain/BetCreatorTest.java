package pl.michal.olszewski.typer.bet.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import pl.michal.olszewski.typer.bet.dto.IllegalGoalArgumentException;
import pl.michal.olszewski.typer.bet.dto.command.CreateNewBet;
import pl.michal.olszewski.typer.match.dto.MatchLeagueNotFoundException;
import pl.michal.olszewski.typer.match.dto.MatchNotFoundException;
import pl.michal.olszewski.typer.match.dto.MatchRoundNotFoundException;
import pl.michal.olszewski.typer.users.UserNotFoundException;

class BetCreatorTest {

  private BetCreator betCreator = new BetCreator();

  @Test
  void shouldThrowExceptionWhenCommandIsNull() {
    assertThrows(NullPointerException.class, () -> BetCreator.from(null));
  }

  @Test
  void shouldCreateNewBetWithStatusNewAnd0Points() {
    //given
    Bet expected = Bet.builder()
        .matchId(1L)
        .userId(3L)
        .betHomeGoals(2L)
        .betAwayGoals(1L)
        .points(0L)
        .status(BetStatus.NEW)
        .matchRoundId(2L)
        .leagueId(4L)
        .build();
    CreateNewBet command = CreateNewBet.builder()
        .betAwayGoals(1L)
        .betHomeGoals(2L)
        .matchId(1L)
        .userId(3L)
        .matchRoundId(2L)
        .leagueId(4L)
        .build();
    //when
    Bet bet = BetCreator.from(command);
    //then
    assertThat(bet).isNotNull().isEqualToComparingFieldByField(expected);
  }

  @Test
  void shouldThrowExceptionWhenOneOfGoalsParameterIsNull() {
    CreateNewBet command = CreateNewBet.builder()
        .betAwayGoals(1L)
        .matchId(1L)
        .build();
    CreateNewBet command2 = CreateNewBet.builder()
        .betHomeGoals(1L)
        .matchId(1L)
        .build();
    //when
    //then
    assertThrows(IllegalGoalArgumentException.class, () -> BetCreator.from(command));
    assertThrows(IllegalGoalArgumentException.class, () -> BetCreator.from(command2));
  }

  @Test
  void shouldThrowExceptionWhenMatchIdIsNull() {
    CreateNewBet command = CreateNewBet.builder()
        .betAwayGoals(1L)
        .betHomeGoals(2L)
        .build();
    //when
    //then
    assertThrows(MatchNotFoundException.class, () -> BetCreator.from(command));
  }

  @Test
  void shouldThrowExceptionWhenUserIdIsNull() {
    CreateNewBet command = CreateNewBet.builder()
        .betAwayGoals(1L)
        .betHomeGoals(2L)
        .matchId(3L)
        .build();
    //when
    //then
    assertThrows(UserNotFoundException.class, () -> BetCreator.from(command));
  }

  @Test
  void shouldThrowExceptionWhenMatchRoundIdIsNull() {
    CreateNewBet command = CreateNewBet.builder()
        .betAwayGoals(1L)
        .betHomeGoals(2L)
        .matchId(3L)
        .userId(1L)
        .build();
    //when
    //then
    assertThrows(MatchRoundNotFoundException.class, () -> BetCreator.from(command));
  }

  @Test
  void shouldThrowExceptionWhenMatchLeagueIdIsNull() {
    CreateNewBet command = CreateNewBet.builder()
        .betAwayGoals(1L)
        .betHomeGoals(2L)
        .matchRoundId(6L)
        .matchId(3L)
        .userId(1L)
        .build();
    //when
    //then
    assertThrows(MatchLeagueNotFoundException.class, () -> BetCreator.from(command));
  }

}