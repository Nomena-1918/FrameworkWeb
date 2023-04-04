package business;

public class Emp {
    String nom;
    int matricule;

    public Emp() {
    }
    public Emp(String nom, int matricule) {
        this.nom = nom;
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getMatricule() {
        return matricule;
    }

    public void setMatricule(int matricule) {
        this.matricule = matricule;
    }
}
