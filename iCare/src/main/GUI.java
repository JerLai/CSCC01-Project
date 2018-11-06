package main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import main.java.com.icare.accounts.User;
import main.java.com.icare.database.databaseAPI;

public class GUI extends JFrame{

	private Rectangle window;
	private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private Container container;
	private Connection connection;
	private ArrayList<JComponent> currentGUIElements = new ArrayList();

	public GUI(String header, Connection connection){
		super(header);
		this.connection = connection;
		this.setSize(screenSize.width*3/4, screenSize.height*3/4);
		this.setLocation(screenSize.width*1/8, screenSize.height*1/8);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		window = this.getBounds();
		container = this.getContentPane();
		container.setLayout(null);
		this.requestFocusInWindow();
		Login();
	}

	public void Login(){
		JButton button = new JButton("Login");
		JTextField username = new JTextField("Username");
		JTextField password = new JPasswordField("Password");
		button.setSize(100, 30);
		button.setBounds(window.width/2 - button.getWidth()/2, window.height/2, button.getWidth(), button.getHeight());
		username.setSize(300,30);
		username.setBounds(window.width/2 - username.getWidth()/2, window.height/2 - 70, username.getWidth(), username.getHeight());
		password.setSize(300,30);
		password.setBounds(window.width/2 - username.getWidth()/2, window.height/2 - 30, username.getWidth(), username.getHeight());
		currentGUIElements.add(button);
		currentGUIElements.add(username);
		currentGUIElements.add(password);

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
						for (JComponent element: currentGUIElements){
							element.setVisible(false);
							container.remove(element);
						}
						currentGUIElements.clear();
						mainMenu();
					} else{
						System.out.println("Login Unsuccessful, Please Try Again");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});

		button.setVisible(true);
		username.setVisible(true);
		password.setVisible(true);
		container.add(button);
		container.add(username);
		container.add(password);

	}

	public void mainMenu() throws SQLException{
		
	}
	
}
