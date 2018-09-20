package pl.michal.olszewski.typer.users.domain;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.michal.olszewski.typer.file.FileStorageProperties;
import pl.michal.olszewski.typer.file.FileStorageService;
import pl.michal.olszewski.typer.users.UserExistsException;

class UserFileAdapterTest {

  private UserFileAdapter userFileAdapter;
  private UserFinder userFinder;

  @BeforeEach
  void setUp() {
    FileStorageProperties fileStorageProperties = new FileStorageProperties();
    fileStorageProperties.setUploadDir("uploads");
    UserSaver userSaver = new InMemoryUserSaver();
    userFinder = new InMemoryUserFinder();
    userFileAdapter = new UserFileAdapter(new UserCreator(userFinder), userSaver, new FileStorageService(fileStorageProperties));
    userSaver.deleteAll();
  }


  @Test
  void shouldCreateOneUserFromXlsxFile() throws IOException, UserExistsException {
    //given
    Path file = Paths.get("testresources/userfile.xlsx");

    //when
    userFileAdapter.loadUsersFromFile(file);

    //then
    assertThat(userFinder.findAll()).isNotEmpty().hasSize(1);
  }

  @Test
  void shouldCreateOneUserFromXlsFile() throws IOException, UserExistsException {
    Path file = Paths.get("testresources/userfile.xls");

    //when
    userFileAdapter.loadUsersFromFile(file);

    //then
    assertThat(userFinder.findAll()).isNotEmpty().hasSize(1);
  }

}