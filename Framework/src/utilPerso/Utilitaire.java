package utilPerso;

import etu1918.framework.annotationPerso.Model;
import etu1918.framework.annotationPerso.ParamValue;
import etu1918.framework.mapping.Mapping;
import etu1918.framework.annotationPerso.URLMapping;
import etu1918.framework.mapping.ModelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URI;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.lang.reflect.Modifier;

public class Utilitaire {


// ===================== COMMUN : SETTERS et GETTERS ===================== //
    // Cast généralisé
    public static Object convert(Object valeurParam, Class fieldC) throws Exception {
        Object o = null;

        if (fieldC.equals(String.class)) {
            o = valeurParam;
        }
        else if (valeurParam.toString().equalsIgnoreCase("true") || valeurParam.toString().equalsIgnoreCase("false")) {
            o = Boolean.parseBoolean((String) valeurParam);
        }
        else if (fieldC.equals(Integer.class)) {
            if (Utilitaire.isNumeric((String) valeurParam))
                o = Integer.parseInt((String) valeurParam);
            else throw new Exception("Divergence de type entre le paramètre de la requête et l'attribut de l'objet");
        }
        else if (fieldC.equals(Date.class)) {
            o = new SimpleDateFormat("yyyy-MM-dd").parse((String) valeurParam);
        }
        else if (fieldC.equals(String[].class)) {
            return valeurParam;
        }
        else if (fieldC.equals(Integer[].class)) {
            Integer[] tab = new Integer[((String[])valeurParam).length];

            for(int i=0; i<((String[])valeurParam).length; i++) {
                tab[i] = Integer.parseInt(((String[])valeurParam)[i]);
            }
            o = tab;
        }
        else if (fieldC.equals(Date[].class)) {
            Date[] tab = new Date[((String[])valeurParam).length];

            for(int i=0; i<((String[])valeurParam).length; i++) {
                tab[i] = new SimpleDateFormat("yyyy/MM/dd").parse(((String[])valeurParam)[0]);
            }
            o = tab;
        }

        else
            throw new Exception("Type de variable non pris en charge");

        return o;
    }



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
    public static boolean isClassAttribute(Class classe, String param) {
        Field[] listFields = classe.getDeclaredFields();

        for (Field f : listFields)
            if (f.getName().equalsIgnoreCase(param))
                return true;
        return false;
    }

    public static String getNameFileUploadAttribute(Class classe) {
        Field[] listFields = classe.getDeclaredFields();
        String name = null;

        for (Field f : listFields)
            if (f.getType().equals(FileUpload.class))
                return f.getName();
                
        return name;
    }


// Exécuter une méthode 
public static Object execMethod(Object objet, String nomMethode, Object[] parametres) throws Exception {
    
    Object retour;
    Class[] typeParametres = null;
    
    if (parametres != null) {
      typeParametres = new Class[parametres.length];
      for (int i = 0; i < parametres.length; ++i) {
        typeParametres[i] = parametres[i].getClass();
      }
    }
    
    Method m = objet.getClass().getMethod(nomMethode, typeParametres);
    if (Modifier.isStatic(m.getModifiers())) {
      retour = m.invoke(null, parametres);
    } else {
      retour = m.invoke(objet, parametres);
    }
    return retour;
  }


    // Liste des noms des vrais paramètres de la méthode d'action
    public static List<String> getTrueParams(Method method) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        List<String> listParams = new ArrayList<>();
        
        Annotation[][] annotationParam = method.getParameterAnnotations();
        Method annotMeth;
        String nameParam;

        for(Annotation[] annots : annotationParam) {

            annotMeth = annots[0].annotationType().getDeclaredMethod("value");
            nameParam = annotMeth.invoke(annots[0]).toString();

            listParams.add(nameParam);
        }

        return listParams;
    }

    // Liste des types des paramètres données
    @SuppressWarnings("rawtypes")
    public static List<Class> getParamType(List<String> nomParam, Method method) throws Exception {
        List<Class> lclass = new ArrayList<>();

        Parameter[] paramsMethod = method.getParameters();

        Method annotMeth;
        String nameParam;
        Annotation annot;

        for (Parameter parameter : paramsMethod) {
            annot = parameter.getAnnotation(ParamValue.class);
            annotMeth = annot.annotationType().getDeclaredMethod("value");
            nameParam = annotMeth.invoke(annot).toString();

            if(nomParam.contains(nameParam)) {
                lclass.add(parameter.getType());
            }
        }

        return lclass;
    } 

    @SuppressWarnings("rawtypes")
    public static Method getMethodeByAnnotation(String annote, String valueAnnote, Class classe) throws Exception{
        HashMap<Method, Annotation> methodes=getAllAnnotedMethods(annote, classe);
        for(Map.Entry<Method,Annotation> entry:methodes.entrySet()){
            if(entry.getValue().annotationType().getMethod("value").invoke(entry.getValue()).equals(valueAnnote)){
                return entry.getKey();
            }
        }
        return null;
    }

    
    @SuppressWarnings("rawtypes")
    public static HashMap<Method, Annotation> getAllAnnotedMethods(String annote, Class classe){
        HashMap<Method, Annotation> liste=new HashMap<>();
        Method[] methods=classe.getDeclaredMethods();
        for(Method m:methods){
            Annotation[] annotes=m.getAnnotations();
            for(Annotation an:annotes){
                if(an.annotationType().getSimpleName().equals(annote)){
                    liste.put(m, an);
                    break;
                }
            }
        }
        return liste;
    }

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


    //Lister les classes dans la liste des packages en argument
    @SuppressWarnings("rawtypes")
    public static HashMap<String, Mapping> initHashMap(String pack) throws Exception {

        List<Class> listClass = new ArrayList<Class>(Utilitaire.getClasses(pack));

        Method[] meths;

        HashMap<String, Mapping> mappingUrls = new HashMap<>();
        Annotation annot;
        String valueUrl = null;
        Method annotMeth;
        Mapping mapping;
        Class<Model> annotationClass = Model.class;

        for (Class c : listClass) {
            if (c.isAnnotationPresent(annotationClass)) {
                meths = c.getDeclaredMethods();
                for (Method m : meths) {
                    if (m.isAnnotationPresent(URLMapping.class)) {

                        // Récupération de l'annotation recherchée
                        annot = m.getAnnotation(URLMapping.class);

                        // Récupération de la valeur d'un attribut de l'annotation
                        annotMeth = annot.annotationType().getDeclaredMethod("value");
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



//============== GESTION DE FICHIERS ================//s

// Vérifier si un champ de formulaire est de type file ou pas 
public static String getNomFichier(Part part) {

    /* Boucle sur chacun des paramètres de l'en-tête "content-disposition". */
    for ( String contentDisposition : part.getHeader("content-disposition").split( ";" ) ) {

        /* Recherche de l'éventuelle présence du paramètre "filename". */
        if ( contentDisposition.trim().startsWith("filename") ) {

            /* Si "filename" est présent, alors renvoi de sa valeur, c'est-à-dire du nom de fichier. */
            return contentDisposition.substring( contentDisposition.indexOf( '=' ) + 1 );
        }
    }
    
    /* Et pour terminer, si rien n'a été trouvé... */
    return null;
}

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

