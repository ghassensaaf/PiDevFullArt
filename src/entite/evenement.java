package entite;

import java.util.Objects;

public class evenement {
    private int id;
    private String nom;

    public evenement() {
    }

    public evenement(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        evenement evenement = (evenement) o;
        return id == evenement.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
