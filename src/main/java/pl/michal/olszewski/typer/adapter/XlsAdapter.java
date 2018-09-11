package pl.michal.olszewski.typer.adapter;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

@Slf4j
public class XlsAdapter extends ExcelAdapter implements FileAdapter {

  private HSSFWorkbook wb;

  public XlsAdapter(File file, List<String> columns) throws IOException {
    log.debug("Otwieranie pliku {} ", file.getName());
    wb = new HSSFWorkbook(new ByteArrayInputStream(Files.readAllBytes(file.toPath())));

    log.debug("Otwarty plik XLSX - dostępna ilość arkuszy: {}", wb.getNumberOfSheets());

    if (wb.getNumberOfSheets() != 1) {
      log.warn("Import z pliku przerwany - XLSX nie zawiera dokładnie jednego arkusza");
      throw new IllegalArgumentException("Nieprawidłowy plik. Oczekiwano pliku zawierajacego dokładnie jeden arkusz.");
    }
    Iterator<Row> rowIterator = wb.getSheetAt(0).iterator();
    headerIndex = checkHeaders(rowIterator, columns, file.getName());
    log.debug("Numer wiersza z nazwami kolumn {}", headerIndex + 1);
  }

  @Override
  public void close() throws IOException {
    if (wb != null) {
      wb.close();
    }
  }

  @Override
  public Iterator<FileAdapterRow> iterator() {
    return new XlsIterator(wb.getSheetAt(0), xlsLabels, headerIndex);
  }

}