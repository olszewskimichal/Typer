package pl.michal.olszewski.typer.policy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import pl.michal.olszewski.typer.bet.BetChecked;
import pl.michal.olszewski.typer.bet.CheckBetMatchEvent;

class DadBetPolicyTest {

  private BetPolicy policy = new DadBetPolicy();

  @Test
  void shouldReturnOnePointOnlyWhenResultIsExactlyTheSame() {
    //given
    CheckBetMatchEvent betEvent = CheckBetMatchEvent
        .builder()
        .betAwayGoals(1L)
        .betHomeGoals(1L)
        .expectedAwayGoals(1L)
        .expectedHomeGoals(1L)
        .build();
    //when
    BetChecked betChecked = policy.applyPolicy(betEvent);
    //then
    assertThat(betChecked).isNotNull();
    assertThat(betChecked.getPoints()).isEqualTo(DadBetPolicy.POINTS_FOR_CORRECT_RESULT);
  }

  @Test
  void shouldReturnZeroPointsOnlyWhenResultIsGoodBytNotExactlyTheSame() {
    //given
    CheckBetMatchEvent betEvent = CheckBetMatchEvent
        .builder()
        .betAwayGoals(2L)
        .betHomeGoals(2L)
        .expectedAwayGoals(1L)
        .expectedHomeGoals(1L)
        .build();
    //when
    BetChecked betChecked = policy.applyPolicy(betEvent);
    //then
    assertThat(betChecked).isNotNull();
    assertThat(betChecked.getPoints()).isEqualTo(DadBetPolicy.POINTS_FOR_INCORRECT_RESULT);
  }

  @Test
  void shouldReturnZeroPointsOnlyWhenHomeGoalsResultIsWrong() {
    //given
    CheckBetMatchEvent betEvent = CheckBetMatchEvent
        .builder()
        .betAwayGoals(1L)
        .betHomeGoals(1L)
        .expectedAwayGoals(1L)
        .expectedHomeGoals(2L)
        .build();
    //when
    BetChecked betChecked = policy.applyPolicy(betEvent);
    //then
    assertThat(betChecked).isNotNull();
    assertThat(betChecked.getPoints()).isEqualTo(DadBetPolicy.POINTS_FOR_INCORRECT_RESULT);
  }

  @Test
  void shouldReturnZeroPointsOnlyWhenAwayGoalsResultIsWrong() {
    //given
    CheckBetMatchEvent betEvent = CheckBetMatchEvent
        .builder()
        .betAwayGoals(2L)
        .betHomeGoals(1L)
        .expectedAwayGoals(1L)
        .expectedHomeGoals(1L)
        .build();
    //when
    BetChecked betChecked = policy.applyPolicy(betEvent);
    //then
    assertThat(betChecked).isNotNull();
    assertThat(betChecked.getPoints()).isEqualTo(DadBetPolicy.POINTS_FOR_INCORRECT_RESULT);
  }

}
