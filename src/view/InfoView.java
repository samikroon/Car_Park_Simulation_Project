package view;

import main.Simulator;

import javax.swing.*;

/**
 * Created by samikroon on 4/10/16.
 */
public class InfoView extends AbstractView {
    private Object[][] data = new Object[8][2];

    public InfoView(Simulator simulator) {
        super(simulator);
        data[0][0] = "Queue at entrance";
        data[1][0] = "Queue at exit";
        data[2][0] = "Queue at payment";
        data[3][0] = "Normal cars inside";
        data[4][0] = "Parking holder cars inside";
        data[5][0] = "Cars Payed";
        data[6][0] = "Empty reserved spots";
        data[7][0] = "Filled reserved spots";
        data[0][1] = 0;
        data[1][1] = 0;
        data[2][1] = 0;
        data[3][1] = 0;
        data[4][1] = 0;
        data[5][1] = 0;
        data[6][1] = 0;
        data[7][1] = 0;
        table = new JTable(data, columnNames);
    }

    public void updateData() {
        int entranceQueue = simulator.getEntranceCarQueue().getQueue().size();
        int exitQueue = simulator.getExitCarQueue().getQueue().size();
        int paymentQueue = simulator.getPaymentCarQueue().getQueue().size();
        int normalCarsInside = simulator.getCarsNormalInside();
        int holderCarsInside = simulator.getCarsHoldersInside();
        int carsPayed = simulator.getCarsPayed();
        int emptyReservedSpots = simulator.getEmptyReservedSpots();
        int carsReservedInside = simulator.getCarsReservedInside();
        data[0][1] = entranceQueue;
        data[1][1] = exitQueue;
        data[2][1] = paymentQueue;
        data[3][1] = normalCarsInside;
        data[4][1] = holderCarsInside;
        data[5][1] = carsPayed;
        data[6][1] = emptyReservedSpots;
        data[7][1] = carsReservedInside;
        table = new JTable(data, columnNames);
    }
}
