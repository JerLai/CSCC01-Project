package main.java.com.icare.gui;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.jdbc.JDBCPieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * ChartPanel generator class that
 *
 */
public class ChartGen {

	//TODO: Add button to the ChartPanel to return to a menu?

	/**
	 * Generates a ChartPanel that displays a bar chart for a specific report
	 * @param parent the parent frame that will contain the generated ChartPanel
	 * @param parsedVals the values to insert to the chart parsed from the database from a specific query
	 * @return a ChartPanel that displays the bar chart
	 */
	public static ChartPanel barChartGen(GUI parent, String chartTitle, HashMap<String, Double> parsedVals) {
		JFreeChart barChart = ChartFactory.createBarChart(chartTitle, "Category", "Score",
				createBarDataset1(parsedVals), PlotOrientation.VERTICAL, true, true, false);

		ChartPanel chartPanel = new ChartPanel(barChart);//ChartPanel extends JPanel
		chartPanel.setPreferredSize(parent.getDefaultSize());
		return chartPanel;
	}

	/**
	 * Generates a ChartPanel that displays a bar chart for a specific report
	 * @param parent the parent frame that will contain the generated ChartPanel
	 * @param parsedVals the values to insert to the chart parsed from the database from a specific query
	 * @return a ChartPanel that displays the bar chart
	 */
	public static ChartPanel barChartGen2(GUI parent, String chartTitle, HashMap<String, HashMap<String, Double>> parsedVals) {
		JFreeChart barChart = ChartFactory.createBarChart(chartTitle, "Category", "Score",
				createBarDataset2(parsedVals), PlotOrientation.VERTICAL, true, true, false);

		ChartPanel chartPanel = new ChartPanel(barChart);//ChartPanel extends JPanel
		chartPanel.setPreferredSize(parent.getDefaultSize());
		return chartPanel;
	}

	/**
	 * Generates a ChartPanel that displays a PiePlot for a specific report
	 * 
	 * @param parent     the parent frame that will contain the generated ChartPanel
	 * @param chartTitle the title of the Chart
	 * @param parsedVals the values to insert to the chart parsed from the database
	 *                   from a specific query
	 * @return a ChartPanel that displays the PiePlot
	 */
	public static ChartPanel pieChartGen(GUI parent, String chartTitle, HashMap<String, Double> parsedVals) {
		JFreeChart pieChart = ChartFactory.createPieChart(chartTitle, // chart title
				createPieDataset(parsedVals), // data
				true, // include legend
				true, false);
		ChartPanel chartPanel = new ChartPanel(pieChart);

		return chartPanel;
	}

	/**
	 * Generates a ChartPanel that displays a PiePlot for a specific report given by
	 * the query itself
	 * 
	 * @param parent     the parent frame that will contain the generated ChartPanel
	 * @param connection the connection to the database
	 * @param chartTitle the title of the Chart
	 * @param query      the SQL query to generate the chart for
	 * @return a ChartPanel that displays the PiePlot
	 * @throws SQLException
	 */
	public static ChartPanel pieChartGen2(GUI parent, Connection connection, String chartTitle, String query)
			throws SQLException {
		JFreeChart pieChart = ChartFactory.createPieChart(chartTitle, // chart title
				createPieDataset2(connection, query), // data
				true, // include legend
				true, false);
		ChartPanel chartPanel = new ChartPanel(pieChart);

		return chartPanel;
	}

	/**
	 * Generates a ChartPanel that displays a line graph
	 * @param parent
	 * @return
	 */
	public static ChartPanel lineChartGen(GUI parent) {
		JFreeChart xylineChart = ChartFactory.createXYLineChart("Sample Chart", "Category", "Score", createXYDataset(),
				PlotOrientation.VERTICAL, true, true, false);
		ChartPanel chartPanel = new ChartPanel(xylineChart);
		chartPanel.setPreferredSize(parent.getDefaultSize());
		return chartPanel;
	}

	/**
	 * Generates the Dataset of values to be represented by a bar chart
	 * @param parsedVals the values to create a Dataset for
	 * @return a Dataset to be used by a bar chart
	 */
	private static CategoryDataset createBarDataset1(HashMap<String, Double> parsedVals) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (Entry<String, Double> entry: parsedVals.entrySet()) {
			dataset.setValue(entry.getValue(), entry.getKey(), "");
		}
		return dataset;
	}
	/**
	 * Generates the Dataset of values to be represented by a bar chart DO NOT USE
	 * @param parsedVals the values to create a Dataset for
	 * @return a Dataset to be used by a bar chart
	 */
	private static CategoryDataset createBarDataset2(HashMap<String, HashMap<String, Double>> parsedVals) {
		final String fiat = "FIAT";
		final String audi = "AUDI";
		final String ford = "FORD";
		final String speed = "Speed";
		final String millage = "Millage";
		final String userrating = "User Rating";
		final String safety = "safety";
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		
		dataset.addValue(1.0, fiat, speed);
		dataset.addValue(3.0, fiat, userrating);
		dataset.addValue(5.0, fiat, millage);
		dataset.addValue(5.0, fiat, safety);

		dataset.addValue(5.0, audi, speed);
		dataset.addValue(6.0, audi, userrating);
		dataset.addValue(10.0, audi, millage);
		dataset.addValue(4.0, audi, safety);

		dataset.addValue(4.0, ford, speed);
		dataset.addValue(2.0, ford, userrating);
		dataset.addValue(3.0, ford, millage);
		dataset.addValue(6.0, ford, safety);

		return dataset;
	}

	/**
	 * Generates the Dataset of values to be represented by a pie chart
	 * @param parsedVals the values to create a Dataset for
	 * @return a dataset to be used by a pie chart
	 */
	private static PieDataset createPieDataset(HashMap<String, Double> parsedVals) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (String category : parsedVals.keySet()) {
			dataset.setValue(category, parsedVals.get(category));
		}
//		dataset.setValue("IPhone 5s", new Double(20)); // Key, Value relation
//		dataset.setValue("SamSung Grand", new Double(20));
//		dataset.setValue("MotoG", new Double(40));
//		dataset.setValue("Nokia Lumia", new Double(10));
		return dataset;
	}

	/**
	 * Proof of concept DO NOT USE
	 * @param connection
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	private static PieDataset createPieDataset2(Connection connection, String query) throws SQLException {
		JDBCPieDataset dataset = new JDBCPieDataset(connection, query);
//		dataset.setValue("IPhone 5s", new Double(20)); // Key, Value relation
//		dataset.setValue("SamSung Grand", new Double(20));
//		dataset.setValue("MotoG", new Double(40));
//		dataset.setValue("Nokia Lumia", new Double(10));
		return dataset;
	}

	/**
	 * Proof of concept DO NOT USE
	 * @return
	 */
	private static XYDataset createXYDataset() {
		final XYSeries firefox = new XYSeries("Firefox");
		firefox.add(1.0, 1.0);//(x value and y value respectively)
		firefox.add(2.0, 4.0);
		firefox.add(3.0, 3.0);

		final XYSeries chrome = new XYSeries("Chrome");
		chrome.add(1.0, 4.0);
		chrome.add(2.0, 5.0);
		chrome.add(3.0, 6.0);

		final XYSeries iexplorer = new XYSeries("InternetExplorer");
		iexplorer.add(3.0, 4.0);
		iexplorer.add(4.0, 5.0);
		iexplorer.add(5.0, 4.0);
		XYSeriesCollection dataset = new XYSeriesCollection();
		return dataset;
	}
}
