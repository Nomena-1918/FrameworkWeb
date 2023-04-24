package classesTest;

import etu1918.framework.annotationPerso.Model;
import etu1918.framework.annotationPerso.URLMapping;
import etu1918.framework.mapping.ModelView;
import utilPerso.Utilitaire;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// ---------------S a la fin : NECESSAIRE POUR ETRE VU DANS LE FORMULAIRE
//----------------public String getMatriculeS() {
@Model
public class Emp {
    Integer matricule;
    Boolean isBoss = false;
    Date dtn;
    String nom;
    String[] prenoms;


    @URLMapping(valeur = "/list-emp.run")
    public ModelView listView() {
        ModelView m = new ModelView();

        List<Emp> listEmp = new ArrayList<>();
        listEmp.add(new Emp(1, "Jeanne"));
        listEmp.add(new Emp(2, "Rasoa"));
        listEmp.add(new Emp(3, "Bema"));

        m.addItem("list-emp", listEmp);

        m.setView("listEmp.jsp");
        return m;
    }

    @URLMapping(valeur = "/form-emp.run")
    public ModelView formView() {
        ModelView m = new ModelView();
        m.setView("formEmp.jsp");
        return m;
    }

    @URLMapping(valeur = "/form-data.run")
    public ModelView affFormData() {
        ModelView m = new ModelView();

        m.addItem("formData", this);
        m.setView("formDataView.jsp");

        return m;
    }

    public Emp(Integer matricule, String nom) {
        setMatricule(matricule);
        setNom(nom);
    }

    public Emp() {
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
}
