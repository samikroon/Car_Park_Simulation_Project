package view;


import main.Simulator;
import javax.swing.*;


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
