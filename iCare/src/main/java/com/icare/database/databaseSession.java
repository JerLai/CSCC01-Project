package main.java.com.icare.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class databaseSession extends databaseAPI{

  public static String sourceQuery(String table, String attributes){
    return "SELECT " + attributes + " FROM " + table + ";";
  }

  public static String sortQuery(String sourceQuery, String sortingCondition){
    return sourceQuery.substring(0, sourceQuery.length() - 1) + " ORDER BY " + sortingCondition + ";";
  }

  public static String filterQuery(String sourceQuery, String filterCondition){
    return sourceQuery.substring(0, sourceQuery.length() - 1) + " WHERE " + filterCondition + ";";
  }

  public static String queryData(Connection connection, String query) throws SQLException{
    String sql = query;
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    ResultSet results = preparedStatement.executeQuery();
    ResultSetMetaData resultsData = results.getMetaData();
    int columns = query.substring("SELECT ".length() , query.indexOf(" FROM")).split(",").length;
    String Output = "";
    for (int i = 1; i <= columns; i++) {
      Output += "\t" + resultsData.getColumnName(i);
    }
    Output = Output.substring(1);
    while (results.next()) {
      Output += "\n";
      for (int i = 1; i <= columns; i++) {
        Output += results.getString(i) + "\t";
      }
    }
    preparedStatement.close();
    results.close();
    return Output;
  }

  public static String queryAsHTML(Connection connection, String query) throws SQLException{
    String sql = query;
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    ResultSet results = preparedStatement.executeQuery();
    ResultSetMetaData resultsData = results.getMetaData();
    int columns = query.substring("SELECT ".length() , query.indexOf(" FROM")).split(",").length;
    String Output = "<html><table><tr>";
    for (int i = 1; i <= columns; i++) {
      Output += "<th>" + resultsData.getColumnName(i) + "</th>";
    }
    Output += "</tr>";
    while (results.next()) {
      Output += "<tr>";
      for (int i = 1; i <= columns; i++) {
        Output += "<td>" + results.getString(i) + "</td>";
      }
      Output += "</tr>";
    }
    Output += "</table></html>";
    preparedStatement.close();
    results.close();
    return Output;
  }

  public static void createTempTable(Connection connection, String table, String sourceQuery) throws SQLException{
    String sql = "CREATE TEMPORARY TABLE " + table + " AS " + sourceQuery +";";
    Statement Statement = connection.createStatement();
    Statement.executeUpdate(sql);
    Statement.close();
  }

}
