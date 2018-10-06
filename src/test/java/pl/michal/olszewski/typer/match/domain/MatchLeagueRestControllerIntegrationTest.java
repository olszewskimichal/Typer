package pl.michal.olszewski.typer.match.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.michal.olszewski.typer.RestControllerIntegrationTestBase;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MatchLeagueRestControllerIntegrationTest extends RestControllerIntegrationTestBase {

  @Autowired
  private MatchLeagueSaver matchLeagueSaver;

  @BeforeEach
  void setUp() {
    matchLeagueSaver.deleteAll();
    mvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  @Test
  void shouldReturnNotFoundWhenMatchRoundByIdNotExist() throws Exception {
    mvc.perform(get("/api/match/league/254"))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnMatchRoundInfoWhenMatchRoundByIdExists() throws Exception {
    MatchLeague matchLeague = MatchLeague.builder().name("name").betTypePolicy(1L).build();
    matchLeagueSaver.save(matchLeague);
    mvc.perform(get("/api/match/league/" + matchLeague.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.id", is(matchLeague.getId().intValue())))
        .andExpect(jsonPath("$.name", is("name")))
        .andExpect(jsonPath("$.betTypePolicy", is(1)));

  }

  @Test
  void shouldCreateNewMatchRound() throws Exception {

    mvc.perform(post("/api/match/league")
        .param("betTypePolicy", "1")
        .param("name", "123"))
        .andExpect(status().isCreated());
  }

  @Test
  void shouldThrowExceptionWhenHomeTeamIdIsNullOnCreateNewMatchRound() throws Exception {
    mvc.perform(post("/api/match/round")
        .param("name", "123"))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

}