package pl.michal.olszewski.typer.match.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

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

  private MatchStatus matchStatus;

}
