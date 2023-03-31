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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        out.println("Tous les chemins menent a moi : "+this.getClass().getName());

        String url = req.getServletPath();
        out.println("URL : "+url);

        List<String> params = Utilitaire.getInfoURL(req);
        out.println("\n\nApres decomposition : ");

        for (String s : params) {
            out.println(s);
        }

        out.println("\n\nMappingUrls :");

        for (Map.Entry<String, Mapping> me : this.mappingUrls.entrySet()) {
            out.println("Key : "+me.getKey()+", Value : "+me.getValue());
        }
        out.println("\n\nL'URL est supportée : "+this.mappingUrls.containsKey(url));

        if(this.mappingUrls.containsKey(url)) {
            Mapping mapping = this.mappingUrls.get(url);

            out.println("C'est ici que ça se passe !, modif");

            out.println("ClassName : "+mapping.getClassName());
            out.println("Méthode : "+mapping.getMethod());

            // Instanciation d'un objet de type classname du Mapping
            Class<?> classe = Class.forName(mapping.getClassName());
            Object object = classe.cast(classe.getDeclaredConstructor().newInstance());

            Method method = classe.getDeclaredMethod(mapping.getMethod());

            // Prendre la view dans le ModelView retourné
            ModelView modelView = (ModelView) method.invoke(object);
            String view = modelView.getView();

            out.println(view);

            //Dispatch vers la vue correspondante
            RequestDispatcher dispat = req.getRequestDispatcher(view);
            dispat.forward(req,res);

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
