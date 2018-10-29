package pl.michal.olszewski.typer.livescore.dto.read;

import static java.lang.Long.valueOf;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class MatchDTO {

  private Long id;
  private LocalDate date;
  private String score;

  public Long getHomeGoals() {
    return valueOf(getScore().split("-")[0].trim());
  }

  public Long getAwayGoals() {
    return valueOf(getScore().split("-")[1].trim());
  }
}
