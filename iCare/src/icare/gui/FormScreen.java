package icare.gui;

import javax.swing.*;
import javax.swing.AbstractButton;
import javax.swing.JButton;

import java.awt.event.*;
import java.awt.Graphics;
import java.util.Arrays;

public class FormScreen implements Screen{

	private JButton submit;
	private static String SUBMIT = "submit";
	private AppMainJPanel parentPanel;
	private JTextField textFields;
	private JLabel labels;

	public FormScreen(AppMainJPanel parentPanel) {
		this.parentPanel = parentPanel;
		
		generateForm();
		generateButtons();
	}

	/**
	 * Generates all the form elements
	 * TODO: Create multiple form fields at once
	 */
	private void generateForm() {
		textFields = new JTextField(10);
		//textFields.setBounds(100, 20, 165, 25);

		labels = new JLabel("Field: ");
		//labels.setBounds(10, 20, 80, 25);
		
		labels.setLabelFor(textFields);
		parentPanel.add(labels);
		parentPanel.add(textFields);


	}

	private void generateButtons() {

		// Create button and make it so that box doesn't appear around text
		submit = new JButton("Submit");
		submit.setFocusable(false);

		// Center the text in button
		submit.setVerticalTextPosition(AbstractButton.CENTER);
		submit.setHorizontalTextPosition(AbstractButton.CENTER);
		submit.setBounds(500, 500, 40, 20);

		
		// Have it recognize key input, enter to submit, 10 is the value for Enter Key
		//logIn.setMnemonic(10);
		submit.setActionCommand(SUBMIT);

		// Listen for actions on this button
		
		parentPanel.add(submit);
		submit.addActionListener(parentPanel);
		

	}

	@Override
	public void update(int deltaTimeMilliseconds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		String action = event.getActionCommand();
		if (action.equals(SUBMIT));
		System.out.println(textFields.getText());
	}

}
