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


    public BarChart(Simulator simulator) {
        super(simulator);
    }

    public void setDataset() {
        double cardHolder = (double) simulator.getCarsHoldersInside();
        System.out.println(cardHolder);
        double normalCar = (double) simulator.getCarsNormalInside();
        double totalCars = cardHolder+normalCar;
        System.out.println(totalCars);
        double percentageCardHolder;
        double percentageNormalCar;
        if (totalCars != 0) {
            percentageCardHolder = (cardHolder / totalCars) * 100;
            System.out.println(percentageCardHolder);
            percentageNormalCar = (normalCar / totalCars) * 100;
            System.out.println(percentageNormalCar);
        } else {
            percentageCardHolder = 0;
            percentageNormalCar = 0;
        }
        System.out.println(percentageCardHolder);


        dataset.setValue("Cardholders inside", percentageCardHolder);
        dataset.setValue("Normal customers inside", percentageNormalCar);
    }

    public ChartPanel createChart() {
        JFreeChart pieChart = ChartFactory.createPieChart(
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

