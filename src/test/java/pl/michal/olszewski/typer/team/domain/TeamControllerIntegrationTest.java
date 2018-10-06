package pl.michal.olszewski.typer.team.domain;

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
import pl.michal.olszewski.typer.RestControllerIntegrationTestBase;

class TeamControllerIntegrationTest extends RestControllerIntegrationTestBase {

  @Autowired
  private TeamSaver teamSaver;

  @BeforeEach
  void setUp() {
    teamSaver.deleteAll();
    mvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  @Test
  void shouldReturnNotFoundWhenTeamByIdNotExist() throws Exception {
    mvc.perform(get("/api/team/254"))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnTeamInfoWhenTeamByIdExists() throws Exception {
    Team team = Team.builder().name("name").build();
    teamSaver.save(team);
    mvc.perform(get("/api/team/" + team.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.id", is(team.getId().intValue())))
        .andExpect(jsonPath("$.name", is("name")));

  }

  @Test
  void shouldCreateNewTeam() throws Exception {
    mvc.perform(post("/api/team")
        .param("name", "123"))
        .andExpect(status().isCreated());
  }

  @Test
  void shouldThrowExceptionWhenHomeTeamIdIsNullOnCreateNewTeam() throws Exception {
    mvc.perform(post("/api/match/round"))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

}