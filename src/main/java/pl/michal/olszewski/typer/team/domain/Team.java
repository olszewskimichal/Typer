package pl.michal.olszewski.typer.team.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.michal.olszewski.typer.team.dto.read.TeamInfo;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
class Team {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Getter
  private Long id;

  @Getter
  private String name;

  private Long livescoreId;

  TeamInfo toTeamInfo() {
    return new TeamInfo(id, name);
  }
}
