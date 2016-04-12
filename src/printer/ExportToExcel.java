package printer;

import main.Simulator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by samikroon on 4/11/16.
 */
public class ExportToExcel {

    public void export(Simulator simulator, File file) throws IOException{
    	double currentRevenue = simulator.getSimulatorView().getRevenueView().getCurrentRevenue();
    	double expectedRevenue = simulator.getSimulatorView().getRevenueView().getExpectedRevenue();
    	double revenueFromReservation = simulator.getSimulatorView().getRevenueView().getRevenueFromReservation();
        FileWriter out = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(out);
        
        
        
        bw.write("Queue at entrance,"+simulator.getEntranceCarQueue().getQueue().size()+"\n");
        bw.write("Queue at exit,"+simulator.getExitCarQueue().getQueue().size()+"\n");
        bw.write("Queue at payment,"+simulator.getPaymentCarQueue().getQueue().size()+"\n");
        bw.write("Normal cars inside,"+simulator.getCarsNormalInside()+"\n");
        bw.write("Parking card holder cars inside,"+simulator.getCarsHoldersInside()+"\n");
        bw.write("Cars paid,"+simulator.getCarsPayed()+"\n");
        bw.write("Empty reserved spots,"+simulator.getEmptyReservedSpots()+"\n");
        bw.write("filled reserved spots,"+simulator.getCarsReservedInside()+"\n");
        
        bw.write("Current revenue,"+currentRevenue+"\n");
        bw.write("Expected revenue,"+expectedRevenue+"\n");
        bw.write("Revenue from reservation,"+revenueFromReservation+"\n");
        
        bw.close();
    }
}