package pl.michal.olszewski.typer.match.domain;

import java.util.Objects;
import pl.michal.olszewski.typer.match.dto.command.CreateNewRound;

public class MatchRoundCreator {

  MatchRound from(CreateNewRound createNewRound) {
    Objects.requireNonNull(createNewRound);
    createNewRound.validCommand();

    return MatchRound
        .builder()
        .name(createNewRound.getName())
        .build();
  }

}
