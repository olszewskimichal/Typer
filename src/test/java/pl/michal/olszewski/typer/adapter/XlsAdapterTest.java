package pl.michal.olszewski.typer.adapter;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class XlsAdapterTest {

  private List<String> columnNames = Arrays.asList("kolumna1", "kolumna2", "kolumna3", "kolumna4");

  @Test
  void shouldThrowExceptionWhenIsMoreThenOneSheet() {
    File file = new File("testresources/Zeszyt2.xls");

    IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> new XlsAdapter(file, columnNames));

    assertThat(illegalArgumentException.getMessage()).isEqualToIgnoringCase("Nieprawidłowy plik. Oczekiwano pliku zawierajacego dokładnie jeden arkusz.");
  }

  @Test
  void shouldReadAllValuesFromFile() throws IOException {
    File file = new File("testresources/Zeszyt1.xls");

    FileAdapter adapter = new XlsAdapter(file, columnNames);

    FileAdapterRow fileAdapterRow = adapter.iterator().next();
    assertAll(
        () -> assertThat(fileAdapterRow.get(columnNames.get(0))).isEqualToIgnoringCase("wartosc1"),
        () -> assertThat(fileAdapterRow.get(columnNames.get(1))).isEqualToIgnoringCase("wartosc2"),
        () -> assertThat(fileAdapterRow.get(columnNames.get(2))).isEqualToIgnoringCase("33"),
        () -> assertThat(fileAdapterRow.get(columnNames.get(3))).isEqualToIgnoringCase("12")
    );

  }

  @Test
  void shouldThrowExceptionWhenFileWithoutRequiredColumns() {
    File file = new File("testresources/Zeszyt3.xls");

    IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> new XlsAdapter(file, columnNames));

    assertThat(illegalArgumentException.getMessage()).isEqualToIgnoringCase("Plik Zeszyt3.xls nie zawiera wymaganych kolumn takich jak : kolumna4.");
  }

}
