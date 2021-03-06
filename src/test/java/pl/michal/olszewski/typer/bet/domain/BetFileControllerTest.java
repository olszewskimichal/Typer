package pl.michal.olszewski.typer.bet.domain;

import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {BetFileController.class})
class BetFileControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private BetFileAdapter fileAdapter;

  @Test
  void shouldSaveUploadedFile() throws Exception {
    MockMultipartFile multipartFile = new MockMultipartFile("file", "test.xlsx",
        "text/plain", "Spring Framework".getBytes());
    this.mvc.perform(multipart("/bet/uploadFile").file(multipartFile))
        .andExpect(status().isOk());
    then(this.fileAdapter).should().uploadFile(multipartFile);
  }
}
