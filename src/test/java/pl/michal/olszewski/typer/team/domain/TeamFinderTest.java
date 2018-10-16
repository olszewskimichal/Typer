package pl.michal.olszewski.typer.team.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.michal.olszewski.typer.RepositoryTestBase;

class TeamFinderTest extends RepositoryTestBase {

  @Autowired
  private TeamFinder teamFinder;

  @Test
  void shouldFindTeamById() {
    Long id = (Long) entityManager.persistAndGetId(Team.builder().build());

    Team team = teamFinder.findOneOrThrow(id);

    assertThat(team).isNotNull();
  }

  @Test
  void shouldFindAllTeams() {
    entityManager.persistAndFlush(Team.builder().build());
    entityManager.persistAndFlush(Team.builder().build());

    List<Team> all = teamFinder.findAll();

    assertThat(all).isNotEmpty().hasSize(2);
  }


  @Test
  void shouldFindTeamByName() {
    entityManager.persistAndFlush(Team.builder().name("abc").build());

    Optional<Team> abc = teamFinder.findByName("abc");

    assertThat(abc).isPresent();
  }

  @Test
  void shouldReturnEmptyOptionalWhenTeamByNameNotExist() {
    Optional<Team> abc = teamFinder.findByName("abcd");

    assertThat(abc).isNotPresent();
  }
}