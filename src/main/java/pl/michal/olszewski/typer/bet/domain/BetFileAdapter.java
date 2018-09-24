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

  private static final String matchId = "matchId";
  private static final String roundId = "roundId";
  private static final String userId = "userId";
  private static final String homeGoals = "homeGoals";
  private static final String awayGoals = "awayGoals";

  private static final List<String> defaultColumns = Arrays.asList(matchId, roundId, userId, homeGoals, awayGoals);

  private final BetCreator betCreator;
  private final BetSaver betSaver;
  private final FileStorageService fileStorageService;


  public BetFileAdapter(BetCreator betCreator, BetSaver betSaver, FileStorageService fileStorageService) {
    this.betCreator = betCreator;
    this.betSaver = betSaver;
    this.fileStorageService = fileStorageService;
  }

  void loadBetsFromFile(Path path) throws IOException {
    try (FileAdapter fileAdapter = selectAdapter(path)) {
      for (FileAdapterRow fileAdapterRow : fileAdapter) {
        Long matchId = Long.valueOf(fileAdapterRow.get(BetFileAdapter.matchId));
        Long roundId = Long.valueOf(fileAdapterRow.get(BetFileAdapter.roundId));
        Long userId = Long.valueOf(fileAdapterRow.get(BetFileAdapter.userId));
        Long homeGoals = Long.valueOf(fileAdapterRow.get(BetFileAdapter.homeGoals));
        Long awayGoals = Long.valueOf(fileAdapterRow.get(BetFileAdapter.awayGoals));

        Bet bet = betCreator.from(CreateNewBet.builder()
            .matchId(matchId)
            .matchRoundId(roundId)
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
