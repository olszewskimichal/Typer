package pl.michal.olszewski.typer.users.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import pl.michal.olszewski.typer.users.UserNotFoundException;
import pl.michal.olszewski.typer.users.dto.command.CreateNewUser;
import pl.michal.olszewski.typer.users.dto.dto.UserInfo;

class UserRestControllerUnitTest {

  private UserRestController userRestController;
  private UserSaver userSaver = new InMemoryUserSaver();
  private UserFinder userFinder = new InMemoryUserFinder();

  @BeforeEach
  void setUp() {
    userRestController = new UserRestController(userFinder, userSaver);
  }

  @Test
  void shouldReturnNotFoundWhenUserByIdNotExist() {
    assertThrows(UserNotFoundException.class, () -> userRestController.getUserInfoById(254L));
  }

  @Test
  void shouldReturnUserInfoWhenMatchByIdExists() {
    User user = User.builder().id(254L).email("nazwa").username("nazwa").build();
    userSaver.save(user);

    ResponseEntity<UserInfo> betInfoById = userRestController.getUserInfoById(254L);
    assertThat(betInfoById.getStatusCodeValue()).isEqualTo(200);
    assertThat(betInfoById.getBody()).isNotNull();
    assertThat(betInfoById.getBody().getId()).isEqualTo(254L);

  }

  @Test
  void shouldCreateNewUser() {
    CreateNewUser createNewUser = CreateNewUser.builder().email("email").username("name").build();
    ResponseEntity<String> responseEntity = userRestController.createNewUser(createNewUser);

    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
    assertThat(responseEntity.getBody()).isNull();
  }

  @AfterEach
  void removeAll() {
    userSaver.deleteAll();
  }


}