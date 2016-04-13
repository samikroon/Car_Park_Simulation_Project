package controller;

import main.Simulator;
import printer.ExportToExcel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;



public class ButtonController extends AbstractController implements ActionListener{
    private ActionEvent event;
    private Boolean alreadyRunning = false;
    private Thread performerThread;

    public ButtonController(Simulator simulator) {
        super(simulator);
    }


    public void setActionEvent(ActionEvent e) {
        event = e;
    }

    public ActionEvent getActionEvent() {
        return event;
    }


    public void setAlreadyRunning(boolean alreadyRunning) {
        this.alreadyRunning = alreadyRunning;
    }

    public Thread getPerformerThread() {
        return performerThread;
    }



    @Override
    public void actionPerformed(ActionEvent e)
    {
        setActionEvent(e);
        performerThread = new Thread() {

            public void run() {
                ActionEvent event = getActionEvent();
                String command = event.getActionCommand();

                switch (command) {
                    case "one step":
                        if (!alreadyRunning) {
                            simulator.runTrue();
                            alreadyRunning = true;
                            simulator.runSteps(1);
                        }
                        break;
                    case "hundred steps":
                        if (!alreadyRunning) {
                            simulator.runTrue();
                            alreadyRunning = true;
                            simulator.runSteps(100);
                        }
                        break;
                    case "Start":
                        if (!alreadyRunning) {
                            simulator.runTrue();
                            alreadyRunning = true;
                            simulator.runSteps(Integer.MAX_VALUE);
                        }
                        break;
                    case "Pause":
                        alreadyRunning = false;
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
                        JTextField weekField = new JTextField(5);
                        JTextField weekendField = new JTextField(5);

                        JPanel popup = new JPanel();
                        popup.add(new JLabel("Number of arrivals during the week:"));
                        popup.add(weekField);
                        popup.add(new JLabel("Number of arrivals during the weekend:"));
                        popup.add(weekendField);

                        int result = JOptionPane.showConfirmDialog(null, popup,
                                "Please enter the number in integer form, the standard is 50:90", JOptionPane.OK_CANCEL_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
                            try{
                                if (weekField.getText() != null) {
                                    int week  = Integer.parseInt(weekField.getText());
                                    simulator.setWeekDayArrivals(week);
                                }
                                if (weekendField.getText() != null) {
                                    int weekend = Integer.parseInt(weekendField.getText());
                                    simulator.setWeekendArrivals(weekend);
                                }
                            }catch (NumberFormatException ex) {
                                ex.getMessage();
                                JOptionPane.showMessageDialog(simulator.getSimulatorView(), "You did not fill in an Integer");
                            }
                        }
                        break;
                }
            }

        };
        performerThread.start();
    }

}
