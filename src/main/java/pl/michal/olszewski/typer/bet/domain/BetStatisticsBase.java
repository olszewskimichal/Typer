package pl.michal.olszewski.typer.bet.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
class BetStatisticsBase {
    @GeneratedValue
    @Id
    private Long id;

    private Long userId;

    private Long points;

    private Long position;
}
