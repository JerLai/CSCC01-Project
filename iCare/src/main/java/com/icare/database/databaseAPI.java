package main.java.com.icare.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import main.java.com.icare.accounts.User;
import main.java.com.icare.accounts.UserFactory;
import main.java.com.icare.passwords.passwords;

public class databaseAPI{

	private static final String DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";


	public static boolean removeData(Connection connection, String table, String condition) throws SQLException{
		String sql = "DELETE FROM " + table + " WHERE " + condition + ";";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		return preparedStatement.execute();
	}

	public static void addColumn(Connection connection, String table, String name, String type) throws SQLException{
		for (String column: getTableColumnData(connection, table)){
			if (column.contains(name)){
				return;
			}
		}
		String sql = "ALTER TABLE " + table + " ADD COLUMN " + name + " " + type + ";";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.execute();
		preparedStatement.close();
	}

	public static void deleteTable(Connection connection, String table) throws SQLException{
		String sql = "DROP TABLE IF EXISTS " + table + ";"; 
		Statement Statement = connection.createStatement();
		Statement.executeUpdate(sql);
		Statement.close();
	}

	public static void createTable(Connection connection, String table, String columnData) throws SQLException{
		if (!columnData.contains("PRIMARY KEY"))
			columnData = "ID INTEGER PRIMARY KEY NOT NULL" + columnData;
		String sql = "CREATE TABLE IF NOT EXISTS " + table + "(" + columnData + ");"; 
		Statement Statement = connection.createStatement();
		Statement.executeUpdate(sql);
		Statement.close();
	}

	public static void renameTable(Connection connection, String table, String newName) throws SQLException{
		String sql = "ALTER TABLE " + table + " RENAME TO " + newName + ";";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.execute();
		preparedStatement.close();
	}

	public static void deleteColumn(Connection connection, String table, String name) throws SQLException{
		List<String> columns = databaseAPI.getTableColumnData(connection, table);
		if (!columns.isEmpty()){
			String columnData = "";
			for (String columnName:columns){
				if (!columnName.contains(name)){
					columnData +=  ", "+ columnName;
				}
			}
			columnData = columnData.substring(2);
			renameTable(connection, table, table + "_TEMP");
			createTable(connection, table, columnData);
			deleteTable(connection, table + "_TEMP");
		}
	}

	public static void updateData(Connection connection, String table, String columnToValue, String condition) throws SQLException{
		String sql = "UPDATE " + table + " SET " + columnToValue + " WHERE " + condition + ";";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.executeUpdate();
		preparedStatement.close();
	}

	public static void insertData(Connection connection, String destination, String attributes, String values) throws SQLException{
		String sql = "INSERT INTO " + destination + "(" + attributes + ")" + " VALUES(" + values + ");";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.execute();
		preparedStatement.close();
	}

	public static void insertUser(Connection connection, String username, String password, String firstName, String lastName, String accountType) throws SQLException {
		ResultSet results = getData(connection, "ID, firstName, lastName, accountType", "Login",
				"username = '" + username + "'");
		if (!results.next()){
			String sql = "INSERT INTO Login(username, firstName, lastName, accountType) VALUES(?,?,?,?);";
			try {

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, username);
				preparedStatement.setString(2, firstName);
				preparedStatement.setString(3, lastName);
				preparedStatement.setString(4, accountType);
				int id = 0;
				if (preparedStatement.executeUpdate()>0){
					ResultSet uniqueKey = preparedStatement.getGeneratedKeys();
					if (uniqueKey.next()) {
						id = uniqueKey.getInt(1);
						accountPasswordHelper(connection, id, password);
					}
				}
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void accountPasswordHelper(Connection connection, int id, String password){
		String sql = "Update Login set password = ? where ID = ?;";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, passwords.passwordHash(password));
			preparedStatement.setInt(2, id);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected static boolean checkPassword(Connection connection, String username, String password) throws SQLException {
		String sql = "SELECT password FROM Login WHERE Username = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, username);
		ResultSet results = preparedStatement.executeQuery();
		preparedStatement.close();
		return results.getString("password").equals(passwords.passwordHash(password));

	}

	public static ResultSet getData(Connection connection, String select, String from, String condition) throws SQLException{
		String sql = "SELECT " + select + " FROM " + from +" WHERE " +condition;
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet results = preparedStatement.executeQuery();
		preparedStatement.close();
		return results;
	}

	public static User login(Connection connection, String username, String password) throws SQLException{
		UserFactory AccountCreator = new UserFactory();
		User Account = null;
		if (checkPassword(connection, username, password)){
			ResultSet results = getData(connection, "ID, firstName, lastName, accountType", "Login",
					"username = '" + username + "'");
			int ID = Integer.parseInt(results.getString("ID"));
			String firstName = results.getString("firstName");
			String lastName = results.getString("lastName");
			String accountType = results.getString("accountType");
			Account = AccountCreator.getUser(username, firstName, lastName, ID, accountType);
		}
		return Account;
	}

	public static LinkedHashMap<String, ArrayList<String>> importData(String fileName) {
		BufferedReader csvReader = null;
		LinkedHashMap <String, ArrayList<String>> fileData = new LinkedHashMap <String, ArrayList<String>>();

		try {
			String headers = null;
			String line = null;
			csvReader = new BufferedReader(new FileReader("resources/" + fileName));

			// read the first line to get the column headers
			headers = csvReader.readLine();

			// split the column headers
			String[] columnHeaders = headers.split(DELIMITER, -1);

			// add column headers as keys to fileData hashmap
			for (String header : columnHeaders) {
				fileData.put(header, new ArrayList<String>());
			}

			// go through each line
			while ((line = csvReader.readLine()) != null) {
				// split each line
				String[] lineData = line.split(DELIMITER, -1);
				// go through each piece of data and add to relevant column header's list
				for (int i = 0; i < lineData.length; i++) {
					ArrayList <String> columnData = fileData.get(columnHeaders[i]);
					columnData.add(lineData[i]);
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

		return fileData;
	}

	public static void exportData(LinkedHashMap<String, ArrayList<String>> fileData, String fileName) {
		FileWriter csvWriter = null;

		try {
			csvWriter = new FileWriter("resources/" + fileName);

			// add column headers as first line in csv file
			int i = 0;
			for (String header : fileData.keySet()) {
				csvWriter.append(header);
				if (i < (fileData.keySet().size() - 1)) {
					csvWriter.append(DELIMITER);
				}
				i++;
			}
			// add new line after column headers
			csvWriter.append(NEW_LINE_SEPARATOR);

			// iterate through each key-value pairing and write to file
			for (int j = 0; j < fileData.keySet().size(); j++) {
				int k = 0;
				for (String header : fileData.keySet()) {
					csvWriter.append(fileData.get(header).get(j));
					k++;
					if (k < (fileData.keySet().size())) {
						csvWriter.append(DELIMITER);
					}
				}
				if (j < (fileData.keySet().size() - 1)) {
					csvWriter.append(NEW_LINE_SEPARATOR);
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

	public static List<String> getTableColumnData(Connection connection, String table) throws SQLException {
		ArrayList<String> columns = new ArrayList<String>();
		String sql = "pragma table_info(" + table + ");";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet results = preparedStatement.executeQuery();
		String columnData;
		while (results.next()) {
			columnData = results.getString(2) + " " + results.getString(3);
			if (results.getString(6).equals("1"))
				columnData += " PRIMARY KEY";
			if (results.getString(4).equals("1"))
				columnData += " NOT NULL";
			columns.add(columnData);
		}
		results.close();
		return columns;
	}

}
