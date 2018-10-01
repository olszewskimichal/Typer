package pl.michal.olszewski.typer.bet.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import pl.michal.olszewski.typer.bet.dto.BetNotFoundException;
import pl.michal.olszewski.typer.bet.dto.command.CreateNewBet;
import pl.michal.olszewski.typer.bet.dto.read.BetInfo;

class BetRestControllerUnitTest {

  private BetRestController betRestController;
  private BetSaver betSaver = new InMemoryBetSaver();
  private BetFinder betFinder = new InMemoryBetFinder();

  @BeforeEach
  void setUp() {
    givenBets().deleteAll();
    betRestController = new BetRestController(betFinder, betSaver);
  }

  @Test
  void shouldReturnNotFoundWhenBetByIdNotExist() {
    assertThrows(BetNotFoundException.class, () -> betRestController.getBetInfoById(254L));
  }

  @Test
  void shouldReturnBetInfoWhenBetByIdExists() {
    Bet bet = Bet.builder().id(254L).userId(1L).matchRoundId(2L).matchId(3L).betHomeGoals(4L).betAwayGoals(5L).points(6L).status(BetStatus.CHECKED).build();
    betSaver.save(bet);

    ResponseEntity<BetInfo> betInfoById = betRestController.getBetInfoById(254L);
    assertThat(betInfoById.getStatusCodeValue()).isEqualTo(200);
    assertThat(betInfoById.getBody()).isNotNull();
    assertThat(betInfoById.getBody().getMatchId()).isEqualTo(3L);

  }

  @Test
  void shouldCreateNewBet() {

    CreateNewBet createNewBet = CreateNewBet.builder().betHomeGoals(1L).betAwayGoals(1L).userId(1L).matchId(1L).matchRoundId(1L).build();
    ResponseEntity<String> responseEntity = betRestController.createNewBet(createNewBet);

    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    assertThat(responseEntity.getBody()).isEqualToIgnoringCase("OK");
  }

  @AfterEach
  void removeAll() {
    givenBets().deleteAll();
  }

  private BetFactory givenBets() {
    return new BetFactory(betSaver);
  }

}