package pl.michal.olszewski.typer.match.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.michal.olszewski.typer.match.dto.read.MatchRoundInfo;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = {"matches", "matchLeague"})
class MatchRound {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Getter
  private Long id;

  private String name;

  @OneToMany(
      mappedBy = "matchRound",
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  @Builder.Default
  private Set<Match> matches = new HashSet<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "LEAGUE_ID")
  @Setter
  @Getter
  private MatchLeague matchLeague;

  @Getter
  @Setter
  private Long betTypePolicy; //zdenormalizowane pole -> by ograniczyc listy zapytań

  void addMatch(Match match) {
    matches.add(match);
    match.setMatchRound(this);
  }

  MatchRoundInfo toMatchRoundInfo() {
    return new MatchRoundInfo(id, name, matchLeague != null ? matchLeague.getId() : null);
  }
}
