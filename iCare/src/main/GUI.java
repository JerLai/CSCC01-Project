package main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.JTableHeader;
import main.java.com.icare.accounts.User;
import main.java.com.icare.database.databaseAPI;
import main.java.com.icare.database.databaseSession;

public class GUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Rectangle window;
	private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private Container container;
	private Connection connection;
	private ArrayList<JComponent> currentGUIElements = new ArrayList<JComponent>();
	private GridBagConstraints gbc;
	private Insets defaultInsets = new Insets(10,10,10,10);
	private Dimension defaultSize;
	private String currentTable;
	private JLabel pad[] = {new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),
			new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),
			new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),
			new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel()};

	public GUI(String header, Connection connection){
		super(header);
		this.connection = connection;
		this.setSize(screenSize.width*3/4, screenSize.height*3/4);
		this.setLocation(screenSize.width*1/8, screenSize.height*1/8);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		window = this.getBounds();
		container = this.getContentPane();
		container.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		defaultSize = new Dimension(window.width*1/8, window.height*1/25);
		gbc.insets = defaultInsets;
		this.requestFocusInWindow();
		Login();
	}

	public void Login(){
		clearScreen();
		JButton button = new JButton("Login");
		JTextField username = new JTextField("Kyle");
		JTextField password = new JPasswordField("password");
		JLabel login = new JLabel("Login");
		login.setText("Login");
		login.setPreferredSize(defaultSize);
		username.setPreferredSize(defaultSize);
		password.setPreferredSize(defaultSize);
		button.setPreferredSize(defaultSize);
		addElement(login, 0, 1);
		addElement(username, 0, 2);
		addElement(password, 0, 3);
		addElement(button, 0, 4);

		username.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				username.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (username.getText().equals(""))
					username.setText("Username");
			}

		});

		password.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				password.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (password.getText().equals("")){
					password.setText("Password");
				}
			}

		});

		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					User userSession = databaseAPI.login(connection, username.getText(), password.getText());
					password.setText("");
					if (userSession != null){
						System.out.println("Login Successful as: " + userSession.getUsername());

						mainMenu(userSession);
					} else{
						login.setText("Login Unsuccessful, Please Try Again");
						System.out.println("Login Unsuccessful, Please Try Again");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});

	}

	public void mainMenu(User userSession) throws SQLException{
		clearScreen();
		JButton logout = new JButton("Logout");
		JLabel welcome = new JLabel("Welcome " + userSession.getFirstName());
		JButton viewTable = new JButton("View Tables");

		welcome.setPreferredSize(defaultSize);
		logout.setPreferredSize(defaultSize);
		viewTable.setPreferredSize(defaultSize);
		addElement(welcome,0,0);
		addElement(logout, 0,1);
		addElement(viewTable,0,2);
		addElement(pad[0], 1,3);
		pad[0].setPreferredSize(new Dimension(1000,500));
		repaint();
		logout.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Login();
			}

		});
		viewTable.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					accessData(userSession);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});
	}

	public void accessData(User userSession) throws SQLException{
		clearScreen();
		addMainMenuButton(userSession);
		JTable data = new JTable();
		data.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JTextField queryInput = new JTextField("Select * from Data");
		JButton submitQuery = new JButton("Query");
		JButton saveChanges = new JButton("Save");
		JComboBox<String> listTables = new JComboBox<String>();
		for (String s: databaseSession.getAllTables(connection)){
			listTables.addItem(s);
		}
		listTables.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				queryInput.setText("Select * from " + listTables.getSelectedItem());
			}
			
		});
		saveChanges.setEnabled(false);
		JScrollPane tableScroll = new JScrollPane(data);
		tableScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		tableScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		JTableHeader header = data.getTableHeader();


		saveChanges.setPreferredSize(defaultSize);
		listTables.setPreferredSize(defaultSize);
		queryInput.setPreferredSize(new Dimension(defaultSize.width*3, defaultSize.height));
		submitQuery.setPreferredSize(defaultSize);
		tableScroll.setPreferredSize(new Dimension(defaultSize.width*3, window.height/2));
		//addElement(header,4,0);
		gbc.gridwidth = 3;
		addElement(queryInput,2,2);
		gbc.gridwidth = 1;
		addElement(listTables, 1,3);
		addElement(submitQuery,2,3);
		addElement(saveChanges,4,3);
		gbc.gridwidth = 3;
		addElement(tableScroll,2,4);
		gbc.gridwidth = 1;
		submitQuery.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String query = queryInput.getText();
					if (data.isEditing())
						data.getCellEditor().stopCellEditing();
					data.setModel(databaseSession.queryJTable(connection, query));
					currentTable = query.substring(query.indexOf("from")).split(" ")[1];
					String pk = databaseSession.findPrimaryKey(connection, currentTable);
					if (databaseSession.primaryKeyInTable(pk, data)){
						saveChanges.setEnabled(true);
						saveChanges.setText("Save to: " + currentTable);
					} else {
						saveChanges.setEnabled(false);
						saveChanges.setText("Cannot Save without Primary Key");
					}

				} catch (NullPointerException npe){
					//npe.printStackTrace();
				}
				catch (SQLException e1) {
					//e1.printStackTrace();
				}
			}
		});

		data.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			int r =-1;
			String values;
			@Override
			public void valueChanged(ListSelectionEvent e) {
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
						//System.out.println(values.substring(1));
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
				for(int r = 0; r < data.getRowCount(); r++){
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

	private void addElement(JComponent element, int x, int y){
		gbc.gridwidth = 2;
		gbc.gridx = x;
		gbc.gridy = y;
		container.add(element, gbc);
		element.setVisible(true);
		currentGUIElements.add(element);
		repaint();
	}

	private void addMainMenuButton(User userSession){
		JButton mainMenu = new JButton("Return to Menu");
		mainMenu.setText("Main Menu");
		mainMenu.setPreferredSize(defaultSize);
		mainMenu.setVisible(true);
		addElement(mainMenu, 0,0);
		mainMenu.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					mainMenu(userSession);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}

		});

	}

	private void clearScreen(){
		for (JComponent element: currentGUIElements){
			element.setVisible(false);
			container.remove(element);
		}
		currentGUIElements.clear();
		repaint();
	}

}
