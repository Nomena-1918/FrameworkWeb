package utilPerso;

import etu1918.framework.Mapping;
import etu1918.framework.annotationPerso.Model;
import etu1918.framework.annotationPerso.URLMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.util.*;

public class Utilitaire {
    public static List<String> getInfoURL(HttpServletRequest req) {
        return Arrays.asList(req.getServletPath().split("/"));
    }

    /*
    public static List<Class> getClasses(String packageName) throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);

        List<File> dirs = new ArrayList<File>();

        URL resource;
        URI uri;

        while (resources.hasMoreElements()) {
            resource = resources.nextElement();
            uri = new URI(resource.toString());
            dirs.add(new File(uri.getPath()));
        }
        List<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }
*/

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
}

