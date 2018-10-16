package pl.michal.olszewski.typer.users.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;
import pl.michal.olszewski.typer.users.UserNotFoundException;

@org.springframework.stereotype.Repository
interface UserFinder extends Repository<User, Long> {

  Optional<User> findByEmail(String email);

  User findById(Long id);

  List<User> findAll();

  default User findOneOrThrow(Long id) {
    User user = findById(id);
    if (user == null) {
      throw new UserNotFoundException(String.format("Uzytkownik o id %s nie istnieje", id));
    }
    return user;
  }

}
