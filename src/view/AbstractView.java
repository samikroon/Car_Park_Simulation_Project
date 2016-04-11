package view;


import main.Simulator;

import javax.swing.*;
import java.awt.*;

/**
 * Created by samikroon on 4/10/16.
 */
public class AbstractView extends JFrame{

    protected Simulator simulator;

    public AbstractView (Simulator simulator) {
        this.simulator = simulator;
    }

}
