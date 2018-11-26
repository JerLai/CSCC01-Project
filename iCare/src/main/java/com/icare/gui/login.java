package main.java.com.icare.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import main.java.com.icare.accounts.User;
import main.java.com.icare.database.databaseAPI;

public class login extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GridBagConstraints gbc;

	/**
	 * Sets up Login GUI panel
	 * @param connection the connection to db
	 * @param parent the parent GUI panel
	 */
	public login(Connection connection, GUI parent){

		this.setLayout(parent.getLayout());
		Dimension defaultSize = parent.getDefaultSize();
		gbc = parent.getGBC();
		
		JButton button = new JButton("Login");
		JTextField username = new JTextField("John");
		JTextField password = new JPasswordField("password");
		JLabel login = new JLabel("Login");
		login.setText("Login");
		login.setPreferredSize(defaultSize);
		username.setPreferredSize(defaultSize);
		password.setPreferredSize(defaultSize);
		button.setPreferredSize(defaultSize);
		addElement(login, 0, 1);
		addElement(username, 0, 2);
		addElement(password, 0, 3);
		addElement(button, 0, 4);

		username.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				username.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (username.getText().equals(""))
					username.setText("Username");
			}

		});

		password.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				password.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (password.getText().equals("")){
					password.setText("Password");
				}
			}

		});

		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					User userSession = databaseAPI.login(connection, username.getText(), password.getText());
					password.setText("");
					if (userSession != null){
						System.out.println("Login Successful as: " + userSession.getUsername());
						parent.next(new mainMenu(connection, userSession, parent));
						//mainMenu(userSession);
					} else{
						login.setText("Login Unsuccessful, Please Try Again");
						System.out.println("Login Unsuccessful, Please Try Again");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});

	}


	/**
	 * Adds element to GUI panel
	 * @param element the element to be added
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	private void addElement(JComponent element, int x, int y){
		gbc.gridx = x;
		gbc.gridy = y;
		this.add(element, gbc);
		element.setVisible(true);
		repaint();
	}

}
