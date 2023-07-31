package root.main;

import etu1918.framework.mapping.ModelView;
import root.classesTest.Plats;
import root.classesTest.V_Empmodel_plat;
import utilPerso.Utilitaire;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("\n\tTest-framework-webapp built successfully ! 🚀\n\n");

        // Create a list and add the V_Empmodel_plat instances
        List<Object> listV_Empmodel_plat = new V_Empmodel_plat().select(null);

        // Create an instance of Plats and set the list
        Plats plats = new Plats();
        plats.setListV_Empmodel_plat(listV_Empmodel_plat);

        ModelView m = new ModelView();
        m.addItem("plats", plats);
        m.addItem("nombre_random", 456);
        m.setView("affXML.jsp");

        StringBuilder xml = new StringBuilder();

        for (Map.Entry<String, Object> entry : m.getData().entrySet()) {
            xml.append("\n");
            xml.append(Utilitaire.toXML(entry.getValue()));
        }
        System.out.println(xml);
    }
}
