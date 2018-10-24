package main.java.com.icare.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;



public class databaseDriver {

  protected static Connection connectOrCreateDataBase() {
    Connection connection = null;
    try {
      Class.forName("org.sqlite.JDBC");
      connection = DriverManager.getConnection("jdbc:sqlite:iCare.db");
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return connection;
  }

  protected static Connection initialize(Connection connection) {
    if (!initializeDatabase(connection)) {
      System.out.println("Error connecting to new DB");
    }
    return connection;
  }
  

  private static boolean initializeDatabase(Connection connection) {
    Statement statement = null;
    
    try {
      statement = connection.createStatement();
      
      String sql = "CREATE TABLE Login " 
              + "(ID INTEGER PRIMARY KEY NOT NULL," 
              + "username TEXT NOT NULL,"
              + "password char(64),"
              + "role INTEGER NOT NULL)";
      statement.executeUpdate(sql);
      
      sql = "CREATE TABLE Data " 
              + "(ID INTEGER PRIMARY KEY NOT NULL," 
              + "sample1 TEXT NOT NULL,"
              + "sample2 TEXT NOT NULL,"
              + "sample3 TEXT NOT NULL)";
      
      statement.executeUpdate(sql);
      statement.close();
      return true;
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
  
  
}
