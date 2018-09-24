package pl.michal.olszewski.typer.bet.domain;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.michal.olszewski.typer.bet.dto.command.CreateNewBet;


@Component
@Slf4j
class BetCreator {

  Bet from(CreateNewBet createNewBet) {
    log.debug("Creating match from command {}", createNewBet);
    Objects.requireNonNull(createNewBet);
    createNewBet.validCommand();

    return Bet.builder()
        .betAwayGoals(createNewBet.getBetAwayGoals())
        .betHomeGoals(createNewBet.getBetHomeGoals())
        .matchId(createNewBet.getMatchId())
        .userId(createNewBet.getUserId())
        .matchRoundId(createNewBet.getMatchRoundId())
        .build();
  }

}
