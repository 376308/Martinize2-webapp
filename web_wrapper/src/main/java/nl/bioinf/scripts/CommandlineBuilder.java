package nl.bioinf.scripts;

import java.io.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommandlineBuilder {

    private static String pdbFile;
    private static String forcefield = null;

    public static void setForcefield(String forcefield) {
        CommandlineBuilder.forcefield = forcefield;
    }

    public void buildLine(File sessionDir) throws IOException{
        // TODO remove hardcoded
        String hardcoded = "python C:/Users/laris/AppData/Local/Programs/Python/Python311/Scripts/martinize2";
        String output =  pdbFile + ".gro";
        String topologyFile = "topol.top";
        List<String> minimalcommand = new ArrayList<>(List.of("cmd", "/c", hardcoded, "-f", pdbFile, "-x", output, "-o", topologyFile));
        if (forcefield != null){
            minimalcommand.add( "-ff");
            minimalcommand.add( forcefield);
        }
        System.out.println(minimalcommand);
        ProcessBuilder processbuilder = new ProcessBuilder(minimalcommand);
        // TODO remove hardcoded
        processbuilder.directory(sessionDir);
        Process process = processbuilder.start();
        
        checkThread(process);
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

}
