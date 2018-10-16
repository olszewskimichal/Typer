package pl.michal.olszewski.typer.match.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import pl.michal.olszewski.typer.livescore.dto.command.FinishLivescoreMatch;

@Component
@Slf4j
class LivescoreMatchEventListener {

  private final MatchUpdater matchUpdater;

  LivescoreMatchEventListener(MatchUpdater matchUpdater) {
    this.matchUpdater = matchUpdater;
  }

  @JmsListener(destination = "finishedLivescoreMatchQueue")
  void checkLivescoreMatchFinishedJMS(FinishLivescoreMatch command) {
    log.info("Received {}", command);
    matchUpdater.finishLivescoreMatch(command);
  }
}
