package etu1918.framework.servlet;

import etu1918.framework.mapping.Mapping;
import etu1918.framework.mapping.ModelView;
import utilPerso.FileUpload;
import utilPerso.Utilitaire;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.*;


public class FrontServlet extends HttpServlet {
    HashMap<String, Mapping> mappingUrls;

   // HashMap<String, Mapping> mappingUrls;

    @Override
    public void init() throws ServletException {
        try {
            String path = this.getInitParameter("classpath");
            this.mappingUrls = Utilitaire.initHashMap(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void ProcessRequest(HttpServletRequest req, HttpServletResponse res) throws Exception {

        res.setContentType("text/plain");
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

            out.println("\n\nL'URL est supportée : " + this.mappingUrls.containsKey(url));

            // Chargement d'une view
            if (this.mappingUrls.containsKey(url)) {
                Mapping mapping = this.mappingUrls.get(url);

                // Instanciation d'un objet de type classname du Mapping
                Class<?> classe = Class.forName(mapping.getClassName());
                Object object = classe.cast(classe.getDeclaredConstructor().newInstance());


                Enumeration<String> nomsParam = req.getParameterNames();
                String[] valeurParam;
                String nomParam, Param, setter, lo, geTest, paramMethodAction = null;
                Object valueParam = null;
                Class<?> fieldC;
                HashMap<String, Object> possibleParamMethodAction = new HashMap<>();
                     

                // Pour les fichiers
                /*
                * Les données reçues sont multipart, on doit donc utiliser la méthode
                * getPart() pour traiter le champ d'envoi de fichiers.
                */

                String nameAttrFile = Utilitaire.getNameFileUploadAttribute(classe);

                if(nameAttrFile != null) {
                    Part part = req.getPart(nameAttrFile);
                    out.println("Part : "+part);

                    
                    /*
                    * Il faut déterminer s'il s'agit d'un champ classique 
                    * ou d'un champ de type fichier : on délègue cette opération 
                    * à la méthode utilitaire getNomFichier().
                    */
                    String nomFichier = Utilitaire.getNomFichier(part);
                    

                    if(nomFichier != null) {
                        nomFichier = nomFichier.substring( nomFichier.lastIndexOf( '/' ) + 1 ).substring( nomFichier.lastIndexOf( '\\' ) + 1 );
                        
                        InputStream in = part.getInputStream();
                        byte[] bytes = in.readAllBytes();

                        out.println("Bytes : "+bytes);

                        FileUpload file = new FileUpload(nomFichier, bytes);

                        // Rendre la 1ère lettre du paramétre majuscule, pour le setter
                        lo = nameAttrFile.substring(0, 1).toUpperCase();
                        Param = lo + nameAttrFile.substring(1);

                        // Création du setter
                        setter = "set".concat(Param);
                        out.println("     setter : " + setter);

                        // Appel setter généralisé
                        Utilitaire.toSet(setter, object, file, FileUpload.class);
                        out.println("\n\t-> Setter FileUpload appelé avec succès !\n");

                        in.close();
                    }
                }

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
                    }

                    else 
                        out.println("!(class attr) - Nom param : " + nomParam + ", Valeur param : " + valeurParam[0]);
                    
                }

                //La méthode d'action correspondant à l'URL
                Method method = Utilitaire.getMethodeByAnnotation("URLMapping", url, Class.forName(mapping.getClassName()));

                //out.println(method);

                // Prendre la view dans le ModelView retourné
                ModelView modelView;

                int count = method.getParameterCount();

                //out.println(count);

                assert method != null;
                if (count == 0) {
                    modelView = (ModelView) method.invoke(object);
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
                    
                    // Appel de la méthode
                    modelView = (ModelView) method.invoke(object, valParamArr);

                }
                
                String view = modelView.getView();
                HashMap<String, Object> dataHsh = modelView.getData();

                // Prendre les data dans le ModelView
                if (dataHsh != null) {

                    // Les mettre dans les attributs de la requête
                    for (Map.Entry<String, Object> m : dataHsh.entrySet()) {
                        req.setAttribute(m.getKey(), m.getValue());
                    }
                }


                out.println(view);

                //Dispatch vers la vue correspondante
                RequestDispatcher dispat = req.getRequestDispatcher(view);
                dispat.forward(req, res);

            } else
                throw new Exception("URL non supportée");
        }
        catch (Exception e) {
            throw e;
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
