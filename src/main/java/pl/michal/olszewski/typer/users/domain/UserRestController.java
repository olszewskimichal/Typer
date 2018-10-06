package pl.michal.olszewski.typer.users.domain;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.michal.olszewski.typer.users.dto.command.CreateNewUser;
import pl.michal.olszewski.typer.users.dto.dto.UserInfo;


@RestController
@RequestMapping("/api/user")
class UserRestController {

  private final UserFinder userFinder;
  private final UserSaver userSaver;

  public UserRestController(UserFinder userFinder, UserSaver userSaver) {
    this.userFinder = userFinder;
    this.userSaver = userSaver;
  }

  @GetMapping("/{id}")
  ResponseEntity<UserInfo> getUserInfoById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(userFinder.findOneOrThrow(id).toUserInfo());
  }


  @PostMapping("")
  ResponseEntity<String> createNewUser(@Valid @ModelAttribute("createNewUser") CreateNewUser createNewUser) {
    User from = UserCreator.from(createNewUser, userFinder);
    userSaver.save(from);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
