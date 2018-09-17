package pl.michal.olszewski.typer.team.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
class Team {

  @GeneratedValue
  @Id
  @Getter
  private Long id;

  @Getter
  private String name;

  private Long livescoreId;

}
