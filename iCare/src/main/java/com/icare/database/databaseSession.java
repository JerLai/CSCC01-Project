package main.java.com.icare.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class databaseSession extends databaseAPI{
	
	public static String queryData(Connection connection, String table, String attributes) throws SQLException{
		String sql = "SELECT " + attributes + " FROM " + table + ";";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet results = preparedStatement.executeQuery();
		ResultSetMetaData resultsData = results.getMetaData();
		int columns = getTableColumnData(connection, table).size();
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
		System.out.println(String.format(Output));
		preparedStatement.close();
		results.close();
		return "";
	}
	
	
	public static String queryData(Connection connection, String table, String attributes, String condition) throws SQLException{
		String sql = "SELECT " + attributes + " FROM " + table + " WHERE " + condition + ";";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet results = preparedStatement.executeQuery();
		preparedStatement.close();
		ResultSetMetaData resultsData = results.getMetaData();
		int columnsNumber = resultsData.getColumnCount();
		while (results.next()) {
		    for (int i = 1; i <= columnsNumber; i++) {
		        if (i > 1) System.out.print(",  ");
		        String columnValue = results.getString(i);
		        System.out.print(columnValue + " " + resultsData.getColumnName(i));
		    }
		    System.out.println("");
		}
		results.close();
		return "";
	}
	
	public static String sourceQuery(String table, String attributes){
		return "SELECT " + attributes + " FROM " + table + ";";
	}
	
	public static String sourceQuery(String table, String attributes, String condition){
		return "SELECT " + attributes + " FROM " + table + " WHERE " + condition + ";";
	}
	
	public static void createTempTable(Connection connection, String table, String sourceQuery) throws SQLException{
		String sql = "CREATE TEMPORARY TABLE " + table + " AS " + sourceQuery +";";
		Statement Statement = connection.createStatement();
		Statement.executeUpdate(sql);
		Statement.close();
	}
	
}
