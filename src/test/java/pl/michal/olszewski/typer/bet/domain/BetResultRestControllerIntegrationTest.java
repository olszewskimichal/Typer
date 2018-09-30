package pl.michal.olszewski.typer.bet.domain;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
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
class BetResultRestControllerIntegrationTest {

  @Autowired
  private BetSaver betSaver;

  @Autowired
  private WebApplicationContext wac;

  private MockMvc mvc;

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
    mvc.perform(get("/api/bet/user/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  void shouldReturnResultListWhenResultByUserExists() throws Exception {
    givenBets().buildNumberOfBetsForUserAndSave(2, 1L);

    mvc.perform(get("/api/bet/user/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(2)));
  }


  @Test
  void shouldReturnNotFoundWhenResultByIdNotExists() throws Exception {
    mvc.perform(get("/api/bet/1"))
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnResultWhenBetByIdExists() throws Exception {
    Bet bet = givenBets().buildNumberOfBetsForUserAndSave(1, 1L).get(0);

    mvc.perform(get("/api/bet/" + bet.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.betId", is(bet.getId().intValue())))
        .andExpect(jsonPath("$.userId", is(1)))
        .andExpect(jsonPath("$.points", is(0)));
  }

  @Test
  void shouldReturnEmptyListWhenResultByMatchNotExists() throws Exception {
    mvc.perform(get("/api/bet/match/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  void shouldReturnResultListWhenResultByMatchExists() throws Exception {
    givenBets().buildNumberOfBetsForMatchAndSave(2, 1L);

    mvc.perform(get("/api/bet/match/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(2)));
  }

  @Test
  void shouldReturnEmptyListWhenResultByRoundNotExists() throws Exception {
    mvc.perform(get("/api/bet/round/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  void shouldReturnResultListWhenResultByRoundExists() throws Exception {
    givenBets().buildNumberOfBetsForMatchRoundAndSave(2, 1L);

    mvc.perform(get("/api/bet/round/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(2)));
  }

  private BetFactory givenBets() {
    return new BetFactory(betSaver);
  }

}