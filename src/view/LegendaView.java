package view;

import main.Simulator;

import javax.swing.*;

/**
 * Created by samikroon on 4/12/16.
 */
public class LegendaView extends AbstractView{
    private Object[][] data = new Object[5][2];

    public LegendaView(Simulator simulator) {
        super(simulator);
        data[0][0] = "White";
        data[1][0] = "Red";
        data[2][0] = "Cyan";
        data[3][0] = "Orange";
        data[4][0] = "Black";
        data[0][1] = "Empty parking spot";
        data[1][1] = "Spot with regular customer";
        data[2][1] = "Spot with cardholder customer";
        data[3][1] = "Reserved spot";
        data[4][1] = "Customer that reserved a spot";
        table = new JTable(data, columnNames);
    }
}
