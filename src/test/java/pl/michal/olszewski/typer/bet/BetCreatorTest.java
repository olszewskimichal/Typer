package pl.michal.olszewski.typer.bet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import pl.michal.olszewski.typer.bet.dto.IllegalGoalArgumentException;
import pl.michal.olszewski.typer.match.dto.MatchNotFoundException;

class BetCreatorTest {

  BetCreator betCreator = new BetCreator();

  @Test
  void shouldThrowExceptionWhenCommandIsNull() {
    assertThrows(NullPointerException.class, () -> betCreator.from(null));
  }

  @Test
  void shouldCreateNewBetWithStatusNewAnd0Points() {
    //given
    Bet expected = Bet.builder()
        .matchId(1L)
        .betHomeGoals(2L)
        .betAwayGoals(1L)
        .points(0L)
        .status(BetStatus.NEW)
        .build();
    CreateNewBet command = CreateNewBet.builder()
        .betAwayGoals(1L)
        .betHomeGoals(2L)
        .matchId(1L)
        .build();
    //when
    Bet bet = betCreator.from(command);
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
    assertThrows(IllegalGoalArgumentException.class, () -> betCreator.from(command));
    assertThrows(IllegalGoalArgumentException.class, () -> betCreator.from(command2));
  }

  @Test
  void shouldThrowExceptionWhenMatchIdIsNull() {
    CreateNewBet command = CreateNewBet.builder()
        .betAwayGoals(1L)
        .betHomeGoals(2L)
        .build();
    //when
    //then
    assertThrows(MatchNotFoundException.class, () -> betCreator.from(command));
  }

}