package nl.bioinf.scripts;

import java.io.File;

public class CommandlineBuilder {

    private static String pdbFile;
    private static String topologyFile; 

    public static void buildLine() {
        ProcessBuilder processbuilder = new ProcessBuilder("-f", pdbFile,  "-x", pdbFile + "gc", "-o", topologyFile);
    }

    public static void setPdbFile(String pdbFile) {
        CommandlineBuilder.pdbFile = pdbFile;
    }

    public static void setTopologyFile(String topologyFile) {
        CommandlineBuilder.topologyFile = topologyFile;
    }
}
