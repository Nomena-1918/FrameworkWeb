package etu1918.framework.servlet;

import com.google.gson.Gson;
import etu1918.framework.annotationPerso.Auth;
import etu1918.framework.annotationPerso.REST_API;
import etu1918.framework.annotationPerso.XML;
import etu1918.framework.mapping.Mapping;
import etu1918.framework.mapping.ModelView;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import utilPerso.FileUpload;
import utilPerso.Utilitaire;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.net.StandardSocketOptions;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;


@MultipartConfig(
        fileSizeThreshold=1024*1024*10, 	// 10 MB
        maxFileSize=1024*1024*50*2,         // 100 MB
        maxRequestSize=1024*1024*100*2      // 200 MB
)
public class FrontServlet extends HttpServlet {
    HashMap<String, Mapping> mappingUrls;
    HashMap<Class, Object> instanceClass;
    private String UPLOAD_DIR;
    String attrFileAttach;

    @Override
    public void init() throws ServletException {
        try {

            String path = this.getInitParameter("classpath");
            UPLOAD_DIR = this.getInitParameter("upload_dir");
            attrFileAttach = this.getInitParameter("fileAttachment");

            if (UPLOAD_DIR==null) UPLOAD_DIR="";
            if (attrFileAttach==null) attrFileAttach="";

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
            System.out.println("\n\n===============================================================================");

            System.out.println("\nBienvenue dans la page de debug : " + this.getClass().getSimpleName());

            String url = req.getServletPath();
            System.out.println("\nURL : " + url);

            List<String> params = Utilitaire.getInfoURL(req);
            System.out.println("\nApres decomposition : ");

            for (String s : params)
                System.out.println(s);

            System.out.println("\n\nMappingUrls :");
            for (Map.Entry<String, Mapping> me : this.mappingUrls.entrySet())
                System.out.println("URL : " + me.getKey() + ", Classe : " + me.getValue().getClassName() + ", Méthode : " + me.getValue().getMethod());

            System.out.println("\n\nInstanceClass :");
            for (Map.Entry<Class, Object> me : this.instanceClass.entrySet())
                System.out.println("Classe : " + me.getKey() + ", Instance : " + me.getValue());


            System.out.println("\n\nL'URL est supportée : " + this.mappingUrls.containsKey(url));

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

                System.out.println("\nInstanciation object Ok\n");


                Enumeration<String> nomsParam = req.getParameterNames();
                String[] valeurParam;
                String nomParam, Param, setter = "", lo, geTest;
                Class<?> fieldC;


                // Association pairs (name, value) du formulaire avec les setters de object
                System.out.println("\n\nParamètres du formulaire :\n");

                while (nomsParam.hasMoreElements()) {

                    // Paramètre du formulaire
                    nomParam = nomsParam.nextElement();

                    System.out.println(nomParam);

                    // Arg setter
                    valeurParam = req.getParameterValues(nomParam);

                    // Check, c'est un attribut de classe sinon Continue
                    if (Utilitaire.isClassAttribute(classe, nomParam)) {

                        System.out.println("\n-> " + nomParam + " = {\n");

                        // Rendre la 1ère lettre du paramétre majuscule, pour le setter
                        lo = nomParam.substring(0, 1).toUpperCase();
                        Param = lo + nomParam.substring(1);

                        // Création du setter
                        setter = "set".concat(Param);
                        System.out.println("     setter : " + setter);

                        // Paramater type du setter
                        fieldC = object.getClass().getDeclaredField(nomParam).getType();
                        System.out.println("     Type du param : " + fieldC.getSimpleName());

                        Object o;

                        if (valeurParam.length == 1) {

                            System.out.println("     arg : " + valeurParam[0]);

                            // ================== CAST ===================== //

                            o = Utilitaire.convert(valeurParam[0], fieldC);

                            // ============================================== //

                            // Appel setter généralisé
                            Utilitaire.toSet(setter, object, o, fieldC);
                            System.out.println("\n\t-> Setter appelé avec succès !\n");

                            // Test setter avec getter
                            geTest = "get" + Param;

                            if (Utilitaire.toGet(geTest, object) != null)
                                System.out.println("\t-> Test setter : " + geTest + " = " + Utilitaire.toGet(geTest, object).toString() + "\n");

                            else
                                System.out.println("\t-> Test setter : " + geTest + " = null\n");

                            System.out.println("    }\n");
                            continue;
                        }

                        if (valeurParam.length > 1) {
                            System.out.println("     arg : " + Arrays.toString(valeurParam));


                            // ================== CAST ===================== //

                            Object tab = Utilitaire.convert(valeurParam, fieldC);

                            // ========================================== //

                            // Appel setter généralisé
                            Utilitaire.toSet(setter, object, tab, fieldC);

                            System.out.println("\n\t-> Setter appelé avec succès !\n");

                            // Test setter avec getter
                            geTest = "get" + Param;
                            if (Utilitaire.toGet(geTest, object) != null)
                                System.out.println("\t-> Test setter : " + geTest + " = " + Arrays.toString((String[]) Utilitaire.toGet(geTest, object)) + "\n");
                            else
                                System.out.println("\t-> Test setter : " + geTest + " = null\n");

                            System.out.println("    }\n");

                        }
                    } else
                        System.out.println("!(class attr) - Nom param : " + nomParam + ", Valeur param : " + valeurParam[0]);

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

                        System.out.println("Part : " + part);

                        /*
                         * Il faut déterminer s'il s'agit d'un champ classique
                         * ou d'un champ de type fichier : on délègue cette opération
                         * à la méthode utilitaire getNomFichier().
                         */

                        String nomFichier = Utilitaire.getNomFichier(part);
                        System.out.println("nomFichier : " + nomFichier);

                        if (nomFichier != null) {

                            InputStream in = part.getInputStream();
                            byte[] bytes = in.readAllBytes();

                            System.out.println("Bytes : " + bytes);

                            FileUpload file = new FileUpload(nomFichier, bytes);

                            // Rendre la 1ère lettre du paramétre majuscule, pour le setter
                            try {
                                lo = nameAttrFile.substring(0, 1).toUpperCase();
                                Param = lo + nameAttrFile.substring(1);

                                // Création du setter
                                setter = "set".concat(Param);
                                System.out.println("     setter : " + setter);

                                // Appel setter généralisé
                                Utilitaire.toSet(setter, object, file, FileUpload.class);


                                /////////// Stockage du fichier sur le serveur /////////////

                                // gets absolute path of the web application
                                String applicationPath = req.getServletContext().getRealPath("");

                                // constructs path of the directory to save uploaded file
                                String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;

                                // creates the save directory if it does not exists
                                File fileSaveDir = new File(uploadFilePath);
                                if (!fileSaveDir.exists()) {
                                    fileSaveDir.mkdirs();
                                }

                                System.out.println("Upload File Directory="+fileSaveDir.getAbsolutePath());

                                // Ecriture du fichier sur le serveur
                                String fullPath = uploadFilePath + File.separator + file.getNom();
                                part.write(fullPath.replaceAll("\"", ""));
                                ////////////////////////////////////////////////////////////

                            } catch (Exception e) {
                                if (e.getMessage().equalsIgnoreCase("Setter : " + setter + " invalide")) {

                                    // Attribution du FileUpload
                                    Field field = classe.getDeclaredField(nameAttrFile);
                                    field.setAccessible(true);
                                    field.set(object, file);
                                } else throw e;
                            }
                            System.out.println("\n\t-> Setter FileUpload appelé avec succès !\n");
                            in.close();
                        }
                    }
                } else
                    System.out.println("pas de fichier");

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
                System.out.println(bol);

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
                    System.out.println("\nPrésence authorisation\n");

                    Annotation annot = method.getAnnotation(Auth.class);
                    Method annotMeth = annot.annotationType().getDeclaredMethod("value");

                    // Accès
                    valAnnot = (String) annotMeth.invoke(annot);
                    System.out.println("\nAccès méthode :" + valAnnot);

                    // Nom profil dans session
                    String valSession = (String) session.getAttribute(varProfil);
                    System.out.println("Valeur session :" + valSession);

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
                System.out.println("\nAccès autorisé "+session.getAttribute(varProfil)+" : "+access);
// ==============================================
                // Prendre la view dans le ModelView retourné
                ModelView modelView = null;

                int count = method.getParameterCount();

                boolean isJson = method.isAnnotationPresent(REST_API.class);
                boolean isXml = method.isAnnotationPresent(XML.class);
                PrintWriter outx = null;

                if (isXml) {
                    res.setContentType("application/xml");
                    outx = res.getWriter();
                }

                if (access) {
                    System.out.println("\nNb params méthode : "+count);

                    if (count == 0) {
                        System.out.println("Method : "+method);
                        System.out.println("Object : "+object);

                        Object o = method.invoke(object);


                        if (isJson) {
                            Gson gson = new Gson();
                            String json = gson.toJson(o);
                            System.out.println("\nREST-API");
                            out.println("\n\n"+json);
                            return;
                        }
                        if (isXml) {
                            // Obtention de la représentation XML en tant que String
                            String xmlString = Utilitaire.toXML(o);

                            // Affichage de la représentation XML
                            System.out.println("\nXML");
                            outx.println("\n\n"+xmlString);
                            System.out.println("\n\n"+xmlString);
                            return;
                        }
                        else
                            modelView = (ModelView)o;

                    }
                    // count >= 1
                    else {
                        // Liste des noms des vrais paramètres de la méthode d'action
                        List<String> trueParams = Utilitaire.getTrueParams(method);

                        System.out.println(" \n Noms paramètres méthode d'action :");
                        for (String string : trueParams)
                            System.out.println(string);

                        // Prendre la valeur de chacun de ces parameters -> List<Object>
                        List<Object> valParam = new ArrayList<>();

                        // Type de ces paramètres
                        List<Class> listParamType = Utilitaire.getParamType(trueParams, method);

                        // Cast des paramètres String -> vraie valeur
                        System.out.println("\n Valeurs paramètres de requête pour la méthode d'action :");
                        for (int i = 0; i < trueParams.size(); i++) {
                            valParam.add(Utilitaire.convert(req.getParameterValues(trueParams.get(i))[0], listParamType.get(i)));
                            System.out.println(Utilitaire.convert(req.getParameterValues(trueParams.get(i))[0], listParamType.get(i)));
                        }

                        // Cast List<Object> en Object[]
                        Object[] valParamArr = valParam.toArray();
                        System.out.println(Arrays.toString(valParamArr));

                        Object o = method.invoke(object, valParamArr);

                        if (isJson) {
                            Gson gson = new Gson();
                            String json = gson.toJson(o);
                            System.out.println("\nREST-API");
                            out.println(json+"\n\n");
                            System.out.println(json+"\n\n");
                            return;
                        }
                        if (isXml) {

                            // Obtention de la représentation XML en tant que String
                            String xmlString = Utilitaire.toXML(object);

                            // Affichage de la représentation XML
                            System.out.println("\nXML");
                            outx.println("\n\n"+xmlString);
                            System.out.println("\n\n"+xmlString);
                            return;
                        }
                        else {
                            System.out.println("\nObject : "+object);

                            // Appel de la méthode
                            modelView = (ModelView) o;

                            System.out.println("\nmodelview : "+modelView);
                        }
                    }

                    String view = modelView.getView();
                    HashMap<String, Object> dataHsh = modelView.getData();
                    HashMap<String, Object> sessionHsh = modelView.getSessionToAdd();

                    // Prendre les data dans le ModelView
                    if (dataHsh != null) {
                        System.out.println("JSON : " + modelView.isJson());

                        // Les mettre dans les attributs de la requête
                        if (modelView.isJson()) {
                            Gson gson = new Gson();
                            String json = gson.toJson(dataHsh);
                            System.out.println(json);
                            req.setAttribute("dataJson", json);
                        }

                        if (modelView.isXml()){
                            Serializer serializer = new Persister();
                            StringWriter result = new StringWriter();
                            serializer.write(modelView, result);

                            // Get the XML string
                            String xmlString = result.toString();
                            System.out.println(xmlString);
                            req.setAttribute("dataXml", xmlString);
                        }

                        else
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

                    System.out.println("\n\nVue pour dispatch : " + view);


                    //////// Téléchargement de fichiers ///
                    // Nom attribut : fileAttachment
                    if(req.getAttribute(attrFileAttach) != null) {
                        String fileToDownload = (String) req.getAttribute(attrFileAttach);

                        // constructs path of the directory to save uploaded file
                        String uploadFilePath = UPLOAD_DIR + File.separator + fileToDownload;

                        String contentType = "application/octet-stream";

                        res.setContentType(contentType);
                        String encodedFileName = URLEncoder.encode(fileToDownload, StandardCharsets.UTF_8);
                        res.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFileName);

                        final int ARBITARY_SIZE = 1048;

                        System.out.println("fileToDownload : " + fileToDownload);
                        System.out.println("uploadFilePath : " + uploadFilePath);

                        try(InputStream in = req.getServletContext().getResourceAsStream("\""+uploadFilePath)) {
                            OutputStream outp = res.getOutputStream();
                            byte[] buffer = new byte[ARBITARY_SIZE];

                            int numBytesRead;
                            while ((numBytesRead = in.read(buffer)) > 0) {
                                outp.write(buffer, 0, numBytesRead);
                            }
                            System.out.println("\nEcriture fichier achevée");
                        }

                    }



                    //Dispatch vers la vue correspondante
                    RequestDispatcher dispat = req.getRequestDispatcher(view);
                    dispat.forward(req, res);
                }
                else {
                    String mess= "Accès refusé pour " + session.getAttribute(varProfil);
                    System.out.println(mess);
                    throw new RuntimeException(mess);
                }
            } else {
                String mess = "URL non supportée";
                System.out.println(mess);
                throw new RuntimeException(mess);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        try {
            ProcessRequest(req, res);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void doPost(HttpServletRequest req, HttpServletResponse res) {
        try {
            ProcessRequest(req, res);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
