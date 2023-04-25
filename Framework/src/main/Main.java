package main;
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("\nFramework built successfully ! 🚀");

        Class fieldC = Boolean.class;
        String s = "true";

        Boolean b = (Boolean) fieldC.cast(s);

    }
}