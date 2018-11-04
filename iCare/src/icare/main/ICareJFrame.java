package icare.main;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import icare.gui.AppMainJPanel;
import icare.gui.LoginPanel;
import icare.gui.Screen;
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
	public ICareJFrame () {

		super ("Placeholder");

		loginPanel = new LoginPanel(PANEL_SIZE);
		loginPanel.setOpaque(true);
		currPanel = loginPanel;
		this.setContentPane(currPanel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.pack();
	}

	public static void main(String[] args)
	{
		ICareJFrame instance = new ICareJFrame();
		instance.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		this.currPanel.actionPerformed(event);
	}
}
