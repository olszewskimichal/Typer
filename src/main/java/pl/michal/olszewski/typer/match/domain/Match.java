package pl.michal.olszewski.typer.match.domain;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.michal.olszewski.typer.match.dto.events.MatchCanceled;
import pl.michal.olszewski.typer.match.dto.events.MatchFinished;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
class Match {

  @GeneratedValue
  @Id
  private Long id;

  private Long homeTeamId;

  private Long awayTeamId;

  private Long homeGoals;

  private Long awayGoals;

  private MatchStatus matchStatus;

  MatchCanceled setStatusAsCanceled() {
    matchStatus = MatchStatus.CANCELED;
    return new MatchCanceled(id, Instant.now());
  }

  MatchFinished setFinalResult(Long finalHomeGoals, Long finalAwayGoals) {
    this.matchStatus = MatchStatus.FINISHED;
    this.homeGoals = finalHomeGoals;
    this.awayGoals = finalAwayGoals;
    return new MatchFinished(id, Instant.now());
  }
}
