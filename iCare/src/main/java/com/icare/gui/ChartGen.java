package main.java.com.icare.gui;

import java.sql.Connection;
import java.util.HashMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import main.java.com.icare.accounts.User;

public class ChartGen {

	public static ChartPanel barChartGen(CategoryDataset data) {
		JFreeChart barChart = ChartFactory.createBarChart("Sample Chart", "Category", "Score", data,
				PlotOrientation.VERTICAL, true, true, false);

		ChartPanel chartPanel = new ChartPanel(barChart);
		return chartPanel;
	}

	public static ChartPanel pieChartGen(DefaultPieDataset data) {
		JFreeChart pieChart = ChartFactory.createPieChart("Sample Pie", // chart title
				data, // data
				true, // include legend
				true, false);
		ChartPanel chartPanel = new ChartPanel(pieChart);
		//chartPanel.setPreferredSize(parent.getDefaultSize());
		return chartPanel;
	}

	private static CategoryDataset createBarDataset() {
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

	public static CategoryDataset barDataConverter(HashMap<String, HashMap<String, Double>> data){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (String mainCat :data.keySet()){
			for (String subCat: data.get(mainCat).keySet()){
				dataset.addValue(data.get(mainCat).get(subCat), subCat, mainCat);
			}
		}
		return dataset;
	}

	public static DefaultPieDataset pieDataConverter(HashMap<String, Double> data){
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (String key :data.keySet())
			dataset.setValue(key, data.get(key));
		return dataset;
	}

	private static PieDataset createPieDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("IPhone 5s", new Double(20)); //Key, Value relation
		dataset.setValue("SamSung Grand", new Double(20));
		dataset.setValue("MotoG", new Double(40));
		dataset.setValue("Nokia Lumia", new Double(10));
		return dataset;
	}
}
