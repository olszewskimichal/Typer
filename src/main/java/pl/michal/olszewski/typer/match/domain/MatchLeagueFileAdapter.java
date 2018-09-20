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
import pl.michal.olszewski.typer.match.dto.command.CreateNewLeague;

@Component
class MatchLeagueFileAdapter {

  private static final String name = "name";
  private static final String policyId = "policyId";
  private static final List<String> defaultColumns = Arrays.asList(name, policyId);

  private final MatchLeagueCreator matchLeagueCreator;
  private final MatchLeagueSaver matchLeagueSaver;
  private final FileStorageService fileStorageService;


  public MatchLeagueFileAdapter(MatchLeagueCreator matchLeagueCreator, MatchLeagueSaver matchLeagueSaver, FileStorageService fileStorageService) {
    this.matchLeagueCreator = matchLeagueCreator;
    this.matchLeagueSaver = matchLeagueSaver;
    this.fileStorageService = fileStorageService;
  }

  void loadLeaguesFromFile(Path path) throws IOException {
    try (FileAdapter fileAdapter = selectAdapter(path)) {
      for (FileAdapterRow fileAdapterRow : fileAdapter) {
        String name = fileAdapterRow.get(MatchLeagueFileAdapter.name);
        Long policyId = Long.valueOf(fileAdapterRow.get(MatchLeagueFileAdapter.policyId));
        MatchLeague matchLeague = matchLeagueCreator.from(CreateNewLeague.builder().name(name).betTypePolicy(policyId).build());
        matchLeagueSaver.save(matchLeague);
      }
    }
  }

  private FileAdapter selectAdapter(Path path) throws IOException {
    if (path.getFileName().toString().toLowerCase().endsWith(".xlsx")) {
      return new XlsxAdapter(path, MatchLeagueFileAdapter.defaultColumns);
    } else {
      return new XlsAdapter(path, MatchLeagueFileAdapter.defaultColumns);
    }
  }

  public Path uploadFile(MultipartFile file) throws IOException {
    Path path = fileStorageService.storeFile(file);
    loadLeaguesFromFile(path);
    return path;
  }
}
