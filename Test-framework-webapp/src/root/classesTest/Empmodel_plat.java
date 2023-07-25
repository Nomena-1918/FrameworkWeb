package root.classesTest;

import java.util.Date;

public class Empmodel_plat extends BDDgeneral {
    Integer id;
    Date date;
    Integer idPlat;
    Integer idEmp;
    String nomfichier;

    public Empmodel_plat() throws Exception {
    }

    public Empmodel_plat(Emp e) throws Exception {
        this.id = e.getId();
        this.date=e.getDate();
        this.idPlat=e.getIdPlat();
        this.idEmp=e.getIdEmp();
        this.nomfichier=e.getFichier().getNom();
    }

}
