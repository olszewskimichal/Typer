package pl.michal.olszewski.typer.match.domain;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;
import pl.michal.olszewski.typer.adapter.FileAdapter;
import pl.michal.olszewski.typer.adapter.FileAdapterRow;
import pl.michal.olszewski.typer.adapter.XlsAdapter;
import pl.michal.olszewski.typer.adapter.XlsxAdapter;
import pl.michal.olszewski.typer.match.dto.command.CreateNewRound;

@Component
class MatchRoundFileAdapter {

  private static final String name = "name";
  private static final String roundId = "roundId";
  private static final List<String> defaultColumns = Arrays.asList(name, roundId);

  private final MatchRoundCreator matchRoundCreator;
  private final MatchRoundSaver matchRoundSaver;

  public MatchRoundFileAdapter(MatchRoundCreator matchRoundCreator, MatchRoundSaver matchRoundSaver) {
    this.matchRoundCreator = matchRoundCreator;
    this.matchRoundSaver = matchRoundSaver;
  }

  void loadLeaguesFromFile(File file) throws IOException {
    try (FileAdapter fileAdapter = selectAdapter(file)) {
      for (FileAdapterRow fileAdapterRow : fileAdapter) {
        String name = fileAdapterRow.get(MatchRoundFileAdapter.name);
        Long roundId = Long.valueOf(fileAdapterRow.get(MatchRoundFileAdapter.roundId));
        MatchRound matchRound = matchRoundCreator.from(CreateNewRound.builder().name(name).leagueId(roundId).build());
        matchRoundSaver.save(matchRound);
      }
    }
  }

  private FileAdapter selectAdapter(File file) throws IOException {
    if (file.getName().toLowerCase().endsWith(".xlsx")) {
      return new XlsxAdapter(file, MatchRoundFileAdapter.defaultColumns);
    } else {
      return new XlsAdapter(file, MatchRoundFileAdapter.defaultColumns);
    }
  }
}
