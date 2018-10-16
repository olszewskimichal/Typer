package pl.michal.olszewski.typer.match.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.michal.olszewski.typer.match.dto.command.IntegrateMatchWithLivescore;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class MatchUpdaterIntegrationTest {

  @Autowired
  private MatchUpdater matchUpdater;

  @Autowired
  private MatchSaver matchSaver;

  @Autowired
  private MatchFinder matchFinder;

  @Test
  void shouldIntegrateMatchWithLivescoreProps() {
    Match match = matchSaver.save(Match.builder().build());
    matchUpdater.integrateMatch(IntegrateMatchWithLivescore.builder().matchId(match.getId()).livescoreId(1L).livescoreLeagueId(2L).build());

    Match integratedMatch = matchFinder.findOneOrThrow(match.getId());
    assertThat(integratedMatch.getLivescoreId()).isNotNull();
    assertThat(integratedMatch.getLivescoreLeagueId()).isNotNull();
  }
}