package icare.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public abstract class Screen extends JPanel implements ActionListener{

	private static final long serialVersionUID = -3166538406297696707L;

	public abstract void generateButtons();

	/**
	 * Tells the screen to respond to an ActionEvent
	 * @param event the specific ActionEvent
	 */
	public abstract void actionPerformed(ActionEvent event);
}
