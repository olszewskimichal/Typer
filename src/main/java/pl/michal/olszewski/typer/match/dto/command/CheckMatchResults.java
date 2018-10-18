package pl.michal.olszewski.typer.match.dto.command;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
@ToString
public class CheckMatchResults {

  private LocalDate date;
  private Long livescoreLeagueId;
  private List<Long> livescoreIds;
}
