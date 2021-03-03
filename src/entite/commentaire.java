package entite;

import java.util.Objects;

public class commentaire {
    private int id_commentaire;
    private int id_pub;
    private String contenu;
    private int nb_like;

    public int getId_commentaire() {
        return id_commentaire;
    }

    public void setId_commentaire(int id_commentaire) {
        this.id_commentaire = id_commentaire;
    }

    public int getId_pub() {
        return id_pub;
    }

    public void setId_pub(int id_pub) {
        this.id_pub = id_pub;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public int getNb_like() {
        return nb_like;
    }

    public void setNb_like(int nb_like) {
        this.nb_like = nb_like;
    }

    public commentaire(int id_commentaire, int id_pub, String contenu, int nb_like) {
        this.id_commentaire = id_commentaire;
        this.id_pub = id_pub;
        this.contenu = contenu;
        this.nb_like = nb_like;
    }

    public commentaire(int id_commentaire, String contenu, int nb_like) {
        this.id_commentaire = id_commentaire;
        this.contenu = contenu;
        this.nb_like = nb_like;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        commentaire that = (commentaire) o;
        return id_commentaire == that.id_commentaire;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_commentaire);
    }
}
