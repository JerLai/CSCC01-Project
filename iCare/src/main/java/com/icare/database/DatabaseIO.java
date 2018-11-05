package main.java.com.icare.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
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
				System.out.println("Error while checking if .old table exists for .csv file: " + fileName);
				e.printStackTrace();
			}
			if (oldTableExistCols.size() > 0) {
				try {
					databaseAPI.deleteTable(connection, fileTable + ".old");
				} catch (Exception e) {
					System.out.println("Error while deleting .old table for .csv file: " + fileName);
					e.printStackTrace();
				}
			}
			if (tableExistCols.size() > 0) {
				try {
					databaseAPI.deleteTable(connection, fileTable + "_old");
					databaseAPI.renameTable(connection, fileTable, fileTable + "_old");
				} catch (Exception e) {
					System.out.println("Error while saving .old table for .csv file: " + fileName);
					e.printStackTrace();
				}
			}

			// read the first line to get the column headers
			headers = csvReader.readLine();
			headers2 = headers;

			// split the column headers
			String[] columnHeaders = headers2.split(DELIMITER, -1);

			// add column headers as keys to fileData hashmap
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
}
