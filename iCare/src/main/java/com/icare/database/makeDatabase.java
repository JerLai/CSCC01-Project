package main.java.com.icare.database;


import java.sql.Connection;
import java.sql.SQLException;



public class makeDatabase {
 

  public static void initialize() {
    
    Connection connection = databaseDriver.connectOrCreateDataBase();
    try {
      
      initializeDatabase(connection);
      // TO DO, arrange table format
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        connection.close();
      } catch (SQLException e) {
        System.out.println("Unable to close connection");
      }
    }
    
  }
  

  private static void initializeDatabase(Connection connection) {
    try {
      databaseDriver.initialize(connection);
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }
  




}