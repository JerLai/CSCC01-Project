package main.java.com.icare.database;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.*;

public class DatabaseIO {

  public static boolean importData(Connection connection, File file) {
    // get file name and decide table name
    String fileName = file.getName();
    String fileTable = fileName.substring(0, fileName.indexOf("."));
    // create workbook from Excel file
    Workbook workbook = null;
    try {
      workbook = WorkbookFactory.create(file);
    } catch (Exception e) {
      System.out.println("Error while reading file: " + fileName);
      e.printStackTrace();
      return false;
    }
    // first sheet
    Sheet sheet = workbook.getSheetAt(0);

    try {
      String headers = "";
      String tableColData = "ID INTEGER PRIMARY KEY NOT NULL";

      ArrayList<String> tableExistCols = new ArrayList<String>();
      // check if table exists
      try {
        tableExistCols = (ArrayList<String>) databaseAPI
            .getTableColumnData(connection, fileTable);
      } catch (Exception e) {
        System.out.println(
            "Error while checking if table exists for file: " + fileName);
        e.printStackTrace();
        return false;
      }

      ArrayList<String> oldTableExistCols = new ArrayList<String>();
      // delete .old table if exists and rename existing table to .old if needed
      try {
        oldTableExistCols = (ArrayList<String>) databaseAPI
            .getTableColumnData(connection, fileTable + "_old");
      } catch (Exception e) {
        System.out.println(
            "Error while checking if _old table exists for file: " + fileName);
        e.printStackTrace();
        return false;
      }
      if (oldTableExistCols.size() > 0) {
        try {
          databaseAPI.deleteTable(connection, fileTable + "_old");
        } catch (Exception e) {
          System.out
              .println("Error while deleting _old table for file: " + fileName);
          e.printStackTrace();
          return false;
        }
      }
      if (tableExistCols.size() > 0) {
        try {
          databaseAPI.deleteTable(connection, fileTable + "_old");
          databaseAPI.renameTable(connection, fileTable, fileTable + "_old");
        } catch (Exception e) {
          System.out
              .println("Error while saving _old table for file: " + fileName);
          e.printStackTrace();
          return false;
        }
      }

      // iterate through 2nd row to get column headers
      int r = 1;
      Row row = null;
      Cell cell;
      if (r < sheet.getLastRowNum()) {
        row = sheet.getRow(r);
      }
      for (int c = 0; c < row.getLastCellNum(); c++) {
        cell = row.getCell(c);
        // determine column headers from 3rd row
        tableColData += ", ";
        String cellValue = cell.getRichStringCellValue().getString();
        tableColData += cellValue;
        tableColData += " char(255)";
        headers += "'" + cellValue;
        headers += "', ";
      }
      headers = headers.substring(0, headers.length() - 2);

      // create table with corresponding columns
      try {
        databaseAPI.createTable(connection, fileTable, tableColData);
      } catch (Exception e) {
        System.out.println("Error while inserting data for file: " + fileName);
        e.printStackTrace();
        return false;
      }

      DataFormatter dataFormatter = new DataFormatter();
      // iterate through each row
      String rowValues;
      for (int r1 = r + 1; r1 <= sheet.getLastRowNum(); r1++) {
        row = sheet.getRow(r1);
        rowValues = "";
        // iterate through each cell in the row
        for (int c1 = 0; c1 < row.getLastCellNum(); c1++) {
          cell = row.getCell(c1);
          String cellValue = dataFormatter.formatCellValue(cell);
          if (cellValue.equals("")) {
            rowValues += ", " + null + "";
          } else {
            rowValues += ", '" + cellValue + "'";
          }
        }
        rowValues = rowValues.substring(2);
        // add into table
        try {
          databaseAPI.insertData(connection, fileTable, headers, rowValues);
        } catch (Exception e) {
          System.out
              .println("Error while inserting data for file: " + fileName);
          e.printStackTrace();
          return false;
        }
      }
    } catch (Exception e) {
      System.out.println("Error while reading file: " + fileName);
      e.printStackTrace();
      return false;
    } finally {
      try {
        // close workbook
        workbook.close();
      } catch (IOException e) {
        System.out.println("Error while closing file: " + fileName);
        e.printStackTrace();
      }
    }
    return true;
  }

}
