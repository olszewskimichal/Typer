package pl.michal.olszewski.typer.match.domain;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.michal.olszewski.typer.adapter.FileAdapter;
import pl.michal.olszewski.typer.adapter.FileAdapterRow;
import pl.michal.olszewski.typer.adapter.XlsAdapter;
import pl.michal.olszewski.typer.adapter.XlsxAdapter;
import pl.michal.olszewski.typer.file.FileStorageService;
import pl.michal.olszewski.typer.match.dto.command.FinishMatch;

@Component
class FinishMatchFileAdapter {

  private static final String MATCH_ID = "matchId";
  private static final String HOME_GOALS = "homeGoals";
  private static final String AWAY_GOALS = "awayGoals";
  private static final List<String> DEFAULT_COLUMNS = Arrays.asList(MATCH_ID, HOME_GOALS, AWAY_GOALS);

  private final MatchUpdater matchUpdater;
  private final FileStorageService fileStorageService;


  FinishMatchFileAdapter(MatchUpdater matchUpdater, FileStorageService fileStorageService) {
    this.matchUpdater = matchUpdater;
    this.fileStorageService = fileStorageService;
  }

  void loadMatchResultsFromFile(Path path) throws IOException {
    try (FileAdapter fileAdapter = selectAdapter(path)) {
      for (FileAdapterRow fileAdapterRow : fileAdapter) {
        Long matchId = Long.valueOf(fileAdapterRow.get(FinishMatchFileAdapter.MATCH_ID));
        Long homeGoals = Long.valueOf(fileAdapterRow.get(FinishMatchFileAdapter.HOME_GOALS));
        Long awayGoals = Long.valueOf(fileAdapterRow.get(FinishMatchFileAdapter.AWAY_GOALS));
        FinishMatch finishMatch = FinishMatch.builder().matchId(matchId).awayGoals(awayGoals).homeGoals(homeGoals).build();
        matchUpdater.finishMatch(finishMatch);
      }
    }
  }

  private FileAdapter selectAdapter(Path path) throws IOException {
    if (path.getFileName().toString().toLowerCase().endsWith(".xlsx")) {
      return new XlsxAdapter(path, FinishMatchFileAdapter.DEFAULT_COLUMNS);
    } else {
      return new XlsAdapter(path, FinishMatchFileAdapter.DEFAULT_COLUMNS);
    }
  }

  public Path uploadFile(MultipartFile file) throws IOException {
    Path path = fileStorageService.storeFile(file);
    loadMatchResultsFromFile(path);
    return path;
  }
}
