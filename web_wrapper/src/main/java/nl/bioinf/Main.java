package nl.bioinf;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        String tmpdir = System.getProperty("java.io.tmpdir");
        System.out.println("Temp file path: " + tmpdir);
    }
}