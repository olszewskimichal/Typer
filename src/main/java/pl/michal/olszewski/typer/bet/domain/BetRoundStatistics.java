package pl.michal.olszewski.typer.bet.domain;

import javax.persistence.Entity;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
class BetRoundStatistics extends BetStatisticsBase {

  private Long roundId;

  @Builder
  public BetRoundStatistics(Long id, Long userId, Long points, Long position, Long roundId) {
    super(id, userId, points, position);
    this.roundId = roundId;
  }
}
