package pl.michal.olszewski.typer.livescore;

import static java.lang.Long.valueOf;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import lombok.Value;

@Value
class MatchDTO {

  private Long id;
  private LocalDate date;
  @JsonProperty("home_name")
  private String homeName;
  @JsonProperty("away_name")
  private String awayName;
  private String score;
  private String status;
  @JsonProperty("league_id")
  private Long leagueId;

  public Long getHomeGoals() {
    return valueOf(getScore().split("-")[0].trim());
  }

  public Long getAwayGoals() {
    return valueOf(getScore().split("-")[1].trim());
  }
}
