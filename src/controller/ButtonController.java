package controller;

import main.Simulator;
import printer.ExportToExcel;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


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
                ActionEvent event = getActionEvent();
                String command = event.getActionCommand();

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
                    case "Exit Application":
                    	System.exit(0);
                    	break;
                    case "Save stats to file":
                        try {
                            ExportToExcel export = new ExportToExcel();
                            JFileChooser chooser = new JFileChooser();
                            chooser.setFileFilter(new FileNameExtensionFilter("csv", "csv"));
                            chooser.setSelectedFile(new File("example.csv"));
                            int option = chooser.showSaveDialog(null);
                            if (option == JFileChooser.APPROVE_OPTION)
                            {
                                export.export(simulator, chooser.getSelectedFile());
                            }
                            JOptionPane.showMessageDialog(simulator.getSimulatorView(), "Successfully saved the file");
                        } catch (IOException e) {
                            e.getMessage();
                        }
                        break;
                    case "Edit incoming cars":
                        JPanel popup = new JPanel();

                        //weekDayArrivals= 200; // average number of arriving cars per hour
                        //int weekendArrivals = 90;
                }
            }

        };
        performerThread.start();

    }

}
