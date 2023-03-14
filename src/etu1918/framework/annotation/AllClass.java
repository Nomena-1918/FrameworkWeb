package etu1918.framework.annotation;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class AllClass {

        public static List<Class> getClasses(String packageName) throws Exception {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            String path = packageName.replace('.', '/');
            Enumeration<URL> resources = classLoader.getResources(path);
            List<File> dirs = new ArrayList<File>();
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                URI uri = new URI(resource.toString());
                dirs.add(new File(uri.getPath()));
            }
            List<Class> classes = new ArrayList<Class>();
            for (File directory : dirs) {
                classes.addAll(findClasses(directory, packageName));
            }
            return classes;
        }

        private static List<Class> findClasses(File directory, String packageName) throws Exception {
            List<Class> classes = new ArrayList<Class>();
            if (!directory.exists()) {
                return classes;
            }
            File[] files = directory.listFiles();
            if (files != null)
                for (File file : files) {
                    if (file.isDirectory()) {
                        classes.addAll(findClasses(file, packageName + "." + file.getName()));
                    } else if (file.getName().endsWith(".class")) {
                        String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                        classes.add(Class.forName(className));
                    }
                }
            else
                throw new Exception("Il n'y a pas de fichiers dans : "+directory);
            return classes;
        }







    /*
    public static Class[] getClasses(String pckgname) throws ClassNotFoundException {
        ArrayList<Class> classes = new ArrayList<Class>();
        // Get a File object for the package
        File directory = null;
        try {
            ClassLoader cld = Thread.currentThread().getContextClassLoader();
            if (cld == null) {
                throw new ClassNotFoundException("Can't get class loader.");
            }
            String path = pckgname.replace('.', '/');
            URL resource = cld.getResource(path);
            if (resource == null) {
                throw new ClassNotFoundException("No resource for " + path);
            }
            directory = new File(resource.getFile());
        }
        catch (NullPointerException x) {
            throw new ClassNotFoundException(pckgname + " (" + directory + ") does not appear to be a valid package");
        }
        if (directory.exists()) {
            // Get the list of the files contained in the package
            String[] files = directory.list();
            for (String file : files) {
                // we are only interested in .class files
                if (file.endsWith(".class")) {
                    // removes the .class extension
                    classes.add(Class.forName(pckgname + '.' + file.substring(0, file.length() - 6)));
                }
            }
        }
        else {
            throw new ClassNotFoundException(pckgname + " does not appear to be a valid package");
        }
        Class[] classesA = new Class[classes.size()];
        classes.toArray(classesA);
        return classesA;
    }





    /**
     * Cette méthode permet de lister toutes les classes d'un package donné
     *
     * @param pckgname Le nom du package à lister
     * @return La liste des classes

    public List<Class> getClassesList(String pckgname)	throws ClassNotFoundException, IOException {
        // Création de la liste qui sera retournée
        ArrayList<Class> classes = new ArrayList<>();

        // On récupère toutes les entrées du CLASSPATH
        String [] entries = System.getProperty("java.class.path")
                .split(System.getProperty("path.separator"));

        // Pour toutes ces entrées, on verifie si elles contiennent
        // un répertoire ou un jar
        for (String entry : entries) {

            if (entry.endsWith(".jar")) {
                classes.addAll(traitementJar(entry, pckgname));
            } else {
                classes.addAll(traitementRepertoire(entry, pckgname));
            }

        }

        return classes;
    }
    */

}
