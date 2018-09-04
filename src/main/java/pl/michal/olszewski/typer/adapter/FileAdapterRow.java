package pl.michal.olszewski.typer.adapter;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;

public class FileAdapterRow {

  static NumberFormat nf;

  static {
    nf = NumberFormat.getNumberInstance(new Locale("PL", "pl"));
    nf.setGroupingUsed(false);
    nf.setMaximumFractionDigits(15);
  }

  private final HSSFRow row;
  private final Map<String, Integer> xlsLabels;

  FileAdapterRow(HSSFRow row, Map<String, Integer> xlsLabels) {
    this.row = row;
    this.xlsLabels = xlsLabels;
  }

  public String get(String colName) {
    Optional<String> ret = excelValue(row.getCell(xlsLabels.get(colName), MissingCellPolicy.RETURN_BLANK_AS_NULL));
    return ret.map(String::trim).orElse("");
  }

  private Optional<String> excelValue(HSSFCell cell) {
    switch (cell.getCellTypeEnum()) {
      case NUMERIC:
        return Optional.ofNullable(format(cell.getNumericCellValue()));
      case STRING:
        return Optional.ofNullable(cell.getRichStringCellValue().getString());
    }
    return Optional.empty();
  }

  private String format(double numericCellValue) {
    return nf.format(numericCellValue);
  }
}