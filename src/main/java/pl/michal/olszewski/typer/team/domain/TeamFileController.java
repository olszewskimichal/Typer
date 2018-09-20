package pl.michal.olszewski.typer.team.domain;

import java.io.IOException;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController()
@Slf4j
class TeamFileController {

  private final TeamFileAdapter teamFileAdapter;

  public TeamFileController(TeamFileAdapter teamFileAdapter) {
    this.teamFileAdapter = teamFileAdapter;
  }

  @PostMapping("/team/uploadFile")
  public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
    Path fileName = teamFileAdapter.uploadFile(file);
    return ResponseEntity.ok("Załadowano plik " + fileName);
  }

}