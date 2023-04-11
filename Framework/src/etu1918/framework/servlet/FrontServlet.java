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
        out.println("URL : " + url);

        List<String> params = Utilitaire.getInfoURL(req);
        out.println("\n\nApres decomposition : ");

        for (String s : params)
            out.println(s);

        out.println("\n\nMappingUrls :");

        for (Map.Entry<String, Mapping> me : this.mappingUrls.entrySet())
            out.println("Key : " + me.getKey() + ", Classe : " + me.getValue().getClassName() + ", Méthode : " + me.getValue().getMethod());

        out.println("\n\nL'URL est supportée : " + this.mappingUrls.containsKey(url));


        // Affichage des paramètres des formulaires

        out.println("\nParamètres du formulaire :");


        Enumeration<String> nomsParam = req.getParameterNames();
        String[] valeurParam;
        String nomParam;

        while(nomsParam.hasMoreElements()) {
            nomParam = nomsParam.nextElement();

            out.println("\n"+nomParam + " : ");

            valeurParam = req.getParameterValues(nomParam);

            for (String s : valeurParam) {
                out.print(s + "\n");
            }

        }
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
