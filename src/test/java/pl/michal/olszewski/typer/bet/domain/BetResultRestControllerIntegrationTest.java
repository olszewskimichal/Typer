package pl.michal.olszewski.typer.bet.domain;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.michal.olszewski.typer.RestControllerIntegrationTestBase;

class BetResultRestControllerIntegrationTest extends RestControllerIntegrationTestBase {

  @Autowired
  private BetSaver betSaver;

  @BeforeEach
  void setUp() {
    mvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }


  @AfterEach
  void removeAll() {
    givenBets().deleteAll();
  }


  @Test
  void shouldReturnEmptyListWhenResultByUserNotExists() throws Exception {
    mvc.perform(get("/api/bet/result/user/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  void shouldReturnResultListWhenResultByUserExists() throws Exception {
    givenBets().buildNumberOfBetsForUserAndStatus(2, 1L, BetStatus.CHECKED);

    mvc.perform(get("/api/bet/result/user/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(2)));
  }


  @Test
  void shouldReturnNotFoundWhenResultByIdNotExists() throws Exception {
    mvc.perform(get("/api/bet/result/1"))
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnForbiddenWhenBetIsNotChecked() throws Exception {
    Bet bet = givenBets().buildNumberOfBetsForUserAndStatus(1, 1L, BetStatus.IN_PROGRESS).get(0);

    mvc.perform(get("/api/bet/result/" + bet.getId()))
        .andExpect(status().isForbidden());
  }

  @Test
  void shouldReturnResultWhenBetByIdExists() throws Exception {
    Bet bet = givenBets().buildNumberOfBetsForUserAndStatus(1, 1L, BetStatus.CHECKED).get(0);

    mvc.perform(get("/api/bet/result/" + bet.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.betId", is(bet.getId().intValue())))
        .andExpect(jsonPath("$.userId", is(1)))
        .andExpect(jsonPath("$.points", is(0)));
  }

  @Test
  void shouldReturnEmptyListWhenResultByMatchNotExists() throws Exception {
    mvc.perform(get("/api/bet/result/match/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  void shouldReturnResultListWhenBetCheckedResultByMatchExists() throws Exception {
    givenBets().buildNumberOfBetsForMatchAndStatus(2, 1L, BetStatus.CHECKED);
    givenBets().buildNumberOfBetsForMatchAndStatus(2, 1L, BetStatus.IN_PROGRESS);

    mvc.perform(get("/api/bet/result/match/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(2)));
  }

  @Test
  void shouldReturnEmptyListWhenResultByRoundNotExists() throws Exception {
    mvc.perform(get("/api/bet/result/round/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  void shouldReturnResultListWhenBetCheckedByRoundExists() throws Exception {
    givenBets().buildNumberOfBetsForMatchRoundAndStatus(2, 1L, BetStatus.CHECKED);
    givenBets().buildNumberOfBetsForMatchRoundAndStatus(2, 1L, BetStatus.IN_PROGRESS);

    mvc.perform(get("/api/bet/result/round/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(2)));
  }

  private BetFactory givenBets() {
    return new BetFactory(betSaver);
  }

}