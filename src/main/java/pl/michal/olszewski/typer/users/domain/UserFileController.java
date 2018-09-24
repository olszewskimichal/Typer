package pl.michal.olszewski.typer.users.domain;

import java.io.IOException;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.michal.olszewski.typer.users.UserExistsException;

@RestController()
@Slf4j
class UserFileController {

  private final UserFileAdapter userFileAdapter;


  public UserFileController(UserFileAdapter userFileAdapter) {
    this.userFileAdapter = userFileAdapter;
  }

  @PostMapping("/users/uploadFile")
  @Transactional(rollbackFor = UserExistsException.class)
  public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
    Path fileName = userFileAdapter.uploadFile(file);
    return ResponseEntity.ok("Załadowano plik " + fileName);
  }

}