package pl.michal.olszewski.typer.match.dto.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class CancelMatch {

  private Long matchId;

}
