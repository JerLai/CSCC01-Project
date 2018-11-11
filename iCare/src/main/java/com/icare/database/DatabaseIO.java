package main.java.com.icare.database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

public class DatabaseIO {

	private static final String DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";

	public static void importData(Connection connection, File file) throws EncryptedDocumentException, InvalidFormatException, IOException {
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
		}
        // first sheet
        Sheet sheet = workbook.getSheetAt(0);

		try {
			String headers = "";
			String tableColData = "ID INTEGER PRIMARY KEY NOT NULL";

			ArrayList<String> tableExistCols = new ArrayList<String>();
			// check if table exists
			try {
				tableExistCols = (ArrayList<String>) databaseAPI.getTableColumnData(connection, fileTable);
			} catch (Exception e) {
				System.out.println("Error while checking if table exists for file: " + fileName);
				e.printStackTrace();
			}

			ArrayList<String> oldTableExistCols = new ArrayList<String>();
			// delete .old table if exists and rename existing table to .old if needed
			try {
				oldTableExistCols = (ArrayList<String>) databaseAPI.getTableColumnData(connection, fileTable + "_old");
			} catch (Exception e) {
				System.out.println("Error while checking if _old table exists for file: " + fileName);
				e.printStackTrace();
			}
			if (oldTableExistCols.size() > 0) {
				try {
					databaseAPI.deleteTable(connection, fileTable + "_old");
				} catch (Exception e) {
					System.out.println("Error while deleting _old table for file: " + fileName);
					e.printStackTrace();
				}
			}
			if (tableExistCols.size() > 0) {
				try {
					databaseAPI.deleteTable(connection, fileTable + "_old");
					databaseAPI.renameTable(connection, fileTable, fileTable + "_old");
				} catch (Exception e) {
					System.out.println("Error while saving _old table for file: " + fileName);
					e.printStackTrace();
				}
			}

			// iterate through 3rd row to get column headers
			Iterator<Row> rowIterator = sheet.rowIterator();
			Row row = null;
			Cell cell;
			if (rowIterator.hasNext()) {
				row = rowIterator.next();
			}
			if (rowIterator.hasNext()) {
				row = rowIterator.next();
			}
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
                cell = cellIterator.next();
                // determine column headers from 3rd row
                tableColData += ", ";
                String cellValue = cell.getRichStringCellValue().getString();
                tableColData += cellValue;
                tableColData += " char(255)";
                headers += cellValue;
                headers += ", ";
			}
			headers = headers.substring(0, headers.length()-2);

			// create table with corresponding columns
			try {
				databaseAPI.createTable(connection, fileTable, tableColData);
			} catch (Exception e) {
				System.out.println("Error while inserting data for file: " + fileName);
				e.printStackTrace();
			}

			DataFormatter dataFormatter = new DataFormatter();
			// iterate through each row
			String rowValues;
			while (rowIterator.hasNext()) {
	            row = rowIterator.next();
	            rowValues = "";
	            // iterate through each cell in the row
	            cellIterator = row.cellIterator();
	            while (cellIterator.hasNext()) {
	                cell = cellIterator.next();
	                String cellValue = dataFormatter.formatCellValue(cell);
	                rowValues += ", '" + cellValue + "'";
	            }
	            rowValues = rowValues.substring(2);
	            // add into table
	            try {
					databaseAPI.insertData(connection, fileTable, headers, rowValues);
				} catch (Exception e) {
					System.out.println("Error while inserting data for file: " + fileName);
					e.printStackTrace();
				}
	        }
		} catch (Exception e) {
			System.out.println("Error while reading file: " + fileName);
			e.printStackTrace();
		} finally {
			try {
				// close workbook
				workbook.close();
			} catch (IOException e) {
				System.out.println("Error while closing file: " + fileName);
				e.printStackTrace();
			}
		}
	}

	public static void exportData(Connection connection, String fileName) {
		String fileTable = fileName.substring(0, fileName.indexOf("."));
		exportData(connection, fileName, fileTable);
	}

	public static void exportData(Connection connection, String fileName, String fileTable) {
		FileWriter csvWriter = null;

		try {
			csvWriter = new FileWriter("resources/" + fileName);

			ArrayList<String> tableCols = new ArrayList<String>();
			// get columnData for table
			try {
				tableCols = (ArrayList<String>) databaseAPI.getTableColumnData(connection, fileTable);
			} catch (Exception e) {
				System.out.println("Error while checking table columns for .csv file: " + fileName);
				e.printStackTrace();
			}

			// add column headers as first line in csv file
			int i = 0;
			for (String header : tableCols) {
				if (i > 0) {
					csvWriter.append(header.substring(0, header.indexOf(" ")));
					if (i < (tableCols.size() - 1)) {
						csvWriter.append(DELIMITER);
					}
				}
				i++;
			}
			// add new line after column headers
			csvWriter.append(NEW_LINE_SEPARATOR);

			// get data from table
			ArrayList<String> dataArray = new ArrayList<String>();
			String sql = "SELECT * FROM " + fileTable;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet results = preparedStatement.executeQuery();
			int numCols = results.getMetaData().getColumnCount();
            while(results.next()) {
                StringBuilder sb = new StringBuilder();
                for (int m = 2; m <= numCols; m++) {
                    sb.append(String.format(String.valueOf(results.getString(m))));
                    if (m < numCols) {
                    	sb.append(DELIMITER);
                    }
                }
                dataArray.add(sb.toString());
            }
			preparedStatement.close();

			// iterate through each row and write to file
			int n = 0;
			for (String row : dataArray) {
	            csvWriter.write(row);
	            n++;
	            if (n < numCols - 1) {
	            	csvWriter.write(NEW_LINE_SEPARATOR);
	            }
	        }
		} catch (Exception e) {
			System.out.println("Error while writing to .csv file: " + fileName);
			e.printStackTrace();
		} finally {
			try {
				csvWriter.close();
			} catch (IOException e) {
				System.out.println("Error while closing .csv file: " + fileName);
				e.printStackTrace();
			}
		}
	}
}
