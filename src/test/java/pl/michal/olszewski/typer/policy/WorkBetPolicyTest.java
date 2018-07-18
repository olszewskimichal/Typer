package pl.michal.olszewski.typer.policy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import pl.michal.olszewski.typer.bet.BetChecked;
import pl.michal.olszewski.typer.bet.CheckBetMatchEvent;

class WorkBetPolicyTest {

  private BetPolicy policy = new WorkBetPolicy();

  @Test
  void shouldReturnFivePointsOnlyWhenResultDrawIsExactlyPredicted() {
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
    assertThat(betChecked.getPoints()).isEqualTo(5L);
  }

  @Test
  void shouldReturnFourPointsOnlyWhenPredictExactlyResultButNotDraw() {
    //given
    CheckBetMatchEvent betEvent = CheckBetMatchEvent
        .builder()
        .betAwayGoals(2L)
        .betHomeGoals(1L)
        .expectedAwayGoals(2L)
        .expectedHomeGoals(1L)
        .build();
    //when
    BetChecked betChecked = policy.applyPolicy(betEvent);
    //then
    assertThat(betChecked).isNotNull();
    assertThat(betChecked.getPoints()).isEqualTo(4L);
  }

  @Test
  void shouldReturnThreePointsOnlyWhenResultIsDrawButNotExactlyPredicted() {
    //given
    CheckBetMatchEvent betEvent = CheckBetMatchEvent
        .builder()
        .betAwayGoals(1L)
        .betHomeGoals(1L)
        .expectedAwayGoals(2L)
        .expectedHomeGoals(2L)
        .build();
    //when
    BetChecked betChecked = policy.applyPolicy(betEvent);
    //then
    assertThat(betChecked).isNotNull();
    assertThat(betChecked.getPoints()).isEqualTo(3L);
  }

  @Test
  void shouldReturnTwoPointsOnlyWhenResultIsNotDrawButNExactlyPredictedOnlyOneTeamGoals() {
    //given
    CheckBetMatchEvent betEvent = CheckBetMatchEvent
        .builder()
        .betAwayGoals(2L)
        .betHomeGoals(1L)
        .expectedAwayGoals(2L)
        .expectedHomeGoals(0L)
        .build();
    //when
    BetChecked betChecked = policy.applyPolicy(betEvent);
    //then
    assertThat(betChecked).isNotNull();
    assertThat(betChecked.getPoints()).isEqualTo(2L);
  }

  @Test
  void shouldReturnTwoPointsOnlyWhenPredictedWhichTeamWonButBetWrongCountOfGoals() {
    //given
    CheckBetMatchEvent betEvent = CheckBetMatchEvent
        .builder()
        .betAwayGoals(2L)
        .betHomeGoals(1L)
        .expectedAwayGoals(3L)
        .expectedHomeGoals(0L)
        .build();
    //when
    BetChecked betChecked = policy.applyPolicy(betEvent);
    //then
    assertThat(betChecked).isNotNull();
    assertThat(betChecked.getPoints()).isEqualTo(1L);
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
    assertThat(betChecked.getPoints()).isEqualTo(0L);
  }

  @Test
  void shouldReturnZeroPointsOnlyWhenAwayGoalsResultIsWrong2() {
    //given
    CheckBetMatchEvent betEvent = CheckBetMatchEvent
        .builder()
        .betAwayGoals(2L)
        .betHomeGoals(1L)
        .expectedAwayGoals(2L)
        .expectedHomeGoals(3L)
        .build();
    //when
    BetChecked betChecked = policy.applyPolicy(betEvent);
    //then
    assertThat(betChecked).isNotNull();
    assertThat(betChecked.getPoints()).isEqualTo(0L);
  }
}
