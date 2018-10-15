package pl.michal.olszewski.typer.bet.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import pl.michal.olszewski.typer.bet.dto.BetLeagueStatisticsNotCalculated;
import pl.michal.olszewski.typer.bet.dto.BetRoundStatisticsNotCalculated;
import pl.michal.olszewski.typer.bet.dto.read.BetLeagueUserStatistics;
import pl.michal.olszewski.typer.bet.dto.read.BetRoundUserStatistics;

class BetStatisticsRestControllerUnitTest {

  private BetStatisticsRestController restController;
  private BetRoundStatisticsFinder betRoundStatisticsFinder = new InMemoryBetRoundStatisticsFinder();
  private BetLeagueStatisticsFinder betLeagueStatisticsFinder = new InMemoryBetLeagueStatisticsFinder();
  private BetRoundStatisticsSaver betRoundStatisticsSaver = new InMemoryBetRoundStatisticsSaver();
  private BetLeagueStatisticsSaver betLeagueStatisticsSaver = new InMemoryBetLeagueStatisticsSaver();

  @BeforeEach
  void setUp() {
    BetStatisticsService betStatisticsService = new BetStatisticsService(betRoundStatisticsFinder, betLeagueStatisticsFinder);
    restController = new BetStatisticsRestController(betStatisticsService);
  }

  @Test
  void shouldReturnLeagueStatisticForUserAndLeagueId() {
    betLeagueStatisticsSaver.save(BetLeagueStatistics.builder().userId(2L).leagueId(1L).build());

    ResponseEntity<BetLeagueUserStatistics> userLeagueStats = restController.getUserLeagueStats(1L, 2L);

    assertThat(userLeagueStats.getStatusCodeValue()).isEqualTo(200);
    assertThat(userLeagueStats.getBody()).isNotNull();
  }


  @Test
  void shouldReturnRoundStatisticForUserAndRoundId() {
    betRoundStatisticsSaver.save(BetRoundStatistics.builder().userId(3L).roundId(1L).build());

    ResponseEntity<BetRoundUserStatistics> userRoundStats = restController.getUserRoundStats(1L, 3L);

    assertThat(userRoundStats.getStatusCodeValue()).isEqualTo(200);
    assertThat(userRoundStats.getBody()).isNotNull();
  }

  @Test
  void shouldThrowExceptionWhenLeagueStatisticForUserIdAndLeagueIdNotExist() {
    assertThrows(BetLeagueStatisticsNotCalculated.class, () -> restController.getUserLeagueStats(3L, 5L));
  }


  @Test
  void shouldThrowExceptionWhenRoundStatisticForUserIdAndRoundIdNotExist() {
    assertThrows(BetRoundStatisticsNotCalculated.class, () -> restController.getUserRoundStats(2L, 4L));
  }

}