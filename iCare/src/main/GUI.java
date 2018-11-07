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
		gbc.fill = GridBagConstraints.HORIZONTAL;
		login.setPreferredSize(new Dimension(200,30));
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
		logout.setPreferredSize(new Dimension(200,30));
		JLabel welcome = new JLabel("Welcome " + userSession.getFirstName());
		JButton viewTable = new JButton("View Tables");


		addElement(welcome,0,0);
		addElement(logout, 0,1);
		addElement(viewTable,0,2);
		//		for (int i = 0; i<=3; i++){
		//			addElement(pad[i], 1,i);
		//			pad[i].setPreferredSize(new Dimension(500,50));
		//		}
		//		pad[3].setPreferredSize(new Dimension(1000,500));
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
		JScrollPane tableScroll = new JScrollPane(data);
		tableScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		tableScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		JTableHeader header = data.getTableHeader();
		tableScroll.setPreferredSize(new Dimension(500,500));


		addElement(header,0,0);
		addElement(saveChanges,0,1);
		addElement(queryInput,0,2);
		addElement(submitQuery,0,3);
		addElement(tableScroll,0,4);
		submitQuery.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String query = queryInput.getText();
					data.setModel(databaseSession.queryTempJTable(connection, query));
				} catch (NullPointerException npe){
					npe.printStackTrace();
				}
				catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		data.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			int c = -1;
			int r = -1;
			@Override
			public void valueChanged(ListSelectionEvent e) {
				r = e.getFirstIndex();
				if (data.isEditing())
					data.getCellEditor().stopCellEditing();
				if (c>-1 && r>-1){
					System.out.println(data.getValueAt(r, c));
				}
				c = data.getSelectedColumn();
				r = e.getFirstIndex();
			}

		});
		saveChanges.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

				//databaseSession.updateData(connection, "Scratch_Table", "data.getColumnName(c)='" + data.getValueAt(r, c) + "'", condition);
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
		mainMenu.setSize(200,30);
		mainMenu.setBounds(50, window.height/20, mainMenu.getWidth(), mainMenu.getHeight());
		mainMenu.setVisible(true);
		currentGUIElements.add(mainMenu);
		container.add(mainMenu);
		repaint();
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
