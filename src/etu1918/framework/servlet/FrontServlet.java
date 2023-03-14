package etu1918.framework.servlet;

import etu1918.framework.Mapping;
import utilPerso.Utilitaire;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

public class FrontServlet extends HttpServlet {

    HashMap<String, Mapping> MappingUrls;


    public void ProcessRequest(HttpServletRequest req, HttpServletResponse res) throws IOException {
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
    }
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        ProcessRequest(req, res);
    }
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        ProcessRequest(req, res);
    }
}
