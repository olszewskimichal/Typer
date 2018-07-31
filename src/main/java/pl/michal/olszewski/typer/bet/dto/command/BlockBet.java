package pl.michal.olszewski.typer.bet.dto.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class BlockBet {
  private final Long betId;
}
