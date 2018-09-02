package pl.michal.olszewski.typer.match.domain;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.michal.olszewski.typer.match.dto.events.MatchCanceled;
import pl.michal.olszewski.typer.match.dto.events.MatchFinished;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
class Match {

  @GeneratedValue
  @Id
  @Getter
  private Long id;

  private Long homeTeamId;

  private Long awayTeamId;

  @Setter
  private Long homeGoals;

  @Setter
  private Long awayGoals;

  @Getter
  private MatchStatus matchStatus;

  @Setter
  @Getter
  private Long livescoreId;

  @Getter
  private Instant startDate;

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
    setHomeGoals(finalHomeGoals);
    setAwayGoals(finalAwayGoals);
    return new MatchFinished(id, homeGoals, awayGoals, matchRound.getBetTypePolicy());
  }

  void integrateMatchWithLivescore(Long livescoreId) {
    setLivescoreId(livescoreId);
  }
}
