package entite;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

public class reclamation {
    private int id_reclamation;
    private String titre;
    private String contenu;
    private Timestamp date;
    private int etat;
    private int id_artiste;
    private int id_client;


    public int getId_reclamation() { return id_reclamation; }

    public void setId_reclamation(int id_reclamation) { this.id_reclamation = id_reclamation; }

    public String getTitre() { return titre; }

    public void setTitre(String titre) { this.titre = titre; }

    public String getContenu() { return contenu; }

    public void setContenu(String contenu) { this.contenu = contenu; }

    public Timestamp getDate() { return date; }

    public void setDate(Timestamp date) { this.date = date; }

    public int getEtat() { return etat; }

    public void setEtat(int etat) { this.etat = etat; }

    public int getId_artiste() { return id_artiste; }

    public void setId_artiste(int id_artiste) { this.id_artiste = id_artiste; }

    public int getId_client() { return id_client; }

    public void setId_client(int id_client) { this.id_client = id_client; }

    public reclamation(int id_reclamation, String titre, String contenu, Timestamp date, int etat, int id_artiste, int id_client) {
        this.id_reclamation = id_reclamation;
        this.titre = titre;
        this.contenu = contenu;
        this.date = date;
        this.etat = etat;
        this.id_artiste = id_artiste;
        this.id_client = id_client;
    }

    public reclamation( String titre, String contenu, Timestamp date, int etat, int id_artiste, int id_client) {
        this.titre = titre;
        this.contenu = contenu;
        this.date = date;
        this.etat = etat;
        this.id_artiste = id_artiste;
        this.id_client = id_client;
    }
    public reclamation(int id_reclamation, String titre, String contenu, Timestamp date, int etat) {
        this.id_reclamation = id_reclamation;
        this.titre = titre;
        this.contenu = contenu;
        this.date = date;
        this.etat = etat;


    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        reclamation that = (reclamation) o;
        return id_reclamation == that.id_reclamation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_reclamation);
    }
}
