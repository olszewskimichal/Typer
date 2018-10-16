package pl.michal.olszewski.typer.match.dto.command;

import java.time.Instant;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pl.michal.olszewski.typer.CommandValid;
import pl.michal.olszewski.typer.match.dto.IllegalMatchMemberException;
import pl.michal.olszewski.typer.match.dto.MatchRoundNotFoundException;

@AllArgsConstructor
@Getter
@Builder
@ToString
public class CreateNewMatch implements CommandValid {

  @NotNull
  private final Long roundId;
  @NotNull
  private final Long homeTeamId;
  @NotNull
  private final Long awayTeamId;
  private final Instant startDate;

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
