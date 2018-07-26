package pl.michal.olszewski.typer.bet;

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
class Bet {

  @Id
  @GeneratedValue
  private Long id;
  @Builder.Default
  private BetStatus status = BetStatus.NEW;
  @Builder.Default
  private Long points = 0L;
  private Long betHomeGoals;
  private Long betAwayGoals;
  private Long matchId;
}
