package pl.michal.olszewski.typer.bet.dto.command;


import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class CheckBet {

  private final Long betId;
}
