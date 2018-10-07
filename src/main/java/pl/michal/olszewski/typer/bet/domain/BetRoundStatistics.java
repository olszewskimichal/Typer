package pl.michal.olszewski.typer.bet.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

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
