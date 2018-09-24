package pl.michal.olszewski.typer.file;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

class FileStorageServiceTest {

  private FileStorageProperties properties = new FileStorageProperties();
  private FileStorageService service;

  @BeforeEach
  void init() {
    properties.setUploadDir("target/files/" + Math.abs(new Random().nextLong()));
    service = new FileStorageService(properties);
  }

  @Test
  void shouldNotStoreFileWithIncorrectFilePath() {
    assertThrows(FileStorageException.class, () -> service.storeFile(new MockMultipartFile("aa",
        "bar/../fooaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.txt",
        MediaType.TEXT_PLAIN_VALUE, "Hello World".getBytes())));
  }


  @Test
  void shouldNotSaveWhenInvalidPathSequence() {
    assertThrows(FileStorageException.class, () -> service.storeFile(new MockMultipartFile("foo", "../foo.txt",
        MediaType.TEXT_PLAIN_VALUE, "Hello World".getBytes())));
  }

  @Test
  void shouldSaveFile() {
    service.storeFile(new MockMultipartFile("foo", "bar/../foo.txt",
        MediaType.TEXT_PLAIN_VALUE, "Hello World".getBytes()));
  }
}