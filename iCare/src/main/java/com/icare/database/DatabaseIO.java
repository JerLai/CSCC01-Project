package main.java.com.icare.database;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.table.TableModel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
				System.out.println(fileTable);
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
					System.out.println(fileTable);
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
				String cellValue = cell.getRichStringCellValue().getString();
				tableColData += ", '" + cellValue + "' TEXT";
				headers += "'" + cellValue;
				headers += "', ";
			}
			headers = headers.substring(0, headers.length() - 2);

			// create table with corresponding columns
			try {
				databaseAPI.createTable(connection, fileTable, tableColData);
			} catch (Exception e) {
				System.out.println(tableColData);
				System.out.println("Error while inserting data for file: " + fileName);
				e.printStackTrace();
				return false;
			}

			DataFormatter dataFormatter = new DataFormatter();
			// iterate through each row
			String rowValues;
			for (int r1 = r + 1; r1 <= sheet.getLastRowNum(); r1++) {
				row = sheet.getRow(r1);
				if (row == null)
					break;
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

	/**export the require file
	 * @param connection to database
	 * @param String name of the file to be export
	 * @return Workbook Workbook contains the information of the file
	 */
	public static Workbook exportData(TableModel data, String fileName) {
		String fileTable = fileName;

		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet(fileTable);

		int rowNum = 0;
		// add the header row
		Row headerRow = sheet.createRow(rowNum++);
		for (int i = 0; i < data.getColumnCount(); i++) {
			Cell headerCell = headerRow.createCell(i);
			headerCell.setCellValue(data.getColumnName(i));
		}
		// iterate through each row and write to file
		for (int rowIndex = 0; rowIndex<data.getRowCount();rowIndex++){
			Row newRow = sheet.createRow(rowNum++);
			for (int columnIndex = 0 ; columnIndex< data.getColumnCount(); columnIndex++){
				Object datum = data.getValueAt(rowIndex, columnIndex);
				if (datum == null)
					newRow.createCell(columnIndex).setCellValue("");
				else
					newRow.createCell(columnIndex).setCellValue(data.getValueAt(rowIndex, columnIndex).toString());
			}
		}
		return workbook;
	}
}
