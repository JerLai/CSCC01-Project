package icare.gui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public interface Screen {

	/**
	 * Updates this Screen for one frame
	 * @param deltaTimeMilliseconds the time elapsed since the last frame
	 */
	public void update(int deltaTimeMilliseconds);

	/**
	 * Paints this screen on the given context
	 * @param g the Graphics context
	 */
	public void paint(Graphics g);

	/**
	 * Tells the screen to respond to a mouse click
	 * @param event the object containing the click's information
	 */
	public void mouseClicked(MouseEvent event);

	/**
	 * Tells the screen to respond to mouse motion
	 * @param event the object containing the motion information
	 */
	public void mouseMoved(MouseEvent event);

	/**
	 * Tells the screen to respond to an ActionEvent
	 * @param event the specific ActionEvent
	 */
	public void actionPerformed(ActionEvent event);
}
