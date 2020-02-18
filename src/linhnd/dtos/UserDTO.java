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
public class UserDTO implements Serializable{
        private String idUser;
        private String userName;
        private String email;
        private String phone;
        private String photoName;
        private String rank;
        
        public Vector toVector(){
            Vector v = new Vector();
            v.add(idUser);
            v.add(userName);
            v.add(email);
            v.add(phone);
            v.add(photoName);
            return v;
        }
        public Vector toVectorRank(){
            Vector v = new Vector();
            v.add(idUser);
            v.add(userName);
            v.add(email);
            v.add(phone);
            v.add(rank);
            return v;
        }

    public UserDTO(String idUser, String userName, String email, String phone, String photoName) {
        this.idUser = idUser;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.photoName = photoName;
    }

    public UserDTO(String idUser, String userName, String email, String phone, String photoName, String rank) {
        this.idUser = idUser;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.photoName = photoName;
        this.rank = rank;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }


    public String getPhotoName() {
        return photoName;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }
    
        
        
}
