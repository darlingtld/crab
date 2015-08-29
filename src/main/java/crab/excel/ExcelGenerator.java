package crab.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by tangl9 on 2015-07-23.
 */
public class ExcelGenerator {
    private String fileName;
    private XSSFWorkbook workbook = new XSSFWorkbook();

    public ExcelGenerator(String fileName) {
        this.fileName = fileName;
    }

    public void generate(List<String> sheetNames, List<String[]> headersList, List<List<String[]>> contents) throws IOException {
        for (int i = 0; i < sheetNames.size(); i++) {
            //Create a blank sheet
            XSSFSheet spreadsheet = workbook.createSheet(sheetNames.get(i));
            //Create row object
            XSSFRow row;
            //This data needs to be written (Object[])
            Map<Integer, String[]> contentMap = new TreeMap<>();
            contentMap.put(1, headersList.get(i));
            for (int j = 0; j < contents.get(i).size(); j++) {
                contentMap.put(j + 2, contents.get(i).get(j));
            }
            //Iterate over data and write to sheet
            Set<Integer> keyid = contentMap.keySet();
            int rowid = 0;
            for (Integer key : keyid) {
                row = spreadsheet.createRow(rowid++);
                String[] objectArr = contentMap.get(key);
                int cellid = 0;
                for (String obj : objectArr) {
                    Cell cell = row.createCell(cellid++);
                    CellStyle style = cell.getCellStyle();
                    style.setWrapText(true);
                    cell.setCellStyle(style);
                    cell.setCellValue(obj);
                }
            }
//            for (int c = 0; c < headersList.get(i).length; c++) {
//                spreadsheet.autoSizeColumn(c);
//            }
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File(fileName));
            workbook.write(out);
            out.close();
        }
    }

    public void generate4Dispatches(List<String> sheetNames, List<String[]> headersList, List<List<String[]>> contents) throws IOException {
        for (int i = 0; i < sheetNames.size(); i++) {
            //Create a blank sheet
            XSSFSheet spreadsheet = workbook.createSheet(sheetNames.get(i));
            //Create row object
            XSSFRow row;
            //This data needs to be written (Object[])
            Map<Integer, String[]> contentMap = new TreeMap<>();
            contentMap.put(1, headersList.get(i));
            for (int j = 0; j < contents.get(i).size(); j++) {
                contentMap.put(j + 2, contents.get(i).get(j));
            }
            //Iterate over data and write to sheet
            Set<Integer> keyid = contentMap.keySet();
            int rowid = 0;
            for (Integer key : keyid) {
                row = spreadsheet.createRow(rowid++);
                String[] objectArr = contentMap.get(key);
                int cellid = 0;
                for (String obj : objectArr) {
                    if (obj.contains("\r\n")) {
                        String[] strArray = obj.split("\r\n");
                        for(String str : strArray){
                            Cell cell = row.createCell(cellid);
                            cell.setCellValue(str);
                            row = spreadsheet.createRow(rowid++);
                        }
                    } else {
                        Cell cell = row.createCell(cellid++);
                        cell.setCellValue(obj);
                    }
                }
            }
//            for (int c = 0; c < headersList.get(i).length; c++) {
//                spreadsheet.autoSizeColumn(c);
//            }
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File(fileName));
            workbook.write(out);
            out.close();
        }
    }
}
