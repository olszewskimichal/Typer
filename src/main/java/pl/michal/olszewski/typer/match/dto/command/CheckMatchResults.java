package pl.michal.olszewski.typer.match.dto.command;

import java.time.Instant;
import java.util.List;
import lombok.Value;

@Value
public class CheckMatchResults {

  private final Instant date;
  private final List<Long> livescoreIds;
}
