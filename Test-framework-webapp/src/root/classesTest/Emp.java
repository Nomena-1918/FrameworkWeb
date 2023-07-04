package root.classesTest;

import etu1918.framework.annotationPerso.Model;
import etu1918.framework.annotationPerso.ParamValue;
import etu1918.framework.annotationPerso.Scope;
import etu1918.framework.annotationPerso.URLMapping;
import etu1918.framework.mapping.ModelView;
import utilPerso.FileUpload;

import java.util.ArrayList;
import java.util.Date;
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

    /*
    @URLMapping(value = "/form-emp.run")
    public ModelView formView() {
        ModelView m = new ModelView();
        m.setView("formEmp.jsp");
        return m;
    }
*/
    @URLMapping(value = "/form-data.run")
    public ModelView affFormData() {
        count++;
        ModelView m = new ModelView();

        m.addItem("count", count);
        m.addItem("formData", this);
        m.setView("formDataView.jsp");

        return m;
    }

    @URLMapping(value = "/nbr/mistery.run")
    public ModelView methodWithSeveralArg(@ParamValue(value = "num") Integer number, @ParamValue(value = "num1") Integer number1) {
        count++;
        ModelView m = new ModelView();

        if (number == null)
            number = 19;

        if (number1 == null)
            number = 24;

        m.addItem("count", count);
        m.addItem("numberMistery", number);
        m.setView("/view/affNumberMistery.jsp");

        return m;
    }


    public Emp(Integer matricule, String nom) {
        setMatricule(matricule);
        setNom(nom);
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

    @Override
    public String toString(){
        return "this object";
    }

    public FileUpload getFichier() {
        return fichier;
    }

    public void setFichier(FileUpload fichier) {
        this.fichier = fichier;
    }

}
