package pl.michal.olszewski.typer.bet.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.michal.olszewski.typer.RepositoryTestBase;
import pl.michal.olszewski.typer.bet.dto.BetNotFoundException;

class BetFinderTest extends RepositoryTestBase {

  @Autowired
  private BetFinder betFinder;

  @Autowired
  private BetSaver betSaver;

  @BeforeEach
  void setUp() {
    betSaver.deleteAll();
  }

  @Test
  void shouldFindAllBetForMatch() {
    List<Bet> bets = givenBets()
        .buildNumberOfBetsForMatchAndSave(2, 2L);
    givenBets()
        .buildNumberOfBetsForMatchAndSave(1, 1L);

    List<Bet> allBetForMatch = betFinder.findAllBetForMatch(2L);

    assertThat(allBetForMatch).isNotNull().hasSize(2).containsAll(bets);
  }

  @Test
  void shouldFindAllBetForUser() {
    givenBets().buildNumberOfBetsForUserAndSave(2, 3L);
    givenBets().buildNumberOfBetsForUserAndSave(1, 4L);

    assertThat(betFinder.findAllBetForUser(3L)).hasSize(2);
    assertThat(betFinder.findAllBetForUser(4L)).hasSize(1);
  }

  @Test
  void shouldFindAllFinishedBetForRound() {
    givenBets().buildNumberOfBetsForMatchRoundAndStatus(2, 3L, BetStatus.CHECKED);
    givenBets().buildNumberOfBetsForMatchRoundAndStatus(2, 3L, BetStatus.IN_PROGRESS);
    givenBets().buildNumberOfBetsForMatchRoundAndStatus(1, 4L, BetStatus.CHECKED);
    givenBets().buildNumberOfBetsForMatchRoundAndStatus(1, 4L, BetStatus.IN_PROGRESS);

    assertThat(betFinder.findAllFinishedBetForRound(3L)).hasSize(2);
    assertThat(betFinder.findAllFinishedBetForRound(4L)).hasSize(1);
  }

  @Test
  void shouldFindBetById() {
    //given
    Bet bet = givenBets()
        .buildNumberOfBetsForMatchAndSave(1, 1L).get(0);

    //when
    Bet foundedBet = betFinder.findOneOrThrow(bet.getId());

    //then
    assertThat(foundedBet).isNotNull().isEqualToComparingFieldByField(bet);
  }

  @Test
  void shouldThrowExceptionWhenBetNotFound() {
    //given
    //when
    //then
    assertThrows(BetNotFoundException.class, () -> betFinder.findOneOrThrow(1L));
  }

  @Test
  void shouldFindAllBets() {
    givenBets()
        .buildNumberOfBetsForMatchAndSave(3, 1L);

    List<Bet> all = betFinder.findAll();

    assertThat(all).hasSize(3);
  }

  @Test
  void shouldFindBetsModifiedFromDate() {
    Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    Bet bet1 = Bet.builder().userId(1L).points(3L).modified(now.minusSeconds(3)).build();
    Bet bet2 = Bet.builder().userId(1L).points(6L).modified(now.plusSeconds(3)).build();
    Bet bet3 = Bet.builder().userId(1L).points(6L).modified(now).build();
    betSaver.save(bet1);
    betSaver.save(bet2);
    betSaver.save(bet3);

    List<Bet> byModifiedAfter = betFinder.findByModifiedAfter(now);
    assertThat(byModifiedAfter).isNotEmpty().hasSize(1);
  }

  private BetFactory givenBets() {
    return new BetFactory(betSaver);
  }

}