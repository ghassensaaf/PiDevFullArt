package entite;

public class jaime {
    private int id_like;
    private int id_artiste;
    private int id_client;
    private int id_pub;
    private int id_commentaire;

    public int getId_like() {
        return id_like;
    }

    public void setId_like(int id_like) {
        this.id_like = id_like;
    }

    public int getId_artiste() {
        return id_artiste;
    }

    public void setId_artiste(int id_artiste) {
        this.id_artiste = id_artiste;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public int getId_pub() {
        return id_pub;
    }

    public void setId_pub(int id_pub) {
        this.id_pub = id_pub;
    }

    public int getId_commentaire() {
        return id_commentaire;
    }

    public void setId_commentaire(int id_commentaire) {
        this.id_commentaire = id_commentaire;
    }

    public jaime(int id_like, int id_artiste, int id_client, int id_pub) {
        this.id_like = id_like;
        this.id_artiste = id_artiste;
        this.id_client = id_client;
        this.id_pub = id_pub;
    }



}
