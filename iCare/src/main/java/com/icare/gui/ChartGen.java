package main.java.com.icare.gui;

import java.sql.Connection;

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

	public static ChartPanel barChartGen(Connection connection, User userSession, GUI parent) {
		JFreeChart barChart = ChartFactory.createBarChart("Sample Chart", "Category", "Score", createBarDataset(),
				PlotOrientation.VERTICAL, true, true, false);

		ChartPanel chartPanel = new ChartPanel(barChart);
		chartPanel.setPreferredSize(parent.getDefaultSize());
		return chartPanel;
	}

	public static ChartPanel pieChartGen(Connection connection, User userSession, GUI parent) {
		JFreeChart pieChart = ChartFactory.createPieChart("Mobile Sales", // chart title
				createPieDataset(), // data
				true, // include legend
				true, false);
		ChartPanel chartPanel = new ChartPanel(pieChart);
		chartPanel.setPreferredSize(parent.getDefaultSize());
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

	private static PieDataset createPieDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("IPhone 5s", new Double(20)); //Key, Value relation
		dataset.setValue("SamSung Grand", new Double(20));
		dataset.setValue("MotoG", new Double(40));
		dataset.setValue("Nokia Lumia", new Double(10));
		return dataset;
	}
}
