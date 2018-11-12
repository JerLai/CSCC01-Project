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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.java.com.icare.accounts.User;
import main.java.com.icare.database.DatabaseIO;

public class DataDownload extends JPanel{
	private GridBagConstraints gbc;
	private File file;

	private JTextField fileName;
	private static String OPEN = "Open";
	private static String DOWNLOAD = "Download";
	private static String MAINMENU = "Return to Menu";

	/**
	 * Constructor for the GUI to display a DataUploadPanel
	 * @param connection connection to the database of the system
	 * @param userSession the user that is logged in
	 * @param parent the parent JFrame
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DataDownload(Connection connection, User userSession, GUI parent) {
		this.setLayout(parent.getLayout());
		Dimension defaultSize = parent.getDefaultSize();
		gbc = parent.getGBC();
		Rectangle window = parent.getBounds();
		fileName = new JTextField("");

		JLabel openInstruction = new JLabel("Type the name of the file you would like to download");
		JButton openFile = new JButton (OPEN);
		JButton downloadFile = new JButton (DOWNLOAD);
		JButton mainMenu = new JButton(MAINMENU);

		openInstruction.setPreferredSize(defaultSize);
		openInstruction.setVisible(true);
		// Generate the option to open a File
		openFile.setText("Open");
		openFile.setPreferredSize(defaultSize);
		openFile.setVisible(true);
		//openFile.setActionCommand(OPEN);

		// Generate the option to download selected File
		downloadFile.setText("Download");
		downloadFile.setPreferredSize(defaultSize);
		downloadFile.setVisible(true);
		//uploadFile.setActionCommand(UPLOAD);

		// Generate the option to return to main menu
		mainMenu.setText("Main Menu");
		mainMenu.setPreferredSize(defaultSize);
		mainMenu.setVisible(true);
		//mainMenu.setActionCommand(MAINMENU);

		// Opens FileChooser to navigate file system
		openFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(fileName.getText());
				//file = DatabaseIO.exportData(connection, fileName.getText());
//				if (file != null) {
//					System.out.println("Retrieval Success");
//				}
//				JFileChooser fileChooser = new JFileChooser();
//				fileChooser.addChoosableFileFilter(new ExcelFilter());
//				fileChooser.setAcceptAllFileFilterUsed(false);
//
//				int returnVal = fileChooser.showDialog(parent, "Open");
//				if (returnVal == JFileChooser.APPROVE_OPTION) {
//					file = fileChooser.getSelectedFile();
//					JOptionPane.showMessageDialog(parent, "File is ready to be uploaded.", "File Primed", JOptionPane.PLAIN_MESSAGE);
//				}
				//fileChooser.setSelectedFile(null);
			}
		});

		// Uploads currently set up file
		downloadFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				boolean retVal = DatabaseIO.importData(connection, file);
//				if (retVal) {
//					JOptionPane.showMessageDialog(parent, "File is accepted.", "Upload Success", JOptionPane.PLAIN_MESSAGE);
//				}
//				else {
//					JOptionPane.showMessageDialog(parent, "Error Uploading File, make sure File isn't"
//							+ "corrupted and/or mandatory fields are filled", "Upload Failed", JOptionPane.ERROR_MESSAGE);
//				}
//				file = null;
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
		addElement(openInstruction, 0, 0);
		addElement(openFile, 0, 1);
		addElement(downloadFile, 0, 2);
		addElement(mainMenu, 0, 3);
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
