package entite;


import java.util.Objects;

public class avis {
     private int id_avis;
     private int id_artiste;
     private int note;
     private String contenu;

    public int getId_artiste() {
        return id_artiste;
    }

    public void setId_artiste(int id_artiste) {
        this.id_artiste = id_artiste;
    }

    public int getId_avis() {
        return id_avis;
    }

    public void setId_avis(int id_avis) {
        this.id_avis = id_avis;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public avis(int id_avis, int note, String contenu) {
        this.id_avis = id_avis;
        this.note = note;
        this.contenu = contenu;
    }

    public avis(int id_avis, int id_artiste, int note, String contenu) {
        this.id_avis = id_avis;
        this.id_artiste = id_artiste;
        this.note = note;
        this.contenu = contenu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        avis avis = (avis) o;
        return id_avis == avis.id_avis;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_avis);
    }
}
