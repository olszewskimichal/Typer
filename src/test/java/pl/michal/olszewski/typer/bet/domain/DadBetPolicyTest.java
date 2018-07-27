package pl.michal.olszewski.typer.bet.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pl.michal.olszewski.typer.bet.domain.BetPolicy;
import pl.michal.olszewski.typer.bet.domain.DadBetPolicy;
import pl.michal.olszewski.typer.bet.dto.BetChecked;
import pl.michal.olszewski.typer.bet.dto.CheckBetMatchEvent;

class DadBetPolicyTest {

  private BetPolicy policy = new DadBetPolicy();

  @ParameterizedTest(name = "predictedResult={0}:{1} expectedResult={2}:{3} expectedPoints={4}")
  @CsvSource({
      "1,1,1,1,1",
      "2,2,1,1,0",
      "1,1,1,2,0",
      "2,1,1,1,0"
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
    BetChecked betChecked = policy.calculatePoints(betEvent);
    //then
    assertThat(betChecked).isNotNull();
    assertThat(betChecked.getPoints()).isEqualTo(points);
  }

}
