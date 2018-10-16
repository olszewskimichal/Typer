package pl.michal.olszewski.typer.users.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import pl.michal.olszewski.typer.users.UserExistsException;
import pl.michal.olszewski.typer.users.dto.command.CreateNewUser;

class UserCreatorTest {

  private UserSaver userSaver = new InMemoryUserSaver();
  private UserFinder userFinder = new InMemoryUserFinder();

  @Test
  void shouldThrowExceptionWhenCommandIsNull() {
    assertThrows(NullPointerException.class, () -> UserCreator.from(null, userFinder));
  }


  @Test
  void shouldThrowExceptionWhenUsernameOrEmailIsNull() {
    //given
    //when
    //then
    assertThrows(IllegalArgumentException.class, () -> UserCreator.from(CreateNewUser.builder().email("a").username(null).build(), userFinder));
    assertThrows(IllegalArgumentException.class, () -> UserCreator.from(CreateNewUser.builder().username("a").email(null).build(), userFinder));
  }

  @Test
  void shouldCreateNewUser() throws UserExistsException {
    //given
    User expected = User.builder().email("email").username("username").build();

    CreateNewUser createNewUser = CreateNewUser.builder().email("email").username("username").build();
    //when
    User user = UserCreator.from(createNewUser, userFinder);
    //then
    assertThat(user).isEqualToComparingFieldByField(expected);
  }

  @Test
  void shouldThrowExceptionWhenUserOnTheSameEmailExists() {
    //given
    userSaver.save(User.builder().id(3L).email("email").build());

    CreateNewUser createNewUser = CreateNewUser.builder().email("email").username("username").build();
    //when
    //then
    assertThrows(UserExistsException.class, () -> UserCreator.from(createNewUser, userFinder));
  }


}