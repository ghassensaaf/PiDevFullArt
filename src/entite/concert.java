package entite;

import java.util.Date;

public class concert {
    private int id_concert;
    private int id_artiste;
    private String lieu;
    private Date date;

    public concert(int id_concert, int id_artiste, String lieu, Date date) {
        this.id_concert = id_concert;
        this.id_artiste = id_artiste;
        this.lieu = lieu;
        this.date = date;
    }

    public concert(int id_artiste, String lieu, Date date) {
        this.id_artiste = id_artiste;
        this.lieu = lieu;
        this.date = date;
    }

    public int getId_concert() {
        return id_concert;
    }

    public void setId_concert(int id_concert) {
        this.id_concert = id_concert;
    }

    public int getId_artiste() {
        return id_artiste;
    }

    public void setId_artiste(int id_artiste) {
        this.id_artiste = id_artiste;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
