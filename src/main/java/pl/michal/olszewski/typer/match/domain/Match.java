package pl.michal.olszewski.typer.match.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ROUND_ID")
  @Setter
  private MatchRound matchRound;

  MatchCanceled setStatusAsCanceled() {
    matchStatus = MatchStatus.CANCELED;
    return new MatchCanceled(id);
  }

  MatchFinished setFinalResult(Long finalHomeGoals, Long finalAwayGoals) {
    this.matchStatus = MatchStatus.FINISHED;
    homeGoals = finalHomeGoals;
    awayGoals = finalAwayGoals;
    return new MatchFinished(id, homeGoals, awayGoals);
  }
}
