package pl.michal.olszewski.typer.livescore;

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
}
