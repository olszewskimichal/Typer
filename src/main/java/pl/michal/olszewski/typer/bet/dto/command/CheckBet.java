package pl.michal.olszewski.typer.bet.dto.command;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.michal.olszewski.typer.CommandValid;
import pl.michal.olszewski.typer.bet.dto.BetNotFoundException;
import pl.michal.olszewski.typer.bet.dto.IllegalGoalArgumentException;

@Getter
@Setter
@ToString
@NoArgsConstructor
public final class CheckBet implements CommandValid {

  private Long betId;
  private Long betAwayGoals;
  private Long betHomeGoals;
  private Long expectedAwayGoals;
  private Long expectedHomeGoals;
  private Long betPolicyId;

  @Builder
  public CheckBet(Long betId, Long betAwayGoals, Long betHomeGoals, Long expectedAwayGoals, Long expectedHomeGoals, Long betPolicyId) {
    this.betId = betId;
    this.betAwayGoals = betAwayGoals;
    this.betHomeGoals = betHomeGoals;
    this.expectedAwayGoals = expectedAwayGoals;
    this.expectedHomeGoals = expectedHomeGoals;
    this.betPolicyId = betPolicyId;
  }


  @Override
  public void validCommand() {
    if (betId == null) {
      throw new BetNotFoundException("Nieznany zakład");
    }
    if (betAwayGoals == null || betHomeGoals == null) {
      throw new IllegalGoalArgumentException("Żeby sprawdzić zakład musza być podane obstawiane gole zdobyte przez gości i przez gospodarzy");
    }

    if (expectedAwayGoals == null || expectedHomeGoals == null) {
      throw new IllegalGoalArgumentException("Żeby sprawdzić zakład musza być podane oczekiwane gole zdobyte przez gości i przez gospodarzy");
    }
    if (betPolicyId == null) {
      throw new IllegalArgumentException("Nieprawidłowa polityka liczenia punktów");
    }
  }
}
