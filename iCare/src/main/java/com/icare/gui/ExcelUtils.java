package main.java.com.icare.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Filter to be used by ExcelFilter to search and display only Excel files
 * @author Jeremy Lai
 *
 */
public class ExcelUtils {

	public final static String [] strings = {"xls", "xlsx"}; //"xlsb", "xlsm"};
	public final static ArrayList<String> acceptedExts = new ArrayList<String>(Arrays.asList(strings));

	/**
	 * Gets the extension of a file
	 * @param file the file currently selected
	 * @return the extension of the file
	 */
	public static String getExtension(File file) {
		String ext = null;
		String fileName = file.getName();
		int i = fileName.lastIndexOf('.');

		if (i > 0 && i < fileName.length() - 1) {
			ext = fileName.substring(i+1).toLowerCase();
		}
		return ext;
	}
}
