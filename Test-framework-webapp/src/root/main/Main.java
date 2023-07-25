package root.main;

import database.ConnectionPerso;
import root.classesTest.EmpModel;
import root.classesTest.Plat;

import java.sql.Connection;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("\n\tTest-framework-webapp built successfully ! 🚀\n\n");

        Connection c = ConnectionPerso.getConnection();

        Plat plat = new Plat();
        List<Object> listPlat = plat.select(c);

        EmpModel emp = new EmpModel();
        List<Object> listEmp = emp.select(c);
    }
}
