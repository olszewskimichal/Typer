package pl.michal.olszewski.typer.livescore;

import static java.lang.Long.valueOf;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
class MatchDTO {

  private Long id;
  private LocalDate date;
  private String score;
  @JsonProperty("league_id")
  private Long leagueId;

  Long getHomeGoals() {
    return valueOf(getScore().split("-")[0].trim());
  }

  Long getAwayGoals() {
    return valueOf(getScore().split("-")[1].trim());
  }
}
