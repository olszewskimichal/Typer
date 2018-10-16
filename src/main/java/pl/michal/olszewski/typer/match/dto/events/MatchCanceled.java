package pl.michal.olszewski.typer.match.dto.events;

import lombok.Builder;

public final class MatchCanceled extends MatchEventBase {

  @Builder
  public MatchCanceled(Long matchId) {
    super(matchId);
  }
}
