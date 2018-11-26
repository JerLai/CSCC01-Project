package main.java.com.icare.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.poi.ss.usermodel.Workbook;

import main.java.com.icare.accounts.User;
import main.java.com.icare.database.DatabaseIO;
import main.java.com.icare.database.databaseSession;


public class accessData extends JPanel{


	private static final long serialVersionUID = 1L;
	private GridBagConstraints gbc;
	private String currentTable;
	private String currentQuery;
	private String currentQueryName;
	private int currentRow;
	private ArrayList<Integer> editedRows = new ArrayList<Integer>();
	private Connection connection;
	private JButton mainMenu, saveChanges, saveQuery, addRow, removeRow, submitQuery, importData, pieData, barData, deleteQuery;
	private JLabel systemOut, nameQueryLabel;
	private JPopupMenu popupMenu;
	private JMenuItem exportTable;
	private JComboBox<String> listTables, listQueries;
	private JTextField queryInput, queryName;
	private JFileChooser chooser;
	private JScrollPane tableScroll;
	private CustomTableModel data;
	private Dimension defaultSize;
	private GUI parent;
	private User userSession;
	private TableModel currentTableModel;

	/** sets the JPanel onto the parent to view data in the database
	 * @param connection the connection to the back end database
	 * @param userSession the account User
	 * @param parent the parent JFrame
	 */	
	public accessData(Connection connection, User userSession, GUI parent) throws SQLException{

		this.parent = parent;
		this.userSession = userSession;
		this.setLayout(parent.getLayout());
		defaultSize = parent.getDefaultSize();
		gbc = parent.getGBC();
		Rectangle window = parent.getBounds();

		this.connection =  connection;
		mainMenu= new JButton("Return to Menu");
		mainMenu.setText("Main Menu");
		mainMenu.setPreferredSize(defaultSize);
		mainMenu.setVisible(true);
		addElement(mainMenu, 0, 0);
		mainMenu.addActionListener(new mainMenuFunction());

		data = new CustomTableModel();
		data.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		queryInput = new JTextField("");
		queryName = new JTextField("");
		submitQuery = new JButton("Query");
		saveChanges = new JButton("Save Edits");
		saveQuery = new JButton("Save Query");
		removeRow = new JButton("Delete Entry (Permanent)");
		addRow = new JButton("Add Blank Entry");
		importData = new JButton("Import");
		pieData = new JButton("Pie Chart");
		barData = new JButton("Bar Chart");
		deleteQuery = new JButton("Delete Query");
		chooser = new JFileChooser("Search csv");
		systemOut = new JLabel();
		nameQueryLabel = new JLabel("Query Name :");

		listTables = new JComboBox<String>();
		updateTablesList();
		listQueries = new JComboBox<String>();
		updateQueriesList();
		listTables.addItemListener(new listTableFunction());
		listQueries.addItemListener(new listQueriesFunction());
		saveChanges.setEnabled(false);
		tableScroll = new JScrollPane(data);
		tableScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		tableScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		data.setAutoCreateRowSorter(true);

		systemOut.setPreferredSize(new Dimension(defaultSize.width*4, defaultSize.height));
		saveChanges.setPreferredSize(defaultSize);
		saveQuery.setPreferredSize(defaultSize);
		removeRow.setPreferredSize(defaultSize);
		listTables.setPreferredSize(defaultSize);
		listQueries.setPreferredSize(defaultSize);
		importData.setPreferredSize(defaultSize);
		pieData.setPreferredSize(defaultSize);
		barData.setPreferredSize(defaultSize);
		queryInput.setPreferredSize(new Dimension(defaultSize.width*3, defaultSize.height));
		queryName.setPreferredSize(defaultSize);
		submitQuery.setPreferredSize(defaultSize);
		tableScroll.setPreferredSize(new Dimension(defaultSize.width*5, window.height/2));
		chooser.setPreferredSize(new Dimension(defaultSize.width*3,defaultSize.height*20));
		addRow.setPreferredSize(defaultSize);
		deleteQuery.setPreferredSize(defaultSize);
		nameQueryLabel.setPreferredSize(defaultSize);


		gbc.gridwidth = 4;addElement(systemOut,1,0);gbc.gridwidth = 1;
		addElement(listTables, 0,2);		gbc.gridwidth = 3;addElement(queryInput,1,2);gbc.gridwidth = 1;
		addElement(listQueries, 0,3);		addElement(importData,1,3);		addElement(submitQuery,2,3);	addElement(saveQuery, 3,3);
		addElement(nameQueryLabel, 0, 4);	addElement(queryName, 1, 4);	addElement(addRow,2,4);			addElement(removeRow,3,4);
		addElement(deleteQuery, 0, 5);		addElement(pieData,1,5);		addElement(barData,2,5);		addElement(saveChanges,3,5);


		gbc.gridwidth = 5;
		addElement(tableScroll,0,6);
		gbc.gridwidth = 1;





		importData.addActionListener(new importFunction());
		removeRow.addActionListener(new removeRowFunction());
		addRow.addActionListener(new addRowFunction());
		submitQuery.addActionListener(new submitQueryFunction());
		data.getSelectionModel().addListSelectionListener(new rowEditFunction());
		saveChanges.addActionListener(new saveEditFunction());
		saveQuery.addActionListener(new saveQueryFunction());
		deleteQuery.addActionListener(new deleteQueryFunction());
		queryInput.addKeyListener(new disableQuerySave());
		pieData.addActionListener(new pieChart());
		barData.addActionListener(new barChart());


		popupMenu = new JPopupMenu();
		exportTable = new JMenuItem("Export");
		exportTable.addActionListener(new exportFunction());
		popupMenu.add(exportTable);
		data.setComponentPopupMenu(popupMenu);

		setCurrentQueryActive(false);
	}


