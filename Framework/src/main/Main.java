package main;

import etu1918.framework.annotationPerso.ParamValue;
import utilPerso.Utilitaire;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public void testMethod(@ParamValue(value = "param") String heyyy) {}

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println("\nFramework built successfully ! 🚀");
        Main main = new Main();

        Method method = main.getClass().getMethod("testMethod", String.class);

        System.out.println(method.getParameterCount());
        System.out.println(method.getParameterTypes()[0]);
        System.out.println(Utilitaire.isMethodParam(method, "param"));
    }
}