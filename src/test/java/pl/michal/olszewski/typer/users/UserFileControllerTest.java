package pl.michal.olszewski.typer.users;

import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.michal.olszewski.typer.file.FileStorageService;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class UserFileControllerTest {
  
  @Autowired
  private MockMvc mvc;

  @MockBean
  private FileStorageService storageService;

  @Test
  void shouldSaveUploadedFile() throws Exception {
    MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
        "text/plain", "Spring Framework".getBytes());
    this.mvc.perform(fileUpload("/uploadFile").file(multipartFile))
        .andExpect(status().isFound())
        .andExpect(header().string("Location", "/"));

    then(this.storageService).should().storeFile(multipartFile);
  }

}