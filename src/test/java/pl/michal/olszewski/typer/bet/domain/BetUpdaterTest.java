package pl.michal.olszewski.typer.bet.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import pl.michal.olszewski.typer.bet.dto.BetNotFoundException;
import pl.michal.olszewski.typer.bet.dto.command.BlockBet;
import pl.michal.olszewski.typer.bet.dto.command.CancelBet;
import pl.michal.olszewski.typer.bet.dto.command.CheckBet;

class BetUpdaterTest {

  private BetFinder betFinder = new InMemoryBetFinder();

  private BetUpdater betUpdater = new BetUpdater(betFinder);

  @Test
  void shouldThrowExceptionWhenCancellingCommandIsNull() {
    assertThrows(NullPointerException.class, () -> betUpdater.cancelBet(null));
  }

  @Test
  void shouldThrowExceptionWhenCancelledBetIdIsNull() {
    CancelBet cancelBet = CancelBet
        .builder()
        .betId(null)
        .build();

    assertThrows(BetNotFoundException.class, () -> betUpdater.cancelBet(cancelBet));
  }

  @Test
  void shouldThrowExceptionWhenCancelledBetIsNotFound() {
    CancelBet cancelBet = CancelBet
        .builder()
        .betId(1L)
        .build();

    assertThrows(BetNotFoundException.class, () -> betUpdater.cancelBet(cancelBet));
  }

  @Test
  void shouldCancelBetWhenCommandIsValidAndBetIsFound() {
    ((InMemoryBetFinder) betFinder).save(2L, Bet.builder().build());

    CancelBet cancelBet = CancelBet
        .builder()
        .betId(2L)
        .build();

    Bet bet = betUpdater.cancelBet(cancelBet);

    assertThat(bet).isNotNull();
    assertThat(bet.getStatus()).isEqualTo(BetStatus.CANCELED);
  }

  @Test
  void shouldThrowExceptionWhenFinishedBetCommandIsNull() {
    assertThrows(NullPointerException.class, () -> betUpdater.checkBet(null));
  }

  @Test
  void shouldThrowExceptionWhenFinishedBetIdIsNull() {
    CheckBet finishBet = CheckBet
        .builder()
        .betId(null)
        .build();

    assertThrows(BetNotFoundException.class, () -> betUpdater.checkBet(finishBet));
  }

  @Test
  void shouldThrowExceptionWhenFinishedBetIsNotFound() {
    CheckBet finishBet = CheckBet
        .builder()
        .betId(1L)
        .expectedHomeGoals(1L)
        .expectedAwayGoals(2L)
        .betHomeGoals(3L)
        .betAwayGoals(4L)
        .build();

    assertThrows(BetNotFoundException.class, () -> betUpdater.checkBet(finishBet));
  }

  @Test
  void shouldCheckBetWhenCommandIsValidAndBetIsFound() {
    ((InMemoryBetFinder) betFinder).save(3L, Bet.builder().build());

    CheckBet finishBet = CheckBet
        .builder()
        .betId(3L)
        .expectedHomeGoals(1L)
        .expectedAwayGoals(2L)
        .betHomeGoals(3L)
        .betAwayGoals(4L)
        .build();

    Bet bet = betUpdater.checkBet(finishBet);

    assertThat(bet).isNotNull();
    assertThat(bet.getStatus()).isEqualTo(BetStatus.CHECKED);
  }

  @Test
  void shouldThrowExceptionWhenBlockBetCommandIsNull() {
    assertThrows(NullPointerException.class, () -> betUpdater.blockBet(null));
  }

  @Test
  void shouldThrowExceptionWhenBlockBetIdIsNull() {
    BlockBet blockBet = BlockBet
        .builder()
        .betId(null)
        .build();

    assertThrows(BetNotFoundException.class, () -> betUpdater.blockBet(blockBet));
  }

  @Test
  void shouldThrowExceptionWhenBlockBetIsNotFound() {
    BlockBet blockBet = BlockBet
        .builder()
        .betId(1L)
        .build();

    assertThrows(BetNotFoundException.class, () -> betUpdater.blockBet(blockBet));
  }

  @Test
  void shouldBlockBetWhenCommandIsValidAndBetIsFound() {
    ((InMemoryBetFinder) betFinder).save(3L, Bet.builder().build());

    BlockBet blockBet = BlockBet
        .builder()
        .betId(3L)
        .build();

    Bet bet = betUpdater.blockBet(blockBet);

    assertThat(bet).isNotNull();
    assertThat(bet.getStatus()).isEqualTo(BetStatus.IN_PROGRESS);
  }

}
