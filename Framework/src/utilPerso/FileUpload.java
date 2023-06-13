package utilPerso;

public class FileUpload {
    String nom;
    String path;
    byte[] file;

    public FileUpload(String nom, byte[] file) {
        setNom(nom);
        setFile(file);
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public byte[] getFile() {
        return file;
    }
    public void setFile(byte[] file) {
        this.file = file;
    }


    

}