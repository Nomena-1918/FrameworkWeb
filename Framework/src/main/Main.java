package main;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import etu1918.framework.annotationPerso.ParamValue;
import etu1918.framework.annotationPerso.URLMapping;
import utilPerso.Utilitaire;


public class Main {

    /*
    @URLMapping(value="url")
    public void testMethod(@ParamValue(value = "id") Integer id, @ParamValue(value = "nom") String nom, @ParamValue(value = "date") Date date) {
        System.out.println(id+" : "+nom+" : "+date);
    }*/

    public static void main(String[] args) throws Exception {
        System.out.println("\n\tFramework built successfully ! 🚀\n");
/*
        //Method m = Main.class.getDeclaredMethod("testMethod", Integer.class, String.class, Date.class);

        Method m = Utilitaire.getMethodeByAnnotation("URLMapping", "url", Main.class);

        //System.out.println(m);

        // Paramètres de la requête (HashMap) "données simulation"
        HashMap<String, Object> parameters = new HashMap<>();

        Object i = 8, no = "Jean";

        parameters.put("id", i);
        parameters.put("nom", no);

        // Nom des paramètres de la méthode
        List<String> list = Utilitaire.getTrueParams(m);
/* 
        for (String string : list) {
            System.out.println(string);
        }
*/   
      /*  // Liste des valeurs de ces paramètres (get dans hashmap)
        List<Object> listVal = new ArrayList<Object>();

        //Avant cast
        System.out.println("Avant cast : \n");
        for(String s : list) {
            listVal.add(parameters.get(s));
            if(parameters.get(s) != null)
                System.out.println(parameters.get(s).getClass()+" : "+parameters.get(s));
            else
            System.out.println("null : null");
        }
*/
        // Liste des types des paramètres de la méthode
        //List<Class> listC = Utilitaire.getParamType(list, m);

        // Cast de la liste des valeurs des paramètres
        /*for (int i = 0; i < listVal.size(); i++) {
            Utilitaire.convert(listVal.get(i), listC.get(i));
        }*/
/*
        // Invokation méthode
        Object[] listValOK = listVal.toArray();
        m.invoke(new Main(), listValOK);
*/
    }
}
