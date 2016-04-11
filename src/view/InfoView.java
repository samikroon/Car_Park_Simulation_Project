package view;

import main.Simulator;

import javax.swing.*;

/**
 * Created by samikroon on 4/10/16.
 */
public class InfoView extends AbstractView {
    private Object[][] data = new Object[5][2];

    public InfoView(Simulator simulator) {
        super(simulator);
        data[0][0] = "Queue at entrance";
        data[1][0] = "Queue at exit";
        data[2][0] = "Queue at payment";
        data[3][0] = "Cars inside";
        data[4][0] = "Cars Payed";
        data[0][1] = 0;
        data[1][1] = 0;
        data[2][1] = 0;
        data[3][1] = 0;
        data[4][1] = 0;
        table = new JTable(data, columnNames);
    }

    public void updateData() {
        int entranceQueue = simulator.getEntranceCarQueue().getQueue().size();
        int exitQueue = simulator.getExitCarQueue().getQueue().size();
        int paymentQueue = simulator.getPaymentCarQueue().getQueue().size();
        int carsInside = simulator.getCarsInside();
        int carsPayed = simulator.getCarsPayed();
        data[0][1] = entranceQueue;
        data[1][1] = exitQueue;
        data[2][1] = paymentQueue;
        data[3][1] = carsInside;
        data[4][1] = carsPayed;
        table = new JTable(data, columnNames);
    }
}
