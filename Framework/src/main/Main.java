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

//@Model
public class Main {
    @URLMapping(value="url")
    public void testMethod(@ParamValue(value = "id") Integer id, @ParamValue(value = "nom") String nom, @ParamValue(value = "date") Date date) {}

    public static void main(String[] args) throws Exception {
        System.out.println("\n\tFramework built successfully ! 🚀\n");

    
        //Method m = Main.class.getDeclaredMethod("testMethod", Integer.class, String.class, Date.class);

        Method m = Utilitaire.getMethodeByAnnotation("URLMapping", "url", Main.class);

        System.out.println(m);

        HashMap<String, Class<?>> listP = getAnnotParamMethod(m);

        // Affichage de ces paramètres
        for (Map.Entry<String, Class<?>> me : listP.entrySet())
            System.out.println("Nom param : " + me.getKey() + ", Type param : " + me.getValue().toString());


        List<String> l = new ArrayList<>();
        l.contains(l);


    }


    
}
