package nl.bioinf.scripts;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class CommandlineBuilder {

    private static String pdbFile;
    private static String topologyFile;

    public void buildLine() throws IOException{
        // TODO remove hardcoded
        String hardcoded = "python C:/Users/laris/AppData/Local/Programs/Python/Python311/Scripts/martinize2";
        String output =  pdbFile + ".gro";
        ProcessBuilder processbuilder = new ProcessBuilder("cmd", "/c", hardcoded, "-f", pdbFile,  "-x", output, "-o", topologyFile);
        // TODO remove hardcoded
        File output_dir = new File("C:\\Users\\laris\\Documents\\School\\Thema_10\\Martinize2-webapp\\web_wrapper\\src\\main\\webapp\\temp");
        processbuilder.directory(output_dir);
        Process test = processbuilder.start();
        checkThread(test);
    }

    public void checkThread(Process process) {
        if (process.isAlive()){
            System.out.println("Still working...");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            checkThread(process);
        }
        else {
            System.out.println("Finished");
        }
    }

    public static void setPdbFile(String pdbFile) {
        CommandlineBuilder.pdbFile = pdbFile;
    }

    public static void setTopologyFile(String topologyFile) {
        CommandlineBuilder.topologyFile = topologyFile;
    }
}
