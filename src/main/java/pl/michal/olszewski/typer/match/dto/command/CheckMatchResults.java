package pl.michal.olszewski.typer.match.dto.command;

import java.time.LocalDate;
import java.util.List;
import lombok.Value;

@Value
public class CheckMatchResults {

  private final LocalDate date;
  private final Long livescoreLeagueId;
  private final List<Long> livescoreIds;
}
