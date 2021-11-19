package utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class DataReader {
XSSFWorkbook wb=null;
XSSFSheet sheet=null;
Cell cell=null;
FileOutputStream fio=null;
int numberOfRows,numberOfCol, rowNum;
public String[][] fileReader(String path,int sheetIndex) throws IOException {
    String[][] data={};
    File file =new File(path);
    FileInputStream fis=new FileInputStream(file);
    wb=new XSSFWorkbook(fis);
    sheet =wb.getSheetAt(sheetIndex);
    numberOfRows =sheet.getLastRowNum();
    numberOfCol =sheet.getRow(0).getLastCellNum();
    data = new String[numberOfRows +1][numberOfCol +1];

    for (int i=1;i< data.length;i++){
        XSSFRow rows=sheet.getRow(i);
        for (int j=0;j<numberOfCol;i++){
            XSSFCell cell=rows.getCell(j);
      String cellData= cell.getStringCellValue();
            data[i][j]=cellData;
      }

    }
    return data;

}

public String getCellValue(XSSFCell cell){
    Object value=null;

    CellType dataType=cell.getCellType();
    switch (dataType){
        case NUMERIC :
            value=cell.getNumericCellValue();
            break;
        case STRING:
            value=cell.getStringCellValue();
            break;
        case BOOLEAN:
            value =cell.getBooleanCellValue();
            break;
    }
return value.toString();
}

public void writeBack(String value) throws IOException {
    wb = new XSSFWorkbook();
    sheet =wb.createSheet();
    Row row=sheet.createRow(rowNum);
    row.setHeightInPoints(10);
    fio=new FileOutputStream(new File("Excelfile.xls"));
    wb.write(fio);
    for(int i=0;i<row.getLastCellNum(); i++){
        row.createCell(i);
        cell.setCellValue(value);
    }
    fio.close();
    wb.close();
}

}
