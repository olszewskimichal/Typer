package pl.michal.olszewski.typer.adapter;

import java.util.Iterator;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;

@Slf4j
class ExcelIterator {

  Iterator<Row> iterator;
  private Map<String, Integer> xlsLabels;

  ExcelIterator(Iterator<Row> iterator, Map<String, Integer> xlsLabels) {
    this.iterator = iterator;
    this.xlsLabels = xlsLabels;
  }

  public FileAdapterRow next() {
    Row row = iterator.next();
    if (row.getRowNum() % 100 == 0) {
      log.trace("Przetwarzanie wiersza o indeksie: {}", row.getRowNum());
    }
    return new FileAdapterRow(row, xlsLabels);
  }

  public boolean hasNext() {
    return iterator.hasNext();
  }

}
