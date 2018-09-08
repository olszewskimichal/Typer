package pl.michal.olszewski.typer.adapter;

import java.util.Iterator;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

@Slf4j
public class XlsxIterator implements Iterator<FileAdapterRow> {

  private Iterator<Row> iterator;
  private Map<String, Integer> xlsLabels;

  XlsxIterator(XSSFSheet sh, Map<String, Integer> xlsLabels, int headerIndex) {
    this.xlsLabels = xlsLabels;
    iterator = sh.iterator();
    while (iterator.hasNext()) {
      Row row = iterator.next();
      if (row.getRowNum() == headerIndex) {
        break;
      }
    }
    log.debug("Utworzony iterator wierszy dla arkusza {}, całkowita liczba wierszy:{}", sh.getSheetName(), sh.getLastRowNum());
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  @Override
  public FileAdapterRow next() {
    XSSFRow row = (XSSFRow) iterator.next();
    if (row.getRowNum() % 100 == 0) {
      log.trace("Przetwarzanie wiersza o indeksie: {}", row.getRowNum());
    }
    return new FileAdapterRow(row, xlsLabels);
  }

}