package view;

import main.Simulator;

import javax.swing.*;

/**
 * Created by samikroon on 4/10/16.
 */
public class InfoView extends AbstractView {
    private JTable table;
    private String[] columnNames = {"nameOfData", "Value"};
    private Object[][] data = new Object[3][2];

    public InfoView(Simulator simulator) {
        super(simulator);
        data[0][0] = "Queue at entrance";
        data[1][0] = "Queue at exit";
        data[2][0] = "Queue at payment";
        data[0][1] = 0;
        data[1][1] = 0;
        data[2][1] = 0;
        table = new JTable(data, columnNames);

    }

    public void updateData() {
        int entranceQueue = simulator.getEntranceCarQueue().getNumberOfCars();
        int exitQueue = simulator.getExitCarQueue().getNumberOfCars();
        int paymentQueue = simulator.getPaymentCarQueue().getNumberOfCars();
        System.out.println("entrance: " + entranceQueue + "exit: " + exitQueue + "payment: " + paymentQueue);
        data[0][1] = entranceQueue;
        data[1][1] = exitQueue;
        data[2][1] = paymentQueue;
        table = new JTable(data, columnNames);
        repaint();

    }


    public JTable getTable() {
        return table;
    }

}
