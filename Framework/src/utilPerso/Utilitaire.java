package utilPerso;

import etu1918.framework.annotationPerso.Model;
import etu1918.framework.mapping.Mapping;
import etu1918.framework.annotationPerso.URLMapping;
import etu1918.framework.mapping.ModelView;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Utilitaire {

// ===================== COMMUN : SETTERS et GETTERS ===================== //
    // Tous les setters ou getters
    public static List<Method> getAllMethodSG(Object o, String Set_Or_Get) {
        List<Method> meths = new ArrayList<>();

        Method[] methods = o.getClass().getDeclaredMethods();

        for (Method method : methods) {
            if (method.getName().startsWith(Set_Or_Get)) {
                meths.add(method);
            }
        }
        return meths;
    }


    // Setters ou Getters valides : Correspondants bien aux attributs
    public static List<Method> getMethodSG_OK(Object o, String Set_Or_Get) {

        List<Method> setters = Utilitaire.getAllMethodSG(o, Set_Or_Get);
        List<Method> settersOk = new ArrayList<>();

        return getMethods(o, setters, settersOk);
    }

    // Setters ou Getters valides (pour formulaire) : Correspondants bien aux attributs + S
    public static List<Method> getMethodSG_OK_str(Object o, String Set_Or_Get) {

        List<Method> setters = Utilitaire.getAllMethodSG(o, Set_Or_Get);
        List<Method> settersOk = new ArrayList<>();

        return getMethods(o, setters, settersOk);
    }


    private static List<Method> getMethods(Object o, List<Method> getters, List<Method> gettersOk) {
        Field[] fields = o.getClass().getDeclaredFields();

        String s1;

        for (Method getter : getters) {

            s1 = getter.getName().substring(3);

            for (Field f : fields) {
                if (s1.equalsIgnoreCase(f.getName())) {
                    gettersOk.add(getter);
                }
            }
        }
        return gettersOk;
    }








    // ===================== SETTER GÉNÉRALISÉ ===================== //
    public static void toSet(String setterName, Object object, Object arg, Class argType) throws Exception {

        Method setter = object.getClass().getDeclaredMethod(setterName, argType);

        if (Utilitaire.getMethodSG_OK(object,"set").contains(setter)) {

            if (argType.isInstance(arg)) {
                // Appeler le setter
                setter.invoke(object, arg);
            }
        }
        else
            throw new Exception("Setter : "+setterName+" invalide");
    }

    // -------------------- GETTER GÉNÉRALISÉ -------------------------------------- //
    public static Object toGet(String getterName, Object object) throws Exception {

        Method getter = object.getClass().getDeclaredMethod(getterName);
        List<Method> listGetOk = Utilitaire.getMethodSG_OK(object, "get");

        if (listGetOk.contains(getter)) {

            // Appeler le setter
            return getter.invoke(object);
        }

        else
            throw new Exception("Getter : "+getterName+" invalide");

    }


//==================== PACKAGES SCAN ====================//
    public static List<Class> getClasses(String path) throws Exception {
        List<Class> classes = new ArrayList<Class>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        File[] dirs = new File(path).listFiles();

        if (dirs != null)
            for (File dir : dirs) {
                if (dir.isDirectory())
                    classes.addAll(findClasses(dir, dir.getName()));
                else
                    if (dir.getName().endsWith(".class"))
                        classes.add(classLoader.loadClass(dir.getName()));
            }

        return classes;
    }

    public static List<Class> findClasses(File directory, String packageName) throws Exception {
        List<Class> classes = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        String className;
        if (files != null)
            for (File file : files) {
                if (file.isDirectory()) {
                    classes.addAll(findClasses(file, packageName + "." + file.getName()));
                } else if (file.getName().endsWith(".class")) {
                    className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                    classes.add(classLoader.loadClass(className));
                }
            }
        else
            throw new Exception("Il n'y a pas de fichiers dans : "+directory);
        return classes;
    }

    //Lister les classes dans la liste des packages en argument
    public static HashMap<String, Mapping> initHashMap(String pack) throws Exception {

        List<Class> listClass = new ArrayList<>(Utilitaire.getClasses(pack));

        Method[] meths;

        HashMap<String, Mapping> mappingUrls = new HashMap<>();
        Annotation annot;
        String valueUrl = null;
        Method annotMeth;
        Mapping mapping;

        for (Class c : listClass) {
            if (c.isAnnotationPresent(Model.class)) {
                meths = c.getDeclaredMethods();
                for (Method m : meths) {
                    if (m.isAnnotationPresent(URLMapping.class)) {

                        annot = m.getAnnotation(URLMapping.class);
                        annotMeth = annot.annotationType().getDeclaredMethod("valeur");
                        valueUrl = annotMeth.invoke(annot).toString();

                        mapping = new Mapping(c.getName(), m.getName());
                        mappingUrls.put(valueUrl, mapping);

                    }
                }
            }
        }

        return mappingUrls;

    }

//============== VALIDATION / CONTROLE DE VALEURS ================//

    public static List<String> getInfoURL(HttpServletRequest req) {
        return Arrays.asList(req.getServletPath().split("/"));
    }


    public static boolean isNumeric(String s) {
        if (s == null || s.equals(""))
            return false;

        return s.chars().allMatch(Character::isDigit);
    }

    public static boolean isArrayNumeric(String[] tab) {
        boolean resp = true;

        for (String s : tab)
            if (!Utilitaire.isNumeric(s))
                return false;

        return resp;
    }



//============== GESTION DE FICHIERS ================//

    public void WriteObjectToFile(Object serObj, String filepath) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(serObj);
            objectOut.close();
            System.out.println("The Object  was succesfully written to a file");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Object ReadObjectFromFile(String filepath) {

        try {

            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            Object obj = objectIn.readObject();

            System.out.println("\n\nThe Object has been read from the file");
            objectIn.close();

            return obj;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}