	/** enables or disables certain features available to proper queries
	 * @param bool true or false
	 */	
	private void setCurrentQueryActive(Boolean bool){
		saveChanges.setEnabled(bool);
		addRow.setEnabled(bool);
		removeRow.setEnabled(bool);
		saveQuery.setEnabled(bool);
		barData.setEnabled(bool);
		pieData.setEnabled(bool);
	}

	/** Saves the table's data as is onto the database by overwriting edited columns/rows
	 * @param table JTable containing the data to be saved
	 */	
	private void save(JTable table) throws SQLException {
		String updatedValues;
		String condition;
		String currentColumnType;
		boolean isMandatory;
		if (table.isEditing())
			table.getCellEditor().stopCellEditing();
		for(int r: editedRows){
			updatedValues = "";
			for (int c = 1; c < table.getColumnCount(); c++){
				currentColumnType = databaseSession.getTableColumnType(connection, currentTable, table.getColumnName(c));
				isMandatory = databaseSession.getTableColumnMandatory(connection, currentTable, table.getColumnName(c));
				if (table.getValueAt(r, c) == null || table.getValueAt(r, c).equals("")){
					// if null but field is mandatory, stop and alert user of error
					if (isMandatory){
						systemOut.setText("Save Unsuccessful! Missing mandatory field at row: " + (r+1) + "\t column: " + table.getColumnName(c));
						return;
					}
					updatedValues += ", " + table.getColumnName(c) + "= null";
				}
				else {
					if (currentColumnType.indexOf("char")>=0 || currentColumnType.indexOf("TEXT")>=0){
						// text requires single quotes to avoid error
						updatedValues += ", " + table.getColumnName(c) + "= '" + table.getValueAt(r, c) + "'";
					}else
						// whereas non textual data requires no single quotes to avoid error
						updatedValues += ", " + table.getColumnName(c) + "=" + table.getValueAt(r, c);
				}						
			}
			updatedValues = updatedValues.substring(1);
			try {
				// save and update table
				int pkColumn = databaseSession.primaryKeyInTable(databaseSession.findPrimaryKey(connection, currentTable), table);
				condition = databaseSession.findPrimaryKey(connection, currentTable) + "=" + table.getValueAt(r, pkColumn);
				databaseSession.updateData(connection, currentTable, updatedValues, condition);
			} catch (SQLException e) {
				System.out.println(updatedValues);
				systemOut.setText("Save Unsuccessful	!");
				e.printStackTrace();
				return;
			}

		}
		systemOut.setText("Save Successful!");
	}

	/** Refreshes the table to display new data off of defined query
	 * @param query in SQL lite format
	 */	
	private void updateTable(JTable table, String query) throws SQLException{
		editedRows.clear();
		systemOut.setText(null);
		setCurrentQueryActive(false);
		if (query != null && !query.isEmpty()){
			ResultSet result = databaseSession.queryJTable(connection, query);
			currentTable = result.getMetaData().getTableName(1);
			currentTableModel = databaseSession.buildTableModel(result);
			table.setModel(currentTableModel);
			String pk = databaseSession.findPrimaryKey(connection, currentTable);
			setCurrentQueryActive(true);
			if (databaseSession.primaryKeyInTable(pk, table) >= 0){
				saveChanges.setText("Save changes to " + currentTable);
			} else {
				saveChanges.setEnabled(false);
				saveChanges.setText("Cannot Save without Primary Key");
			}
			currentQuery = query;
		}
	}

