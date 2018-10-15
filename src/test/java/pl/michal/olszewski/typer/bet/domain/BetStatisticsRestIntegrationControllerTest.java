package pl.michal.olszewski.typer.bet.domain;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.michal.olszewski.typer.RestControllerIntegrationTestBase;

class BetStatisticsRestIntegrationControllerTest extends RestControllerIntegrationTestBase {

  @Autowired
  private BetRoundStatisticsSaver betRoundStatisticsSaver;

  @Autowired
  private BetLeagueStatisticsSaver betLeagueStatisticsSaver;

  @BeforeEach
  void setUp() {
    mvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  @Test
  void shouldReturnLeagueStatisticForUserAndLeagueId() throws Exception {
    betLeagueStatisticsSaver.save(BetLeagueStatistics.builder().leagueId(3L).userId(1L).build());

    mvc.perform(get("/api/bet/statistics/league/3/user/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.userId", is(1)))
        .andExpect(jsonPath("$.leagueId", is(3)));
  }

  @Test
  void shouldReturnRoundStatisticForUserAndRoundId() throws Exception {
    betRoundStatisticsSaver.save(BetRoundStatistics.builder().roundId(3L).userId(1L).build());

    mvc.perform(get("/api/bet/statistics/round/3/user/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.roundPoints.roundId", is(3)))
        .andExpect(jsonPath("$.userId", is(1)));
  }

  @Test
  void shouldThrowExceptionWhenLeagueStatisticForUserIdAndLeagueIdNotExist() throws Exception {
    mvc.perform(get("/api/bet/statistics/league/4/user/2"))
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldThrowExceptionWhenRoundStatisticForUserIdAndRoundIdNotExist() throws Exception {
    mvc.perform(get("/api/bet/statistics/round/5/user/3"))
        .andExpect(status().isNotFound());
  }
}