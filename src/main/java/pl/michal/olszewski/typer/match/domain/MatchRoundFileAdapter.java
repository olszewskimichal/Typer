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
import pl.michal.olszewski.typer.match.dto.command.CreateNewRound;

@Component
@Slf4j
class MatchRoundFileAdapter {

  private static final String NAME = "name";
  private static final String LEAGUE_ID = "leagueId";
  private static final List<String> DEFAULT_COLUMNS = Arrays.asList(NAME, LEAGUE_ID);

  private final MatchRoundCreator matchRoundCreator;
  private final MatchRoundSaver matchRoundSaver;
  private final FileStorageService fileStorageService;


  public MatchRoundFileAdapter(MatchRoundCreator matchRoundCreator, MatchRoundSaver matchRoundSaver, FileStorageService fileStorageService) {
    this.matchRoundCreator = matchRoundCreator;
    this.matchRoundSaver = matchRoundSaver;
    this.fileStorageService = fileStorageService;
  }

  void loadLeaguesFromFile(Path file) throws IOException {
    try (FileAdapter fileAdapter = selectAdapter(file)) {
      for (FileAdapterRow fileAdapterRow : fileAdapter) {
        String name = fileAdapterRow.get(MatchRoundFileAdapter.NAME);
        Long leagueId = Long.valueOf(fileAdapterRow.get(MatchRoundFileAdapter.LEAGUE_ID));
        MatchRound matchRound = matchRoundCreator.from(CreateNewRound.builder().name(name).leagueId(leagueId).build());
        matchRoundSaver.save(matchRound);
        log.debug("Zapisałem runde {}", matchRound);
      }
    }
  }

  private FileAdapter selectAdapter(Path file) throws IOException {
    if (file.getFileName().toString().toLowerCase().endsWith(".xlsx")) {
      return new XlsxAdapter(file, MatchRoundFileAdapter.DEFAULT_COLUMNS);
    } else {
      return new XlsAdapter(file, MatchRoundFileAdapter.DEFAULT_COLUMNS);
    }
  }

  public Path uploadFile(MultipartFile file) throws IOException {
    Path path = fileStorageService.storeFile(file);
    loadLeaguesFromFile(path);
    return path;
  }
}
