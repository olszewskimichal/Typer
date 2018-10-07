package pl.michal.olszewski.typer.bet.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
