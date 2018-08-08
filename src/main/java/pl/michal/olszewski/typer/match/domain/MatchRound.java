package pl.michal.olszewski.typer.match.domain;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchRound {

  @GeneratedValue
  @Id
  private Long id;

  private String name;

  @OneToMany(
      mappedBy = "matchRound",
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private Set<Match> matches;

  public void addMatch(Match match) {
    matches.add(match);
    match.setMatchRound(this);
  }

  public void removeMatch(Match match) {
    matches.remove(match);
    match.setMatchRound(null);
  }

}
