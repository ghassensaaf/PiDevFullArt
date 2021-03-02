package entite;

import java.util.Date;

public class publication {
    private int id_pub;
    private int id_artiste;
    private int id_type;
    private String titre;
    private String contenu;
    private Date date_pub;
    private int nb_like;

    public publication(int id_pub, int id_artiste, int id_type, String titre, String contenu, Date date_pub, int nb_like) {
        this.id_pub = id_pub;
        this.id_artiste = id_artiste;
        this.id_type = id_type;
        this.titre = titre;
        this.contenu = contenu;
        this.date_pub = date_pub;
        this.nb_like = nb_like;
    }

    public publication(int id_artiste, int id_type, String titre, String contenu, Date date_pub, int nb_like) {
        this.id_artiste = id_artiste;
        this.id_type = id_type;
        this.titre = titre;
        this.contenu = contenu;
        this.date_pub = date_pub;
        this.nb_like = nb_like;
    }

    public int getId_pub() {
        return id_pub;
    }

    public void setId_pub(int id_pub) {
        this.id_pub = id_pub;
    }

    public int getId_artiste() {
        return id_artiste;
    }

    public void setId_artiste(int id_artiste) {
        this.id_artiste = id_artiste;
    }

    public int getId_type() {
        return id_type;
    }

    public void setId_type(int id_type) {
        this.id_type = id_type;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDate_pub() {
        return date_pub;
    }

    public void setDate_pub(Date date_pub) {
        this.date_pub = date_pub;
    }

    public int getNb_like() {
        return nb_like;
    }

    public void setNb_like(int nb_like) {
        this.nb_like = nb_like;
    }
}
