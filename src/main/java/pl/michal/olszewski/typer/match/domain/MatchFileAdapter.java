package pl.michal.olszewski.typer.match.domain;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.michal.olszewski.typer.adapter.FileAdapter;
import pl.michal.olszewski.typer.adapter.FileAdapterRow;
import pl.michal.olszewski.typer.adapter.XlsAdapter;
import pl.michal.olszewski.typer.adapter.XlsxAdapter;
import pl.michal.olszewski.typer.file.FileStorageService;
import pl.michal.olszewski.typer.match.dto.command.CreateNewMatch;

@Component
@Slf4j
class MatchFileAdapter {

  private static final String ROUND_ID = "roundId";
  private static final String HOME_TEAM_ID = "homeTeamId";
  private static final String AWAY_TEAM_ID = "awayTeamId";
  private static final List<String> defaultColumns = Arrays.asList(ROUND_ID, HOME_TEAM_ID, AWAY_TEAM_ID);

  private final MatchCreator matchCreator;
  private final MatchSaver matchSaver;
  private final FileStorageService fileStorageService;


  public MatchFileAdapter(MatchCreator matchCreator, MatchSaver matchSaver, FileStorageService fileStorageService) {
    this.matchCreator = matchCreator;
    this.matchSaver = matchSaver;
    this.fileStorageService = fileStorageService;
  }

  void loadMatchesFromFile(Path path) throws IOException {
    try (FileAdapter fileAdapter = selectAdapter(path)) {
      for (FileAdapterRow fileAdapterRow : fileAdapter) {
        Long roundId = Long.valueOf(fileAdapterRow.get(MatchFileAdapter.ROUND_ID));
        Long homeTeamId = Long.valueOf(fileAdapterRow.get(MatchFileAdapter.HOME_TEAM_ID));
        Long awayTeamId = Long.valueOf(fileAdapterRow.get(MatchFileAdapter.AWAY_TEAM_ID));
        Match match = matchCreator.from(CreateNewMatch.builder().roundId(roundId).homeTeamId(homeTeamId).awayTeamId(awayTeamId).build());
        matchSaver.save(match);
        log.debug("Zapisałem mecz {}", match);
      }
    }
  }

  private FileAdapter selectAdapter(Path path) throws IOException {
    if (path.getFileName().toString().toLowerCase().endsWith(".xlsx")) {
      return new XlsxAdapter(path, MatchFileAdapter.defaultColumns);
    } else {
      return new XlsAdapter(path, MatchFileAdapter.defaultColumns);
    }
  }

  public Path uploadFile(MultipartFile file) throws IOException {
    Path path = fileStorageService.storeFile(file);
    loadMatchesFromFile(path);
    return path;
  }
}
