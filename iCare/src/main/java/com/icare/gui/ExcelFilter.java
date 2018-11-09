package main.java.com.icare.gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class ExcelFilter extends FileFilter{

	/**
	 * Regulates what types of files or directories can be accepted, in this case Excel files and directories
	 */
	public boolean accept(File file) {
		// Needed to traverse file system
		if (file.isDirectory()) {
			return true;
		}
		// Get the extension of the file and see if it's an Excel file
		String extension = ExcelUtils.getExtension(file);
		if (extension != null) {
			return (ExcelUtils.acceptedExts).contains(extension);
		}
		return false;
	}

	@Override
	public String getDescription() {
		StringBuilder descriptor = new StringBuilder("Excel File(");
		//return "Excel File(.xls, .xlsx, .xlsm, .xlsb)";
		for (String ext : ExcelUtils.acceptedExts) {
			descriptor.append(String.format(".%s, ", ext));
		}
		descriptor.deleteCharAt(descriptor.length()-1);
		descriptor.append(')');
		return descriptor.toString();
	}
}
