package pl.michal.olszewski.typer.bet.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.michal.olszewski.typer.bet.dto.read.BetLeagueUserStatistics;
import pl.michal.olszewski.typer.bet.dto.read.BetRoundUserStatistics;

class BetStatisticsServiceTest {

  private BetFinder betFinder = new InMemoryBetFinder();
  private BetStatisticsService betStatisticsService;

  @BeforeEach
  void configureSystemUnderTests() {
    betStatisticsService = new BetStatisticsService(betFinder);
    givenBets()
        .deleteAll();
  }

  @Test
  void shouldReturnStatisticsForUserAndRound() {
    Bet bet1 = Bet.builder().id(1L).userId(1L).matchRoundId(2L).points(3L).build();
    Bet bet2 = Bet.builder().id(2L).userId(1L).matchRoundId(2L).points(6L).build();
    givenBets().save(bet1);
    givenBets().save(bet2);

    BetRoundUserStatistics statisticsForUserAndRound = betStatisticsService.getStatisticsForUserAndRound(1L, 2L);
    assertThat(statisticsForUserAndRound.getPosition()).isEqualTo(1L);
    assertThat(statisticsForUserAndRound.getUserId()).isEqualTo(1L);
    assertThat(statisticsForUserAndRound.getRoundPoints().getPoints()).isEqualTo(9L);
    assertThat(statisticsForUserAndRound.getRoundPoints().getRoundId()).isEqualTo(2L);
  }

  @Test
  void shouldReturnStatisticsForUserAndLeague() {
    Bet bet1 = Bet.builder().id(1L).userId(1L).matchRoundId(2L).points(3L).build();
    Bet bet2 = Bet.builder().id(2L).userId(1L).matchRoundId(2L).points(6L).build();
    givenBets().save(bet1);
    givenBets().save(bet2);

    BetLeagueUserStatistics statisticsForUserAndRound = betStatisticsService.getStatisticsForUserAndLeague(1L, 2L);
    assertThat(statisticsForUserAndRound.getPosition()).isEqualTo(1L);
    assertThat(statisticsForUserAndRound.getUserId()).isEqualTo(1L);
    assertThat(statisticsForUserAndRound.getPoints()).isEqualTo(9L);
    assertThat(statisticsForUserAndRound.getLeagueId()).isEqualTo(2L);
  }

  private BetFactory givenBets() {
    return new BetFactory(new InMemoryBetSaver());
  }
}