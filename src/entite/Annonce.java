package entite;

import java.sql.Timestamp;
import java.util.Date;

public class Annonce {
    private int id_annonce;
    private int id_client;
    private String titre;
    private String description;
    private int prix_min;
    private int prix_max;
    private Date date;
    private String adresse;
    private boolean etat;
    private Timestamp date_annonce;
    private int nb_candidature;
    private int id_type_eve;

    public Annonce(int id_annonce, int id_client, String titre, String description, int prix_min, int prix_max, Date date, String adresse, boolean etat, Timestamp date_annonce, int nb_candidature, int id_type_eve) {
        this.id_annonce = id_annonce;
        this.id_client = id_client;
        this.titre = titre;
        this.description = description;
        this.prix_min = prix_min;
        this.prix_max = prix_max;
        this.date = date;
        this.adresse = adresse;
        this.etat = etat;
        this.date_annonce = date_annonce;
        this.nb_candidature = nb_candidature;
        this.id_type_eve = id_type_eve;
    }
    public Annonce( int id_client, String titre, String description, int prix_min, int prix_max, Date date, String adresse, boolean etat,  int nb_candidature, int id_type_eve) {
        this.id_client = id_client;
        this.titre = titre;
        this.description = description;
        this.prix_min = prix_min;
        this.prix_max = prix_max;
        this.date = date;
        this.adresse = adresse;
        this.etat = etat;
        this.nb_candidature = nb_candidature;
        this.id_type_eve = id_type_eve;
    }
    public int getId_annonce() {
        return id_annonce;
    }

    public void setId_annonce(int id_annonce) {
        this.id_annonce = id_annonce;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrix_min() {
        return prix_min;
    }

    public void setPrix_min(int prix_min) {
        this.prix_min = prix_min;
    }

    public int getPrix_max() {
        return prix_max;
    }

    public void setPrix_max(int prix_max) {
        this.prix_max = prix_max;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public Timestamp getDate_annonce() {
        return date_annonce;
    }

    public void setDate_annonce(Timestamp date_annonce) {
        this.date_annonce = date_annonce;
    }

    public int getNb_candidature() {
        return nb_candidature;
    }

    public void setNb_candidature(int nb_candidature) {
        this.nb_candidature = nb_candidature;
    }

    public int getId_type_eve() {
        return id_type_eve;
    }

    public void setId_type_eve(int id_type_eve) {
        this.id_type_eve = id_type_eve;
    }
}
