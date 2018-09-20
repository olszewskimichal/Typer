package pl.michal.olszewski.typer.match.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = "matchRounds")
class MatchLeague {

  @GeneratedValue
  @Id
  @Getter
  private Long id;

  private String name;

  private Long betTypePolicy;

  @Getter
  private Long livescoreLeagueId;

  @OneToMany(
      mappedBy = "matchLeague",
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  @Builder.Default
  private Set<MatchRound> matchRounds = new HashSet<>();

  void addMatchRound(MatchRound matchRound) {
    matchRounds.add(matchRound);
    matchRound.setMatchLeague(this);
    matchRound.setBetTypePolicy(betTypePolicy);
  }

}
