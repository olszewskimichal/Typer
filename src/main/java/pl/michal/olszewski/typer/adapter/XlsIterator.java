package pl.michal.olszewski.typer.adapter;

import java.util.Iterator;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Row;

@Slf4j
class XlsIterator extends ExcelIterator implements Iterator<FileAdapterRow> {

  XlsIterator(HSSFSheet sh, Map<String, Integer> xlsLabels, int headerIndex) {
    super(sh.iterator(), xlsLabels);
    while (iterator.hasNext()) {
      Row row = iterator.next();
      if (row.getRowNum() == headerIndex) {
        break;
      }
    }
    log.debug("Utworzony iterator wierszy dla arkusza {}, całkowita liczba wierszy:{}", sh.getSheetName(), sh.getLastRowNum());
  }
}