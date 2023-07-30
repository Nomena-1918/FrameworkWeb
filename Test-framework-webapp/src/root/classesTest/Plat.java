package root.classesTest;


import database.BDDgeneral;

public class Plat extends BDDgeneral {
    Integer id;
    String libelle;

    public Plat() throws Exception {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
