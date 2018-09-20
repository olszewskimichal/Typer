package pl.michal.olszewski.typer.users.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.michal.olszewski.typer.file.FileStorageService;

@RestController
@Slf4j
public class UserFileController {

  @Autowired
  private FileStorageService fileStorageService;

  @PostMapping("/uploadFile")
  public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
    String fileName = fileStorageService.storeFile(file);
    return ResponseEntity.ok("Załadowano plik " + fileName);
  }

}