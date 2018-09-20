package pl.michal.olszewski.typer.adapter;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileAdapterRowTest {

  private HSSFRow row;
  private Map<String, Integer> columns;

  @BeforeEach
  void setUp() {
    HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
    HSSFSheet sheet = hssfWorkbook.createSheet("sheet");
    row = sheet.createRow(0);
    columns = new HashMap<>();
    columns.put("A", 0);
  }

  @Test
  void shouldGetValueFromStringExcelCell() {
    //given
    String RESULT = "String";
    row.createCell(0).setCellValue(RESULT);
    //when
    FileAdapterRow fileAdapterRow = new FileAdapterRow(row, columns);
    String cellValue = fileAdapterRow.get("A");
    //then
    assertThat(cellValue).isEqualTo(RESULT);
  }

  @Test
  void shouldGetValueFromNumericExcelCell() {
    //given
    double RESULT = 20.01;
    row.createCell(0).setCellValue(RESULT);
    //when
    FileAdapterRow fileAdapterRow = new FileAdapterRow(row, columns);
    String cellValue = fileAdapterRow.get("A");
    //then
    assertThat(cellValue).isEqualTo("20,01");
  }

  @Test
  void shouldGetValueFromFormulaExcelCell() {
    //given
    HSSFCell cell = row.createCell(0);
    cell.setCellFormula("3+4");
    //when
    FileAdapterRow fileAdapterRow = new FileAdapterRow(row, columns);
    String cellValue = fileAdapterRow.get("A");
    //then
    assertThat(cellValue).isEmpty();
  }
}