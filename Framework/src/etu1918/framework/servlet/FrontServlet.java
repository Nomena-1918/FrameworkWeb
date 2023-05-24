package etu1918.framework.servlet;

import etu1918.framework.mapping.Mapping;
import etu1918.framework.mapping.ModelView;
import utilPerso.Utilitaire;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import java.util.*;

//@MultipartConfig
public class FrontServlet extends HttpServlet {
    HashMap<String, Mapping> mappingUrls;
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


                // Association pairs (name, value) du formulaire avec les setters de object
                out.println("\n\nParamètres du formulaire :\n");

                Enumeration<String> nomsParam = req.getParameterNames();
                String[] valeurParam;
                String nomParam, Param, setter, lo, geTest, paramMethodAction = null;

                Object valueParam = null;

                Class<?> fieldC;

                List<String> possibleParamMethodAction = new ArrayList<>();

                while (nomsParam.hasMoreElements()) {

                    // Paramètre du formulaire
                    nomParam = nomsParam.nextElement();


                    // Check, c'est un attribut de classe sinon Continue
                    if (Utilitaire.isClassAttribute(classe, nomParam)) {

                        out.println("\n-> " + nomParam + " = {\n");

                        // Rendre la 1ère lettre du paramétre majuscule, pour le setter
                        lo = nomParam.substring(0, 1).toUpperCase();
                        Param = lo + nomParam.substring(1);

                        // Création du setter
                        setter = "set".concat(Param);
                        out.println("     setter : " + setter);

                        // Arg setter
                        valeurParam = req.getParameterValues(nomParam);

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


                    else {
                        out.println("Nom param : "+nomParam);
                        paramMethodAction = nomParam;
                        valueParam = req.getParameterValues(nomParam)[0];
                        out.println("Valeur param : "+valueParam);

                        // Possible paramètre de la méthode d'action
                        possibleParamMethodAction.add(nomParam);
                    }
                }

                //La méthode d'action correspondant à l'URL
                Method method = Utilitaire.getMethodeByAnnotation("URLMapping", url, Class.forName(mapping.getClassName()));

                // Prendre la view dans le ModelView retourné
                ModelView modelView;

                int count = method.getParameterCount();

                assert method != null;
                if (count == 0) {
                    modelView = (ModelView) method.invoke(object);
                }
                // count >= 1
                else {

                    // Liste des noms des vrais paramètres de la méthode d'action
                    List<String> trueParams = Utilitaire.getTrueParams(method);

                    // Liste des noms en commun avec possibleParamMethodAction
                    List<String> paramCom = new ArrayList<>(trueParams);
                    paramCom.retainAll(possibleParamMethodAction);


                    // Prendre la valeur de chacun de ces parameters -> List<Object>
                    List<Object> valParamCom = new ArrayList<>();
                    for (String s : paramCom) {
                        valParamCom.add(req.getParameterValues(s)[0]);
                    }

                    // Cast List<Object> en Object[]
                    Object[] valParamComArr = valParamCom.toArray();
                    
                    // Get list des vrais types des paramètres de la méthode
                    List<Class> paramType = Utilitaire.getParamType(paramCom, method);

                    // Cast des éléments vers vrai type
                    for(int i=0; i<valParamComArr.length; i++) {
                        valParamComArr[i] = Utilitaire.convert(valParamComArr[i], paramType.get(i));
                    }
                    
                    // Appel de la méthode avec les paramètres castés
                    modelView = (ModelView) method.invoke(object, valParamComArr);
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
