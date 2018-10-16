package pl.michal.olszewski.typer.bet.domain;

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
import pl.michal.olszewski.typer.bet.dto.command.CreateNewBet;
import pl.michal.olszewski.typer.file.FileStorageService;

@Component
@Slf4j
class BetFileAdapter {

  private static final String MATCH_ID = "matchId";
  private static final String ROUND_ID = "roundId";
  private static final String LEAGUE_ID = "leagueId";
  private static final String USER_ID = "userId";
  private static final String HOME_GOALS = "homeGoals";
  private static final String AWAY_GOALS = "awayGoals";

  private static final List<String> defaultColumns = Arrays.asList(MATCH_ID, LEAGUE_ID, ROUND_ID, USER_ID, HOME_GOALS, AWAY_GOALS);

  private final BetSaver betSaver;
  private final FileStorageService fileStorageService;


  public BetFileAdapter(BetSaver betSaver, FileStorageService fileStorageService) {
    this.betSaver = betSaver;
    this.fileStorageService = fileStorageService;
  }

  void loadBetsFromFile(Path path) throws IOException {
    try (FileAdapter fileAdapter = selectAdapter(path)) {
      for (FileAdapterRow fileAdapterRow : fileAdapter) {
        Long matchId = Long.valueOf(fileAdapterRow.get(BetFileAdapter.MATCH_ID));
        Long roundId = Long.valueOf(fileAdapterRow.get(BetFileAdapter.ROUND_ID));
        Long leagueId = Long.valueOf(fileAdapterRow.get(BetFileAdapter.ROUND_ID));
        Long userId = Long.valueOf(fileAdapterRow.get(BetFileAdapter.USER_ID));
        Long homeGoals = Long.valueOf(fileAdapterRow.get(BetFileAdapter.HOME_GOALS));
        Long awayGoals = Long.valueOf(fileAdapterRow.get(BetFileAdapter.AWAY_GOALS));

        Bet bet = BetCreator.from(CreateNewBet.builder()
            .matchId(matchId)
            .matchRoundId(roundId)
            .leagueId(leagueId)
            .userId(userId)
            .betAwayGoals(awayGoals)
            .betHomeGoals(homeGoals)
            .build());
        betSaver.save(bet);
        log.debug("Zapisałem zakład {}", bet);
      }
    }
  }

  private FileAdapter selectAdapter(Path path) throws IOException {
    if (path.getFileName().toString().toLowerCase().endsWith(".xlsx")) {
      return new XlsxAdapter(path, BetFileAdapter.defaultColumns);
    } else {
      return new XlsAdapter(path, BetFileAdapter.defaultColumns);
    }
  }

  public Path uploadFile(MultipartFile file) throws IOException {
    Path path = fileStorageService.storeFile(file);
    loadBetsFromFile(path);
    return path;
  }
}
