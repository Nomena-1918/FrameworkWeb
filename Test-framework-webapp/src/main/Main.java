package main;

import utilPerso.Utilitaire;

public class Main {
    boolean truc = false;

    public void setTruc(boolean truc) {
        this.truc = truc;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println("\nTest-framework-webapp built successfully ! 🚀");

        Main m = new Main();

        Boolean a = true;

        m.setTruc(a);

        System.out.println(m.truc);


        double f = 234567.456789;
        int e = (int) f;

        String g = String.valueOf(f);

        System.out.println(Utilitaire.isNumeric(g));
    }
}
