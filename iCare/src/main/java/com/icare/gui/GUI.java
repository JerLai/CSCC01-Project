package main.java.com.icare.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.sql.Connection;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Rectangle window;
	private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private Container container;
	private LayoutManager layout;
	private GridBagConstraints gbc;
	private Insets defaultInsets = new Insets(10,10,10,10);
	private Dimension defaultSize;


	public GUI(String header, Connection connection){
		super(header);
		this.setSize(screenSize.width*3/4, screenSize.height*3/4);
		this.setLocation(screenSize.width*1/8, screenSize.height*1/8);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		window = this.getBounds();
		container = this.getContentPane();
		layout = new GridBagLayout();
		container.setLayout(layout);
		gbc = new GridBagConstraints();
		defaultSize = new Dimension(window.width*1/8, window.height*1/25);
		gbc.insets = defaultInsets;
		this.requestFocusInWindow();
		setContentPane(new login(connection, this));
	}

	public Dimension getDefaultSize(){
		return defaultSize;
	}
	
	public GridBagConstraints getGBC(){
		return this.gbc;
	}
	
	public LayoutManager getLayout(){
		return this.layout;
	}
	
	protected void next(JPanel panel){
		setContentPane(panel);
		validate();
		repaint();
	}
	
}
