package pl.michal.olszewski.typer.match.dto;

import lombok.Builder;
import lombok.Value;
import pl.michal.olszewski.typer.CommandValid;

@Value
@Builder
public class CreateNewMatch implements CommandValid {

  private final Long homeTeamId;
  private final Long awayTeamId;

  public void validCommand() {
    if (awayTeamId == null || homeTeamId == null) {
      throw new IllegalMatchMemberException("One of team not exists");
    }
    if (homeTeamId.equals(awayTeamId)) {
      throw new IllegalMatchMemberException("You cannot create match with two the same teams");
    }
  }
}
