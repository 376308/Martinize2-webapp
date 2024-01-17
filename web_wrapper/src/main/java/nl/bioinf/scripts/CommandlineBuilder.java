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

    public void buildLine(File sessionDir) throws IOException {
        // TODO remove hardcoded
        String hardcoded = "python C:/Users/laris/AppData/Local/Programs/Python/Python311/Scripts/martinize2";
        String output = pdbFile + ".gro";
        String topologyFile = "topol.top";
        List<String> minimalcommand = new ArrayList<>(List.of("cmd", "/c", hardcoded, "-f", pdbFile, "-x", output, "-o", topologyFile));
        if (forcefield != null) {
            minimalcommand.add("-ff");
            minimalcommand.add(forcefield);
        }
        System.out.println(minimalcommand);
        ProcessBuilder processbuilder = new ProcessBuilder(minimalcommand);
        processbuilder.directory(sessionDir);
        processbuilder.redirectErrorStream(true);
        Process process = processbuilder.start();
        try {
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // Read the output line by line and print it
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Close the streams
            reader.close();
            inputStream.close();

            // Wait for the process to complete
            int exitCode = process.waitFor();
            System.out.println("Exit Code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void setPdbFile(String pdbFile) {
        CommandlineBuilder.pdbFile = pdbFile;
    }

}
