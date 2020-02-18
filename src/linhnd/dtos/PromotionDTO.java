/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnd.dtos;

import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author Duc Linh
 */
public class PromotionDTO implements Serializable{
    private String idPro;
    private String namePro;
    private String desPro;
    private String rank;
    private String date;

     public Vector toVector(){
        Vector v = new Vector();
        v.add(idPro);
        v.add(namePro);
        v.add(desPro);
        return v;
    }
     public Vector toVectorUser(){
         Vector v = new Vector();
         v.add(idPro);
         v.add(namePro);
         v.add(desPro);
         v.add(rank);
         v.add(date);
         return v;
     }
     public Vector toVectorHistory(){
         Vector v = new Vector();
         v.add(idPro);
         v.add(namePro);
         v.add(rank);
         v.add(date);
         return v;
     }

    public PromotionDTO(String idPro, String namePro, String rank, String date) {
        this.idPro = idPro;
        this.namePro = namePro;
        this.rank = rank;
        this.date = date;
    }
     
    
    public PromotionDTO(String idPro, String namePro, String despro) {
        this.idPro = idPro;
        this.namePro = namePro;
        this.desPro = despro;
    }

    public PromotionDTO(String idPro, String namePro, String desPro, String rank, String date) {
        this.idPro = idPro;
        this.namePro = namePro;
        this.desPro = desPro;
        this.rank = rank;
        this.date = date;
    }

    public String getRank() {
        return rank;
    }

    public String getDate() {
        return date;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIdPro() {
        return idPro;
    }

    public String getNamePro() {
        return namePro;
    }

    public String getDesPro() {
        return desPro;
    }

    public void setIdPro(String idPro) {
        this.idPro = idPro;
    }

    public void setNamePro(String namePro) {
        this.namePro = namePro;
    }

    public void setDesPro(String desPro) {
        this.desPro = desPro;
    }
    
}
