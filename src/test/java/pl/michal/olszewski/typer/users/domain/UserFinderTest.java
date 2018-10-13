package pl.michal.olszewski.typer.users.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.michal.olszewski.typer.RepositoryTestBase;
import pl.michal.olszewski.typer.users.UserNotFoundException;

class UserFinderTest extends RepositoryTestBase {

  @Autowired
  private UserFinder userFinder;

  @Test
  void shouldUserById() {
    //given
    User user = User.builder().build();
    Long id = (Long) entityManager.persistAndGetId(user);
    entityManager.flush();
    entityManager.clear();

    //when
    User founded = userFinder.findOneOrThrow(id);

    //then
    assertThat(founded).isNotNull().isEqualToComparingFieldByField(user);
  }

  @Test
  void shouldThrowExceptionWhenUserNotFound() {
    //given
    //when
    //then
    assertThrows(UserNotFoundException.class, () -> userFinder.findOneOrThrow(1L));
  }

  @Test
  void shouldFindAllUsers() {
    entityManager.persist(User.builder().build());
    entityManager.persist(User.builder().build());

    List<User> all = userFinder.findAll();

    assertThat(all).isNotEmpty().hasSize(2);
  }

  @Test
  void shouldReturnOptionalEmptyWhenUserByEmailNotExists() {
    //given
    //when
    Optional<User> founded = userFinder.findByEmail("email1");

    //then
    assertThat(founded).isNotPresent();

  }

  @Test
  void shouldReturnUserByEmail() {
//given
    User user = User.builder().email("email1").build();
    entityManager.persistAndFlush(user);
    entityManager.clear();

    //when
    Optional<User> founded = userFinder.findByEmail("email1");

    //then
    assertThat(founded).isPresent().get().isEqualToComparingFieldByField(user);
  }

}