package pl.michal.olszewski.typer.bet.dto.command;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class CheckBet {

  private final Long betId;
}
