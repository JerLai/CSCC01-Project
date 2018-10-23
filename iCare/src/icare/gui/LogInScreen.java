package icare.gui;

import javax.swing.*;
import javax.swing.AbstractButton;
import javax.swing.JButton;

import java.awt.event.*;
import java.awt.Graphics;
import java.util.Arrays;


public class LogInScreen implements Screen{

	private static String LOGIN = "login";
	private AppMainJPanel parentPanel;
	private JButton logIn;

	private static final int userColLength = 10;
	JLabel userLabel;
	private JTextField userField;
	private static String tempUser = "user";

	private static final int passwordColLength = 10;
	JLabel passLabel;
	private JPasswordField passwordField;
	private static char[] tempPass = { 't', 'e', 'm', 'p'};

	//TODO: INCLUDE CODE THAT DRAWS FROM ACCOUNT INFO DATABASE TO IMPLEMENT LOG IN

	public LogInScreen(AppMainJPanel parentPanel) {
		this.parentPanel = parentPanel;

		generateLoginInput();
		generateButtons();
	}

	private void generateButtons() {

		// Create button and make it so that box doesn't appear around text
		logIn = new JButton("Log In");
		logIn.setFocusable(false);

		// Center the text in button
		logIn.setVerticalTextPosition(AbstractButton.CENTER);
		logIn.setHorizontalTextPosition(AbstractButton.CENTER);

		
		// Have it recognize key input, enter to submit, 10 is the value for Enter Key
		//logIn.setMnemonic(10);
		logIn.setActionCommand(LOGIN);

		// Listen for actions on this button
		parentPanel.add(logIn);
		logIn.addActionListener(parentPanel);
		

	}

	/**
	 * Sets up the Password Field for input
	 */
	private void generateLoginInput() {

		// Username Field
		userField = new JTextField(userColLength);
		userLabel = new JLabel("Username: ");
		userLabel.setLabelFor(userField);
		parentPanel.add(userLabel);
		parentPanel.add(userField);

		// Password Field
		passwordField = new JPasswordField(passwordColLength);
		passLabel = new JLabel("Password: ");
		passLabel.setLabelFor(passwordField);
		parentPanel.add(passLabel);
		parentPanel.add(passwordField);
	}

	/**
	 * Removes elements to get Login Input, prevent memory leaks
	 */
	private void removeLoginComponents() {
		userField.setVisible(false);
		userLabel.setVisible(false);
		passwordField.setVisible(false);
		passLabel.setVisible(false);
		logIn.setVisible(false);
		parentPanel.remove(userField);
		parentPanel.remove(userLabel);
		parentPanel.remove(passwordField);
		parentPanel.remove(passLabel);
		parentPanel.remove(logIn);
		parentPanel.repaint();
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
	public void update(int deltaTimeMilliseconds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paint(Graphics g) {

		
	}

	/**
	 * Responds to mouse clicks
	 * @param event the mouse click to respond to
	 */
	public void mouseClicked(MouseEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Responds to action for Login
	 * @param event the event to respond to
	 * TODO: Implement account type selection
	 */
	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		if (action.equals(LOGIN)) {
			char[] inputPass = passwordField.getPassword();
			String inputUser = userField.getText();
			// Check if user and pass match account records
			// TODO: RIGOROUS CHECK OF MATCHING CREDENTIALS, PROOF OF CONCEPT FOR NOW
			// Idea: Pull username from database, see if matches, then check if password matches, ideally a HashMap?
			if (isPasswordCorrect(inputPass) && inputUser.equals(tempUser)) {
				// TODO: Re-route to Account Screen rather than Form Screen in later implementations
				this.parentPanel.switchToForm();
				//removeLoginComponents();
			}
			Arrays.fill(inputPass, '0');
		}
	}

}
