package controller;

import main.Simulator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created by samikroon on 4/6/16.
 */
public class ButtonController extends AbstractController implements ActionListener{
    private ActionEvent event;

    public ButtonController(Simulator simulator) {
        super(simulator);
    }


    public void setActionEvent(ActionEvent e) {
        event = e;
    }

    public ActionEvent getActionEvent() {
        return event;
    }



    @Override
    public void actionPerformed(ActionEvent e)
    {
        setActionEvent(e);
        Thread performerThread = new Thread() {

            public void run() {
                ActionEvent e = getActionEvent();
                String command = e.getActionCommand();

                switch (command) {
                    case "one step":
                        simulator.runTrue();
                        simulator.runSteps(1);
                        break;
                    case "hundred steps":
                        simulator.runTrue();
                        simulator.runSteps(100);
                        break;
                    case "Start":
                        simulator.runTrue();
                        simulator.runSteps(Integer.MAX_VALUE);
                        break;
                    case "Pause":
                        simulator.runFalse();
                        break;
                }
            }

        };
        performerThread.start();

    }

}
