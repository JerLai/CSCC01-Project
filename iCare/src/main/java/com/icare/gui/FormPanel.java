package main.java.com.icare.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class FormPanel extends Screen{

	private ICareJFrame parentFrame;
	private static final long serialVersionUID = -6182799282494130258L;
	private JTextField formFields1;
	private JTextField formFields2;
	private JTextField formFields3;
	private JLabel formLabels1;
	private JLabel formLabels2;
	private JLabel formLabels3;
	private static String SUBMIT = "submit";
	private JButton submit;

	public FormPanel(ICareJFrame parentFrame, Dimension size) {
		//this.setLayout(null);
		this.parentFrame = parentFrame;
		this.setPreferredSize(new Dimension(size));
		generateComponents();
	}

	public void generateComponents() {
		//Dimension size = submit.getPreferredSize();
		//submit.setBounds(470, 700, size.width, size.height);
		formLabels1 = new JLabel("Unique Identifier:");
		formLabels2 = new JLabel("Unique Identifier Value:");
		formLabels3 = new JLabel("Date of Birth (YYYY-MM-DD)");

		formFields1 = new JTextField(10);
		formFields2 = new JTextField(10);
		formFields3 = new JTextField(10);

		formLabels1.setLabelFor(formFields1);
		formLabels2.setLabelFor(formFields2);
		formLabels3.setLabelFor(formFields3);
		this.add(formLabels1);
		this.add(formFields1);
		this.add(formLabels2);
		this.add(formFields2);
		this.add(formLabels3);
		this.add(formFields3);
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
		System.out.println(formFields1.getText());
		System.out.println(formFields2.getText());
		System.out.println(formFields3.getText());

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
