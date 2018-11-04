package icare.gui.panels;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractButton;
import javax.swing.JButton;

import icare.gui.Screen;

public class FormPanel extends Screen{

	private static final long serialVersionUID = -6182799282494130258L;
	
	private static String SUBMIT = "submit";
	private JButton submit;

	public FormPanel(Dimension size) {
		this.setPreferredSize(new Dimension(size));
		generateButtons();
	}

	public void generateButtons() {
		// TODO: For now only make 1 button
		// Create button and make it so that box doesn't appear around text
		submit = new JButton("Submit");
		submit.setFocusable(false);

		// Center the text in button
		submit.setVerticalTextPosition(AbstractButton.CENTER);
		submit.setHorizontalTextPosition(AbstractButton.CENTER);

		submit.setActionCommand(SUBMIT);

		// Listen for actions on this button
		submit.addActionListener(this);
		this.add(submit);
		
	}
	/**
	 * TEMPORARY METHOD TO DEMONSTRATE THE FUNCTION
	 * TODO: Remove later once database is rectified
	 */
	private void printToConsole() {
		System.out.println("Hi");
	}

	/**
	 * Submits info currently received to the appropriate account in the database
	 */
	private void saveToDatabase() {
		printToConsole();
	}

	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		if(action.equals(SUBMIT)) {
			saveToDatabase();
		}
		
	}

}
