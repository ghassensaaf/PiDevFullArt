package entite;

import java.sql.Timestamp;
import java.util.Objects;

public class Message {
    private int id_message;
    private String contenu;
    private Timestamp date;
    private int id_client_dest;
    private int id_client_exp;
    private int id_artiste_dest;
    private int id_artiste_exp;

    public int getId_message() {
        return id_message;
    }

    public void setId_message(int id_message) {
        this.id_message = id_message;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getId_client_dest() {
        return id_client_dest;
    }

    public void setId_client_dest(int id_client_dest) {
        this.id_client_dest = id_client_dest;
    }

    public int getId_client_exp() {
        return id_client_exp;
    }

    public void setId_client_exp(int id_client_exp) {
        this.id_client_exp = id_client_exp;
    }

    public int getId_artiste_dest() {
        return id_artiste_dest;
    }

    public void setId_artiste_dest(int id_artiste_dest) {
        this.id_artiste_dest = id_artiste_dest;
    }

    public int getId_artiste_exp() {
        return id_artiste_exp;
    }

    public void setId_artiste_exp(int id_artiste_exp) {
        this.id_artiste_exp = id_artiste_exp;
    }

    public Message(int id_message, String contenu, Timestamp date, int id_client_dest, int id_client_exp, int id_artiste_dest, int id_artiste_exp) {

        this.id_message = id_message;
        this.contenu = contenu;
        this.date = date;
        this.id_client_dest = id_client_dest;
        this.id_client_exp = id_client_exp;
        this.id_artiste_dest = id_artiste_dest;
        this.id_artiste_exp = id_artiste_exp;
    }
    public Message(int id_message, String contenu, Timestamp date) {

        this.id_message = id_message;
        this.contenu = contenu;
        this.date = date;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return id_message == message.id_message;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_message);
    }
}
