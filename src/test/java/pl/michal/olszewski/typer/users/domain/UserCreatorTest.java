package pl.michal.olszewski.typer.users.domain;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import pl.michal.olszewski.typer.users.UserExistsException;
import pl.michal.olszewski.typer.users.dto.command.CreateNewUser;

class UserCreatorTest {

  private UserCreator userCreator = new UserCreator();

  @Test
  void shouldThrowExceptionWhenCommandIsNull() {
    assertThrows(NullPointerException.class, () -> userCreator.from(null));
  }


  @Test
  void shouldThrowExceptionWhenUsernameOrEmailIsNull() {
    //given
    //when
    //then
    assertThrows(IllegalArgumentException.class, () -> userCreator.from(CreateNewUser.builder().email("a").username(null).build()));
    assertThrows(IllegalArgumentException.class, () -> userCreator.from(CreateNewUser.builder().username("a").email(null).build()));
  }

  @Test
  void shouldCreateNewUser() {
    //given
    CreateNewUser createNewUser = CreateNewUser.builder().email("email").username("username").build();
    //when
    userCreator.from(createNewUser);
    //then
    Assert.fail();
  }

  @Test
  void shouldThrowExceptionWhenUserOnTheSameEmailExists() {
    //given
    CreateNewUser createNewUser = CreateNewUser.builder().email("email").username("username").build();
    //when
    assertThrows(UserExistsException.class, () -> userCreator.from(createNewUser));
  }


}