package pl.michal.olszewski.typer.users.domain;

import org.springframework.stereotype.Repository;

@Repository
interface UserSaver extends org.springframework.data.repository.Repository<User, Long> {

  User save(User user);

  void deleteAll();
}
