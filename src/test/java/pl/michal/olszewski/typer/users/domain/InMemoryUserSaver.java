package pl.michal.olszewski.typer.users.domain;

import java.util.Random;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class InMemoryUserSaver implements UserSaver {


  @Override
  public User save(User user) {
    return InMemoryUserFinder.map.put(user.getId() != null ? user.getId() : new Random().nextLong(), user);
  }

  @Override
  public void deleteAll() {
    log.debug("Usuwam wszystkie elementy z tabeli Users");
    InMemoryUserFinder.map.clear();
  }
}
