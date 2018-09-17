package pl.michal.olszewski.typer.users.domain;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserFileAdapterTest {

  private UserFileAdapter userFileAdapter;
  private UserFinder userFinder;

  @BeforeEach
  void setUp() {
    UserSaver userSaver = new InMemoryUserSaver();
    userFinder = new InMemoryUserFinder();
    userFileAdapter = new UserFileAdapter(new UserCreator(userFinder), userSaver);
    userSaver.deleteAll();
  }


  @Test
  void shouldCreateOneUserFromXlsxFile() throws IOException {
    //given
    File file = new File("testresources/userfile.xlsx");

    //when
    userFileAdapter.loadUsersFromFile(file);

    //then
    assertThat(userFinder.findAll()).isNotEmpty().hasSize(1);
  }

  @Test
  void shouldCreateOneUserFromXlsFile() throws IOException {
    File file = new File("testresources/userfile.xls");

    //when
    userFileAdapter.loadUsersFromFile(file);

    //then
    assertThat(userFinder.findAll()).isNotEmpty().hasSize(1);
  }

}