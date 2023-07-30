package root.classesTest;

import com.google.gson.annotations.SerializedName;
import database.BDDgeneral;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.sql.Date;

@Root(name="Plat")

public class V_Empmodel_plat extends BDDgeneral {
    @Element
    @SerializedName("v_empmodel_plat_id")
    Integer id;
    @Element
    Date date;
    @Element(name = "nomEmp")
    String nom;
    @Element
    String libelle;
    @Element
    String nomfichier;

    public V_Empmodel_plat(Integer id, Date date, String nom, String libelle, String nomfichier) throws Exception {
        this.id = id;
        this.date = date;
        this.nom = nom;
        this.libelle = libelle;
        this.nomfichier = nomfichier;
    }

    public V_Empmodel_plat() throws Exception {}

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getNomfichier() {
        return nomfichier;
    }

    public void setNomfichier(String nomfichier) {
        this.nomfichier = nomfichier;
    }
}
