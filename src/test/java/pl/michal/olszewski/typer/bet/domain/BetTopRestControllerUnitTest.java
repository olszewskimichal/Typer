package pl.michal.olszewski.typer.bet.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import pl.michal.olszewski.typer.bet.dto.read.BetLeagueUserStatistics;
import pl.michal.olszewski.typer.bet.dto.read.BetRoundUserStatistics;

class BetTopRestControllerUnitTest {

  private BetTopRestController restController;
  private BetRoundStatisticsFinder betRoundStatisticsFinder = new InMemoryBetRoundStatisticsFinder();
  private BetLeagueStatisticsFinder betLeagueStatisticsFinder = new InMemoryBetLeagueStatisticsFinder();
  private BetRoundStatisticsSaver betRoundStatisticsSaver = new InMemoryBetRoundStatisticsSaver();
  private BetLeagueStatisticsSaver betLeagueStatisticsSaver = new InMemoryBetLeagueStatisticsSaver();

  @BeforeEach
  void setUp() {
    betLeagueStatisticsSaver.deleteAll();
    betRoundStatisticsSaver.deleteAll();
    BetStatisticsService betStatisticsService = new BetStatisticsService(betRoundStatisticsFinder, betLeagueStatisticsFinder);
    restController = new BetTopRestController(betStatisticsService);
  }

  @Test
  void shouldReturnLeagueStatisticForUserAndLeagueId() {
    betLeagueStatisticsSaver.save(BetLeagueStatistics.builder().userId(4L).leagueId(1L).points(6L).build());
    betLeagueStatisticsSaver.save(BetLeagueStatistics.builder().userId(3L).leagueId(1L).points(5L).build());
    betLeagueStatisticsSaver.save(BetLeagueStatistics.builder().userId(2L).leagueId(1L).points(4L).build());

    ResponseEntity<List<BetLeagueUserStatistics>> userLeagueStats = restController.getLeagueTOP(1L, 2L);

    assertThat(userLeagueStats.getStatusCodeValue()).isEqualTo(200);
    assertThat(userLeagueStats.getBody()).isNotNull().hasSize(2);
  }


  @Test
  void shouldReturnRoundStatisticForUserAndRoundId() {
    betRoundStatisticsSaver.save(BetRoundStatistics.builder().userId(4L).roundId(2L).points(6L).build());
    betRoundStatisticsSaver.save(BetRoundStatistics.builder().userId(3L).roundId(2L).points(5L).build());
    betRoundStatisticsSaver.save(BetRoundStatistics.builder().userId(2L).roundId(2L).points(4L).build());

    ResponseEntity<List<BetRoundUserStatistics>> userRoundStats = restController.getRoundTOP(2L, 2L);

    assertThat(userRoundStats.getStatusCodeValue()).isEqualTo(200);
    assertThat(userRoundStats.getBody()).isNotNull().hasSize(2);
  }

}