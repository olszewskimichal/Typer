package pl.michal.olszewski.typer.match.domain;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.michal.olszewski.typer.match.dto.events.MatchFinished;

class FinishMatchFileAdapterTest {

  private FinishMatchFileAdapter matchLeagueFileAdapter;
  private MatchEventPublisher eventPublisher;

  @BeforeEach
  void setUp() {
    eventPublisher = mock(MatchEventPublisher.class);
    MatchSaver matchSaver = new InMemoryMatchSaver();
    matchSaver.save(Match.builder().id(1L).matchRound(new MatchRound()).build());
    MatchFinder matchFinder = new InMemoryMatchFinder();
    MatchUpdater matchUpdater = new MatchUpdater(matchFinder, eventPublisher);
    matchLeagueFileAdapter = new FinishMatchFileAdapter(matchUpdater);

  }

  @Test
  void shouldCreateOneMatchFinishEventFromXlsxFile() throws IOException {
    //given
    Path file = Paths.get("testresources/finishMatch.xlsx");

    //when
    matchLeagueFileAdapter.loadMatchResultsFromFile(file);

    //then
    Mockito.verify(eventPublisher, times(1)).sendMatchFinishedToJMS(Mockito.any(MatchFinished.class));
  }

  @Test
  void shouldCreateOneMatchFinishEventFromXlsFile() throws IOException {
    Path file = Paths.get("testresources/finishMatch.xls");

    //when
    matchLeagueFileAdapter.loadMatchResultsFromFile(file);

    //then
    Mockito.verify(eventPublisher, times(1)).sendMatchFinishedToJMS(Mockito.any(MatchFinished.class));
  }

}