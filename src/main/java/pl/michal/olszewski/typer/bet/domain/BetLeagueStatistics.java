package pl.michal.olszewski.typer.bet.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
class BetLeagueStatistics extends BetStatisticsBase {
    private Long leagueId;

    @Builder
    public BetLeagueStatistics(Long id, Long userId, Long points, Long position, Long leagueId) {
        super(id, userId, points, position);
        this.leagueId = leagueId;
    }
}
