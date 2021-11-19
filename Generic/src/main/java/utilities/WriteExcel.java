package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Iterator;

public class WriteExcel {

    private static final String FILE_NAME = System.getProperty("user.dir") + "/DataTest/TestExcelFile.xlsx";
//  This method is used to read Excel File based on sheet index number
public static void writeExcel(String fileName) throws IOException {
    XSSFWorkbook workbook=new XSSFWorkbook();
    XSSFSheet sheet=workbook.createSheet("studentList");
    Object[][] stDetails= {
            {"sl", "FirstName", "LastName", "ContactNumber"},
            {"1", "Hasan", "Ibrahim", "3455432345"},
            {"2", "Thomas", "Harry", "5435653456"},

    };

    int rowNum = 0;
    System.out.println("Excel File Created");
    for(Object[] std:stDetails){
        Row row=sheet.createRow(rowNum++);
        int colNum=0;
        for(Object field:std){
            Cell cell =row.createCell(colNum++);
            if(field instanceof String){
                cell.setCellValue((String) field);
            }else if(field instanceof Integer){
                cell.setCellValue((Integer) field);
            }
        }
    }
    try {
        FileOutputStream fileOutputStream=new FileOutputStream(fileName);
        workbook.write(fileOutputStream);
        workbook.close();

    }catch (FileNotFoundException e){
        e.printStackTrace();
    }catch (IOException io){
        io.printStackTrace();
    }
System.out.println("Done");
}

}
