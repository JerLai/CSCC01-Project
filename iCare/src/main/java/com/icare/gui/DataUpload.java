package main.java.com.icare.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.java.com.icare.accounts.User;
import main.java.com.icare.database.DatabaseIO;

/**
 * GUI display to upload files onto data management system
 * @author Jeremy Lai
 *
 */
public class DataUpload extends JPanel{

	private GridBagConstraints gbc;
	private static String[] fileTypes = {"iCare Excel"};
	private File file;

	private static String OPEN = "Open";
	private static String UPLOAD = "Upload";
	private static String MAINMENU = "Return to Menu";

	/**
	 * Constructor for the GUI to display a DataUploadPanel
	 * @param connection connection to the database of the system
	 * @param userSession the user that is logged in
	 * @param parent the parent JFrame
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DataUpload(Connection connection, User userSession, GUI parent) {
		this.setLayout(parent.getLayout());
		Dimension defaultSize = parent.getDefaultSize();
		gbc = parent.getGBC();
		Rectangle window = parent.getBounds();

		JButton openFile = new JButton (OPEN);
		JButton uploadFile = new JButton (UPLOAD);
		JComboBox typeSelector = new JComboBox(fileTypes);
		JButton mainMenu = new JButton(MAINMENU);

		// Generate the option to open a File
		openFile.setText("Open");
		openFile.setPreferredSize(defaultSize);
		openFile.setVisible(true);
		//openFile.setActionCommand(OPEN);

		// Generate the option to upload selected File
		uploadFile.setText("Upload");
		uploadFile.setPreferredSize(defaultSize);
		uploadFile.setVisible(true);
		//uploadFile.setActionCommand(UPLOAD);

		// Generate the option to return to main menu
		mainMenu.setText("Main Menu");
		mainMenu.setPreferredSize(defaultSize);
		mainMenu.setVisible(true);
		//mainMenu.setActionCommand(MAINMENU);

		// Currently only the Excel iCare templates
		typeSelector.setSelectedIndex(0);
		typeSelector.setPreferredSize(defaultSize);

		// Opens FileChooser to navigate file system
		openFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				//TODO: Change file filter based on combobox option using the .getSelectedItem or getAction on the combobox
				fileChooser.addChoosableFileFilter(new ExcelFilter());
				fileChooser.setAcceptAllFileFilterUsed(false);

				int returnVal = fileChooser.showDialog(parent, "Open");
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					file = fileChooser.getSelectedFile();
					JOptionPane.showMessageDialog(parent, "File is ready to be uploaded.", "File Primed", JOptionPane.PLAIN_MESSAGE);
				}
				//fileChooser.setSelectedFile(null);
			}
		});

		// Uploads currently set up file
		uploadFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean retVal = DatabaseIO.importData(connection, file);
				if (retVal) {
					JOptionPane.showMessageDialog(parent, "File is accepted.", "Upload Success", JOptionPane.PLAIN_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(parent, "Error Uploading File, make sure File isn't"
							+ "corrupted and/or mandatory fields filled", "Upload Failed", JOptionPane.ERROR_MESSAGE);
				}
				file = null;
			}
			
		});

		// Returns to main menu
		mainMenu.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					parent.next(new mainMenu(connection, userSession, parent));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		addElement(openFile, 0, 0);
		addElement(typeSelector, 1, 0);
		addElement(uploadFile, 0, 1);
		addElement(mainMenu, 0, 2);
	}

	/**
	 * Adds given JComponent to this DataUpload Panel
	 * @param element the component to add
	 * @param x horizontal position for the Layout of the Panel
	 * @param y vertical position for the Layout of the Panel
	 */
	private void addElement(JComponent element, int x, int y){
		gbc.gridx = x;
		gbc.gridy = y;
		this.add(element, gbc);
		element.setVisible(true);
		repaint();
	}

}
