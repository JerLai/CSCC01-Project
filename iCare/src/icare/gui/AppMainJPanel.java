package icare.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

public class AppMainJPanel extends JPanel implements MouseListener,
		MouseMotionListener, ActionListener{

	private static final long serialVersionUID = -8640204662402852255L;

	static final Dimension PANEL_SIZE = new Dimension(1024, 768);

	private Timer timer;
	private final int UPDATE_TIME = 1000 / 60; // Milliseconds
	private final int NANOSECONDS_TO_MILLISECONDS = 1000000;
	private long startTime, deltaTime;

	private LogInScreen loginScreen;
	private FormScreen formScreen;
	private Screen currentScreen;

	/**
	 * Constructor that creates all buttons and required
	 */
	public AppMainJPanel() {
		// Set up the panel stuff
		this.setPreferredSize(new Dimension(PANEL_SIZE));
		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		// Create Log In Screen
		this.loginScreen = new LogInScreen(this);
		this.formScreen = new FormScreen(this);
		this.currentScreen = loginScreen;

		// Set up the update timer
		timer = new Timer(UPDATE_TIME, this);
		timer.start();
		this.startTime = System.nanoTime();
		this.deltaTime = 0;
	}

	/**
	 * Sets the currently showing Screen to the given Screen
	 */
	public void setScreen(Screen newScreen) {
		if (newScreen == null)
			return;
		this.currentScreen = newScreen;
	}

	/**
	 * Sets the currently showing screen to the Login Screen
	 */
	public void returnToLogin() {
		setScreen(this.loginScreen);
	}

	/**
	 * Draws the currently showing Screen on the Panel
	 * @param g
	 */
	public void paintComponent (Graphics g) {
//		this.currentScreen.paint(g);
	}

	/**
	 * Responds to ActionEvents. If the sender is the update timer, updates the current Screen
	 * 
	 * @param event the ActionEvent to respond to
	 */
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == this.timer)
		{
			this.currentScreen.update((int) (this.deltaTime));
			//this.repaint();
			this.deltaTime = (System.nanoTime() - this.startTime)
					/ NANOSECONDS_TO_MILLISECONDS;
			this.startTime = System.nanoTime();
		}
		else {
			this.currentScreen.actionPerformed(event);
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Responds to mouse motion - forwards the mouse motion to the current Screen
	 * 
	 * @param event the Mouse motion to respond to
	 */
	public void mouseMoved(MouseEvent event) {
		this.currentScreen.mouseMoved(event);
	}

	/**
	 * Responds to mouse clicks - forwards the mouse click to the current Screen
	 * 
	 * @param event the Mouse click to respond to
	 */
	public void mouseClicked(MouseEvent event) {
		this.currentScreen.mouseClicked(event);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void switchToForm() {
		// TODO Auto-generated method stub
		setScreen(this.formScreen);
		
	}

}
