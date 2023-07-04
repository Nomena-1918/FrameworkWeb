package main;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import etu1918.framework.Test;
import etu1918.framework.annotationPerso.ParamValue;
import etu1918.framework.annotationPerso.URLMapping;
import utilPerso.Utilitaire;


public class Main {
    public static void main(String[] args) throws Exception {

        // Crée un objet de test
        Test myObject = new Test();
        myObject.setName("John Doe");
        myObject.setAge(30);
        myObject.setAddress("123 Main St");

        System.out.println("Objet avant réinitialisation : " + myObject);

        try {
            // Réinitialise l'objet
            Utilitaire.resetObject(myObject);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        System.out.println("Objet après réinitialisation : " + myObject);


        //System.out.println("\n\tFramework built successfully ! 🚀\n");
    }
}
