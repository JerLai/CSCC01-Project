package main.java.com.icare.gui;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JFrame;
//TODO: CHANGE IMPLEMENTATION FROM USING SCREEN TO USING SWAPPABLE JPANELS
/**
 * Represents Frame for our Data Management System. Includes methods to construct the visible program
 * Contains code to run the program
 * @author Jeremy Lai
 * @version 0, 20 October 2018
 *
 */
public class ICareJFrame extends JFrame implements ActionListener{

	private static final long serialVersionUID = 8060444441550607315L;
	static final Dimension PANEL_SIZE = new Dimension(1024, 768);
	private Screen currPanel;
	private LoginPanel loginPanel;
	private FormPanel formPanel;

	/**
	 * Constructs the Application
	 */
	public ICareJFrame (Connection connection) {

		super ("iCare Data Management");
		Screen.registerConnection(connection);
		loginPanel = new LoginPanel(this, PANEL_SIZE);
		loginPanel.setOpaque(true);
		loginPanel.setLayout(null);
		currPanel = loginPanel;
		this.setContentPane(loginPanel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.pack();
	}

	public void switchToFormScreen() {
		loginPanel.setVisible(false);
		formPanel = new FormPanel(this, PANEL_SIZE);
		this.currPanel = formPanel;
		this.setContentPane(formPanel);
		this.pack();
	}

	public void actionPerformed(ActionEvent event) {
		this.currPanel.actionPerformed(event);
	}

}
