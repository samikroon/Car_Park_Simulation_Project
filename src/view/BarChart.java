package view;

import main.Simulator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;



/**
 * Created by samikroon on 4/12/16.
 */


class BarChart extends AbstractView{
    private DefaultPieDataset dataset = new DefaultPieDataset();
    private JFreeChart pieChart;


    public BarChart(Simulator simulator) {
        super(simulator);
    }

    public void setDataset() {
        double cardHolder = (double) simulator.getCarsHoldersInside();
        double normalCar = (double) simulator.getCarsNormalInside();
        double totalCars = cardHolder+normalCar;
        double percentageCardHolder;
        double percentageNormalCar;
        if (totalCars != 0) {
            percentageCardHolder = (cardHolder / totalCars) * 100;
            percentageNormalCar = (normalCar / totalCars) * 100;
        } else {
            percentageCardHolder = 0;
            percentageNormalCar = 0;
        }


        dataset.setValue("Cardholders inside", percentageCardHolder);
        dataset.setValue("Normal customers inside", percentageNormalCar);
    }

    public ChartPanel createChart() {
        pieChart = ChartFactory.createPieChart(
                "Cars inside",
                dataset,
                true,
                true,
                false
        );

        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        plot.setNoDataMessage("There is no info yet");
        plot.setCircular(false);
        plot.setLabelGap(0.02);
        repaint();
        return new ChartPanel(pieChart);
    }



}

