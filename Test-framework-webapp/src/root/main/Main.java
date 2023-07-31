package root.main;

import root.classesTest.V_Empmodel_plat;
import utilPerso.Utilitaire;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("\n\tTest-framework-webapp built successfully ! 🚀\n\n");

        // Create a list and add the V_Empmodel_plat instances
        List<Object> listV_Empmodel_plat = new V_Empmodel_plat().select(null);
        String csv = Utilitaire.listToCSV(listV_Empmodel_plat, null, true);
        System.out.println(csv);

    }
}
