package entite;

import java.util.Objects;

public class artiste {
    private String login;
    private String pwd;
    private String nom;
    private String prenom;
    private String mail;
    private int tel;
    private String adresse;
    private String photo;
    private int id_artiste;
    private String description;
    private String couverture;
    private Integer etat;


    public Integer getEtat() {
        return etat;
    }

    public void setEtat(Integer etat) {
        this.etat = etat;
    }

    public int getId_artiste() {
        return id_artiste;
    }

    public void setId_artiste(int id_artiste) {
        this.id_artiste = id_artiste;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCouverture() {
        return couverture;
    }

    public void setCouverture(String couverture) {
        this.couverture = couverture;
    }

    public artiste(int id_artiste) {
        this.id_artiste = id_artiste;
    }

    public artiste(int id_artiste, String login, String nom, String prenom, String adresse, String mail, int tel, String pwd, String description, String photo, String couverture) {
        this.id_artiste = id_artiste;
        this.login = login;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.mail = mail;
        this.tel = tel;
        this.pwd = pwd;
        this.description = description;
        this.photo = photo;
        this.couverture = couverture;
    }
    public artiste( int id_artiste ,String nom, String prenom,String adresse, String mail, int tel, String description) {
        this.id_artiste = id_artiste;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.mail = mail;
        this.tel = tel;
        this.description = description;
    }

    public artiste(int id_artiste,String login, String nom, String prenom, String adresse,String mail, int tel,String pwd, String description, Integer etat) {
        this.id_artiste = id_artiste;
        this.login = login;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.mail = mail;
        this.tel = tel;
        this.pwd=pwd;
        this.description = description;
        this.etat = etat;
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof artiste)) return false;
        artiste artiste = (artiste) o;
        return id_artiste == artiste.id_artiste;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_artiste);
    }
}
