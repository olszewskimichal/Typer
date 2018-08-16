package pl.michal.olszewski.typer.bet.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.michal.olszewski.typer.RepositoryTestBase;
import pl.michal.olszewski.typer.bet.dto.BetNotFoundException;

class BetFinderTest extends RepositoryTestBase {

  @Autowired
  private BetFinder betFinder;

  @Test
  void shouldFindAllBetForMatch() {
    List<Bet> bets = givenBets()
        .buildNumberOfBetsForMatchAndPersistInDb(2, 2L);
    givenBets()
        .buildNumberOfBetsForMatchAndPersistInDb(1, 1L);

    List<Bet> allBetForMatch = betFinder.findAllBetForMatch(2L);

    assertThat(allBetForMatch).isNotNull().hasSize(2).containsAll(bets);
  }

  @Test
  void shouldFindBetById() {
    //given
    Bet bet = givenBets()
        .buildNumberOfBetsForMatchAndPersistInDb(1, 1L).get(0);

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

  private BetFactory givenBets() {
    return new BetFactory(entityManager);
  }

}