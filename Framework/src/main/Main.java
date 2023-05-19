package main;
/* 
import etu1918.framework.Test;
import etu1918.framework.annotationPerso.Model;
import etu1918.framework.annotationPerso.ParamTest;
import etu1918.framework.annotationPerso.ParamValue;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
*/
import java.lang.reflect.InvocationTargetException;

//@Model
public class Main {
    //public void testMethod(@ParamValue(value = "param") String hey) {}

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        /* 
        Test test = new Test();
        Annotation[] listAnnot = test.getClass().getAnnotations();

        // Annotation de classe
        for (Annotation a : listAnnot)
            System.out.println("Nom de l'annotation  : "+a.annotationType().getSimpleName());


        // Récupération des méthodes de notre objet Test
        Method[] listMethod = test.getClass().getDeclaredMethods();
        Parameter[] listParam;
        Method annotMeth;
        Object valueAnnot = null;
        Annotation annot;

        // Recherche des paramètres annotés de ces méthodes
        for (Method m : listMethod) {
            listParam = m.getParameters();
            for (Parameter p : listParam) {
                if (p.isAnnotationPresent(ParamTest.class)) {
                    annot = p.getAnnotation(ParamTest.class);
                    System.out.println("\n Annotation trouvée : " + annot);

                    // Récupération de la valeur d'un attribut de l'annotation
                    annotMeth = p.getAnnotation(ParamTest.class).annotationType().getDeclaredMethod("listString");
                    valueAnnot = annotMeth.invoke(annot);
                }
            }
        }

        assert valueAnnot != null;
        System.out.println("\nLa valeur de l'annotation d'argument de méthode : ");

        for(String s : (String[]) valueAnnot)
            System.out.println(s);
*/

            System.out.println("\n\tFramework built successfully ! 🚀\n");

        }
       
}
