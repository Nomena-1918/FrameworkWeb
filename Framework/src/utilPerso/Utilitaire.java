package utilPerso;

import etu1918.framework.annotationPerso.Model;
import etu1918.framework.mapping.Mapping;
import etu1918.framework.annotationPerso.URLMapping;
import etu1918.framework.mapping.ModelView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Utilitaire {

    // ===================== SETTERS ===================== //

    // Tous les setters
    public static List<Method> getSetters(Object o) {
        List<Method> setters = new ArrayList<>();

        Method[] methods = o.getClass().getDeclaredMethods();

        for (Method method : methods) {
            if (method.getName().startsWith("set")) {
                setters.add(method);
            }
        }
        return setters;
    }


    // Setters valides : Correspondants bien aux attributs
    public static List<Method> getSettersOK(Object o) {

        List<Method> setters = Utilitaire.getSetters(o);
        List<Method> settersOk = new ArrayList<>();

        return getMethods(o, setters, settersOk);
    }

    public static void toSet(String set, Object object, Object arg) throws Exception {

        Method setter = object.getClass().getDeclaredMethod(set, String.class);
        if (Utilitaire.getSettersOK(object).contains(setter)) {

            // Appeler le setter
            setter.invoke(object, arg.toString());
        }
        else
            throw new Exception("Setter : "+set+" invalide");
    }

    // -------------------- GETTERS -------------------------------------- //

    // Tous les getters
    public static List<Method> getGetters(Object o) {
        List<Method> getters = new ArrayList<>();

        Method[] methods = o.getClass().getDeclaredMethods();

        for (Method method : methods) {
            if (method.getName().startsWith("get")) {
                getters.add(method);
            }
        }
        return getters;
    }

    // Getters valides : Correspondants bien aux attributs
    public static List<Method> getGettersOK(Object o) {

        List<Method> getters = Utilitaire.getGetters(o), gettersOk;
        gettersOk = new ArrayList<>();

        return getMethods(o, getters, gettersOk);
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

    public static Object toGet(String get, Object object) throws Exception {

        Method getter = object.getClass().getDeclaredMethod(get);
        if (Utilitaire.getGettersOK(object).contains(getter)) {

            // Appeler le setter
            return getter.invoke(object);
        }
        else
            throw new Exception("Getter : "+get+" invalide");

    }

    public static List<String> getInfoURL(HttpServletRequest req) {
        return Arrays.asList(req.getServletPath().split("/"));
    }

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

    public static boolean isNumeric(String s)
    {
        if (s == null || s.equals("")) {
            return false;
        }

        return s.chars().allMatch(Character::isDigit);
    }

}

