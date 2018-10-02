package pl.michal.olszewski.typer.match.domain;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.michal.olszewski.typer.match.dto.events.MatchCanceled;
import pl.michal.olszewski.typer.match.dto.events.MatchFinished;
import pl.michal.olszewski.typer.match.dto.read.MatchInfo;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
class Match {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Getter
  private Long id;

  private Long homeTeamId;

  private Long awayTeamId;

  @Getter
  @Setter
  private Instant startDate;

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
  @Setter
  private Long livescoreLeagueId; //Zdenormalizowana kolumna by ograniczyć liczbę zapytań

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ROUND_ID")
  @Setter
  @Getter
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

  void integrateMatchWithLivescore(Long livescoreId, Long livescoreLeagueId) {
    setLivescoreId(livescoreId);
    setLivescoreLeagueId(livescoreLeagueId);
  }

  MatchInfo toMatchInfo() {
    return new MatchInfo(id, homeGoals, awayGoals, homeTeamId, awayTeamId, matchStatus);
  }
}
