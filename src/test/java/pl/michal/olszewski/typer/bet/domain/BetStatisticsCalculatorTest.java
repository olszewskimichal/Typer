package pl.michal.olszewski.typer.bet.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BetStatisticsCalculatorTest {

  private BetStatisticsCalculator betStatisticsCalculator;
  private BetFinder betFinder = new InMemoryBetFinder();
  private BetSaver betSaver = new InMemoryBetSaver();
  private BetRoundStatisticsFinder betRoundStatisticsFinder = new InMemoryBetRoundStatisticsFinder();
  private BetLeagueStatisticsFinder betLeagueStatisticsFinder = new InMemoryBetLeagueStatisticsFinder();

  private BetRoundStatisticsSaver betRoundStatisticsSaver = new InMemoryBetRoundStatisticsSaver();
  private BetLeagueStatisticsSaver betLeagueStatisticsSaver = new InMemoryBetLeagueStatisticsSaver();

  @BeforeEach
  void setUp() {
    betSaver.deleteAll();
    betLeagueStatisticsSaver.deleteAll();
    betRoundStatisticsSaver.deleteAll();
    betStatisticsCalculator = new BetStatisticsCalculator(betRoundStatisticsSaver, betLeagueStatisticsSaver, betRoundStatisticsFinder, betLeagueStatisticsFinder, betFinder);
  }

  @Test
  void shouldCalculateStatisticsPerRoundForMoreThen2Users() {
    betSaver.save(Bet.builder().status(BetStatus.CHECKED).matchRoundId(1L).userId(1L).points(2L).build());
    betSaver.save(Bet.builder().status(BetStatus.CHECKED).matchRoundId(1L).userId(2L).points(3L).build());
    betSaver.save(Bet.builder().status(BetStatus.CHECKED).matchRoundId(1L).userId(3L).points(1L).build());
    betSaver.save(Bet.builder().status(BetStatus.CHECKED).matchRoundId(1L).userId(3L).points(0L).build());

    betStatisticsCalculator.calculateStatisticsForRound(1L);

    assertThat(betRoundStatisticsFinder.findAll()).hasSize(3);
    assertThat(betRoundStatisticsFinder.findByUserIdAndRoundId(1L, 1L).get().getPosition()).isEqualTo(2L);
    assertThat(betRoundStatisticsFinder.findByUserIdAndRoundId(1L, 1L).get().getPoints()).isEqualTo(2L);
    assertThat(betRoundStatisticsFinder.findByUserIdAndRoundId(2L, 1L).get().getPosition()).isEqualTo(1L);
    assertThat(betRoundStatisticsFinder.findByUserIdAndRoundId(2L, 1L).get().getPoints()).isEqualTo(3L);
    assertThat(betRoundStatisticsFinder.findByUserIdAndRoundId(3L, 1L).get().getPosition()).isEqualTo(3L);
    assertThat(betRoundStatisticsFinder.findByUserIdAndRoundId(3L, 1L).get().getPoints()).isEqualTo(1L);
  }

  @Test
  void shouldCalculateStatisticsPerRoundForMoreThen1UserHave1Points() {
    betSaver.save(Bet.builder().status(BetStatus.CHECKED).matchRoundId(1L).userId(1L).points(1L).build());
    betSaver.save(Bet.builder().status(BetStatus.CHECKED).matchRoundId(1L).userId(2L).points(3L).build());
    betSaver.save(Bet.builder().status(BetStatus.CHECKED).matchRoundId(1L).userId(3L).points(1L).build());
    betSaver.save(Bet.builder().status(BetStatus.CHECKED).matchRoundId(1L).userId(3L).points(0L).build());

    betStatisticsCalculator.calculateStatisticsForRound(1L);

    assertThat(betRoundStatisticsFinder.findAll()).hasSize(3);
    assertThat(betRoundStatisticsFinder.findByUserIdAndRoundId(1L, 1L).get().getPosition()).isEqualTo(2L);
    assertThat(betRoundStatisticsFinder.findByUserIdAndRoundId(1L, 1L).get().getPoints()).isEqualTo(1L);
    assertThat(betRoundStatisticsFinder.findByUserIdAndRoundId(2L, 1L).get().getPosition()).isEqualTo(1L);
    assertThat(betRoundStatisticsFinder.findByUserIdAndRoundId(2L, 1L).get().getPoints()).isEqualTo(3L);
    assertThat(betRoundStatisticsFinder.findByUserIdAndRoundId(3L, 1L).get().getPosition()).isEqualTo(2L);
    assertThat(betRoundStatisticsFinder.findByUserIdAndRoundId(3L, 1L).get().getPoints()).isEqualTo(1L);
  }

  @Test
  void shouldCalculateStatisticsPerRoundForLessThen2Users() {
    betSaver.save(Bet.builder().status(BetStatus.CHECKED).matchRoundId(1L).userId(1L).points(2L).build());

    betStatisticsCalculator.calculateStatisticsForRound(1L);

    assertThat(betRoundStatisticsFinder.findAll()).hasSize(1);
    assertThat(betRoundStatisticsFinder.findByUserIdAndRoundId(1L, 1L).get().getPosition()).isEqualTo(1L);
    assertThat(betRoundStatisticsFinder.findByUserIdAndRoundId(1L, 1L).get().getPoints()).isEqualTo(2L);
  }

  @Test
  void shouldCalculateStatisticsPerLeagueForMoreThen2Users() {
    betRoundStatisticsSaver.save(BetRoundStatistics.builder().roundId(1L).userId(1L).points(2L).build());
    betRoundStatisticsSaver.save(BetRoundStatistics.builder().roundId(1L).userId(2L).points(3L).build());
    betRoundStatisticsSaver.save(BetRoundStatistics.builder().roundId(1L).userId(3L).points(1L).build());
    betRoundStatisticsSaver.save(BetRoundStatistics.builder().roundId(1L).userId(3L).points(0L).build());

    betStatisticsCalculator.calculateStatisticsForLeague(1L);

    List<BetLeagueStatistics> statisticsBaseList = betLeagueStatisticsFinder.findAll();
    assertThat(statisticsBaseList).hasSize(3);
    assertThat(betLeagueStatisticsFinder.findByUserIdAndLeagueId(1L, 1L).get().getPosition()).isEqualTo(2L);
    assertThat(betLeagueStatisticsFinder.findByUserIdAndLeagueId(1L, 1L).get().getPoints()).isEqualTo(2L);
    assertThat(betLeagueStatisticsFinder.findByUserIdAndLeagueId(2L, 1L).get().getPosition()).isEqualTo(1L);
    assertThat(betLeagueStatisticsFinder.findByUserIdAndLeagueId(2L, 1L).get().getPoints()).isEqualTo(3L);
    assertThat(betLeagueStatisticsFinder.findByUserIdAndLeagueId(3L, 1L).get().getPosition()).isEqualTo(3L);
    assertThat(betLeagueStatisticsFinder.findByUserIdAndLeagueId(3L, 1L).get().getPoints()).isEqualTo(1L);
  }

  @Test
  void shouldCalculateStatisticsPerLeagueForMoreThen1UserHave1Points() {
    betRoundStatisticsSaver.save(BetRoundStatistics.builder().roundId(1L).userId(1L).points(1L).build());
    betRoundStatisticsSaver.save(BetRoundStatistics.builder().roundId(1L).userId(2L).points(3L).build());
    betRoundStatisticsSaver.save(BetRoundStatistics.builder().roundId(1L).userId(3L).points(1L).build());
    betRoundStatisticsSaver.save(BetRoundStatistics.builder().roundId(1L).userId(3L).points(0L).build());

    betStatisticsCalculator.calculateStatisticsForLeague(1L);

    List<BetLeagueStatistics> statisticsBaseList = betLeagueStatisticsFinder.findAll();
    assertThat(statisticsBaseList).hasSize(3);
    assertThat(betLeagueStatisticsFinder.findByUserIdAndLeagueId(1L, 1L).get().getPosition()).isEqualTo(2L);
    assertThat(betLeagueStatisticsFinder.findByUserIdAndLeagueId(1L, 1L).get().getPoints()).isEqualTo(1L);
    assertThat(betLeagueStatisticsFinder.findByUserIdAndLeagueId(2L, 1L).get().getPosition()).isEqualTo(1L);
    assertThat(betLeagueStatisticsFinder.findByUserIdAndLeagueId(2L, 1L).get().getPoints()).isEqualTo(3L);
    assertThat(betLeagueStatisticsFinder.findByUserIdAndLeagueId(3L, 1L).get().getPosition()).isEqualTo(2L);
    assertThat(betLeagueStatisticsFinder.findByUserIdAndLeagueId(3L, 1L).get().getPoints()).isEqualTo(1L);
  }

  @Test
  void shouldCalculateStatisticsPerLeagueForLessThen2Users() {
    betRoundStatisticsSaver.save(BetRoundStatistics.builder().roundId(1L).userId(1L).points(2L).build());

    betStatisticsCalculator.calculateStatisticsForLeague(1L);

    List<BetLeagueStatistics> statisticsBaseList = betLeagueStatisticsFinder.findAll();
    assertThat(statisticsBaseList).hasSize(1);
    assertThat(betLeagueStatisticsFinder.findByUserIdAndLeagueId(1L, 1L).get().getPosition()).isEqualTo(1L);
    assertThat(betLeagueStatisticsFinder.findByUserIdAndLeagueId(1L, 1L).get().getPoints()).isEqualTo(2L);
  }

  @Test
  void shouldRecalculateRoundStatisticsWhenHaveOne() {
    betRoundStatisticsSaver.save(BetRoundStatistics.builder().roundId(1L).userId(1L).points(2L).build());
    BetLeagueStatistics betLeagueStatistics = BetLeagueStatistics.builder().id(2L).userId(1L).leagueId(1L).build();
    betLeagueStatisticsSaver.save(betLeagueStatistics);

    betStatisticsCalculator.calculateStatisticsForLeague(1L);

    List<BetLeagueStatistics> statisticsBaseList = betLeagueStatisticsFinder.findAll();
    assertThat(statisticsBaseList).hasSize(1);
    assertThat(betLeagueStatisticsFinder.findByUserIdAndLeagueId(1L, 1L).get().getPosition()).isEqualTo(1L);
    assertThat(betLeagueStatisticsFinder.findByUserIdAndLeagueId(1L, 1L).get().getPoints()).isEqualTo(2L);
    assertThat(betLeagueStatisticsFinder.findByUserIdAndLeagueId(1L, 1L).get().getId()).isEqualTo(2L);

  }

  @Test
  void shouldRecalculateLeagueStatisticsWhenHaveOne() {
    betSaver.save(Bet.builder().status(BetStatus.CHECKED).matchRoundId(1L).userId(1L).points(2L).build());
    BetRoundStatistics betRoundStatistics = BetRoundStatistics.builder().id(2L).userId(1L).roundId(1L).build();
    betRoundStatisticsSaver.save(betRoundStatistics);

    betStatisticsCalculator.calculateStatisticsForRound(1L);

    assertThat(betRoundStatisticsFinder.findAll()).hasSize(1);
    assertThat(betRoundStatisticsFinder.findByUserIdAndRoundId(1L, 1L).get().getPosition()).isEqualTo(1L);
    assertThat(betRoundStatisticsFinder.findByUserIdAndRoundId(1L, 1L).get().getPoints()).isEqualTo(2L);
    assertThat(betRoundStatisticsFinder.findByUserIdAndRoundId(1L, 1L).get().getId()).isEqualTo(2L);
  }

}