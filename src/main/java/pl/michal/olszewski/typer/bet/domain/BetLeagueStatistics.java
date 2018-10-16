package pl.michal.olszewski.typer.bet.domain;

import javax.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
class BetLeagueStatistics extends BetStatisticsBase {

  private Long leagueId;

  @Builder
  public BetLeagueStatistics(Long id, Long userId, Long points, Long position, Long leagueId) {
    super(id, userId, points, position);
    this.leagueId = leagueId;
  }
}
