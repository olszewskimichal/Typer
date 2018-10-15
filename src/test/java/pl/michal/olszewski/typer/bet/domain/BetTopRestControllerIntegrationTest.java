package pl.michal.olszewski.typer.bet.domain;

import static org.hamcrest.Matchers.hasSize;
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

class BetTopRestControllerIntegrationTest extends RestControllerIntegrationTestBase {

  @Autowired
  private BetRoundStatisticsSaver betRoundStatisticsSaver;

  @Autowired
  private BetLeagueStatisticsSaver betLeagueStatisticsSaver;

  @BeforeEach
  void setUp() {
    mvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  @Test
  void shouldReturnTOP3StatisticForLeagueId() throws Exception {
    betLeagueStatisticsSaver.save(BetLeagueStatistics.builder().userId(4L).leagueId(1L).points(6L).build());
    betLeagueStatisticsSaver.save(BetLeagueStatistics.builder().userId(3L).leagueId(1L).points(5L).build());
    betLeagueStatisticsSaver.save(BetLeagueStatistics.builder().userId(2L).leagueId(1L).points(4L).build());

    mvc.perform(get("/api/bet/top/league/1?pageSize=3"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(3)));
  }

  @Test
  void shouldReturnTOP3RoundStatisticRoundId() throws Exception {
    betRoundStatisticsSaver.save(BetRoundStatistics.builder().userId(4L).roundId(2L).points(6L).build());
    betRoundStatisticsSaver.save(BetRoundStatistics.builder().userId(3L).roundId(2L).points(5L).build());
    betRoundStatisticsSaver.save(BetRoundStatistics.builder().userId(2L).roundId(2L).points(4L).build());

    mvc.perform(get("/api/bet/top/round/2?pageSize=3"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(3)));
  }

  @Test
  void shouldThrowExceptionWhenLeagueStatisticForUserIdAndLeagueIdNotExist() throws Exception {
    mvc.perform(get("/api/bet/top/league/2?pageSize=3"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  void shouldThrowExceptionWhenRoundStatisticForUserIdAndRoundIdNotExist() throws Exception {
    mvc.perform(get("/api/bet/top/round/4?pageSize=3"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$", hasSize(0)));
  }
}