	private class CustomTableModel extends JTable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int pkColumn= -1;

		public void setColumnAsPK(int column){
			pkColumn = column;
		}

		@Override
		public boolean isCellEditable(int row, int column){
			return (column != pkColumn);
		}

	}

	/** Simplification of adding elements to JPanel
	 * @param element JComponent to be added
	 * @param x integer coordinate
	 * @param y integer coordinate
	 */	
	private void addElement(JComponent element, int x, int y){
		gbc.gridx = x;
		gbc.gridy = y;
		this.add(element, gbc);
		element.setVisible(true);
		repaint();
	}

	/** Refreshes the Table list
	 * 
	 */	
	private void updateTablesList() throws SQLException{
		listTables.removeAllItems();
		listTables.addItem("Select Table");
		for (String s: databaseSession.getAllTables(connection)){
			if (!s.equals("Login") && !s.equals("SavedQueries"))
				if (s.contains(" "))
					listTables.addItem("'" + s + "'");
				else
					listTables.addItem(s);
		}
		listTables.setSelectedIndex(0);
	}

	/** Refreshes the Queries list
	 * 
	 */	
	private void updateQueriesList() throws SQLException{
		listQueries.removeAllItems();
		listQueries.addItem("Select Query");
		for (String s: databaseSession.getAllSavedQueries(connection)){
			listQueries.addItem(s);
		}
	}


	class mainMenuFunction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				parent.next(new mainMenu(connection, userSession, parent));
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		}

	}

	class listTableFunction implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			try {
				if (listTables.getSelectedIndex() <= 0){
					return;
				}
				queryInput.setText("Select * from " + listTables.getSelectedItem());
				updateTable(data, "Select * from " + listTables.getSelectedItem());
				setCurrentQueryActive(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (NullPointerException e2){
				return;
			}
			listTables.setSelectedIndex(0);
		}

	}

	class listQueriesFunction implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			try {
				if (listQueries.getSelectedIndex() <= 0){
					return;
				}
				currentQueryName = listQueries.getSelectedItem().toString();
				String query = databaseSession.getSavedQuery(connection, currentQueryName);
				queryInput.setText(query);
				deleteQuery.setEnabled(true);
				updateTable(data, query);
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (NullPointerException e2){
				return;
			}
			listQueries.setSelectedIndex(0);
		}

	}

	class importFunction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			chooser.addChoosableFileFilter(new FileNameExtensionFilter("xls", "xlsx"));
			chooser.showOpenDialog(parent);
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if (chooser.getSelectedFile() != null){
				String path = chooser.getSelectedFile().getName();
				if (path != null)
					DatabaseIO.importData(connection, chooser.getSelectedFile());
				try {
					updateTablesList();
					systemOut.setText("Import Successful");
				} catch (SQLException e1) {
					systemOut.setText("Import Unsuccessful");
				}
			}
		}

	}

	class removeRowFunction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				save(data);
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			currentRow = data.getSelectedRow();
			try {
				if (data.isEditing())
					data.getCellEditor().stopCellEditing();
				String pk;
				pk = databaseSession.findPrimaryKey(connection, currentTable);
				int pkColumn = -1;
				for (int i = 0; i < data.getColumnCount();i++){
					if (data.getColumnName(i).equals(pk))
						pkColumn = i;
				}
				if (currentRow >= 0 && pkColumn > -1){
					for (int row:data.getSelectedRows()){
						editedRows.remove(editedRows.indexOf(row));
						databaseSession.removeData(connection, currentTable, pk + "=" + data.getValueAt(row, pkColumn));
					}
					updateTable(data, currentQuery);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}				
		}

	}

	class addRowFunction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				databaseSession.insertData(connection, currentTable);
				updateTable(data, currentQuery);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		}

	}

	class submitQueryFunction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			setCurrentQueryActive(false);
			try {
				String query = queryInput.getText();
				if (data.isEditing())
					data.getCellEditor().stopCellEditing();
				if (query.substring(query.indexOf("from")).split(" ")[1].isEmpty()){
					data.setModel(null);
					return;
				} else if (query.substring(query.indexOf("from")).split(" ")[1].equals("Login")){
					systemOut.setText("Sorry, I don't quite understand that query");
					return;
				}
				updateTable(data,query);
				setCurrentQueryActive(true);
			} catch (Exception e1){
				systemOut.setText("Sorry, I don't quite understand that query");
				data.setModel(new DefaultTableModel());
				setCurrentQueryActive(false);
			}
		}
	}

	class rowEditFunction implements ListSelectionListener{
		int r =-1;
		String values;
		@Override
		public void valueChanged(ListSelectionEvent e) {
			try {
				if (data.getColumnName(data.getSelectedColumn()).equals(databaseSession.findPrimaryKey(connection, currentTable)))
					data.setColumnAsPK(data.getSelectedColumn());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if (r != data.getSelectedRow()){
				r = data.getSelectedRow();
				if (data.isEditing())
					data.getCellEditor().stopCellEditing();
				if (r>-1){
					values = "";
					for (int c = 0; c<data.getColumnCount();c++){
						if (data.getValueAt(r, c) == null)
							values += ",NULL";
						else
							values += ","+ data.getValueAt(r, c);
					}
					removeRow.setEnabled(true);
					if (!editedRows.contains(r)){
						editedRows.add(r);
					}
				}
			}
		}

	}

	class saveEditFunction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				save(data);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}

	class saveQueryFunction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				databaseSession.insertData(connection, "SavedQueries", "name, data",
						"'" + queryName.getText() + "','" + queryInput.getText()+ "'");
				updateQueriesList();
			} catch (SQLException e1) {
				e1.printStackTrace();
				systemOut.setText("Save Unsuccessful");
			}

		}

	}

	class deleteQueryFunction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				databaseSession.removeData(connection, "SavedQueries", "name='" + currentQueryName + "'");
				updateQueriesList();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		}

	}

	class disableQuerySave implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			return;
		}

		@Override
		public void keyPressed(KeyEvent e) {
			saveQuery.setEnabled(false);

		}

		@Override
		public void keyReleased(KeyEvent e) {
			return;
		}

	}

	class pieChart implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JFrame chart = new JFrame();
			try {
				HashMap<String, Double> pieData = new HashMap<String, Double>();
				for (int columnIndex = 0; columnIndex<currentTableModel.getColumnCount(); columnIndex++){
					pieData.put(currentTableModel.getColumnName(columnIndex), new Double(currentTableModel.getValueAt(0, columnIndex).toString()));
				}
				chart.setContentPane(ChartGen.pieChartGen(ChartGen.pieDataConverter(pieData)));
				chart.setVisible(true);
				chart.setSize(new Dimension(defaultSize.width*5,defaultSize.height*25));
				chart.setLocation(0, 0);
			} catch (Exception e1){
				return;
			}
		}

	}

	class barChart implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JFrame chart = new JFrame();
			try {
				HashMap<String, HashMap<String, Double>> barData = new HashMap<String, HashMap<String, Double>>();
				for (int columnIndex = 0; columnIndex<currentTableModel.getColumnCount(); columnIndex++){
					HashMap<String, Double> subCat = new HashMap<String, Double>();
					for (int rowIndex = 0; rowIndex<currentTableModel.getRowCount(); rowIndex++){
						if (currentTableModel.getValueAt(rowIndex, columnIndex) == null)
							continue;
						String key = currentTableModel.getValueAt(rowIndex, columnIndex).toString();
						if (!subCat.containsKey(key)){
							subCat.put(key, new Double(1));
						} else
							subCat.put(key, subCat.get(key)+1);
					}
					barData.put(currentTableModel.getColumnName(columnIndex), subCat);
				}
				chart.setVisible(true);
				chart.setSize(new Dimension(defaultSize.width*5,defaultSize.height*25));
				chart.setLocation(0, 0);
				chart.setContentPane(ChartGen.barChartGen(ChartGen.barDataConverter(barData)));
			} catch (Exception e1){
				return;
			}
		}
	}

	class exportFunction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String fileAbsPath = "";
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.addChoosableFileFilter(new ExcelFilter());
			fileChooser.setAcceptAllFileFilterUsed(false);
			fileChooser.setDialogTitle("Save file");
			fileChooser.setSelectedFile(new File(fileAbsPath));
			int returnVal = fileChooser.showSaveDialog(fileChooser);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				fileAbsPath = fileChooser.getSelectedFile().getAbsolutePath();
			} else {
				return;
			}
			// System independent way to extract directory save path and to save actual file
			String exportName = fileChooser.getSelectedFile().getName().toString();
			File retFile = new File(fileChooser.getSelectedFile().toString() + ".xlsx");
			try {
				Workbook workbook = DatabaseIO.exportData(currentTableModel, exportName);
				FileOutputStream out = new FileOutputStream(retFile);
				workbook.write(out);
				out.close();
				workbook.close();
				systemOut.setText("Export Successful");
			} catch (IOException exception) {
				System.out.println("Error while closing .xlsx file: " + exportName);
				exception.printStackTrace();
			}
		}

	}

}



