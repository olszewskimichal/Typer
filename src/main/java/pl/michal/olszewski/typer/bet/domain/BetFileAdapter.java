package pl.michal.olszewski.typer.bet.domain;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;
import pl.michal.olszewski.typer.adapter.FileAdapter;
import pl.michal.olszewski.typer.adapter.FileAdapterRow;
import pl.michal.olszewski.typer.adapter.XlsAdapter;
import pl.michal.olszewski.typer.adapter.XlsxAdapter;
import pl.michal.olszewski.typer.bet.dto.command.CreateNewBet;

@Component
class BetFileAdapter {

  private static final String matchId = "matchId";
  private static final String roundId = "roundId";
  private static final String userId = "userId";
  private static final String homeGoals = "homeGoals";
  private static final String awayGoals = "awayGoals";

  private static final List<String> defaultColumns = Arrays.asList(matchId, roundId, userId, homeGoals, awayGoals);

  private final BetCreator betCreator;
  private final BetSaver betSaver;

  public BetFileAdapter(BetCreator betCreator, BetSaver betSaver) {
    this.betCreator = betCreator;
    this.betSaver = betSaver;
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
}