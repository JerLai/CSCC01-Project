package main;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import main.java.com.icare.database.makeDatabase;
import main.java.com.icare.accounts.User;
import main.java.com.icare.database.databaseAPI;
import main.java.com.icare.database.databaseSession;
public class main {

	public static void main(String[]args) throws SQLException{
		final File f = new File("iCare.db");
		if (!f.exists())
			makeDatabase.initialize();
		Connection connection = databaseConnector.testConnection();
		//databaseAPI.insertUser(connection, "Kyle", "password", "J", "H", "receptionist");
		//databaseAPI.insertData(connection, "Data", "Sample1, Sample2, Sample3", "'Ha','HaHA','AHAHAHa'");
		//databaseAPI.updateData(connection, "Data", "Sample1 = 'NO!'", "ID = 1");
		//databaseAPI.addColumn(connection, "Data", "TestColumn", "char(20)");
		//not working
		//databaseAPI.removeData(connection, "Data", "Sample1 = 'Ha'");
		//databaseAPI.deleteTable(connection, "Data_TEMP");
		//databaseAPI.deleteColumn(connection, "Data", "TestColumn");
		//databaseAPI.deleteTable(connection, "DUP");
		databaseSession.createTempTable(connection, "Data2", databaseSession.sourceQuery("Data", "sample1, sample3"));
		databaseSession.queryData(connection, "Data2", "*");
		System.out.println("Finished");
	}


}
