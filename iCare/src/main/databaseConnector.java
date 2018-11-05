package main;

import java.sql.Connection;

import main.java.com.icare.database.databaseDriver;

public class databaseConnector extends databaseDriver{
	protected static Connection testConnection(){
		return databaseDriver.connectDataBase();
	}
}
