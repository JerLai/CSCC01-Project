package main.java.com.icare.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import main.java.com.icare.accounts.User;
import main.java.com.icare.database.databaseAPI;

/**
 * LoginPanel class, creates the Login Page for the software
 * Unused code snippets commented out for future implementation
 * @author Jeremy Lai
 *
 */
public class LoginPanel extends Screen{

	private ICareJFrame parentFrame;
	private static final long serialVersionUID = -3887000510193812095L;

	private static String LOGIN = "login";
	private JButton logIn;

	private static final int userColLength = 10;
	JLabel userLabel;
	private JTextField userField;

	private static final int passwordColLength = 10;
	JLabel passLabel;
	private JPasswordField passField;

	//private GridBagConstraints c = new GridBagConstraints();

	public LoginPanel (ICareJFrame parentFrame, Dimension size) {
		//TODO: Use LayoutManagers later to fix
		//this.setLayout(new GridBagLayout());
		this.setLayout(null);
		this.parentFrame = parentFrame;
		this.setPreferredSize(new Dimension(size));
		generateComponents();
		this.setVisible(true);
	}

	/**
	 * Sets up the Password Field for input
	 */
	private void generateLoginInput() {
		// Username Field
		userField = new JTextField(userColLength);
		userLabel = new JLabel("Username: ");
		userLabel.setLabelFor(userField);

		// Password Field
		passField = new JPasswordField(passwordColLength);
		passLabel = new JLabel("Password: ");
		passLabel.setLabelFor(passField);

		//c.gridx = 0;
		//c.gridy = 0;
		this.add(userLabel);
		//c.gridx = 1;
		this.add(userField);


		//c.weightx = 0.1;
		//c.gridx = 2;
		this.add(passLabel);
		//c.gridx = 4;
		this.add(passField);

		int startX = 400;
		int startY = 400;

		Dimension size = userLabel.getPreferredSize();
		int offsetX = size.width;
		userLabel.setBounds(startX, startY, size.width, size.height);
		size = userField.getPreferredSize();
		int offsetY = size.height + 10;
		userField.setBounds(startX + offsetX, startY, size.width, size.height);
		size = passLabel.getPreferredSize();
		passLabel.setBounds(startX, startY + offsetY, size.width, size.height);
		size = passField.getPreferredSize();
		passField.setBounds(startX + offsetX, startY + offsetY, size.width, size.height);
	}

	public void generateComponents() {
		generateLoginInput();
		// Create button and make it so that box doesn't appear around text
		logIn = new JButton("Log In");
		logIn.setFocusable(false);

		// Center the text in button
		logIn.setVerticalTextPosition(AbstractButton.CENTER);
		logIn.setHorizontalTextPosition(AbstractButton.CENTER);

		logIn.setActionCommand(LOGIN);

		// Listen for actions on this button
		logIn.addActionListener(this);
		//c.gridx = 1;
		//c.gridy = 1;log
		this.add(logIn);
		Dimension size = logIn.getPreferredSize();
		logIn.setBounds(465, 460, size.width, size.height);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		if (action.equals(LOGIN)) {
			String inputUser = userField.getText();
			@SuppressWarnings("deprecation")
			String inputPass = passField.getText();
			// Check if user and pass match account records
			User userCheck;
			try {
				userCheck = databaseAPI.login(connection, inputUser, inputPass);
				if (!(userCheck).equals(null)) {
					this.parentFrame.switchToFormScreen();
				}
				else {
					JOptionPane.showMessageDialog(parentFrame, "Invalid Username and/or Password.", "Login Error", JOptionPane.ERROR_MESSAGE);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(parentFrame, "Invalid Username and/or Password.", "Login Error", JOptionPane.ERROR_MESSAGE);
			}

		}
	}
}
