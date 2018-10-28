package main.java.com.icare.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import main.java.com.icare.passwords.passwords;

public class databaseAPI {


	public static boolean insertUser(Connection connection, String username, String password, String firstName, String lastName, String accountType) {

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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private static boolean accountPasswordHelper(Connection connection, int id, String password){
		String sql = "Update Login set password = ? where ID = ?;";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, passwords.passwordHash(password));
			preparedStatement.setInt(2, id);
			preparedStatement.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
