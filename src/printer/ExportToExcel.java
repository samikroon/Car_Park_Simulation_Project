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
        FileWriter out = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(out);
        bw.write("Card holders inside:,"+simulator.getCarsHoldersInside()+"\n");
        bw.close();
    }


}
