package root.classesTest;


import database.ConnectionPerso;
import etu1918.framework.annotationPerso.*;
import etu1918.framework.mapping.ModelView;
import utilPerso.FileUpload;

import java.sql.Connection;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;


@Model
@Scope("singleton")
public class Emp {
    Integer id;
    Date date;
    Integer idPlat;
    Integer idEmp;
    FileUpload fichier;
    HashMap<String, Object> _session;


    @Auth("admin")
    @URLMapping(value = "/form-emp.run")
    public ModelView formView() throws Exception {
        ModelView m = new ModelView();
        Connection c = ConnectionPerso.getConnection();

        Plat plat = new Plat();
        List<Object> listPlat = plat.select(c);

        EmpModel emp = new EmpModel();
        List<Object> listEmp = emp.select(c);

        m.addItem("list-emp", listEmp);
        m.addItem("list-plat", listPlat);

        m.setView("formEmp.jsp");

        c.close();

        return m;
    }


    @URLMapping(value = "/list-emp-plat.run")
    public ModelView listView() throws Exception {
        System.out.println("list-view");

        ModelView m = new ModelView();
        List<Object> listEmpPlat = new V_Empmodel_plat().select(null);
        m.addItem("list-emp-plat", listEmpPlat);
        m.setView("listEmp.jsp");

        return m;
    }

    @Auth("admin")
    @URLMapping(value = "/form-data.run")
    public ModelView insertFormData(@ParamValue(value = "dtn") Date date) throws Exception {
        ModelView m = new ModelView();

        System.out.println("form-data");

        Connection c = ConnectionPerso.getConnection();

        // Insertion
        this.date = date;
        Empmodel_plat e = new Empmodel_plat(this);
        e.save(c);

        c.commit();
        c.close();

        m.setView("list-emp-plat.run");
        return m;
    }

////////////////////////////
    @URLMapping(value = "/login.run")
    public ModelView Login() {
        ModelView m = new ModelView();
        System.out.println("Login");

        /// SESSION
        String varProfil = "profil";
        String valProfil = "admin";

        m.addSession(varProfil, valProfil);
        m.addSession("varSession", "just a simple string");

        m.setView("index.jsp");

        return m;
    }

    @XML
    @URLMapping(value = "/export-xml.run")
    public Plats ExportXML() throws Exception {

        // Create a list and add the V_Empmodel_plat instances
        List<Object> listV_Empmodel_plat = new V_Empmodel_plat().select(null);

        // Create an instance of Plats and set the list
        Plats plats = new Plats();
        plats.setListV_Empmodel_plat(listV_Empmodel_plat);

        return plats;

    }


    @URLMapping(value = "/process-login.run")
    public ModelView processFormfLogin(@ParamValue(value = "mdp") String mdp) {
        ModelView m = new ModelView();

        /// SESSION
        String varProfil = "profil";
        String valProfil = null;

        String motCle = "root";
        if (mdp.equalsIgnoreCase(motCle))
            valProfil = mdp;

        m.addSession(varProfil, valProfil);
        ////

        m.setView("index.jsp");

        return m;
    }

    @Auth
    @URLMapping(value = "/logout.run")
    public ModelView RemoveOneSession() {
        ModelView m = new ModelView();
        m.removeSession("profil");
        m.setView("index.jsp");
        return m;
    }

    @Auth("admin")
    @URLMapping(value = "/invalidate-session.run")
    public ModelView RemoveAllSession() {
        ModelView m = new ModelView();
        m.InvalidateSession();
        m.setView("index.jsp");
        return m;
    }

//////////////////////////
    @Auth("admin")
    @URLMapping(value = "/nbr/mistery.run")
    public ModelView methodWithSeveralArg(@ParamValue(value = "num") Integer number, @ParamValue(value = "num1") Integer number1) {

        ModelView m = new ModelView();

        if (number == null)
            number = 19;

        if (number1 == null)
            number1 = 24;


        Integer somme = number+number1+(Integer)_session.get("nbr");

        m.addItem("numberMistery", somme);

        m.setJson(true);
        m.setView("/view/affNumberMistery.jsp");

        return m;
    }

    @REST_API
    @URLMapping(value = "/rest-api.run")
    public V_Empmodel_plat methodJson(@ParamValue(value = "arg") Boolean isBoss) throws Exception {
        if (isBoss)
            return new V_Empmodel_plat(3, Date.valueOf("2023-07-30"), "Bob Smith", "Libelle3", "File3");
        else
            return new V_Empmodel_plat(4, Date.valueOf("2023-07-30"), "Alice Johnson", "Libelle4", "File4");
    }


    @URLMapping(value = "/download-file.run")
    public ModelView DownloadFile(@ParamValue(value = "filename") String filename) {
        ModelView m = new ModelView();
        m.addItem("filename", filename);
        m.setView("list-emp-plat.run");
        return m;
    }

    public Emp() {
        this._session = new HashMap<>();
    }

    public HashMap<String, Object> getSession() {
        return _session;
    }

    public void setSession(HashMap<String, Object> session) {
        this._session = session;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public FileUpload getFichier() {
        return fichier;
    }

    public void setFichier(FileUpload fichier) {
        this.fichier = fichier;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(Integer idPlat) {
        this.idPlat = idPlat;
    }

    public Integer getIdEmp() {
        return idEmp;
    }

    public void setIdEmp(Integer idEmp) {
        this.idEmp = idEmp;
    }
}
