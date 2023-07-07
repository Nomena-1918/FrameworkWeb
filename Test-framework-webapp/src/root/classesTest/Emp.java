package root.classesTest;

import etu1918.framework.annotationPerso.*;
import etu1918.framework.mapping.ModelView;
import utilPerso.FileUpload;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Model
@Scope("singleton")
public class Emp {
    Integer matricule;
    Boolean isBoss = false;
    Date dtn;
    String nom;
    String[] prenoms;
    FileUpload fichier;
    Integer count = 0;
    HashMap<String, Object> session;

    @Auth
    @URLMapping(value = "/list-emp.run")
    public ModelView listView() {
        count++;
        ModelView m = new ModelView();

        List<Emp> listEmp = new ArrayList<>();
        listEmp.add(new Emp(1, "Jeanne"));
        listEmp.add(new Emp(2, "Rasoa"));
        listEmp.add(new Emp(3, "Bema"));

        m.addItem("count", count);
        m.addItem("list-emp", listEmp);


        m.setView("listEmp.jsp");
        return m;
    }

    @Auth("admin")
    @URLMapping(value = "/form-emp.run")
    public ModelView formView() {
        ModelView m = new ModelView();
        m.addItem("count", count);
        m.setView("formEmp.jsp");
        return m;
    }


    @URLMapping(value = "/form-data.run")
    public ModelView affFormData() {
        count++;
        ModelView m = new ModelView();

        m.addItem("count", count);
        m.addItem("formData", this);
        m.setView("formDataView.jsp");

        return m;
    }
////////////////////////////
    @URLMapping(value = "/login.run")
    public ModelView Login() {
        count++;
        ModelView m = new ModelView();

        m.addItem("count", count);
        //m.setView("viewTest/login.jsp");

        /// SESSION
        String varProfil = "profil";
        String valProfil = "admin";

        m.addSession(varProfil, valProfil);
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

//////////////////////////
    @Auth("admin")
    @URLMapping(value = "/nbr/mistery.run")
    public ModelView methodWithSeveralArg(@ParamValue(value = "num") Integer number, @ParamValue(value = "num1") Integer number1) {
        count++;
        ModelView m = new ModelView();

        if (number == null)
            number = 19;

        if (number1 == null)
            number1 = 24;

        Integer somme = number+number1+(Integer)session.get("nbr");

        m.addItem("count", count);
        m.addItem("numberMistery", somme);

        m.setJson(true);


        m.setView("/view/affNumberMistery.jsp");


        return m;
    }


    public Emp(Integer matricule, String nom) {
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
        return session;
    }

    public void setSession(HashMap<String, Object> session) {
        this.session = session;
    }

    public Emp() {
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
