package main;

import java.sql.Connection;

import main.java.com.icare.database.databaseDriver;

public class databaseConnector extends databaseDriver{
	/**
	 * Connects database
	 * @return the connection
	 */
	protected static Connection testConnection(){
		return databaseDriver.connectDataBase();
	}
}
