package pl.michal.olszewski.typer.match.domain;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.michal.olszewski.typer.RestControllerIntegrationTestBase;

class MatchRestControllerIntegrationTest extends RestControllerIntegrationTestBase {

  @Autowired
  private MatchSaver matchSaver;
  @Autowired
  private MatchRoundSaver matchRoundSaver;

  @BeforeEach
  void setUp() {
    matchSaver.deleteAll();
    matchRoundSaver.deleteAll();
    mvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  @Test
  void shouldReturnNotFoundWhenMatchByIdNotExist() throws Exception {
    mvc.perform(get("/api/match/254"))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnMatchInfoWhenMatchByIdExists() throws Exception {
    Match match = Match.builder().awayTeamId(1L).homeTeamId(2L).matchStatus(MatchStatus.FINISHED).homeGoals(1L).awayGoals(2L).build();
    matchSaver.save(match);
    mvc.perform(get("/api/match/" + match.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.id", is(match.getId().intValue())))
        .andExpect(jsonPath("$.awayTeamId", is(1)))
        .andExpect(jsonPath("$.homeTeamId", is(2)))
        .andExpect(jsonPath("$.homeGoals", is(1)))
        .andExpect(jsonPath("$.awayGoals", is(2)))
        .andExpect(jsonPath("$.status", is("FINISHED")));

  }

  @Test
  void shouldCreateNewMatch() throws Exception {
    MatchRound round = matchRoundSaver.save(MatchRound.builder().build());

    mvc.perform(post("/api/match/")
        .param("roundId", round.getId() + "")
        .param("homeTeamId", "1")
        .param("awayTeamId", "2")
        .param("startDate", Instant.now() + ""))
        .andExpect(status().isCreated());
  }

  @Test
  void shouldThrowExceptionWhenHomeTeamIdIsNullOnCreateNewMatch() throws Exception {
    mvc.perform(post("/api/match/")
        .param("roundId", "1")
        .param("awayTeamId", "1")
        .param("startDate", Instant.now() + ""))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldIntegrateMatchWithLivescore() throws Exception {
    Match match = Match.builder().id(1L).build();
    matchSaver.save(match);

    mvc.perform(put("/api/match/integrate")
        .param("matchId", "1")
        .param("livescoreId", "1")
        .param("livescoreLeagueId", "2"))
        .andExpect(status().isCreated());
  }


}