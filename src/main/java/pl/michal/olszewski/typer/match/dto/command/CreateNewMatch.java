package pl.michal.olszewski.typer.match.dto.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.michal.olszewski.typer.CommandValid;
import pl.michal.olszewski.typer.match.dto.IllegalMatchMemberException;
import pl.michal.olszewski.typer.match.dto.MatchRoundNotFoundException;

@AllArgsConstructor
@Getter
@Builder
public class CreateNewMatch implements CommandValid {

  private final Long roundId;
  private final Long homeTeamId;
  private final Long awayTeamId;

  public void validCommand() {
    if (roundId == null) {
      throw new MatchRoundNotFoundException("Nieprawidłowy identyfikator kolejki ligowej");
    }

    if (awayTeamId == null || homeTeamId == null) {
      throw new IllegalMatchMemberException("One of team not exists");
    }
    if (homeTeamId.equals(awayTeamId)) {
      throw new IllegalMatchMemberException("You cannot create match with two the same teams");
    }
  }
}
