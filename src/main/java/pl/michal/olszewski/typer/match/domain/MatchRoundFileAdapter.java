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
import pl.michal.olszewski.typer.match.dto.command.CreateNewRound;

@Component
class MatchRoundFileAdapter {

  private static final String name = "name";
  private static final String leagueId = "leagueId";
  private static final List<String> defaultColumns = Arrays.asList(name, leagueId);

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
        String name = fileAdapterRow.get(MatchRoundFileAdapter.name);
        Long leagueId = Long.valueOf(fileAdapterRow.get(MatchRoundFileAdapter.leagueId));
        MatchRound matchRound = matchRoundCreator.from(CreateNewRound.builder().name(name).leagueId(leagueId).build());
        matchRoundSaver.save(matchRound);
      }
    }
  }

  private FileAdapter selectAdapter(Path file) throws IOException {
    if (file.getFileName().toString().toLowerCase().endsWith(".xlsx")) {
      return new XlsxAdapter(file, MatchRoundFileAdapter.defaultColumns);
    } else {
      return new XlsAdapter(file, MatchRoundFileAdapter.defaultColumns);
    }
  }

  public Path uploadFile(MultipartFile file) throws IOException {
    Path path = fileStorageService.storeFile(file);
    loadLeaguesFromFile(path);
    return path;
  }
}
