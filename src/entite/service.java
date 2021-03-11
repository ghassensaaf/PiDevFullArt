/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;


/**
 *
 * @author User P
 */
public class service {
    

    private int id_service;
    private int id_artiste;
    private String nom_service;
    private int prix_service;
    private String detail;

    public service(int id_service, int id_artiste, String nom_service, int prix_service, String detail) {
        this.id_service = id_service;
        this.id_artiste = id_artiste;
        this.nom_service = nom_service;
        this.prix_service = prix_service;
        this.detail = detail;
    }

    public service(int id_artiste, String nom_service, int prix_service, String detail) {
        this.id_artiste = id_artiste;
        this.nom_service = nom_service;
        this.prix_service = prix_service;
        this.detail = detail;
    }

    public int getId_service() {
        return id_service;
    }

    public void setId_service(int id_service) {
        this.id_service = id_service;
    }

    public int getId_artiste() {
        return id_artiste;
    }

    public void setId_artiste(int id_artiste) {
        this.id_artiste = id_artiste;
    }

    public String getNom_service() {
        return nom_service;
    }

    public void setNom_service(String nom_service) {
        this.nom_service = nom_service;
    }

    public int getPrix_service() {
        return prix_service;
    }

    public void setPrix_service(int prix_service) {
        this.prix_service = prix_service;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    
    

}
    

