package icare.main;


import javax.swing.JFrame;

import icare.gui.AppMainJPanel;
//TODO: CHANGE IMPLEMENTATION FROM USING SCREEN TO USING SWAPPABLE JPANELS
/**
 * Represents Frame for our Data Management System. Includes methods to construct the visible program
 * Contains code to run the program
 * @author Jeremy Lai
 * @version 0, 20 October 2018
 *
 */
public class AppMainJFrame extends JFrame{

	private static final long serialVersionUID = 8060444441550607315L;

	private AppMainJPanel mainJPanel;

	/**
	 * Constructs the Application
	 */
	public AppMainJFrame () {

		super ("Placeholder");

		mainJPanel = new AppMainJPanel();
		mainJPanel.setOpaque(true);
		this.setContentPane(mainJPanel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.pack();
	}

	public static void main(String[] args)
	{
		AppMainJFrame instance = new AppMainJFrame();
		instance.setVisible(true);
	}
}
