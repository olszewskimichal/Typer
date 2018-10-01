package pl.michal.olszewski.typer.bet.domain;

import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class BetRestControllerIntegrationTest {

  @Autowired
  private WebApplicationContext wac;
  @Autowired
  private BetSaver betSaver;

  private MockMvc mvc;

  @BeforeEach
  void setUp() {
    betSaver.deleteAll();
    mvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  @Test
  void shouldReturnNotFoundWhenBetByIdNotExist() throws Exception {
    mvc.perform(get("/api/bet/254"))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnBetInfoWhenBetByIdExists() throws Exception {
    Bet bet = Bet.builder().userId(1L).matchRoundId(2L).matchId(3L).betHomeGoals(4L).betAwayGoals(5L).points(6L).status(BetStatus.CHECKED).build();
    betSaver.save(bet);
    mvc.perform(get("/api/bet/" + bet.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.id", is(bet.getId().intValue())))
        .andExpect(jsonPath("$.userId", is(1)))
        .andExpect(jsonPath("$.roundId", is(2)))
        .andExpect(jsonPath("$.matchId", is(3)))
        .andExpect(jsonPath("$.homeGoals", is(4)))
        .andExpect(jsonPath("$.awayGoals", is(5)))
        .andExpect(jsonPath("$.points", is(6)))
        .andExpect(jsonPath("$.status", is("CHECKED")));

  }

  @Test
  void shouldCreateNewBet() throws Exception {
    mvc.perform(post("/api/bet/")
        .param("matchId", "1")
        .param("userId", "1")
        .param("matchRoundId", "1")
        .param("betAwayGoals", "1")
        .param("betHomeGoals", "1"))
        .andExpect(status().isOk());
  }

  @Test
  void shouldThrowExceptionWhenMatchIdIsNullOnCreateNewBet() throws Exception { //TODO zrobić podobne testy dla innych wartosci/walidacji
    mvc.perform(post("/api/bet/")
        .param("userId", "1")
        .param("matchRoundId", "1")
        .param("betAwayGoals", "1")
        .param("betHomeGoals", "1"))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

}