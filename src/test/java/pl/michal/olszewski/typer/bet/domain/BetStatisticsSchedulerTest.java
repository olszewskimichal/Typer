package pl.michal.olszewski.typer.bet.domain;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BetStatisticsSchedulerTest {

  private BetStatisticsScheduler betStatisticsScheduler;
  private BetFinder betFinder = new InMemoryBetFinder();
  private BetSaver betSaver = new InMemoryBetSaver();
  private BetStatisticsProperties betStatisticsProperties = new BetStatisticsProperties();
  private BetStatisticsCalculator betStatisticsCalculator = mock(BetStatisticsCalculator.class);

  @BeforeEach
  void setUp() {
    betSaver.deleteAll();
    betStatisticsScheduler = new BetStatisticsScheduler(betFinder, betStatisticsProperties, betStatisticsCalculator);
  }

  @Test
  void shouldNotCalculateStatisticsWhenNothingChanged() {
    betStatisticsScheduler.calculateStatistics();
    Mockito.verifyNoMoreInteractions(betStatisticsCalculator);
  }

  @Test
  void shouldCalculateStatistics() {
    betSaver.save(Bet.builder().modified(Instant.now()).matchRoundId(2L).leagueId(1L).build());

    betStatisticsScheduler.calculateStatistics();
    Mockito.verify(betStatisticsCalculator, times(1)).calculateStatisticsForRound(2L);
    Mockito.verify(betStatisticsCalculator, times(1)).calculateStatisticsForLeague(1L);

    Mockito.verifyNoMoreInteractions(betStatisticsCalculator);
  }
}