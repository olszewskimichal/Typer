package pl.michal.olszewski.typer.bet.dto.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.michal.olszewski.typer.CommandValid;
import pl.michal.olszewski.typer.bet.dto.BetNotFoundException;

@Builder
@AllArgsConstructor
@Getter
public class BlockBet implements CommandValid {

  private final Long betId;

  @Override
  public void validCommand() {
    if (betId == null) {
      throw new BetNotFoundException("Nieznany zakład");
    }

  }
}
