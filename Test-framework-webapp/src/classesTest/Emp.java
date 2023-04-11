package classesTest;

import etu1918.framework.annotationPerso.Model;
import etu1918.framework.annotationPerso.URLMapping;
import etu1918.framework.mapping.ModelView;

import java.util.ArrayList;
import java.util.List;

@Model
public class Emp {
    int matricule;
    String nom;

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


    public Emp(int matricule, String nom) {
        this.matricule = matricule;
        this.nom = nom;
    }

    public Emp() {
    }

    public int getMatricule() {
        return matricule;
    }

    public void setMatricule(int matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
