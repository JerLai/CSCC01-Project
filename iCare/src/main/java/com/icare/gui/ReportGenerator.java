package main.java.com.icare.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.java.com.icare.accounts.User;

public class ReportGenerator extends JPanel {
	private static final long serialVersionUID = 1L;
	private GridBagConstraints gbc;
	private static String[] reportTypes = {"Report #1", "Report #2"};
	private static String[] tables = {"Table #1", "Table #2"};
	
	private static String GENERATE = "Generate Report";
	private static String MAINMENU = "Return to Menu";

	/**
	 * Constructor for the GUI to display a ReportGeneratorPanel
	 * @param connection connection to the database of the system
	 * @param userSession the user that is logged in
	 * @param parent the parent JFrame
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ReportGenerator(Connection connection, User userSession, GUI parent) {
		this.setLayout(parent.getLayout());
		Dimension defaultSize = parent.getDefaultSize();
		gbc = parent.getGBC();
		Rectangle window = parent.getBounds();

		JButton generateReport = new JButton (GENERATE);
		JComboBox tableSelector = new JComboBox(tables);
		JComboBox typeSelector = new JComboBox(reportTypes);
		JButton mainMenu = new JButton(MAINMENU);

		// Generate the option to generate a Report
		generateReport.setText("Generate Report");
		generateReport.setPreferredSize(defaultSize);
		generateReport.setVisible(true);
		//generateReport.setActionCommand(OPEN);

		// Generate the option to return to main menu
		mainMenu.setText("Main Menu");
		mainMenu.setPreferredSize(defaultSize);
		mainMenu.setVisible(true);
		//mainMenu.setActionCommand(MAINMENU);

		// Currently allows certain pre-set report types
		typeSelector.setSelectedIndex(0);
		typeSelector.setPreferredSize(defaultSize);

		// Allows picking of table to generate report on
		tableSelector.setSelectedIndex(0);
		tableSelector.setPreferredSize(defaultSize);

		// calls appropriate report generator function(s)
		generateReport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// tells User if report was appropriately generated
				boolean retVal = true;
				if (retVal) {
					JOptionPane.showMessageDialog(parent, "The report was successfully generated!", "Report Generation Success", JOptionPane.PLAIN_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(parent, "There was a problem generating the report! Please make sure the selected report"
							+ " can be generated using the selected table.", "Report Generation Failed", JOptionPane.ERROR_MESSAGE);
				}
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
		addElement(tableSelector, 0, 0);
		addElement(typeSelector, 0, 1);
		addElement(generateReport, 0, 2);
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
