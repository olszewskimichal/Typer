package pl.michal.olszewski.typer.bet.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.michal.olszewski.typer.bet.dto.read.BetLeagueUserStatistics;
import pl.michal.olszewski.typer.bet.dto.read.BetRoundUserStatistics;

class BetStatisticsServiceTest {

  private BetStatisticsFinder betStatisticsFinder = new InMemoryBetStatisticsFinder();
  private BetStatisticsSaver betStatisticsSaver = new InMemoryBetStatisticsSaver();
  private BetStatisticsService betStatisticsService;

  @BeforeEach
  void configureSystemUnderTests() {
    betStatisticsService = new BetStatisticsService(betStatisticsFinder);
  }

  @Test
  void shouldReturnStatisticsForUserAndRound() {
    betStatisticsSaver.save(BetRoundStatistics.builder().position(1L).points(9L).roundId(2L).userId(1L).build());

    BetRoundUserStatistics statisticsForUserAndRound = betStatisticsService.getStatisticsForUserAndRound(1L, 2L);
    assertThat(statisticsForUserAndRound.getPosition()).isEqualTo(1L);
    assertThat(statisticsForUserAndRound.getUserId()).isEqualTo(1L);
    assertThat(statisticsForUserAndRound.getRoundPoints().getPoints()).isEqualTo(9L);
    assertThat(statisticsForUserAndRound.getRoundPoints().getRoundId()).isEqualTo(2L);
  }

  @Test
  void shouldReturnStatisticsForUserAndLeague() {
    betStatisticsSaver.save(BetLeagueStatistics.builder().position(1L).leagueId(2L).userId(1L).points(9L).build());

    BetLeagueUserStatistics statisticsForUserAndRound = betStatisticsService.getStatisticsForUserAndLeague(1L, 2L);
    assertThat(statisticsForUserAndRound.getPosition()).isEqualTo(1L);
    assertThat(statisticsForUserAndRound.getUserId()).isEqualTo(1L);
    assertThat(statisticsForUserAndRound.getPoints()).isEqualTo(9L);
    assertThat(statisticsForUserAndRound.getLeagueId()).isEqualTo(2L);
  }

}