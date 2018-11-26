package main.java.com.icare.gui;

import java.util.HashMap;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 * ChartPanel generator class that generates displayable Charts of several types
 *
 */
public class ChartGen {

	/**
	 * Generates a bar chart given a dataset generated for a specific report
	 * @param data the data to be represented by the chart in a CategoryDataset
	 * @return the ChartPanel that displays the bar chart
	 */
	public static ChartPanel barChartGen(CategoryDataset data) {
		JFreeChart barChart = ChartFactory.createBarChart("Sample Chart", "Category", "Score", data,
				PlotOrientation.VERTICAL, true, true, false);

		ChartPanel chartPanel = new ChartPanel(barChart);
		return chartPanel;
	}

	/**
	 * Generates a ChartPanel that displays a pie chart for a specific report
	 * @param data the data to be represented by the chart in a DefaultPieDataset
	 * @return the ChartPanel that displays the pie chart
	 */
	public static ChartPanel pieChartGen(DefaultPieDataset data) {
		JFreeChart pieChart = ChartFactory.createPieChart("Sample Pie", // chart title
				data, // data
				true, // include legend
				true, false);
		ChartPanel chartPanel = new ChartPanel(pieChart);
		//chartPanel.setPreferredSize(parent.getDefaultSize());
		return chartPanel;
	}

	/**
	 * Converts parsed data from query into a CategoryDataset to use for bar chart generation
	 * @param data the parsed data
	 * @return the CategoryDataset to be used in a bar chart
	 */
	public static CategoryDataset barDataConverter(HashMap<String, HashMap<String, Double>> data){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (String mainCat :data.keySet()){
			for (String subCat: data.get(mainCat).keySet()){
				dataset.addValue(data.get(mainCat).get(subCat), subCat, mainCat);
			}
		}
		return dataset;
	}

	/**
	 * Converts parsed data from query into a DefaultPieDataset to use for pie chart generation
	 * @param data the parsed data
	 * @return the DefaultPieDataset to be used in a pie chart
	 */
	public static DefaultPieDataset pieDataConverter(HashMap<String, Double> data){
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (String key :data.keySet())
			dataset.setValue(key, data.get(key));
		return dataset;
	}

}
