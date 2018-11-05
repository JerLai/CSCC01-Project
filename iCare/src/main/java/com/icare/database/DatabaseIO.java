package main.java.com.icare.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DatabaseIO {

	private static final String DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";

	public static void importData(Connection connection, String fileName) {
		String fileTable = fileName.substring(0, fileName.indexOf("."));
		BufferedReader csvReader = null;

		try {
			String headers = null;
			String headers2 = null;
			String values = null;
			String tableColData = "ID INTEGER PRIMARY KEY NOT NULL";
			csvReader = new BufferedReader(new FileReader("resources/" + fileName));

			ArrayList<String> tableExistCols = new ArrayList<String>();
			// check if table exists
			try {
				tableExistCols = (ArrayList<String>) databaseAPI.getTableColumnData(connection, fileTable);
			} catch (Exception e) {
				System.out.println("Error while checking if table exists for .csv file: " + fileName);
				e.printStackTrace();
			}

			ArrayList<String> oldTableExistCols = new ArrayList<String>();
			// delete .old table if exists and rename existing table to .old if needed
			try {
				oldTableExistCols = (ArrayList<String>) databaseAPI.getTableColumnData(connection, fileTable + "_old");
			} catch (Exception e) {
				System.out.println("Error while checking if _old table exists for .csv file: " + fileName);
				e.printStackTrace();
			}
			if (oldTableExistCols.size() > 0) {
				try {
					databaseAPI.deleteTable(connection, fileTable + "_old");
				} catch (Exception e) {
					System.out.println("Error while deleting _old table for .csv file: " + fileName);
					e.printStackTrace();
				}
			}
			if (tableExistCols.size() > 0) {
				try {
					databaseAPI.deleteTable(connection, fileTable + "_old");
					databaseAPI.renameTable(connection, fileTable, fileTable + "_old");
				} catch (Exception e) {
					System.out.println("Error while saving _old table for .csv file: " + fileName);
					e.printStackTrace();
				}
			}

			// read the first line to get the column headers
			headers = csvReader.readLine();
			headers2 = headers;

			// split the column headers
			String[] columnHeaders = headers2.split(DELIMITER, -1);

			// add column headers
			for (String header : columnHeaders) {
				tableColData += ", ";
				tableColData += header;
				tableColData += " char(255)";
			}
			try {
				databaseAPI.createTable(connection, fileTable, tableColData);
			} catch (Exception e) {
				System.out.println("Error while inserting data for .csv file: " + fileName);
				e.printStackTrace();
			}

			// go through each line
			while ((values = csvReader.readLine()) != null) {
				// go through each line and add to table
				try {
					String[] valuesArray = values.split(",");
					values = "";
					for (String entry: valuesArray){
						values += ", '" + entry + "'";
					}
					values = values.substring(2);
					databaseAPI.insertData(connection, fileTable, headers, values);
				} catch (Exception e) {
					System.out.println("Error while inserting data for .csv file: " + fileName);
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			System.out.println("Error while reading .csv file: " + fileName);
			e.printStackTrace();
		} finally {
			try {
				csvReader.close();
			} catch (IOException e) {
				System.out.println("Error while closing .csv file: " + fileName);
				e.printStackTrace();
			}
		}
	}

	public static void exportData(Connection connection, String fileName) {
		FileWriter csvWriter = null;
		String fileTable = fileName.substring(0, fileName.indexOf("."));

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
