package pl.michal.olszewski.typer.bet.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.michal.olszewski.typer.RepositoryTestBase;
import pl.michal.olszewski.typer.bet.dto.BetNotFoundException;
import pl.michal.olszewski.typer.bet.dto.read.RoundPoints;
import pl.michal.olszewski.typer.match.domain.MatchLeagueFactory;

class BetFinderTest extends RepositoryTestBase {

  @Autowired
  private BetFinder betFinder;

  @Autowired
  private BetSaver betSaver;

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
  void shouldFindAllBetForRound() {
    givenBets().buildNumberOfBetsForMatchRoundAndSave(2, 3L);
    givenBets().buildNumberOfBetsForMatchRoundAndSave(1, 4L);

    assertThat(betFinder.findAllBetForRound(3L)).hasSize(2);
    assertThat(betFinder.findAllBetForRound(4L)).hasSize(1);
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
  void shouldReturnPointsForUserAndRound() {
    Bet bet1 = Bet.builder().userId(1L).matchRoundId(2L).points(3L).build();
    Bet bet2 = Bet.builder().userId(1L).matchRoundId(2L).points(6L).build();
    betSaver.save(bet1);
    betSaver.save(bet2);

    RoundPoints result = betFinder.findSumOfPointsForRoundAndUser(1L, 2L);
    assertThat(result.getPoints()).isEqualTo(9L);
    assertThat(result.getRoundId()).isEqualTo(2L);
  }


  @Test
  void shouldReturnPointsForUserAndLeague() {

    long leagueID = new MatchLeagueFactory(entityManager).buildMatchLeague();
    long roundID = new MatchLeagueFactory(entityManager).buildMatchRound(leagueID);

    Bet bet1 = Bet.builder().userId(1L).matchRoundId(roundID).points(3L).build();
    Bet bet2 = Bet.builder().userId(1L).matchRoundId(roundID).points(6L).build();
    Bet bet3 = Bet.builder().userId(1L).matchRoundId(roundID).points(6L).build();

    betSaver.save(bet1);
    betSaver.save(bet2);
    betSaver.save(bet3);

    Long result = betFinder.findSumOfPointsForLeagueAndUser(1L, leagueID);
    assertThat(result).isEqualTo(15L);
  }


  @Test
  void shouldFindAllBets() {
    givenBets()
        .buildNumberOfBetsForMatchAndSave(3, 1L);

    List<Bet> all = betFinder.findAll();

    assertThat(all).hasSize(3);
  }

  private BetFactory givenBets() {
    return new BetFactory(betSaver);
  }

}