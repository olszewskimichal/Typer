package pl.michal.olszewski.typer.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import pl.michal.olszewski.typer.file.FileStorageService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileUploadIntegrationTests {

  @Autowired
  private TestRestTemplate restTemplate;

  @MockBean
  private FileStorageService storageService;

  @LocalServerPort
  private int port;

  @Test
  public void shouldUploadFile() {
    ClassPathResource resource = new ClassPathResource("testupload.txt", getClass());

    MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
    map.add("file", resource);
    ResponseEntity<String> response = this.restTemplate.postForEntity("/uploadFile", map, String.class);

    assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    then(storageService).should().storeFile(any(MultipartFile.class));
  }
}