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
    Bet bet1 = Bet.builder().matchId(2L).build();
    Bet bet2 = Bet.builder().matchId(1L).build();
    Bet bet3 = Bet.builder().matchId(2L).build();
    entityManager.persist(bet1);
    entityManager.persist(bet2);
    entityManager.persist(bet3);

    entityManager.flush();
    entityManager.clear();

    List<Bet> allBetForMatch = betFinder.findAllBetForMatch(2L);

    assertThat(allBetForMatch).isNotNull().hasSize(2).contains(bet1, bet3);
  }

  @Test
  void shouldFindBetById() {
    //given
    Bet bet = Bet.builder().build();
    Long id = (Long) entityManager.persistAndGetId(bet);
    entityManager.flush();
    entityManager.clear();

    //when
    Bet foundedBet = betFinder.findOneOrThrow(id);

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
}