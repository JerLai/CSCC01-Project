package main.java.com.icare.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.com.icare.accounts.User;

public class mainMenu extends JPanel{


	private static final long serialVersionUID = 1L;
	private GridBagConstraints gbc;

	/**
	 * Constructs the mainMenu content Panel by creating the several actions currently possible for the session of the User
	 * @param connection
	 * @param userSession
	 * @param parent
	 * @throws SQLException
	 */
	public mainMenu(Connection connection, User userSession, GUI parent) throws SQLException{

		this.setLayout(parent.getLayout());
		Dimension defaultSize = parent.getDefaultSize();
		gbc = parent.getGBC();
		
		JButton logout = new JButton("Logout");
		JLabel welcome = new JLabel("Welcome " + userSession.getFirstName());
		JButton viewTable = new JButton("View Tables");
		JButton admin = new JButton("Admin");
		JButton addPatient = new JButton("Add Patient");
		JButton uploadData = new JButton("Upload Data");
		JButton reports = new JButton("Generate Reports");


		// Set all the elements to default JComponent size and add to Panel
		welcome.setPreferredSize(defaultSize);
		logout.setPreferredSize(defaultSize);
		viewTable.setPreferredSize(defaultSize);
		admin.setPreferredSize(defaultSize);
		addPatient.setPreferredSize(defaultSize);
		uploadData.setPreferredSize(defaultSize);
		reports.setPreferredSize(defaultSize);
		addElement(welcome,0,0);
		addElement(logout, 0,1);
		addElement(viewTable,0,2);
		addElement(admin,0,3);
		addElement(addPatient,0,4);
		addElement(uploadData, 0, 5);
		addElement(reports, 0, 6);
		repaint();
		logout.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				parent.next(new login(connection, parent));
			}

		});
		viewTable.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					parent.next(new accessData(connection, userSession, parent));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});
		addPatient.setEnabled(false);
//		addPatient.addActionListener(new ActionListener(){
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				addPatientData(userSession);
//			}
//
//		});
		admin.setEnabled(false);
//		admin.addActionListener(new ActionListener(){
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				admin(userSession);
//			}
//
//		});

		uploadData.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				parent.next(new DataUpload(connection, userSession, parent));
			}
			
		});

		reports.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				parent.next(new ReportGenerator(connection, userSession, parent));
			}
			
		});
	}


	
	private void addElement(JComponent element, int x, int y){
		gbc.gridx = x;
		gbc.gridy = y;
		this.add(element, gbc);
		element.setVisible(true);
		repaint();
	}
	
}
