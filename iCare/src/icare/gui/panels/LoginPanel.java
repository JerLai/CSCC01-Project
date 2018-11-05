package icare.gui.panels;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Arrays;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import icare.gui.Screen;
import icare.main.ICareJFrame;

public class LoginPanel extends Screen{

	private ICareJFrame parentFrame;
	private static final long serialVersionUID = -3887000510193812095L;

	private static String LOGIN = "login";
	private JButton logIn;

	private static final int userColLength = 10;
	JLabel userLabel;
	private JTextField userField;
	private static String tempUser = "user";

	private static final int passwordColLength = 10;
	JLabel passLabel;
	private JPasswordField passwordField;

	private static char[] tempPass = { 't', 'e', 'm', 'p'};

	public LoginPanel (ICareJFrame parentFrame, Dimension size) {
		this.parentFrame = parentFrame;
		this.setPreferredSize(new Dimension(size));
		generateButtons();
		generateLoginInput();
	}

	public void generateButtons() {

		// Create button and make it so that box doesn't appear around text
		logIn = new JButton("Log In");
		logIn.setFocusable(false);

		// Center the text in button
		logIn.setVerticalTextPosition(AbstractButton.CENTER);
		logIn.setHorizontalTextPosition(AbstractButton.CENTER);

		logIn.setActionCommand(LOGIN);

		// Listen for actions on this button
		logIn.addActionListener(this);
		this.add(logIn);
	}

	/**
	 * Sets up the Password Field for input
	 */
	private void generateLoginInput() {

		// Username Field
		userField = new JTextField(userColLength);
		userLabel = new JLabel("Username: ");
		userLabel.setLabelFor(userField);
		this.add(userLabel);
		this.add(userField);

		// Password Field
		passwordField = new JPasswordField(passwordColLength);
		passLabel = new JLabel("Password: ");
		passLabel.setLabelFor(passwordField);
		this.add(passLabel);
		this.add(passwordField);
	}

	/**
	 * Checks to see if received password matches corresponding correct password
	 * @param input the password given from user
	 * @return if passwords match user records
	 */
	private static boolean isPasswordCorrect(char[] input) {
		// TODO modify when actual account system works
		boolean isCorrect = true;

		if (input.length != tempPass.length) {
			isCorrect = false;
		}
		else {
			isCorrect = Arrays.equals(input, tempPass);
		}
		// TODO: better security
		//Arrays.fill(tempPass, '0');
		return isCorrect;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		if (action.equals(LOGIN)) {
			char[] inputPass = passwordField.getPassword();
			String inputUser = userField.getText();
			// Check if user and pass match account records
			// TODO: RIGOROUS CHECK OF MATCHING CREDENTIALS, PROOF OF CONCEPT FOR NOW
			// Idea: send the strings to database, returns maybe HashMap of <AccType, Data> or null if not valid
			if (isPasswordCorrect(inputPass) && inputUser.equals(tempUser)) {
				// TODO: Re-route to Account Screen rather than Form Screen in later implementations
				this.parentFrame.switchToFormScreen();
			}
			Arrays.fill(inputPass, '0');
		}
	}
}
