package pl.michal.olszewski.typer.match.domain;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.michal.olszewski.typer.RestControllerIntegrationTestBase;

class MatchRoundRestControllerIntegrationTest extends RestControllerIntegrationTestBase {

  @Autowired
  private MatchRoundSaver matchRoundSaver;
  @Autowired
  private MatchLeagueSaver matchLeagueSaver;

  @BeforeEach
  void setUp() {
    matchRoundSaver.deleteAll();
    matchLeagueSaver.deleteAll();
    mvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  @Test
  void shouldReturnNotFoundWhenMatchRoundByIdNotExist() throws Exception {
    mvc.perform(get("/api/match/round/254"))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnMatchRoundInfoWhenMatchRoundByIdExists() throws Exception {
    MatchRound matchRound = MatchRound.builder().name("name").build();
    matchRoundSaver.save(matchRound);
    mvc.perform(get("/api/match/round/" + matchRound.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.id", is(matchRound.getId().intValue())))
        .andExpect(jsonPath("$.name", is("name")))
        .andExpect(jsonPath("$.leagueId", is(nullValue())));

  }

  @Test
  void shouldCreateNewMatchRound() throws Exception {
    MatchLeague round = matchLeagueSaver.save(MatchLeague.builder().build());

    mvc.perform(post("/api/match/round")
        .param("leagueId", round.getId() + "")
        .param("name", "123"))
        .andExpect(status().isCreated());
  }

  @Test
  void shouldThrowExceptionWhenHomeTeamIdIsNullOnCreateNewMatchRound() throws Exception {
    mvc.perform(post("/api/match/round")
        .param("leagueId", "123"))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

}