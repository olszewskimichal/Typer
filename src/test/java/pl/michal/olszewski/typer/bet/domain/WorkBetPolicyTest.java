package pl.michal.olszewski.typer.bet.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pl.michal.olszewski.typer.bet.dto.IllegalGoalArgumentException;
import pl.michal.olszewski.typer.bet.dto.command.CheckBet;
import pl.michal.olszewski.typer.bet.dto.events.BetChecked;

class WorkBetPolicyTest {

  private BetPolicy policy = new WorkBetPolicy();

  @ParameterizedTest(name = "predictedResult={0}:{1} expectedResult={2}:{3} expectedPoints={4}")
  @CsvSource({
      "1,1,1,1,5",
      "2,1,2,1,4",
      "1,2,1,2,4",
      "1,1,2,2,3",
      "2,1,2,0,2",
      "1,2,0,2,2",
      "2,1,3,0,1",
      "2,1,1,1,0",
      "1,2,1,1,0",
      "2,1,2,3,0",
      "1,3,0,2,1"
  })
  void shouldCalculatePoints(Long betAwayGoals, Long betHomeGoals, Long expectedAwayGoals, Long expectedHomeGoals, Long points) {
    //given
    CheckBet betEvent = CheckBet
        .builder()
        .betId(3L)
        .betAwayGoals(betAwayGoals)
        .betHomeGoals(betHomeGoals)
        .expectedAwayGoals(expectedAwayGoals)
        .expectedHomeGoals(expectedHomeGoals)
        .betPolicyId(BetTypePolicy.WORKBET_POLICY.getValue())
        .build();
    //when
    BetChecked betChecked = policy.calculatePoints(betEvent);
    //then
    assertThat(betChecked).isNotNull();
    assertThat(betChecked.getPoints()).isEqualTo(points);
  }

  @Test
  void shouldThrowExceptionWhenOneOfBetGoalsAreNotSet() {
    assertThrows(IllegalGoalArgumentException.class,
        () -> policy.calculatePoints(CheckBet.builder().betId(1L).betAwayGoals(1L).betHomeGoals(null).expectedAwayGoals(1L).expectedHomeGoals(1L).build()));
    assertThrows(IllegalGoalArgumentException.class,
        () -> policy.calculatePoints(CheckBet.builder().betId(1L).betAwayGoals(null).betHomeGoals(2L).expectedAwayGoals(1L).expectedHomeGoals(1L).build()));
  }

  @Test
  void shouldThrowExceptionWhenOneOfExpectedGoalsAreNotSet() {
    assertThrows(IllegalGoalArgumentException.class,
        () -> policy.calculatePoints(CheckBet.builder().betId(1L).betAwayGoals(1L).betHomeGoals(2L).expectedAwayGoals(null).expectedHomeGoals(1L).build()));
    assertThrows(IllegalGoalArgumentException.class,
        () -> policy.calculatePoints(CheckBet.builder().betId(1L).betAwayGoals(1L).betHomeGoals(2L).expectedAwayGoals(1L).expectedHomeGoals(null).build()));
  }
}
