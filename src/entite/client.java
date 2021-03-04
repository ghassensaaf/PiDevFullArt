package entite;

import java.util.Objects;

public class client {

    private int id_client;
    private String login;
    private String pwd;
    private String nom;
    private String prenom;
    private String mail;
    private int tel;
    private String photo;
    private String adresse;

    public client(int id_client) {
        this.id_client = id_client;
    }

    public client(int id_client, String login, String pwd, String nom, String prenom, String mail, int tel, String photo, String adresse) {
        this.id_client = id_client;
        this.login = login;
        this.pwd = pwd;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.tel = tel;
        this.photo = photo;
        this.adresse = adresse;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        client client = (client) o;
        return id_client == client.id_client;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_client);
    }

    @Override
    public String toString() {
        return "client{" +
                "id_client=" + id_client +
                ", login='" + login + '\'' +
                ", pwd='" + pwd + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", mail='" + mail + '\'' +
                ", tel=" + tel +
                ", photo='" + photo + '\'' +
                ", adresse='" + adresse + '\'' +
                '}';
    }
}

