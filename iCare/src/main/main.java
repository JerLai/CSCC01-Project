package main;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import main.java.com.icare.database.makeDatabase;
import main.java.com.icare.gui.GUI;

public class main {


	JPanel panel1 = new JPanel();
	JButton button = new JButton();
	JLabel label = new JLabel();

	public static void main(String[]args) throws SQLException{

		// check if db file exists, create one otherwise before connecting
		final File f = new File("iCare.db");
		if (!f.exists())
			makeDatabase.initialize();
		Connection connection = databaseConnector.testConnection();

		// start up the first GUI panel
		SwingUtilities.invokeLater(new Runnable(){
			@SuppressWarnings("unused")
			public void run(){
				JFrame frame = new GUI("iCare", connection);
			}
		});
		System.out.println("Finished");
	}
}
