package pl.michal.olszewski.typer.team.domain;

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
@WebMvcTest(controllers = {TeamFileController.class})
class TeamFileControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private TeamFileAdapter fileAdapter;

  @Test
  void shouldSaveUploadedFile() throws Exception {
    MockMultipartFile multipartFile = new MockMultipartFile("file", "test.xlsx",
        "text/plain", "Spring Framework".getBytes());
    this.mvc.perform(multipart("/team/uploadFile").file(multipartFile))
        .andExpect(status().isOk());
    then(this.fileAdapter).should().uploadFile(multipartFile);
  }
}
