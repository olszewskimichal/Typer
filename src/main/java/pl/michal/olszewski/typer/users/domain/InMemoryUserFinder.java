package pl.michal.olszewski.typer.users.domain;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryUserFinder implements UserFinder {

  private ConcurrentHashMap<Long, User> userMap = new ConcurrentHashMap<>();

  @Override
  public Optional<User> findByEmail(String email) {
    return userMap.values()
        .stream()
        .filter(v -> v.getEmail().equalsIgnoreCase(email))
        .findAny();
  }

  @Override
  public User findById(Long id) {
    return userMap.get(id);
  }

  void save(Long id, User match) {
    userMap.put(id, match);
  }
}
