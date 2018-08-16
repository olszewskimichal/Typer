package pl.michal.olszewski.typer.users.domain;

import java.util.Random;

class InMemoryUserSaver implements UserSaver {


  @Override
  public User save(User user) {
    return InMemoryUserFinder.map.put(user.getId() != null ? user.getId() : new Random().nextLong(), user);
  }

  @Override
  public void deleteAll() {
    InMemoryUserFinder.map.clear();
  }
}
