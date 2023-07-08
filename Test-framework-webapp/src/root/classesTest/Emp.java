package root.classesTest;

import etu1918.framework.annotationPerso.*;
import etu1918.framework.mapping.ModelView;
import utilPerso.FileUpload;
import java.util.*;

@Model
@Scope("singleton")
public class Emp {
    Integer matricule;
    Boolean isBoss;
    Date dtn;
    String nom;
    String[] prenoms;
    FileUpload fichier;
    Integer count;
    HashMap<String, Object> _session;

    @Auth
    @URLMapping(value = "/list-emp.run")
    public ModelView listView() {

        ModelView m = new ModelView();

        List<Emp> listEmp = new ArrayList<>();
        listEmp.add(new Emp(1, "Jeanne"));
        listEmp.add(new Emp(2, "Rasoa"));
        listEmp.add(new Emp(3, "Bema"));

        int c = 0;
        if (count!=null) {
            count++;
            c += count;
        }

        m.addItem("count", c);
        m.addItem("list-emp", listEmp);

        m.setView("listEmp.jsp");
        return m;
    }

    @Auth("admin")
    @URLMapping(value = "/form-emp.run")
    public ModelView formView() {
        ModelView m = new ModelView();
        int c = 0;
        if (count!=null) {
            count++;
            c += count;
        }

        m.addItem("count", c);
        m.setView("formEmp.jsp");
        return m;
    }


    @URLMapping(value = "/form-data.run")
    public ModelView affFormData() {
        ModelView m = new ModelView();

        int c = 0;
        if (count!=null) {
            count++;
            c += count;
        }

        m.addItem("count", c);
        m.addItem("formData", this);
        m.setView("formDataView.jsp");

        return m;
    }
////////////////////////////
    @URLMapping(value = "/login.run")
    public ModelView Login() {
        ModelView m = new ModelView();
        int c = 0;
        if (count!=null) {
            count++;
            c += count;
        }

        m.addItem("count", c);
        //m.setView("viewTest/login.jsp");

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

    public Emp() {
        this.isBoss = false;
        this.count = 0;
        this._session = new HashMap<>();
    }

    public Emp(Integer matricule, String nom) {
        this.isBoss = false;
        this.count = 0;
        setMatricule(matricule);
        setNom(nom);
    }

    public Boolean getBoss() {
        return isBoss;
    }

    public void setBoss(Boolean boss) {
        isBoss = boss;
    }

    public HashMap<String, Object> getSession() {
        return _session;
    }

    public void setSession(HashMap<String, Object> session) {
        this._session = session;
    }


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getMatricule() {
        return matricule;
    }

    public void setMatricule(Integer matricule) {
        this.matricule = matricule;
    }

    public Boolean getIsBoss() {
        return isBoss;
    }

    public void setIsBoss(Boolean boss) {
        isBoss = boss;
    }

    public Date getDtn() {
        return dtn;
    }

    public void setDtn(Date dtn) {
        this.dtn = dtn;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String[] getPrenoms() {
        return prenoms;
    }

    public void setPrenoms(String[] prenoms) {
        this.prenoms = prenoms;
    }

    public FileUpload getFichier() {
        return fichier;
    }

    public void setFichier(FileUpload fichier) {
        this.fichier = fichier;
    }

}
