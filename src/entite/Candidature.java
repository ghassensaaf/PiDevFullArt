package entite;

import java.sql.Timestamp;
import java.util.Objects;

public class Candidature {
    private int id_candidature;
    private int id_annonce;
    private String contenu;
    private int prix;
    private int etat;
    private int id_artiste;
    private Timestamp date;

    public Candidature() {
    }

    public Candidature(int id_candidature, int id_annonce, String contenu, int prix, int etat, int id_artiste, Timestamp date) {
        this.id_candidature = id_candidature;
        this.id_annonce = id_annonce;
        this.contenu = contenu;
        this.prix = prix;
        this.etat = etat;
        this.id_artiste = id_artiste;
        this.date = date;
    }

    public int getId_candidature() {
        return id_candidature;
    }

    public void setId_candidature(int id_candidature) {
        this.id_candidature = id_candidature;
    }

    public int getId_annonce() {
        return id_annonce;
    }

    public void setId_annonce(int id_annonce) {
        this.id_annonce = id_annonce;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getId_artiste() {
        return id_artiste;
    }

    public void setId_artiste(int id_artiste) {
        this.id_artiste = id_artiste;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidature that = (Candidature) o;
        return id_candidature == that.id_candidature;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_candidature);
    }
}
