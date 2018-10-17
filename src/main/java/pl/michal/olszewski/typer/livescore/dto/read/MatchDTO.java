package pl.michal.olszewski.typer.livescore.dto.read;

import static java.lang.Long.valueOf;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class MatchDTO {

  private Long id;
  private LocalDate date;
  private String score;
  @JsonProperty("league_id")
  private Long leagueId;

  public Long getHomeGoals() {
    return valueOf(getScore().split("-")[0].trim());
  }

  public Long getAwayGoals() {
    return valueOf(getScore().split("-")[1].trim());
  }
}
