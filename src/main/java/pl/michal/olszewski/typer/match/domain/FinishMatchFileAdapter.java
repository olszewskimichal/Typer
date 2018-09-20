package pl.michal.olszewski.typer.match.domain;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;
import pl.michal.olszewski.typer.adapter.FileAdapter;
import pl.michal.olszewski.typer.adapter.FileAdapterRow;
import pl.michal.olszewski.typer.adapter.XlsAdapter;
import pl.michal.olszewski.typer.adapter.XlsxAdapter;
import pl.michal.olszewski.typer.match.dto.command.FinishMatch;

@Component
class FinishMatchFileAdapter {

  private static final String matchId = "matchId";
  private static final String homeGoals = "homeGoals";
  private static final String awayGoals = "awayGoals";
  private static final List<String> defaultColumns = Arrays.asList(matchId, homeGoals, awayGoals);

  private final MatchUpdater matchUpdater;

  FinishMatchFileAdapter(MatchUpdater matchUpdater) {
    this.matchUpdater = matchUpdater;
  }

  void loadMatchResultsFromFile(Path path) throws IOException {
    try (FileAdapter fileAdapter = selectAdapter(path)) {
      for (FileAdapterRow fileAdapterRow : fileAdapter) {
        Long matchId = Long.valueOf(fileAdapterRow.get(FinishMatchFileAdapter.matchId));
        Long homeGoals = Long.valueOf(fileAdapterRow.get(FinishMatchFileAdapter.homeGoals));
        Long awayGoals = Long.valueOf(fileAdapterRow.get(FinishMatchFileAdapter.awayGoals));
        FinishMatch finishMatch = FinishMatch.builder().matchId(matchId).awayGoals(awayGoals).homeGoals(homeGoals).build();
        matchUpdater.finishMatch(finishMatch);
      }
    }
  }

  private FileAdapter selectAdapter(Path path) throws IOException {
    if (path.getFileName().toString().toLowerCase().endsWith(".xlsx")) {
      return new XlsxAdapter(path, FinishMatchFileAdapter.defaultColumns);
    } else {
      return new XlsAdapter(path, FinishMatchFileAdapter.defaultColumns);
    }
  }
}
