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

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
class MatchLeague {

  @GeneratedValue
  @Id
  @Getter
  private Long id;

  private String name;

  private Long betTypePolicy;

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
