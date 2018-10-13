package pl.michal.olszewski.typer.bet.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.michal.olszewski.typer.bet.domain.BetRoundStatistics.builder;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.michal.olszewski.typer.RepositoryTestBase;
import pl.michal.olszewski.typer.match.domain.MatchLeagueFactory;

class BetStatisticsFinderTest extends RepositoryTestBase {

  @Autowired
  private BetRoundStatisticsFinder betRoundStatisticsFinder;
  @Autowired
  private BetLeagueStatisticsFinder betLeagueStatisticsFinder;
  @Autowired
  private BetLeagueStatisticsSaver betLeagueStatisticsSaver;
  @Autowired
  private BetRoundStatisticsSaver betRoundStatisticsSaver;

  @BeforeEach
  void setUp() {
    betLeagueStatisticsSaver.deleteAll();
    betRoundStatisticsSaver.deleteAll();
  }

  @Test
  void shouldFindAllStats() {
    BetLeagueStatistics betLeagueStatistics = BetLeagueStatistics.builder().build();
    BetRoundStatistics betRoundStatistics = builder().build();
    betLeagueStatisticsSaver.save(betLeagueStatistics);
    betRoundStatisticsSaver.save(betRoundStatistics);

    assertThat(betLeagueStatisticsFinder.findAll()).isNotEmpty().hasSize(1);
    assertThat(betRoundStatisticsFinder.findAll()).isNotEmpty().hasSize(1);
  }

  @Test
  void shouldFindBetRoundStatisticForLeague() {
    MatchLeagueFactory matchLeagueFactory = new MatchLeagueFactory(entityManager);
    long leagueId = matchLeagueFactory.buildMatchLeague();
    long roundId = matchLeagueFactory.buildMatchRound(leagueId);
    betRoundStatisticsSaver.save(builder().roundId(roundId).build());
    betRoundStatisticsSaver.save(builder().roundId(roundId).build());

    List<BetRoundStatistics> byLeague = betRoundStatisticsFinder.findByLeague(leagueId);

    assertThat(byLeague).isNotEmpty().hasSize(2);
  }

  @Test
  void shouldFindStatsForUserIdAndRoundId() {
    betRoundStatisticsSaver.save(builder().roundId(1L).userId(2L).build());

    Optional<BetRoundStatistics> byUserIdAndRoundId = betRoundStatisticsFinder.findByUserIdAndRoundId(2L, 1L);
    assertThat(byUserIdAndRoundId).isPresent();
  }

  @Test
  void shouldReturnOptionalEmptyWhenStatsByUserIdAndRoundIdNotExist() {
    Optional<BetRoundStatistics> byUserIdAndRoundId = betRoundStatisticsFinder.findByUserIdAndRoundId(3L, 4L);

    assertThat(byUserIdAndRoundId).isNotPresent();
  }

  @Test
  void shouldFindStatsForUserIdAndLeagueId() {
    betLeagueStatisticsSaver.save(BetLeagueStatistics.builder().userId(1L).leagueId(2L).build());

    Optional<BetLeagueStatistics> byUserIdAndLeagueId = betLeagueStatisticsFinder.findByUserIdAndLeagueId(1L, 2L);

    assertThat(byUserIdAndLeagueId).isPresent();
  }

  @Test
  void shouldReturnOptionalEmptyWhenStatsByUserIdAndLeagueIdNotExist() {
    Optional<BetLeagueStatistics> byUserIdAndLeagueId = betLeagueStatisticsFinder.findByUserIdAndLeagueId(5L, 6L);

    assertThat(byUserIdAndLeagueId).isNotPresent();

  }

}