package main.java.com.icare.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;



public class databaseDriver {

  /**
   * Connects to SQL db
   * @return the connection
   */
  protected static Connection connectDataBase() {
    Connection connection = null;
    try {
      Class.forName("org.sqlite.JDBC");
      connection = DriverManager.getConnection("jdbc:sqlite:iCare.db");
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return connection;
  }

  /**
   * Initializes the connection with SQL database
   * @param connection the connection to be initialized
   * @return the initialized connection
   */
  protected static Connection initialize(Connection connection) {
    if (!initializeDatabase(connection)) {
      System.out.println("Error connecting to new DB");
    }
    return connection;
  }
  

  /**
   * Initialize the db
   * @param connection the connection to which db is connected
   * @return true/false depending on db initialization success
   */
  private static boolean initializeDatabase(Connection connection) {
    Statement statement = null;
    
    try {
      statement = connection.createStatement();
      
      String sql = "CREATE TABLE Login " 
              + "(ID INTEGER PRIMARY KEY NOT NULL," 
              + "username TEXT,"
              + "password TEXT,"
              + "firstName TEXT,"
              + "lastName TEXT,"
              + "accountType TEXT)";
      statement.executeUpdate(sql);
      sql = "CREATE TABLE SavedQueries " 
              + "(ID INTEGER PRIMARY KEY NOT NULL," 
              + "name TEXT,"
              + "description TEXT,"
              + "query TEXT UNIQUE)";
      
      statement.executeUpdate(sql);
      statement.close();
      return true;
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
}
