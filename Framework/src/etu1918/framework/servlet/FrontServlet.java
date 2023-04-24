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
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDateTime;
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
                out.println("\n\nParamètres du formulaire :");

                Enumeration<String> nomsParam = req.getParameterNames();
                String[] valeurParam;
                String valeur;
                String nomParam, Param, setter, lo, geTest;

                Class<?> fieldC;

                while (nomsParam.hasMoreElements()) {

                    // Paramètre du formulaire
                    nomParam = nomsParam.nextElement();
                    out.println("\n-> "+nomParam+" = {\n");

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
                    out.println("     Type du param : "+ fieldC.getSimpleName());

                    Object o;

                    if (valeurParam.length == 1) {
                        out.println("     arg : " + valeurParam[0]);

                // ================== CAST ===================== //

                        if (fieldC.getSimpleName().equalsIgnoreCase("String")) {
                            o = valeurParam[0];
                        }
                        else if (valeurParam[0].equalsIgnoreCase("true") || valeurParam[0].equalsIgnoreCase("false")) {
                            o = Boolean.parseBoolean(valeurParam[0]);
                        }
                        else if (fieldC.getSimpleName().equalsIgnoreCase("Integer") && Utilitaire.isNumeric(valeurParam[0])) {
                            o = Integer.parseInt(valeurParam[0]);
                        }
                        else if (fieldC.getSimpleName().equalsIgnoreCase("Date")) {
                            o = new SimpleDateFormat("yyyy-MM-dd").parse(valeurParam[0]);
                        }

                        else
                            throw new Exception("Type de variable non pris en charge");

                // ========================================== //

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
                        out.println("     arg : "+Arrays.toString(valeurParam));


                // ================== CAST ===================== //

                        if (fieldC.equals(String[].class)) {
                            // Appel setter généralisé
                            Utilitaire.toSet(setter, object, valeurParam, fieldC);
                        }
                        else if (fieldC.getSimpleName().equalsIgnoreCase("int")) {
                            int[] tab = new int[valeurParam.length];

                            for(int i=0; i<valeurParam.length; i++) {
                                tab[i] = Integer.parseInt(valeurParam[i]);
                            }
                            // Appel setter généralisé
                            Utilitaire.toSet(setter, object, tab, fieldC);
                        }
                        else if (fieldC.getSimpleName().equalsIgnoreCase("Date[]")) {
                            Date[] tab = new Date[valeurParam.length];

                            for(int i=0; i<valeurParam.length; i++) {
                                tab[i] = new SimpleDateFormat("yyyy/MM/dd").parse(valeurParam[0]);
                            }
                            // Appel setter généralisé
                            Utilitaire.toSet(setter, object, tab, fieldC);
                        }
                        else
                            throw new Exception("Type de variable non pris en charge");


                // ========================================== //

                        out.println("\n\t-> Setter appelé avec succès !\n");

                        // Test setter avec getter
                        geTest = "get" + Param;
                        if (Utilitaire.toGet(geTest, object) != null)
                            out.println("\t-> Test setter : " + geTest + " = " + Arrays.toString((String[])Utilitaire.toGet(geTest, object)) + "\n");
                        else
                            out.println("\t-> Test setter : " + geTest + " = null\n");

                        out.println("    }\n");


                    }
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

            } else
                throw new Exception("URL non supportée");

        }
        catch (Exception e) {
            out.println(e);
        }

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
