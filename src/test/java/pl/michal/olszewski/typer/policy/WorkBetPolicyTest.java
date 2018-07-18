package pl.michal.olszewski.typer.policy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pl.michal.olszewski.typer.bet.BetChecked;
import pl.michal.olszewski.typer.bet.CheckBetMatchEvent;

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
      "2,1,2,3,0"
  })
  void shouldCalculatePoints(Long betAwayGoals, Long betHomeGoals, Long expectedAwayGoals, Long expectedHomeGoals, Long points) {
    //given
    CheckBetMatchEvent betEvent = CheckBetMatchEvent
        .builder()
        .betAwayGoals(betAwayGoals)
        .betHomeGoals(betHomeGoals)
        .expectedAwayGoals(expectedAwayGoals)
        .expectedHomeGoals(expectedHomeGoals)
        .build();
    //when
    BetChecked betChecked = policy.applyPolicy(betEvent);
    //then
    assertThat(betChecked).isNotNull();
    assertThat(betChecked.getPoints()).isEqualTo(points);
  }
}
