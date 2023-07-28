package root.classesTest;

import database.BDDgeneral;

import java.sql.Date;

public class Empmodel_plat extends BDDgeneral {
    Integer id;
    Integer idEmp;
    Integer idPlat;
    Date date;
    String nomfichier;


    public Empmodel_plat() throws Exception {
    }

    public Empmodel_plat(Emp e) throws Exception {
        this.id = e.getId();
        this.date= Date.valueOf(e.getDate().toString());
        this.idPlat=e.getIdPlat();
        this.idEmp=e.getIdEmp();
        this.nomfichier=e.getFichier().getNom();
    }

    @Override
    public String toString() {
        return "{ id : " + id +
                "\n,date : " + date +
                "\n,idPlat : " + idPlat +
                "\n,idEmp : " + idEmp +
                "\n,nomfichier : " + nomfichier + " }";
    }

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

    public String getNomfichier() {
        return nomfichier;
    }

    public void setNomfichier(String nomfichier) {
        this.nomfichier = nomfichier;
    }
}
