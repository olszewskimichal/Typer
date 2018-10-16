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
import pl.michal.olszewski.typer.match.dto.command.CreateNewLeague;

@Component
@Slf4j
class MatchLeagueFileAdapter {

  private static final String NAME = "name";
  private static final String POLICY_ID = "policyId";
  private static final List<String> DEFAULT_COLUMNS = Arrays.asList(NAME, POLICY_ID);

  private final MatchLeagueSaver matchLeagueSaver;
  private final FileStorageService fileStorageService;


  public MatchLeagueFileAdapter(MatchLeagueSaver matchLeagueSaver, FileStorageService fileStorageService) {
    this.matchLeagueSaver = matchLeagueSaver;
    this.fileStorageService = fileStorageService;
  }

  void loadLeaguesFromFile(Path path) throws IOException {
    try (FileAdapter fileAdapter = selectAdapter(path)) {
      for (FileAdapterRow fileAdapterRow : fileAdapter) {
        String name = fileAdapterRow.get(MatchLeagueFileAdapter.NAME);
        Long policyId = Long.valueOf(fileAdapterRow.get(MatchLeagueFileAdapter.POLICY_ID));
        MatchLeague matchLeague = MatchLeagueCreator.from(CreateNewLeague.builder().name(name).betTypePolicy(policyId).build());
        matchLeagueSaver.save(matchLeague);
        log.debug("Zapisałem nowa lige {}", matchLeague);
      }
    }
  }

  private FileAdapter selectAdapter(Path path) throws IOException {
    if (path.getFileName().toString().toLowerCase().endsWith(".xlsx")) {
      return new XlsxAdapter(path, MatchLeagueFileAdapter.DEFAULT_COLUMNS);
    } else {
      return new XlsAdapter(path, MatchLeagueFileAdapter.DEFAULT_COLUMNS);
    }
  }

  public Path uploadFile(MultipartFile file) throws IOException {
    Path path = fileStorageService.storeFile(file);
    loadLeaguesFromFile(path);
    return path;
  }
}
