package main.java.com.icare.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JPanel;

public abstract class Screen extends JPanel implements ActionListener{

	public static Connection connection;

	public static void registerConnection(Connection connection) {
		Screen.connection = connection;
	}

	private static final long serialVersionUID = -3166538406297696707L;

	public abstract void generateComponents();

	/**
	 * Tells the screen to respond to an ActionEvent
	 * @param event the specific ActionEvent
	 */
	public abstract void actionPerformed(ActionEvent event);
}
