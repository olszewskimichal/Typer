package pl.michal.olszewski.typer.users.domain;

import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.michal.olszewski.typer.file.FileStorageService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {UserFileController.class})
public class UserFileControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private FileStorageService storageService;

  @Test
  public void shouldSaveUploadedFile() throws Exception {
    MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
        "text/plain", "Spring Framework".getBytes());
    this.mvc.perform(multipart("/uploadFile").file(multipartFile))
        .andExpect(status().isOk());
    then(this.storageService).should().storeFile(multipartFile);
  }


}
