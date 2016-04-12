package view;

import main.Simulator;
import java.awt.*;



/**
 * Created by samikroon on 4/12/16.
 */


class BarChart extends AbstractView{

    public BarChart(Simulator simulator) {
        super(simulator);

    }

    public void paintComponent(Graphics g) {
        int cardHolder = simulator.getCarsHoldersInside();
        g.setColor(Color.red);
        g.fillRect(0, 0, 150, 150);
        g.setColor(Color.cyan);
        g.fillArc(10, 10, 130, 130 , 0, cardHolder);
    }

}

