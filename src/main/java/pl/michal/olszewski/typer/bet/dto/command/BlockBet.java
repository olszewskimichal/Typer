package pl.michal.olszewski.typer.bet.dto.command;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class BlockBet {
  private final Long betId;
}
