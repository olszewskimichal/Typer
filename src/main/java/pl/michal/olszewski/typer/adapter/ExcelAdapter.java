package pl.michal.olszewski.typer.adapter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

@Slf4j
class ExcelAdapter {

  int headerIndex;

  Map<String, Integer> xlsLabels;

  ExcelAdapter() {
  }

  int checkHeaders(Iterator<Row> rowIt, List<String> columns, String filename) {
    xlsLabels = new HashMap<>();
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

}
