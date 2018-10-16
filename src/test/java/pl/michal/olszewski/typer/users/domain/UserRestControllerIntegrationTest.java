package pl.michal.olszewski.typer.users.domain;

import static org.hamcrest.Matchers.is;
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

class UserRestControllerIntegrationTest extends RestControllerIntegrationTestBase {

  @Autowired
  private UserSaver userSaver;


  @BeforeEach
  void setUp() {
    mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    userSaver.deleteAll();
  }

  @Test
  void shouldReturnNotFoundWhenUserByIdNotExist() throws Exception {
    mvc.perform(get("/api/user/254"))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnUserInfoWhenUserByIdExists() throws Exception {
    User user = User.builder().email("email").username("name").build();
    userSaver.save(user);
    mvc.perform(get("/api/user/" + user.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.id", is(user.getId().intValue())))
        .andExpect(jsonPath("$.email", is("email")))
        .andExpect(jsonPath("$.username", is("name")));

  }

  @Test
  void shouldCreateNewUser() throws Exception {

    mvc.perform(post("/api/user")
        .param("email", "134")
        .param("username", "123"))
        .andExpect(status().isCreated());
  }

  @Test
  void shouldThrowExceptionWhenHomeTeamIdIsNullOnCreateNewUser() throws Exception {
    mvc.perform(post("/api/user")
        .param("name", "123"))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

}