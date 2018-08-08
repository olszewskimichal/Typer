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
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
class MatchRound {

  @GeneratedValue
  @Id
  private Long id;

  private String name;

  @OneToMany(
      mappedBy = "matchRound",
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  @Builder.Default
  private Set<Match> matches = new HashSet<>();

  void addMatch(Match match) {
    matches.add(match);
    match.setMatchRound(this);
  }

}
