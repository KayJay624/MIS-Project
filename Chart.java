package badania;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;


public class Chart {
	 // Create a simple XY chart
	 public XYSeries series = new XYSeries("Best");
	 public XYSeries series2 = new XYSeries("Worst");
	 	 


	 // Add the series to your data set
	 public void display() {
		 XYSeriesCollection dataset = new XYSeriesCollection();
		 dataset.addSeries(series);
		 dataset.addSeries(series2);
		 // Generate the graph
		 JFreeChart chart = ChartFactory.createXYLineChart(
		 "XY Chart", // Title
		 "Generacja", // x-axis Label
		 "Wartoœæ funkcji fitness", // y-axis Label
		 dataset, // Dataset
		 PlotOrientation.VERTICAL, // Plot Orientation
		 true, // Show Legend
		 true, // Use tooltips
		 false // Configure chart to generate URLs?
		 );
		 ChartPanel cp = new ChartPanel(chart);
		 JFrame frame = new JFrame("Chart");
	 	frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	 	frame.setPreferredSize(new Dimension(800,500));
	 	frame.add(cp); 
	 	frame.pack();
	 	frame.setVisible(true); 
	 }
	}


