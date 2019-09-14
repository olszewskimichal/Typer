package pl.michal.olszewski.typer.users.domain;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.michal.olszewski.typer.adapter.FileAdapter;
import pl.michal.olszewski.typer.adapter.FileAdapterRow;
import pl.michal.olszewski.typer.adapter.XlsAdapter;
import pl.michal.olszewski.typer.adapter.XlsxAdapter;
import pl.michal.olszewski.typer.file.FileStorageService;
import pl.michal.olszewski.typer.users.dto.command.CreateNewUser;

@Component
@Slf4j
class UserFileAdapter {

  private static final String USERNAME = "username";
  private static final String EMAIL = "email";
  private static final List<String> defaultColumns = Arrays.asList(EMAIL, USERNAME);

  private final UserFinder userFinder;
  private final UserSaver userSaver;
  private final FileStorageService fileStorageService;

  public UserFileAdapter(UserFinder userFinder, UserSaver userSaver, FileStorageService fileStorageService) {
    this.userFinder = userFinder;
    this.userSaver = userSaver;
    this.fileStorageService = fileStorageService;
  }

  void loadUsersFromFile(Path file) throws IOException {
    try (FileAdapter fileAdapter = selectAdapter(file)) {
      for (FileAdapterRow fileAdapterRow : fileAdapter) {
        String username = fileAdapterRow.get(UserFileAdapter.USERNAME);
        String email = fileAdapterRow.get(UserFileAdapter.EMAIL);
        CreateNewUser createNewUser = CreateNewUser.builder().email(email).username(username).build();
        User from = UserCreator.from(createNewUser, userFinder);
        userSaver.save(from);
        log.debug("Zapisałem uzytkownika {}", from);
      }
    }
  }

  Path uploadFile(MultipartFile file) throws IOException {
    Path path = fileStorageService.storeFile(file);
    loadUsersFromFile(path);
    return path;
  }

  private FileAdapter selectAdapter(Path path) throws IOException {
    if (path.getFileName().toString().toLowerCase().endsWith(".xlsx")) {
      return new XlsxAdapter(path, UserFileAdapter.defaultColumns);
    } else {
      return new XlsAdapter(path, UserFileAdapter.defaultColumns);
    }
  }
}
