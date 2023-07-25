package root.classesTest;

import database.ConnectionPerso;
import etu1918.framework.annotationPerso.*;
import etu1918.framework.mapping.ModelView;
import utilPerso.FileUpload;

import java.sql.Connection;
import java.util.*;

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
        //Connection c = ConnectionPerso.getConnection();

        Plat plat = new Plat();
        List<Object> listPlat = new ArrayList<>();
                //= plat.select(c);

        EmpModel emp = new EmpModel();
        List<Object> listEmp = new ArrayList<>();
                //= emp.select(c);

        m.addItem("list-emp", listEmp);
        m.addItem("list-plat", listPlat);

        m.setView("formEmp.jsp");
        //c.close();

        return m;
    }


    @URLMapping(value = "/list-emp-plat.run")
    public ModelView listView() throws Exception {
        ModelView m = new ModelView();

        List<Object> listEmpPlat = new V_Empmodel_plat().select(null);

        m.addItem("list-emp-plat", listEmpPlat);

        m.setView("listEmp.jsp");
        return m;
    }


    @URLMapping(value = "/form-data.run")
    public ModelView insertFormData() throws Exception {
        ModelView m = new ModelView();

        Empmodel_plat e = new Empmodel_plat(this);
        e.save(null);

        m.setView("index.jsp");

        return m;
    }

////////////////////////////
    @URLMapping(value = "/login.run")
    public ModelView Login() {
        ModelView m = new ModelView();

        /// SESSION
        String varProfil = "profil";
        String valProfil = "admin";

        m.addSession(varProfil, valProfil);
        m.addSession("varSession", "just a simple string");

        m.setView("index.jsp");

        return m;
    }

    /*
    @URLMapping(value = "/process-login.run")
    public ModelView processFormLogin(@ParamValue(value = "mdp") String mdp) {
        count++;
        ModelView m = new ModelView();
        m.addItem("count", count);

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
    */

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
/*
//////////////////////////
    @Auth("admin")
    @URLMapping(value = "/nbr/mistery.run")
    public ModelView methodWithSeveralArg(@ParamValue(value = "num") Integer number, @ParamValue(value = "num1") Integer number1) {

        ModelView m = new ModelView();

        if (number == null)
            number = 19;

        if (number1 == null)
            number1 = 24;

        int c = 0;
        if (count!=null) {
            count++;
            c += count;
        }

        Integer somme = number+number1+(Integer)_session.get("nbr");

        m.addItem("count", c);

        m.addItem("numberMistery", somme);

        m.setJson(true);
        m.setView("/view/affNumberMistery.jsp");

        return m;
    }

    @REST_API
    @URLMapping(value = "/rest-api.run")
    public Emp methodJson(@ParamValue(value = "arg") Boolean isBoss) {
        Emp emp = new Emp();
        emp.setMatricule(1);
        emp.setBoss(Objects.requireNonNullElse(isBoss, false));
        emp.setNom("Jay");
        emp.setPrenoms(new String[]{"Son"});
        emp.setDtn(new Date());
        emp.setSession(this._session);
        return emp;
    }
*/
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

    public void setDate(Date date) {
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
