package test.java.com.icare.database;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import main.java.com.icare.database.databaseAPI;

public class DatabaseAPITest {
	@Test
	@DisplayName("Full csv file")
	void testImportDataFullCSVFile () {
		LinkedHashMap <String, ArrayList<String>> fileData = new LinkedHashMap <String, ArrayList<String>>();
		ArrayList <String> data = new ArrayList <String>();
		data.add("bye");
		data.add("bye2");
		fileData.put("hello", data);
		ArrayList <String> data2 = new ArrayList <String>();
		data2.add("lol");
		data2.add("lol2");
		fileData.put("haha", data2);
		assertEquals(fileData, databaseAPI.importData("csvTest.csv"));
	}

	@Test
	@DisplayName("Missing values in csv file")
	void testImportDataMissingValuesCSVFile () {
		LinkedHashMap <String, ArrayList<String>> fileData = new LinkedHashMap <String, ArrayList<String>>();
		ArrayList <String> data = new ArrayList <String>();
		data.add("");
		data.add("bye2");
		fileData.put("hello", data);
		ArrayList <String> data2 = new ArrayList <String>();
		data2.add("lol");
		data2.add("");
		fileData.put("haha", data2);
		assertEquals(fileData, databaseAPI.importData("csvTest2.csv"));
	}

	@Test
	@DisplayName("Full csv file writing")
	void testExportDataFullCSVFile () {
		LinkedHashMap <String, ArrayList<String>> fileData = new LinkedHashMap <String, ArrayList<String>>();
		ArrayList <String> data = new ArrayList <String>();
		data.add("bye");
		data.add("bye2");
		fileData.put("hello", data);
		ArrayList <String> data2 = new ArrayList <String>();
		data2.add("lol");
		data2.add("lol2");
		fileData.put("haha", data2);
		databaseAPI.exportData(fileData, "csvTest3.csv");
		assertEquals(fileData, databaseAPI.importData("csvTest3.csv"));
	}

	@Test
	@DisplayName("Missing values in csv file writing")
	void testExportDataMissingValuesCSVFile () {
		LinkedHashMap <String, ArrayList<String>> fileData = new LinkedHashMap <String, ArrayList<String>>();
		ArrayList <String> data = new ArrayList <String>();
		data.add("");
		data.add("bye2");
		fileData.put("hello", data);
		ArrayList <String> data2 = new ArrayList <String>();
		data2.add("lol");
		data2.add("");
		fileData.put("haha", data2);
		databaseAPI.exportData(fileData, "csvTest4.csv");
		assertEquals(fileData, databaseAPI.importData("csvTest4.csv"));
	}
}
