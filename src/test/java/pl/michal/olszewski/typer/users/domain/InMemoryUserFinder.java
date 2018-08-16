package pl.michal.olszewski.typer.users.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryUserFinder implements UserFinder {

  static ConcurrentHashMap<Long, User> map = new ConcurrentHashMap<>();

  @Override
  public Optional<User> findByEmail(String email) {
    return map.values()
        .stream()
        .filter(v -> v.getEmail().equalsIgnoreCase(email))
        .findAny();
  }

  @Override
  public User findById(Long id) {
    return map.get(id);
  }

  @Override
  public List<User> findAll() {
    return new ArrayList<>(map.values());
  }
}
