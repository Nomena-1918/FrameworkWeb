package utilPerso;

import etu1918.framework.annotationPerso.Model;
import etu1918.framework.mapping.Mapping;
import etu1918.framework.annotationPerso.URLMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class Utilitaire {

    // Setters
    public List<Method> getSetters() {
        List<Method> setters = new ArrayList<>();

        Method[] methods = this.getClass().getDeclaredMethods();

        for (int i = 0; i < methods.length; i++) {

            if (methods[i].getName().startsWith("set") == true) {
                setters.add(methods[i]);
            }
        }
        return setters;
    }


    // Setters valides : Correspondants bien aux attributs
    public List<Method> getSettersOK() {

        List<Method> setters = this.getSetters();
        List<Method> settersOk = new ArrayList<>();

        Field[] fields = this.getClass().getDeclaredFields();

        String s1;

        for(int i = 0; i<setters.size(); i++) {

            s1 = setters.get(i).getName().substring(3);

            for (Field f : fields) {
                if (s1.equalsIgnoreCase(f.getName())) {
                    settersOk.add(setters.get(i));
                }
            }

        }

        return settersOk;
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

