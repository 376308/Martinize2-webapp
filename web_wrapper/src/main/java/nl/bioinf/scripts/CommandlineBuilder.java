package nl.bioinf.scripts;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class CommandlineBuilder {

    private static String pdbFile;
    private static String topologyFile;

    public void buildLine(File sessionDir) throws IOException{
        // TODO remove hardcoded
        String hardcoded = "python C:/Users/laris/AppData/Local/Programs/Python/Python311/Scripts/martinize2";
        String output =  pdbFile + ".gro";
        ProcessBuilder processbuilder = new ProcessBuilder("cmd", "/c", hardcoded, "-f", pdbFile,  "-x", output, "-o", topologyFile);
        // TODO remove hardcoded
        processbuilder.directory(sessionDir);
        Process test = processbuilder.start();
        //TODO Check thread elsewhere, this prevents the popup message of successful upload
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
