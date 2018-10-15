package pl.michal.olszewski.typer.bet.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
abstract class BetStatisticsBase {

  @GeneratedValue
  @Id
  @Setter
  private Long id;

  private Long userId;

  private Long points;

  private Long position;
}
