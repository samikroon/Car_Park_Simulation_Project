package view;


import main.Simulator;

import javax.swing.*;
import java.awt.*;

/**
 * Created by samikroon on 4/10/16.
 */
public class AbstractView extends JFrame{
    protected JTable table;
    protected String[] columnNames = {"nameOfData", "Value"};
    protected Simulator simulator;

    public AbstractView (Simulator simulator) {
        this.simulator = simulator;
    }

    public JTable getTable() {
        return table;
    }
}
