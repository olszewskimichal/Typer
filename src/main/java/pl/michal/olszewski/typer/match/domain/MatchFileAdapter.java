package pl.michal.olszewski.typer.match.domain;

import static pl.michal.olszewski.typer.match.domain.MatchCreator.from;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
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
  private static final String DATE_TIME = "date";
  private static final List<String> defaultColumns = Arrays.asList(ROUND_ID, HOME_TEAM_ID, AWAY_TEAM_ID);

  private final MatchRoundFinder matchRoundFinder;
  private final MatchSaver matchSaver;
  private final FileStorageService fileStorageService;


  public MatchFileAdapter(MatchRoundFinder matchRoundFinder, MatchSaver matchSaver, FileStorageService fileStorageService) {
    this.matchRoundFinder = matchRoundFinder;
    this.matchSaver = matchSaver;
    this.fileStorageService = fileStorageService;
  }

  public Path uploadFile(MultipartFile file) throws IOException {
    Path path = fileStorageService.storeFile(file);
    loadMatchesFromFile(path);
    return path;
  }

  void loadMatchesFromFile(Path path) throws IOException {
    try (FileAdapter fileAdapter = selectAdapter(path)) {
      for (FileAdapterRow fileAdapterRow : fileAdapter) {
        Long roundId = Long.valueOf(fileAdapterRow.get(MatchFileAdapter.ROUND_ID));
        Long homeTeamId = Long.valueOf(fileAdapterRow.get(MatchFileAdapter.HOME_TEAM_ID));
        Long awayTeamId = Long.valueOf(fileAdapterRow.get(MatchFileAdapter.AWAY_TEAM_ID));
        String dateTime = fileAdapterRow.get(DATE_TIME);
        Instant date = null;
        if (dateTime != null && !dateTime.isEmpty()) {
          date = Instant.parse(dateTime);
        }
        Match match = from(CreateNewMatch.builder().roundId(roundId).homeTeamId(homeTeamId).startDate(date).awayTeamId(awayTeamId).build(), matchRoundFinder);
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
}
