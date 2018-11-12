package main;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import main.java.com.icare.database.makeDatabase;
import main.java.com.icare.gui.GUI;
import main.java.com.icare.accounts.User;
import main.java.com.icare.database.DatabaseIO;
import main.java.com.icare.database.databaseAPI;
import main.java.com.icare.database.databaseSession;

public class main {


	JPanel panel1 = new JPanel();
	JButton button = new JButton();
	JLabel label = new JLabel();

	public static void main(String[]args) throws SQLException{

		final File f = new File("iCare.db");
		if (!f.exists())
			makeDatabase.initialize();
		Connection connection = databaseConnector.testConnection();

		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				JFrame frame = new GUI("iCare", connection);
			}
		});
		//databaseAPI.insertData(connection, "Data", "Sample1, Sample2, Sample3", "'Ha','HaHA','AHAHAHa'");
		//databaseAPI.updateData(connection, "Data", "Sample1 = 'NO!'", "ID = 1");
		//databaseAPI.addColumn(connection, "Data", "TestColumn", "char(20)");
		//not working
		//databaseAPI.removeData(connection, "Data", "Sample1 = 'Ha'");
		//databaseAPI.deleteTable(connection, "Data_TEMP");
		//databaseAPI.deleteColumn(connection, "Data", "TestColumn");
		//databaseAPI.deleteTable(connection, "DUP");
		//DatabaseIO.importData(connection, "csvTest2.csv");
		//DatabaseIO.exportData(connection, "csvTest3.csv", "csvTest2");
		//String querySource = databaseSession.sourceQuery("Data", "ID, sample1, sample3");
		//String queryTemporary = databaseSession.sourceQuery("Data2", "ID, sample1, sample3");
		//exec = databaseSession.filterQuery(exec, "ID = 2");
		//querySource = databaseSession.sortQuery(querySource, "ID DESC");
		//queryTemporary = databaseSession.sortQuery(queryTemporary, "ID DESC");
		//databaseSession.createTempTable(connection, "Data2", querySource);
		//System.out.println(String.format(databaseSession.queryData(connection, querySource)));
		//System.out.println(String.format(databaseSession.queryData(connection, queryTemporary)));

		// import testing
		// adding .xlsx file for first time
		//File file = new File("resources/iCARE_template.xlsx");
		//DatabaseIO.importData(connection, file);
		// adding same named file again, moves existing filename table data to filename_old table and "overwrites data"
		//file = new File("resources/iCARE_template.xlsx");
		//DatabaseIO.importData(connection, file);
		// adding .xls file for first time
		//file = new File("resources/sample1.xls");
		//DatabaseIO.importData(connection, file);
		// adding same named file again, following same db table changes as above for .xlsx files
		//file = new File("resources/sample1.xls");
		//DatabaseIO.importData(connection, file);
		//try to expot sample1
		//File file = DatabaseIO.exportData(connection, "sample1.xls");
		//try to export iCare template
		//file = DatabaseIO.exportData(connection, "iCARE_template.xlsx");
		System.out.println("Finished");
	}
}
