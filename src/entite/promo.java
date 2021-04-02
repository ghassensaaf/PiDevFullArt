/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

import java.util.Date;

/**
 *
 * @author User P
 */
public class promo {
    

    
  private  int idpromo;
   private int id_service;
   private int id_artiste;
   private Date dated;
   private Date datef;
   private int vpromo;
   private String nom_service;

    public promo(int idpromo, int id_service, int id_artiste, Date dated, Date datef, int vpromo, String nom_service) {
        this.idpromo = idpromo;
        this.id_service = id_service;
        this.id_artiste = id_artiste;
        this.dated = dated;
        this.datef = datef;
        this.vpromo = vpromo;
        this.nom_service = nom_service;
    }

    public promo(int idpromo, int id_artiste, Date dated, Date datef, int vpromo, String nom_service) {
        this.idpromo = idpromo;
        this.id_artiste = id_artiste;
        this.dated = dated;
        this.datef = datef;
        this.vpromo = vpromo;
        this.nom_service = nom_service;
    }

    public int getIdpromo() {
        return idpromo;
    }

    public void setIdpromo(int idpromo) {
        this.idpromo = idpromo;
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

    public Date getDated() {
        return dated;
    }

    public void setDated(Date dated) {
        this.dated = dated;
    }

    public Date getDatef() {
        return datef;
    }

    public void setDatef(Date datef) {
        this.datef = datef;
    }

    public int getVpromo() {
        return vpromo;
    }

    public void setVpromo(int vpromo) {
        this.vpromo = vpromo;
    }

    public String getNom_service() {
        return nom_service;
    }

    public void setNom_service(String nom_service) {
        this.nom_service = nom_service;
    }

    
   
   
}

