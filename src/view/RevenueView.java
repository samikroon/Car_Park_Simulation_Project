package view;

import main.Simulator;

import javax.swing.*;

/**
 * Created by samikroon on 4/11/16.
 */
public class RevenueView extends AbstractView{
    private Object[][] data = new Object[2][2];
    private double price = 8.0;

    public RevenueView(Simulator simulator) {
        super(simulator);
        data[0][0] = "Current revenue";
        data[1][0] = "Expected revenue";
        data[0][1] = 0;
        data[1][1] = 0;
        table = new JTable(data, columnNames);
    }

    public void updateRevenueView() {
        double currentRevenue = (simulator.getCarsPayed() * price) + simulator.getPassHoldersPayment().getParkingHolderRevenue();
        double expectedRevenue = ((simulator.getCarsPayed() + simulator.getCarsNormalInside()) * price) +
                (simulator.getPassHoldersPayment().getParkingHolderRevenue() + (simulator.getCarsHoldersInside() * simulator.getPassHoldersPayment().getPrice()) );
        data[0][1] = currentRevenue;
        data[1][1]= expectedRevenue;
        table = new JTable(data, columnNames);
    }
}
