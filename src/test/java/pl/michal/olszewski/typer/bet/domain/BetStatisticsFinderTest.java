package pl.michal.olszewski.typer.bet.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.michal.olszewski.typer.bet.domain.BetRoundStatistics.builder;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

  @Test
  void shouldReturnTOP3BetLeagueStats() {
    BetLeagueStatistics stats3 = BetLeagueStatistics.builder().userId(4L).leagueId(2L).points(6L).build();
    BetLeagueStatistics stats2 = BetLeagueStatistics.builder().userId(3L).leagueId(2L).points(5L).build();
    BetLeagueStatistics stats1 = BetLeagueStatistics.builder().userId(2L).leagueId(2L).points(4L).build();
    betLeagueStatisticsSaver.save(BetLeagueStatistics.builder().userId(1L).leagueId(2L).points(3L).build());
    betLeagueStatisticsSaver.save(stats1);
    betLeagueStatisticsSaver.save(stats2);
    betLeagueStatisticsSaver.save(stats3);

    Page<BetLeagueStatistics> byLeagueIdOrderByPointsDesc = betLeagueStatisticsFinder.findByLeagueIdOrderByPointsDesc(2L, PageRequest.of(0, 3));

    assertThat(byLeagueIdOrderByPointsDesc.getTotalElements()).isEqualTo(4);
    assertThat(byLeagueIdOrderByPointsDesc.getTotalPages()).isEqualTo(2);
    assertThat(byLeagueIdOrderByPointsDesc.getContent()).contains(stats3, stats2, stats1);
  }

  @Test
  void shouldReturnTOP3BetRoundStats() {
    BetRoundStatistics stats3 = BetRoundStatistics.builder().userId(4L).roundId(2L).points(6L).build();
    BetRoundStatistics stats2 = BetRoundStatistics.builder().userId(3L).roundId(2L).points(5L).build();
    BetRoundStatistics stats1 = BetRoundStatistics.builder().userId(2L).roundId(2L).points(4L).build();
    betRoundStatisticsSaver.save(BetRoundStatistics.builder().userId(1L).roundId(2L).points(3L).build());
    betRoundStatisticsSaver.save(stats1);
    betRoundStatisticsSaver.save(stats2);
    betRoundStatisticsSaver.save(stats3);

    Page<BetRoundStatistics> byLeagueIdOrderByPointsDesc = betRoundStatisticsFinder.findByRoundIdOrderByPointsDesc(2L, PageRequest.of(0, 3));

    assertThat(byLeagueIdOrderByPointsDesc.getTotalElements()).isEqualTo(4);
    assertThat(byLeagueIdOrderByPointsDesc.getTotalPages()).isEqualTo(2);
    assertThat(byLeagueIdOrderByPointsDesc.getContent()).contains(stats3, stats2, stats1);
  }
}