package main;

import java.sql.Connection;

import main.java.com.icare.database.databaseDriver;
import main.java.com.icare.database.makeDatabase;
import main.java.com.icare.database.databaseAPI;
public class main {

	public static void main(String[]args){
		//makeDatabase.initialize();
		Connection connection = databaseConnector.testConnection();
		//databaseAPI.insertUser(connection, username, password, firstName, lastName, accountType)
	}


}
