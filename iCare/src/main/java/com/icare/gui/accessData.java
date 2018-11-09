package main.java.com.icare.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import main.java.com.icare.accounts.User;
import main.java.com.icare.database.DatabaseIO;
import main.java.com.icare.database.databaseSession;


public class accessData extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GridBagConstraints gbc;
	private String currentTable;
	private String currentQuery;
	private int currentRow;
	private ArrayList<Integer> editedRows = new ArrayList<Integer>();
	
	public accessData(Connection connection, User userSession, GUI parent) throws SQLException{
		
		this.setLayout(parent.getLayout());
		Dimension defaultSize = parent.getDefaultSize();
		gbc = parent.getGBC();
		Rectangle window = parent.getBounds();
		
		JButton mainMenu = new JButton("Return to Menu");
		mainMenu.setText("Main Menu");
		mainMenu.setPreferredSize(defaultSize);
		mainMenu.setVisible(true);
		addElement(mainMenu, 0, 0);
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
		
		MyTableModel data = new MyTableModel();
		data.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JTextField queryInput = new JTextField("");
		JButton submitQuery = new JButton("Query");
		JButton saveChanges = new JButton("Save Edits");
		JButton removeRow = new JButton("Delete Entry (Permanent)");
		JButton addRow = new JButton("Add Blank Entry");
		JButton importCsv = new JButton("Import");
		JButton exportCsv = new JButton("Export");
		JFileChooser chooser = new JFileChooser("Search csv");
		JLabel systemOut = new JLabel();
		
		addRow.setEnabled(false);
		removeRow.setEnabled(false);
		JComboBox<String> listTables = new JComboBox<String>();
		listTables.addItem("");
		for (String s: databaseSession.getAllTables(connection)){
			if (!s.equals("Login"))
				listTables.addItem(s);
		}
		listTables.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				try {
					if (listTables.getSelectedItem().toString().isEmpty()){
						data.setModel(new DefaultTableModel());
						queryInput.setText("" + listTables.getSelectedItem());
						return;
					}
					queryInput.setText("Select * from " + listTables.getSelectedItem());
					data.setModel(databaseSession.queryJTable(connection, "Select * from " + listTables.getSelectedItem()));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});
		saveChanges.setEnabled(false);
		JScrollPane tableScroll = new JScrollPane(data);
		tableScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		tableScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		data.setAutoCreateRowSorter(true);

		systemOut.setPreferredSize(new Dimension(defaultSize.width*4, defaultSize.height));
		saveChanges.setPreferredSize(defaultSize);
		removeRow.setPreferredSize(defaultSize);
		listTables.setPreferredSize(defaultSize);
		importCsv.setPreferredSize(defaultSize);
		exportCsv.setPreferredSize(defaultSize);
		queryInput.setPreferredSize(new Dimension(defaultSize.width*3, defaultSize.height));
		submitQuery.setPreferredSize(defaultSize);
		tableScroll.setPreferredSize(new Dimension(defaultSize.width*5, window.height/2));
		addRow.setPreferredSize(defaultSize);
		gbc.gridwidth = 4;
		addElement(systemOut,1,0);
		gbc.gridwidth = 3;
		addElement(queryInput,1,2);
		gbc.gridwidth = 1;
		addElement(listTables, 0,2);
		addElement(importCsv,1,3);
		addElement(exportCsv,1,4);
		addElement(submitQuery,2,3);
		addElement(saveChanges,3,3);
		gbc.gridwidth = 5;
		addElement(tableScroll,0,5);
		gbc.gridwidth = 1;
		addElement(removeRow,2,6);
		addElement(addRow,2,4);
		exportCsv.setEnabled(false);
		importCsv.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				chooser.showOpenDialog(parent);
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				String path = chooser.getSelectedFile().getName();
				if (!path.isEmpty())
					DatabaseIO.importData(connection, chooser.getSelectedFile().getName());
				try {
					parent.next(new accessData(connection, userSession, parent));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});
		removeRow.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
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
							databaseSession.removeData(connection, currentTable, pk + "=" + data.getValueAt(row, pkColumn));
						}
						data.setModel(databaseSession.queryJTable(connection, currentQuery));
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
			}

		});
		addRow.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					databaseSession.insertData(connection, currentTable);
					data.setModel(databaseSession.queryJTable(connection, currentQuery));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		});
		submitQuery.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				addRow.setEnabled(false);
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
					data.setModel(databaseSession.queryJTable(connection, query));
					currentTable = query.substring(query.indexOf("from")).split(" ")[1];
					String pk = databaseSession.findPrimaryKey(connection, currentTable);
					addRow.setEnabled(true);
					if (databaseSession.primaryKeyInTable(pk, data)){
						saveChanges.setEnabled(true);
						saveChanges.setText("Save changes to " + currentTable);
					} else {
						saveChanges.setEnabled(false);
						saveChanges.setText("Cannot Save without Primary Key");
					}
					currentQuery = query;
				} catch (NullPointerException npe){
					systemOut.setText("Sorry, I don't quite understand that query");
				} catch (StringIndexOutOfBoundsException e1){
					systemOut.setText("Sorry, I don't quite understand that query");
				}
				catch (SQLException e2) {
					systemOut.setText("Sorry, I don't quite understand that query");
				}
			}
		});

		data.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			int r =-1;
			String values;
			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					if (data.getColumnName(data.getSelectedColumn()).equals(databaseSession.findPrimaryKey(connection, currentTable)))
						data.setColumnAsPK(data.getSelectedColumn());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
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
						editedRows.add(r);
						System.out.println(values.substring(1));
					}
				}
			}

		});
		saveChanges.addActionListener(new ActionListener(){
			String updatedValues;
			String condition;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (data.isEditing())
					data.getCellEditor().stopCellEditing();
				for(int r: editedRows){
					updatedValues = "";
					for (int c = 1; c < data.getColumnCount(); c++){
						if (data.getValueAt(r, c) != null)
							updatedValues += ", " + data.getColumnName(c) + "= '" + data.getValueAt(r, c) + "'";
						else
							updatedValues += ", " + data.getColumnName(c) + "= null";
					}
					updatedValues = updatedValues.substring(1);
					try {
						condition = databaseSession.findPrimaryKey(connection, currentTable) + "=" + data.getValueAt(r, 0);
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
					try {
						databaseSession.updateData(connection, currentTable, updatedValues, condition);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}

		});
	}
	
	private class MyTableModel extends JTable {
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
	
	private void addElement(JComponent element, int x, int y){
		gbc.gridx = x;
		gbc.gridy = y;
		this.add(element, gbc);
		element.setVisible(true);
		repaint();
	}
	
}