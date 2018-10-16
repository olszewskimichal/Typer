package pl.michal.olszewski.typer.bet.domain;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pl.michal.olszewski.typer.bet.dto.read.BetInfo;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
@ToString
@EntityListeners(AuditingEntityListener.class)
class Bet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Builder.Default
  private BetStatus status = BetStatus.NEW;
  @Builder.Default
  @Setter
  private Long points = 0L;
  private Long betHomeGoals;
  private Long betAwayGoals;
  private Long matchId;
  private Long userId;
  private Long matchRoundId; //Pole dla normalizacji i uproszczenia selectów bazodanowych
  private Long leagueId; //Pole dla normalizacji i uproszczenia selectów bazodanowych
  @LastModifiedDate
  private Instant modified;
  @CreatedDate
  private Instant created;

  void setStatusAsChecked() {
    status = BetStatus.CHECKED;
  }

  void setStatusAsCanceled() {
    status = BetStatus.CANCELED;
  }

  void setStatusAsBlocked() {
    status = BetStatus.IN_PROGRESS;
  }

  boolean isChecked() {
    return status.equals(BetStatus.CHECKED);
  }

  BetInfo toBetInfo() {
    return new BetInfo(id, userId, matchId, betHomeGoals, betAwayGoals, points, status);
  }
}
