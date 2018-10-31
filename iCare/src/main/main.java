package main;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import main.java.com.icare.database.makeDatabase;
import main.java.com.icare.accounts.User;
import main.java.com.icare.database.databaseAPI;
public class main {

	public static void main(String[]args) throws SQLException{
		final File f = new File("iCare.db");
		if (!f.exists())
			makeDatabase.initialize();
		Connection connection = databaseConnector.testConnection();
		//databaseAPI.insertUser(connection, "John", "password", "J", "H", "receptionist");
		User u = databaseAPI.login(connection, "John", "password");
		System.out.println(u.ID);
	}


}
