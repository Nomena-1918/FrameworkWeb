package etu1918.framework.servlet;

import etu1918.framework.mapping.Mapping;
import etu1918.framework.mapping.ModelView;
import utilPerso.Utilitaire;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.*;

public class FrontServlet extends HttpServlet {
    HashMap<String, Mapping> mappingUrls;
    @Override
    public void init() throws ServletException {
        try {
            String path = this.getInitParameter("packClass");
            this.mappingUrls = Utilitaire.initHashMap(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void ProcessRequest(HttpServletRequest req, HttpServletResponse res) throws Exception {
        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();
        out.println("Bienvenue dans la page de debug : " + this.getClass().getName());

        String url = req.getServletPath();
        out.println("\nURL : " + url);

        List<String> params = Utilitaire.getInfoURL(req);
        out.println("\nApres decomposition : ");

        for (String s : params)
            out.println(s);

        out.println("\n\nMappingUrls :");

        for (Map.Entry<String, Mapping> me : this.mappingUrls.entrySet())
            out.println("URL : " + me.getKey() + " Classe : " + me.getValue().getClassName() + ", Méthode : " + me.getValue().getMethod());

        out.println("\n\nL'URL est supportée : " + this.mappingUrls.containsKey(url));

        /*
        /// Tsy mandeha lay HashMap
        // Traitement des données envoyées par formulaire
        if (req.getParameterMap() != null) {
            out.println("\nLes params du formulaire : ");

            HashMap<String, String[]> formData = (HashMap<String, String[]>) req.getParameterMap();

            for (Map.Entry<String, String[]> me : formData.entrySet())
                out.println(me.getKey() + " : " + Arrays.toString(me.getValue()));

        }
         */

        // Chargement d'une view
        if (this.mappingUrls.containsKey(url)) {
            Mapping mapping = this.mappingUrls.get(url);

            // Instanciation d'un objet de type classname du Mapping
            Class<?> classe = Class.forName(mapping.getClassName());
            Object object = classe.cast(classe.getDeclaredConstructor().newInstance());


            // Association pairs (name, value) du formulaire avec les setters de object
            out.println("\nParamètres du formulaire :\n");

            Enumeration<String> nomsParam = req.getParameterNames();
            String[] valeurParam;
            Object valeur;
            String nomParam, Param, setter, lo, geTest;
            Method getter;

            while (nomsParam.hasMoreElements()) {

                nomParam = nomsParam.nextElement();

                // Rendre la 1ère lettre du paramétre majuscule, pour le setter
                lo = nomParam.substring(0, 1).toUpperCase();
                Param = lo + nomParam.substring(1);

                // Création du setter
                setter = "set".concat(Param);

                out.println("Setter : "+setter);

                // Arg setter
                valeurParam = req.getParameterValues(nomParam);

                if (valeurParam.length == 1) {
                    valeur = valeurParam[0];

                    out.println("arg : "+ valeur);

                    // Appel setter généralisé
                    Utilitaire.toSet(setter, object, valeur);

                    out.println("\n\t-> Setter appelé avec succès !\n");

                    // Test setter avec getter
                    geTest = "get"+Param;

                    out.println("\t-> Test setter : "+ geTest + " = "+Utilitaire.toGet(geTest, object)+"\n");

                }
                else {
                    valeur = valeurParam;
                    out.println("\n\t-> Gestion des paramètres String[] pas encore implémentée !\n");
                }
                /*
                    out.println("arg : "+ Arrays.toString(valeurParam));

                    // Appel setter généralisé
                    Utilitaire.toSet(setter, object, Arrays.toString(valeurParam));

                    out.println("\n\t-> Setter appelé avec succès !\n");

                    // Test setter avec getter
                    geTest = "get"+Param;

                    out.println("\t-> Test setter : "+ geTest + " = "+Utilitaire.toGet(geTest, object)+"\n");

                 */

            }

            Method method = classe.getDeclaredMethod(mapping.getMethod());

            // Prendre la view dans le ModelView retourné
            ModelView modelView = (ModelView) method.invoke(object);
            String view = modelView.getView();

            HashMap<String, Object> dataHsh;

            // Prendre les data dans le ModelView
            if (modelView.getData() != null) {
                dataHsh = modelView.getData();

                // Les mettre dans les attributs de la requête
                for (Map.Entry<String, Object> m : dataHsh.entrySet()) {
                    req.setAttribute(m.getKey(), m.getValue());
                }
            }

            //Dispatch vers la vue correspondante
            RequestDispatcher dispat = req.getRequestDispatcher(view);
            dispat.forward(req, res);

        }

        else
            throw new Exception("URL non supportée");

    }
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            ProcessRequest(req, res);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            ProcessRequest(req, res);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
