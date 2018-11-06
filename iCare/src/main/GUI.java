package main;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

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

  public GUI(String header, Connection connection){
    super(header);
    this.connection = connection;
    this.setSize(screenSize.width*3/4, screenSize.height*3/4);
    this.setLocation(screenSize.width*1/8, screenSize.height*1/8);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
    window = this.getBounds();
    container = this.getContentPane();
    container.setLayout(null);
    this.requestFocusInWindow();
    Login();
  }

  public void Login(){
    clearScreen();
    JButton button = new JButton("Login");
    JTextField username = new JTextField("Username");
    JTextField password = new JPasswordField("Password");
    JLabel login = new JLabel("Login");
    login.setText("Login");
    login.setSize(300,30);
    login.setBounds(window.width/2 - login.getWidth()/2, window.height/2 - 120, login.getWidth(), login.getHeight());
    button.setSize(100, 30);
    button.setBounds(window.width/2 - button.getWidth()/2, window.height/2, button.getWidth(), button.getHeight());
    username.setSize(300,30);
    username.setBounds(window.width/2 - username.getWidth()/2, window.height/2 - 70, username.getWidth(), username.getHeight());
    password.setSize(300,30);
    password.setBounds(window.width/2 - username.getWidth()/2, window.height/2 - 30, username.getWidth(), username.getHeight());


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

    currentGUIElements.add(login);
    currentGUIElements.add(button);
    currentGUIElements.add(username);
    currentGUIElements.add(password);
    for (JComponent element: currentGUIElements){
      element.setVisible(true);
      container.add(element);
    }
    repaint();
  }

  public void mainMenu(User userSession) throws SQLException{
    clearScreen();
    JButton logout = new JButton();
    currentGUIElements.add(logout);
    logout.setText("Logout");
    JButton viewTable = new JButton();
    currentGUIElements.add(viewTable);
    viewTable.setText("View Tables");
    int yOffset = 0;
    for (JComponent button: currentGUIElements){
      button.setSize(200, 30);
      button.setBounds(window.width/10, window.height/10 + yOffset, button.getWidth(), button.getHeight());
      button.setVisible(true);
      container.add(button);
      yOffset += 50;
    }
    repaint();
    
    JLabel welcome = new JLabel("Welcome " + userSession.getFirstName());
    welcome.setSize(300 , 30);
    welcome.setBounds(window.width/2 - welcome.getWidth()/2, window.height/20, welcome.getWidth(), welcome.getHeight());
    addElement(welcome);
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
    JLabel data = new JLabel("Sample Table");
    JTextField queryInput = new JTextField();
    JButton submitQuery = new JButton("Query");
    submitQuery.setBounds(window.width-500, window.height/20, 100, 30);
    data.setBounds(300, 100, window.width-200, window.height-600);
    queryInput.setBounds(300, window.height/20, window.width-800, 30);
    addElement(data);
    addElement(queryInput);
    addElement(submitQuery);
    String querySource = databaseSession.sourceQuery("Data", "ID, sample1, sample3");
    //exec = databaseSession.filterQuery(exec, "ID = 2");
    querySource = databaseSession.sortQuery(querySource, "ID DESC");
    databaseSession.createTempTable(connection, "Data2", querySource);
    System.out.println(querySource);
    System.out.println(String.format(databaseSession.queryData(connection, querySource)));
    submitQuery.addActionListener(new ActionListener(){

      @Override
      public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        try {
          data.setText(databaseSession.queryAsHTML(connection, queryInput.getText()));
        } catch (SQLException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
      
    });
  }
  
  private void addElement(JComponent element){
    element.setVisible(true);
    container.add(element);
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
