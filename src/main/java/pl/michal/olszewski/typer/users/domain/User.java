package pl.michal.olszewski.typer.users.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.michal.olszewski.typer.users.dto.dto.UserInfo;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
class User {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Getter
  private Long id;

  @Getter
  private String email;

  private String username;

  UserInfo toUserInfo() {
    return new UserInfo(id, email, username);
  }

}
