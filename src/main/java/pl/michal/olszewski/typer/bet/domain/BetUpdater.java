package pl.michal.olszewski.typer.bet.domain;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.michal.olszewski.typer.bet.dto.command.BlockBet;
import pl.michal.olszewski.typer.bet.dto.command.CancelBet;
import pl.michal.olszewski.typer.bet.dto.command.CheckBet;
import pl.michal.olszewski.typer.bet.dto.events.BetChecked;

@Service
@Slf4j
class BetUpdater {

  private final BetFinder betFinder;
  private final BetSaver betSaver;
  private final BetEventPublisher eventPublisher;

  BetUpdater(BetFinder betFinder, BetSaver betSaver, BetEventPublisher eventPublisher) {
    this.betFinder = betFinder;
    this.betSaver = betSaver;
    this.eventPublisher = eventPublisher;
  }

  Bet cancelBet(CancelBet command) {
    Objects.requireNonNull(command);
    command.validCommand();

    Bet bet = betFinder.findOneOrThrow(command.getBetId());
    bet.setStatusAsCanceled();
    return bet;
  }

  Bet checkBet(CheckBet command) {
    Objects.requireNonNull(command);
    command.validCommand();

    Bet bet = betFinder.findOneOrThrow(command.getBetId());
    BetChecked betChecked = BetTypePolicy.fromValue(command.getBetPolicyId()).getBetPolicy().calculatePoints(command);

    log.debug(betChecked.toString());
    eventPublisher.sendBetCheckedToJMS(betChecked);
    bet.setStatusAsChecked();
    bet.setPoints(betChecked.getPoints());
    betSaver.save(bet);
    return bet;
  }

  Bet blockBet(BlockBet command) {
    Objects.requireNonNull(command);
    command.validCommand();

    Bet bet = betFinder.findOneOrThrow(command.getBetId());
    bet.setStatusAsBlocked();
    return bet;
  }
}
