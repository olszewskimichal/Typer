package pl.michal.olszewski.typer.bet.domain;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.michal.olszewski.typer.match.domain.InMemoryMatchLeagueFinder;
import pl.michal.olszewski.typer.match.domain.InMemoryMatchLeagueSaver;
import pl.michal.olszewski.typer.match.domain.MatchLeagueFinder;

class BetStatisticsSchedulerTest {

  BetStatisticsScheduler betStatisticsScheduler;
  private BetFinder betFinder = new InMemoryBetFinder();
  private BetSaver betSaver = new InMemoryBetSaver();
  private MatchLeagueFinder matchLeagueFinder = new InMemoryMatchLeagueFinder();
  private BetStatisticsProperties betStatisticsProperties = new BetStatisticsProperties();
  private BetStatisticsCalculator betStatisticsCalculator = mock(BetStatisticsCalculator.class);

  @BeforeEach
  void setUp() {
    betSaver.deleteAll();
    betStatisticsScheduler = new BetStatisticsScheduler(betFinder, betStatisticsProperties, betStatisticsCalculator, matchLeagueFinder);
  }

  @Test
  void shouldNotCalculateStatisticsWhenNothingChanged() {
    betStatisticsScheduler.calculateStatistics();
    //Mockito.verify(betUpdater, times(1)).cancelBet(cancelBet);
    Mockito.verifyNoMoreInteractions(betStatisticsCalculator);
  }

  @Test
  void shouldCalculateStatistics() {
    InMemoryMatchLeagueSaver inMemoryMatchLeagueSaver = new InMemoryMatchLeagueSaver();
    inMemoryMatchLeagueSaver.buildMatchLeague(1L);
    inMemoryMatchLeagueSaver.buildMatchRound(1L, 2L);

    betSaver.save(Bet.builder().modified(Instant.now()).matchRoundId(2L).build());

    betStatisticsScheduler.calculateStatistics();
    Mockito.verify(betStatisticsCalculator, times(1)).calculateStatisticsForRound(2L);
    Mockito.verify(betStatisticsCalculator, times(1)).calculateStatisticsForLeague(1L);

    Mockito.verifyNoMoreInteractions(betStatisticsCalculator);
  }
}