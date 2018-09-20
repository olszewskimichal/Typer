package pl.michal.olszewski.typer.bet.dto.command;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pl.michal.olszewski.typer.CommandValid;
import pl.michal.olszewski.typer.bet.dto.BetNotFoundException;
import pl.michal.olszewski.typer.bet.dto.IllegalGoalArgumentException;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class CheckBet implements CommandValid {

  private final Long betId;
  private final Long betAwayGoals;
  private final Long betHomeGoals;
  private final Long expectedAwayGoals;
  private final Long expectedHomeGoals;
  private final Long betPolicyId;

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
