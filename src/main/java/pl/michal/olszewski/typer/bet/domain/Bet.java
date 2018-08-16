package pl.michal.olszewski.typer.bet.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
@ToString
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
  private Long userId;
  private Long matchRoundId; //Pole dla normalizacji i uproszczenia selectów bazodanowych

  void setStatusAsChecked() {
    status = BetStatus.CHECKED;
  }

  void setStatusAsCanceled() {
    status = BetStatus.CANCELED;
  }

  void setStatusAsBlocked() {
    status = BetStatus.IN_PROGRESS;
  }
}
