package pl.michal.olszewski.typer.bet.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import pl.michal.olszewski.typer.bet.dto.command.CancelBet;
import pl.michal.olszewski.typer.bet.dto.command.CheckBet;
import pl.michal.olszewski.typer.match.dto.events.MatchCanceled;
import pl.michal.olszewski.typer.match.dto.events.MatchFinished;

@Slf4j
@Component
class MatchEventListener {

  private final BetFinder betFinder;
  private final BetCommandPublisher betCommandPublisher;

  public MatchEventListener(BetFinder betFinder, BetCommandPublisher betCommandPublisher) {
    this.betFinder = betFinder;
    this.betCommandPublisher = betCommandPublisher;
  }

  /**
   * Przyjmuje zdarzenia o zakończonym meczu, pobiera wszystkie zakłady na ten mecz i wysyła dla nich rozkaz o anulowaniu ich
   * Jako wynik zwraca liczbę komend które zostały wyssłane do kolejki
   *
   * @return zwraca liczbę komend które zostały wyssłane do kolejki
   */
  @JmsListener(destination = "cancelMatchQueue")
  public void handleMatchCanceledEventJMS(MatchCanceled event) {
    log.info("Received {}", event);
    for (Bet bet : betFinder.findAllBetForMatch(event.getMatchId())) {
      betCommandPublisher.sendCancelCommandToJms(new CancelBet(bet.getId()));
    }
  }

  @JmsListener(destination = "finishedMatchQueue")
  public void handleMatchFinishedEventJMS(MatchFinished event) {
    log.info("Received {}", event);
    for (Bet bet : betFinder.findAllBetForMatch(event.getMatchId())) {
      betCommandPublisher.sendCheckCommandToJms(new CheckBet(bet.getId(), bet.getBetAwayGoals(), bet.getBetHomeGoals(), event.getAwayGoals(), event.getHomeGoals(), event.getBetPolicyId()));
    }
  }
}
