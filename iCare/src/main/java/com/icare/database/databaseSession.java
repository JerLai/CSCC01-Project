package main.java.com.icare.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import net.proteanit.sql.DbUtils;

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
		int columns = resultsData.getColumnCount();
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

	public static TableModel queryTempJTable(Connection connection, String query) throws SQLException{
		deleteTable(connection, "Scratch_Table");
		createTempTable(connection, "Scratch_Table", query);
		return queryJTable(connection, "SELECT * FROM Scratch_Table;");
	}
	
	public static TableModel queryJTable(Connection connection, String query) throws SQLException{
		String sql = query;
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet results = preparedStatement.executeQuery();
		return DbUtils.resultSetToTableModel(results);
	}


	public static String queryAsHTML(Connection connection, String query) throws SQLException{
		PreparedStatement preparedStatement = null;
		ResultSet results = null;
		try {
			String sql = query;
			preparedStatement = connection.prepareStatement(sql);
			results = preparedStatement.executeQuery();
			ResultSetMetaData resultsData = results.getMetaData();
			int columns = resultsData.getColumnCount();
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
		} finally {
			preparedStatement.close();
			results.close();
		}
	}

	public static void createTempTable(Connection connection, String table, String sourceQuery) throws SQLException{
		String sql = "CREATE TEMPORARY TABLE IF NOT EXISTS " + table + " AS " + sourceQuery +";";
		Statement Statement = connection.createStatement();
		Statement.executeUpdate(sql);
		Statement.close();
	}

	public static ArrayList<String> getAllTables(Connection connection) throws SQLException{
		ArrayList<String> tableNames = new ArrayList<String>();
		DatabaseMetaData tables = connection.getMetaData();
		ResultSet results = tables.getTables(null, null, "%", null);
		while (results.next()) {
			tableNames.add(results.getString("TABLE_NAME"));
		}
		results.close();
		return tableNames;

	}

	public static String findPrimaryKey(Connection connection, String query) throws SQLException{
		String table = query.substring(query.indexOf("from")).split(" ")[1];
		String sql = "pragma table_info(" + table + ");";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet results = preparedStatement.executeQuery();
		String primaryKey = null;
		while (results.next()) {
			if (results.getString(6).equals("1")){
				primaryKey = results.getString(2);
				break;
			}
		}
		results.close();
		return primaryKey;
	}

	public static ArrayList<String> getAllColumnNames(Connection connection, String table) throws SQLException{
		ArrayList<String> columns = new ArrayList<String>();
		String sql = "pragma table_info(" + table + ");";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet results = preparedStatement.executeQuery();
		String columnData;
		while (results.next()) {
			columnData = results.getString(2);
			columns.add(columnData);
		}
		results.close();
		return columns;
	}

}
