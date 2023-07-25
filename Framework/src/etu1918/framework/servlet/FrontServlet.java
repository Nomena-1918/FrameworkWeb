package etu1918.framework.servlet;

import com.google.gson.Gson;
import etu1918.framework.annotationPerso.Auth;
import etu1918.framework.annotationPerso.REST_API;
import etu1918.framework.mapping.Mapping;
import etu1918.framework.mapping.ModelView;
import utilPerso.FileUpload;
import utilPerso.Utilitaire;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.*;


@MultipartConfig
public class FrontServlet extends HttpServlet {
    HashMap<String, Mapping> mappingUrls;
    HashMap<Class, Object> instanceClass;

    @Override
    public void init() throws ServletException {
        try {

            String path = this.getInitParameter("classpath");
            Utilitaire.initHashMap(this, path);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public HashMap<String, Mapping> getMappingUrls() {
        return mappingUrls;
    }

    public void setMappingUrls(HashMap<String, Mapping> mappingUrls) {
        this.mappingUrls = mappingUrls;
    }

    public HashMap<Class, Object> getInstanceClass() {
        return instanceClass;
    }

    public void setInstanceClass(HashMap<Class, Object> instanceClass) {
        this.instanceClass = instanceClass;
    }

    public void ProcessRequest(HttpServletRequest req, HttpServletResponse res) throws Exception {
       res.setContentType("application/json");
       PrintWriter out = res.getWriter();

        try {
            out.println("Bienvenue dans la page de debug : " + this.getClass().getSimpleName());

            String url = req.getServletPath();
            out.println("\nURL : " + url);

            List<String> params = Utilitaire.getInfoURL(req);
            out.println("\nApres decomposition : ");

            for (String s : params)
                out.println(s);

            out.println("\n\nMappingUrls :");
            for (Map.Entry<String, Mapping> me : this.mappingUrls.entrySet())
                out.println("URL : " + me.getKey() + ", Classe : " + me.getValue().getClassName() + ", Méthode : " + me.getValue().getMethod());

            out.println("\n\nInstanceClass :");
            for (Map.Entry<Class, Object> me : this.instanceClass.entrySet())
                out.println("Classe : " + me.getKey() + ", Instance : " + me.getValue());


            out.println("\n\nL'URL est supportée : " + this.mappingUrls.containsKey(url));

            // Chargement d'une view
            if (this.mappingUrls.containsKey(url)) {

                Mapping mapping = this.mappingUrls.get(url);

                // Instanciation d'un objet de type classname du Mapping
                Class<?> classe = Class.forName(mapping.getClassName());

                // Contrôle singleton
                Object object;
                Object oTemp;
                if (this.instanceClass.containsKey(classe)) {
                    oTemp = this.instanceClass.get(classe);

                    if (oTemp != null)
                        //object = oTemp;
                        object = Utilitaire.resetFields(oTemp);

                    else {
                        object = classe.cast(classe.getDeclaredConstructor().newInstance());
                        this.instanceClass.put(classe, object);
                    }
                } else
                    object = classe.cast(classe.getDeclaredConstructor().newInstance());

                out.println("\nInstanciation object Ok\n");


                Enumeration<String> nomsParam = req.getParameterNames();
                String[] valeurParam;
                String nomParam, Param, setter = "", lo, geTest;
                Class<?> fieldC;


                // Association pairs (name, value) du formulaire avec les setters de object
                out.println("\n\nParamètres du formulaire :\n");

                while (nomsParam.hasMoreElements()) {

                    // Paramètre du formulaire
                    nomParam = nomsParam.nextElement();

                    out.println(nomParam);

                    // Arg setter
                    valeurParam = req.getParameterValues(nomParam);

                    // Check, c'est un attribut de classe sinon Continue
                    if (Utilitaire.isClassAttribute(classe, nomParam)) {

                        out.println("\n-> " + nomParam + " = {\n");

                        // Rendre la 1ère lettre du paramétre majuscule, pour le setter
                        lo = nomParam.substring(0, 1).toUpperCase();
                        Param = lo + nomParam.substring(1);

                        // Création du setter
                        setter = "set".concat(Param);
                        out.println("     setter : " + setter);

                        // Paramater type du setter
                        fieldC = object.getClass().getDeclaredField(nomParam).getType();
                        out.println("     Type du param : " + fieldC.getSimpleName());

                        Object o;

                        if (valeurParam.length == 1) {

                            out.println("     arg : " + valeurParam[0]);

                            // ================== CAST ===================== //

                            o = Utilitaire.convert(valeurParam[0], fieldC);

                            // ============================================== //

                            // Appel setter généralisé
                            Utilitaire.toSet(setter, object, o, fieldC);
                            out.println("\n\t-> Setter appelé avec succès !\n");

                            // Test setter avec getter
                            geTest = "get" + Param;

                            if (Utilitaire.toGet(geTest, object) != null)
                                out.println("\t-> Test setter : " + geTest + " = " + Utilitaire.toGet(geTest, object).toString() + "\n");

                            else
                                out.println("\t-> Test setter : " + geTest + " = null\n");

                            out.println("    }\n");
                            continue;
                        }

                        if (valeurParam.length > 1) {
                            out.println("     arg : " + Arrays.toString(valeurParam));


                            // ================== CAST ===================== //

                            Object tab = Utilitaire.convert(valeurParam, fieldC);

                            // ========================================== //

                            // Appel setter généralisé
                            Utilitaire.toSet(setter, object, tab, fieldC);

                            out.println("\n\t-> Setter appelé avec succès !\n");

                            // Test setter avec getter
                            geTest = "get" + Param;
                            if (Utilitaire.toGet(geTest, object) != null)
                                out.println("\t-> Test setter : " + geTest + " = " + Arrays.toString((String[]) Utilitaire.toGet(geTest, object)) + "\n");
                            else
                                out.println("\t-> Test setter : " + geTest + " = null\n");

                            out.println("    }\n");

                        }
                    } else
                        out.println("!(class attr) - Nom param : " + nomParam + ", Valeur param : " + valeurParam[0]);

                }

// ============= FICHIERS ==================

                // Pour les fichiers
                /*
                 * Les données reçues sont multipart, on doit donc utiliser la méthode
                 * getPart() pour traiter le champ d'envoi de fichiers.
                 */
                if (req.getContentType() != null && req.getContentType().startsWith("multipart/")) {
                    String nameAttrFile = Utilitaire.getNameFileUploadAttribute(classe);

                    if (nameAttrFile != null && req.getPart(nameAttrFile) != null) {
                        Part part = req.getPart(nameAttrFile);

                        out.println("Part : " + part);

                        /*
                         * Il faut déterminer s'il s'agit d'un champ classique
                         * ou d'un champ de type fichier : on délègue cette opération
                         * à la méthode utilitaire getNomFichier().
                         */
                        String nomFichier = Utilitaire.getNomFichier(part);
                        out.println("nomFichier : " + nomFichier);

                        if (nomFichier != null) {

                            InputStream in = part.getInputStream();
                            byte[] bytes = in.readAllBytes();

                            out.println("Bytes : " + bytes);

                            FileUpload file = new FileUpload(nomFichier, bytes);


                            // Rendre la 1ère lettre du paramétre majuscule, pour le setter
                            try {
                                lo = nameAttrFile.substring(0, 1).toUpperCase();
                                Param = lo + nameAttrFile.substring(1);

                                // Création du setter
                                setter = "set".concat(Param);
                                out.println("     setter : " + setter);

                                // Appel setter généralisé
                                Utilitaire.toSet(setter, object, file, FileUpload.class);
                            } catch (Exception e) {
                                if (e.getMessage().equalsIgnoreCase("Setter : " + setter + " invalide")) {

                                    // Attribution du FileUpload
                                    Field field = classe.getDeclaredField(nameAttrFile);
                                    field.setAccessible(true);
                                    field.set(object, file);
                                } else throw e;
                            }
                            out.println("\n\t-> Setter FileUpload appelé avec succès !\n");
                            in.close();
                        }
                    }
                } else
                    out.println("pas de fichier");

                //La méthode d'action correspondant à l'URL
                Method method = Utilitaire.getMethodeByAnnotation("URLMapping", url, Class.forName(mapping.getClassName()));


//====== Simulation login =======================
                HttpSession session = req.getSession();

                // Val test
                session.setAttribute("nbr", 5);

                String varProfil = this.getInitParameter("session_profil");
                //String profilCourant = "admin";
                //session.setAttribute(varProfil, profilCourant);

                boolean bol = Utilitaire.isClassAttribute(classe, "_session");
                out.println(bol);

                if (bol) {
                    Field fieldSession = classe.getDeclaredField("_session");

                    if (fieldSession.getType().equals(HashMap.class)) {
                        // Create a HashMap to store the keys and values
                        HashMap<String, Object> sessionData = new HashMap<>();

                        // Get all the attribute names in the HttpSession
                        Enumeration<String> attributeNames = session.getAttributeNames();

                        // Loop through each attribute and put it in the HashMap
                        while (attributeNames.hasMoreElements()) {
                            String attributeName = attributeNames.nextElement();
                            Object attributeValue = session.getAttribute(attributeName);
                            sessionData.put(attributeName, attributeValue);
                        }

                        fieldSession.setAccessible(true);
                        fieldSession.set(object, sessionData);
                    }
                }

//===============================================
                boolean access = true;
                String valAnnot;
                if (method.isAnnotationPresent(Auth.class)) {
                    out.println("\nPrésence authorisation\n");

                    Annotation annot = method.getAnnotation(Auth.class);
                    Method annotMeth = annot.annotationType().getDeclaredMethod("value");

                    // Accès
                    valAnnot = (String) annotMeth.invoke(annot);
                    out.println("\nAccès méthode :" + valAnnot);

                    // Nom profil dans session
                    String valSession = (String) session.getAttribute(varProfil);
                    out.println("Valeur session :" + valSession);
                    
                    // Utilisateurs authentifiés
                    if (valAnnot.equalsIgnoreCase("")) {
                        if (valSession == null) {
                            access = false;
                        }
                    }
                    else {
                        if (!valAnnot.equalsIgnoreCase(valSession))
                            access = false;
                    }
                }
                out.println("\nAccès autorisé "+session.getAttribute(varProfil)+" : "+access);
// ==============================================
                // Prendre la view dans le ModelView retourné
                ModelView modelView = null;

                int count = method.getParameterCount();

                boolean isJson = method.isAnnotationPresent(REST_API.class);

                if (access) {
                    out.println("\nNb params méthode : "+count);

                    if (count == 0) {
                        out.println("Method : "+method);
                        out.println("Object : "+object);


                        if (!isJson){
                            modelView = (ModelView) method.invoke(object);
                            out.println("modelview : "+method.invoke(object));
                            out.println(modelView);
                        }
                        else {
                            Object o = method.invoke(object);
                            Gson gson = new Gson();
                            String json = gson.toJson(o);
                            out.println("\nREST-API");
                            out.println("\n\n"+json);
                        }
                    }
                    // count >= 1
                    else {
                        // Liste des noms des vrais paramètres de la méthode d'action
                        List<String> trueParams = Utilitaire.getTrueParams(method);

                        out.println(" \n Noms paramètres méthode d'action :");
                        for (String string : trueParams)
                            out.println(string);

                        // Prendre la valeur de chacun de ces parameters -> List<Object>
                        List<Object> valParam = new ArrayList<>();

                        // Type de ces paramètres
                        List<Class> listParamType = Utilitaire.getParamType(trueParams, method);

                        // Cast des paramètres String -> vraie valeur
                        out.println("\n Valeurs paramètres de requête pour la méthode d'action :");
                        for (int i = 0; i < trueParams.size(); i++) {
                            valParam.add(Utilitaire.convert(req.getParameterValues(trueParams.get(i))[0], listParamType.get(i)));
                            out.println(Utilitaire.convert(req.getParameterValues(trueParams.get(i))[0], listParamType.get(i)));
                        }

                        // Cast List<Object> en Object[]
                        Object[] valParamArr = valParam.toArray();
                        out.println(Arrays.toString(valParamArr));

                        //out.println("\nNb params : "+count+"\n");

                        if (!isJson) {
                            out.println("\nObject : "+object);

                            // Appel de la méthode
                            if (count == 1)
                                modelView = (ModelView) method.invoke(object, valParamArr[0]);
                            else
                                modelView = (ModelView) method.invoke(object, valParamArr);


                            out.println("\nmodelview : "+method.invoke(object, valParamArr));
                            //out.println(modelView);
                        }
                        else {
                            Object o = method.invoke(object, valParamArr);
                            Gson gson = new Gson();
                            String json = gson.toJson(o);
                            out.println("\nREST-API");
                            out.println(json+"\n\n");
                        }
                    }

                    if (!isJson) {
                        String view = modelView.getView();
                        HashMap<String, Object> dataHsh = modelView.getData();
                        HashMap<String, Object> sessionHsh = modelView.getSessionToAdd();

                        // Prendre les data dans le ModelView
                        if (dataHsh != null) {
                            out.println("JSON : " + modelView.isJson());

                            // Les mettre dans les attributs de la requête
                            if (modelView.isJson()) {
                                Gson gson = new Gson();
                                String json = gson.toJson(dataHsh);
                                out.println(json);
                                req.setAttribute("dataJson", json);

                                //Dispatch vers la vue correspondante
                                RequestDispatcher dispat = req.getRequestDispatcher(view);
                                dispat.forward(req, res);

                            } else
                                for (Map.Entry<String, Object> m : dataHsh.entrySet()) {
                                    req.setAttribute(m.getKey(), m.getValue());
                                }
                        }

                        // Prendre les sessions dans le ModelView, mettre dans HttpSession
// Les mettre dans les attributs de la requête
                        for (Map.Entry<String, Object> m : sessionHsh.entrySet()) {
                            session.setAttribute(m.getKey(), m.getValue());
                        }


                        // Enlever des variables de session
                        List<String> sessionToRemove = modelView.getSessionToRemove();

                        for (String m : sessionToRemove) {
                            session.removeAttribute(m);
                        }

                        // Tout supprimer
                        if (modelView.isSessionInvalidate())
                            session.invalidate();

                        out.println("\n\nVue pour dispatch : " + view);

                        //Dispatch vers la vue correspondante
                        RequestDispatcher dispat = req.getRequestDispatcher(view);
                        dispat.forward(req, res);
                    }
                }
                else
                    out.println("Accès refusé pour "+session.getAttribute(varProfil));
            } else
                out.println("URL non supportée");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        try {
            ProcessRequest(req, res);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void doPost(HttpServletRequest req, HttpServletResponse res) {
        try {
            ProcessRequest(req, res);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
