package pl.michal.olszewski.typer.adapter;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

@Slf4j
class XlsAdapter implements FileAdapter {

  private int headerIndex;

  private Map<String, Integer> xlsLabels;

  private HSSFWorkbook wb;

  XlsAdapter(File file, List<String> columns) throws IOException {

    log.debug("Otwieranie pliku {} ", file.getName());
    wb = new HSSFWorkbook(new ByteArrayInputStream(Files.readAllBytes(file.toPath())));

    log.debug("Otwarty plik XLSX - dostępna ilość arkuszy: {}", wb.getNumberOfSheets());

    if (wb.getNumberOfSheets() != 1) {
      log.warn("Import z pliku przerwany - XLSX nie zawiera dokładnie jednego arkusza");
      throw new IllegalArgumentException("Nieprawidłowy plik. Oczekiwano pliku zawierajacego dokładnie jeden arkusz.");
    }
    headerIndex = checkHeaders(wb, columns, file.getName());
    log.debug("Numer wiersza z nazwami kolumn {}", headerIndex + 1);
  }

  private int checkHeaders(HSSFWorkbook wb, List<String> columns, String filename) {
    xlsLabels = new HashMap<>();
    HSSFSheet sheet = wb.getSheetAt(0);
    Iterator<Row> rowIt = sheet.iterator();
    String missingColumnName = null;
    while (rowIt.hasNext()) {
      Row row = rowIt.next();
      Iterator<Cell> iterator = row.cellIterator();
      while (iterator.hasNext()) {
        Cell cell = iterator.next();
        if (cell.getCellTypeEnum() == CellType.STRING) {
          xlsLabels.put(cell.getStringCellValue(), cell.getColumnIndex());
        }
      }
      boolean missingColumn = false;
      for (String colName : columns) {
        if (!xlsLabels.keySet().contains(colName)) {
          log.debug("Brakuje kolumny = " + colName);
          missingColumnName = colName;
          missingColumn = true;
          break;
        }
      }

      if (!missingColumn) {
        return row.getRowNum();
      }

      if (row.getRowNum() > 5) {
        break;
      }
    }
    throw new IllegalArgumentException(String.format("Plik %s nie zawiera wymaganych kolumn takich jak : %s.", filename, missingColumnName));
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