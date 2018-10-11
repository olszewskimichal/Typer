package pl.michal.olszewski.typer.bet.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BetStatisticsCalculatorTest {

  private BetStatisticsCalculator betStatisticsCalculator;
  private BetFinder betFinder = new InMemoryBetFinder();
  private BetSaver betSaver = new InMemoryBetSaver();
  private BetStatisticsFinder betStatisticsFinder = new InMemoryBetStatisticsFinder();
  private BetStatisticsSaver betStatisticsSaver = new InMemoryBetStatisticsSaver();

  @BeforeEach
  void setUp() {
    betSaver.deleteAll();
    betStatisticsSaver.deleteAll();
    betStatisticsCalculator = new BetStatisticsCalculator(betStatisticsSaver, betStatisticsFinder, betFinder);
  }

  @Test
  void shouldCalculateStatisticsPerRoundForMoreThen2Users() {
    betSaver.save(Bet.builder().status(BetStatus.CHECKED).matchRoundId(1L).userId(1L).points(2L).build());
    betSaver.save(Bet.builder().status(BetStatus.CHECKED).matchRoundId(1L).userId(2L).points(3L).build());
    betSaver.save(Bet.builder().status(BetStatus.CHECKED).matchRoundId(1L).userId(3L).points(1L).build());
    betSaver.save(Bet.builder().status(BetStatus.CHECKED).matchRoundId(1L).userId(3L).points(0L).build());

    betStatisticsCalculator.calculateStatisticsForRound(1L);

    assertThat(betStatisticsFinder.findAll()).hasSize(3);
    assertThat(forUser(1L, betStatisticsFinder.findAll()).getPosition()).isEqualTo(2L);
    assertThat(forUser(1L, betStatisticsFinder.findAll()).getPoints()).isEqualTo(2L);
    assertThat(forUser(2L, betStatisticsFinder.findAll()).getPosition()).isEqualTo(1L);
    assertThat(forUser(2L, betStatisticsFinder.findAll()).getPoints()).isEqualTo(3L);
    assertThat(forUser(3L, betStatisticsFinder.findAll()).getPosition()).isEqualTo(3L);
    assertThat(forUser(3L, betStatisticsFinder.findAll()).getPoints()).isEqualTo(1L);
  }

  @Test
  void shouldCalculateStatisticsPerRoundForMoreThen1UserHave1Points() {
    betSaver.save(Bet.builder().status(BetStatus.CHECKED).matchRoundId(1L).userId(1L).points(1L).build());
    betSaver.save(Bet.builder().status(BetStatus.CHECKED).matchRoundId(1L).userId(2L).points(3L).build());
    betSaver.save(Bet.builder().status(BetStatus.CHECKED).matchRoundId(1L).userId(3L).points(1L).build());
    betSaver.save(Bet.builder().status(BetStatus.CHECKED).matchRoundId(1L).userId(3L).points(0L).build());

    betStatisticsCalculator.calculateStatisticsForRound(1L);

    assertThat(betStatisticsFinder.findAll()).hasSize(3);
    assertThat(forUser(1L, betStatisticsFinder.findAll()).getPosition()).isEqualTo(2L);
    assertThat(forUser(1L, betStatisticsFinder.findAll()).getPoints()).isEqualTo(1L);
    assertThat(forUser(2L, betStatisticsFinder.findAll()).getPosition()).isEqualTo(1L);
    assertThat(forUser(2L, betStatisticsFinder.findAll()).getPoints()).isEqualTo(3L);
    assertThat(forUser(3L, betStatisticsFinder.findAll()).getPosition()).isEqualTo(2L);
    assertThat(forUser(3L, betStatisticsFinder.findAll()).getPoints()).isEqualTo(1L);
  }

  @Test
  void shouldCalculateStatisticsPerRoundForLessThen2Users() {
    betSaver.save(Bet.builder().status(BetStatus.CHECKED).matchRoundId(1L).userId(1L).points(2L).build());

    betStatisticsCalculator.calculateStatisticsForRound(1L);

    assertThat(betStatisticsFinder.findAll()).hasSize(1);
    assertThat(forUser(1L, betStatisticsFinder.findAll()).getPosition()).isEqualTo(1L);
    assertThat(forUser(1L, betStatisticsFinder.findAll()).getPoints()).isEqualTo(2L);
  }

  @Test
  void shouldCalculateStatisticsPerLeagueForMoreThen2Users() {
    betStatisticsSaver.save(BetRoundStatistics.builder().roundId(1L).userId(1L).points(2L).build());
    betStatisticsSaver.save(BetRoundStatistics.builder().roundId(1L).userId(2L).points(3L).build());
    betStatisticsSaver.save(BetRoundStatistics.builder().roundId(1L).userId(3L).points(1L).build());
    betStatisticsSaver.save(BetRoundStatistics.builder().roundId(1L).userId(3L).points(0L).build());

    betStatisticsCalculator.calculateStatisticsForLeague(1L);

    List<BetStatisticsBase> statisticsBaseList = betStatisticsFinder.findAll().stream().filter(v -> v instanceof BetLeagueStatistics).collect(Collectors.toList());
    assertThat(statisticsBaseList).hasSize(3);
    assertThat(forUser(1L, statisticsBaseList).getPosition()).isEqualTo(2L);
    assertThat(forUser(1L, statisticsBaseList).getPoints()).isEqualTo(2L);
    assertThat(forUser(2L, statisticsBaseList).getPosition()).isEqualTo(1L);
    assertThat(forUser(2L, statisticsBaseList).getPoints()).isEqualTo(3L);
    assertThat(forUser(3L, statisticsBaseList).getPosition()).isEqualTo(3L);
    assertThat(forUser(3L, statisticsBaseList).getPoints()).isEqualTo(1L);
  }

  @Test
  void shouldCalculateStatisticsPerLeagueForMoreThen1UserHave1Points() {
    betStatisticsSaver.save(BetRoundStatistics.builder().roundId(1L).userId(1L).points(1L).build());
    betStatisticsSaver.save(BetRoundStatistics.builder().roundId(1L).userId(2L).points(3L).build());
    betStatisticsSaver.save(BetRoundStatistics.builder().roundId(1L).userId(3L).points(1L).build());
    betStatisticsSaver.save(BetRoundStatistics.builder().roundId(1L).userId(3L).points(0L).build());

    betStatisticsCalculator.calculateStatisticsForLeague(1L);

    List<BetStatisticsBase> statisticsBaseList = betStatisticsFinder.findAll().stream().filter(v -> v instanceof BetLeagueStatistics).collect(Collectors.toList());
    assertThat(statisticsBaseList).hasSize(3);
    assertThat(forUser(1L, statisticsBaseList).getPosition()).isEqualTo(2L);
    assertThat(forUser(1L, statisticsBaseList).getPoints()).isEqualTo(1L);
    assertThat(forUser(2L, statisticsBaseList).getPosition()).isEqualTo(1L);
    assertThat(forUser(2L, statisticsBaseList).getPoints()).isEqualTo(3L);
    assertThat(forUser(3L, statisticsBaseList).getPosition()).isEqualTo(2L);
    assertThat(forUser(3L, statisticsBaseList).getPoints()).isEqualTo(1L);
  }

  @Test
  void shouldCalculateStatisticsPerLeagueForLessThen2Users() {
    betStatisticsSaver.save(BetRoundStatistics.builder().roundId(1L).userId(1L).points(2L).build());

    betStatisticsCalculator.calculateStatisticsForLeague(1L);

    List<BetStatisticsBase> statisticsBaseList = betStatisticsFinder.findAll().stream().filter(v -> v instanceof BetLeagueStatistics).collect(Collectors.toList());
    assertThat(statisticsBaseList).hasSize(1);
    assertThat(forUser(1L, statisticsBaseList).getPosition()).isEqualTo(1L);
    assertThat(forUser(1L, statisticsBaseList).getPoints()).isEqualTo(2L);
  }

  @Test
  void shouldRecalculateRoundStatisticsWhenHaveOne() {
    betStatisticsSaver.save(BetRoundStatistics.builder().roundId(1L).userId(1L).points(2L).build());
    BetLeagueStatistics betLeagueStatistics = BetLeagueStatistics.builder().id(2L).userId(1L).leagueId(1L).build();
    betStatisticsSaver.save(betLeagueStatistics);

    betStatisticsCalculator.calculateStatisticsForLeague(1L);

    List<BetStatisticsBase> statisticsBaseList = betStatisticsFinder.findAll().stream().filter(v -> v instanceof BetLeagueStatistics).collect(Collectors.toList());
    assertThat(statisticsBaseList).hasSize(1);
    assertThat(forUser(1L, statisticsBaseList).getPosition()).isEqualTo(1L);
    assertThat(forUser(1L, statisticsBaseList).getPoints()).isEqualTo(2L);
    assertThat(betStatisticsFinder.findByUserIdAndLeagueId(1L, 1L).get().getId()).isEqualTo(2L);

  }

  @Test
  void shouldRecalculateLeagueStatisticsWhenHaveOne() {
    betSaver.save(Bet.builder().status(BetStatus.CHECKED).matchRoundId(1L).userId(1L).points(2L).build());
    BetRoundStatistics betRoundStatistics = BetRoundStatistics.builder().id(2L).userId(1L).roundId(1L).build();
    betStatisticsSaver.save(betRoundStatistics);

    betStatisticsCalculator.calculateStatisticsForRound(1L);

    assertThat(betStatisticsFinder.findAll()).hasSize(1);
    assertThat(forUser(1L, betStatisticsFinder.findAll()).getPosition()).isEqualTo(1L);
    assertThat(forUser(1L, betStatisticsFinder.findAll()).getPoints()).isEqualTo(2L);
    assertThat(betStatisticsFinder.findByUserIdAndRoundId(1L, 1L).get().getId()).isEqualTo(2L);
  }

  BetStatisticsBase forUser(Long userId, List<BetStatisticsBase> list) {
    return list.stream().filter(v -> v.getUserId().equals(userId)).findAny().get();
  }

}