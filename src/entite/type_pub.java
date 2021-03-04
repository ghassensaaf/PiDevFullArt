package entite;

public class type_pub {
    private int id_type;
    private String nom;

    public type_pub(int id_type, String nom) {
        this.id_type = id_type;
        this.nom = nom;


    }

    public type_pub() {
    }

    public int getId_type() {
        return id_type;
    }

    public void setId_type(int id_type) {
        this.id_type = id_type;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
