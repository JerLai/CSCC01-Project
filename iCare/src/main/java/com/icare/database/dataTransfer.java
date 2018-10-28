package main.java.com.icare.database;

import java.util.ArrayList;
import java.util.HashMap;

public interface dataTransfer {

	HashMap <String, ArrayList<String>> importData (String filename);

	void exportData (HashMap <String, ArrayList<String>> fileData);
}
