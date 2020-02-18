/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnd.daos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import linhnd.db.MyConnection;
import linhnd.dtos.PromotionDTO;
import linhnd.dtos.UserDTO;

/**
 *
 * @author Duc Linh
 */
public class PromotionDAO implements Serializable{
    Connection conn;
    PreparedStatement preStm;
    ResultSet rs;
    
    public void closeConnection() throws SQLException{
        if (rs!=null) {
            rs.close();
        }
        if (preStm != null) {
            preStm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
    
    public List<PromotionDTO> getPromotionList() throws Exception{
        String idPro,namePro,desPro;
        List<PromotionDTO> result = null;
        PromotionDTO dto;
        try {
            String sql = "SELECT IDPro,namePro,desPro FROM dbo.Promotion ";
            conn = MyConnection.GetMyconnection();
            preStm = conn.prepareStatement(sql);
            rs = preStm.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {                
                idPro = rs.getString("IDPro");
                namePro = rs.getString("namePro");
                desPro = rs.getString("desPro");
                dto = new PromotionDTO(idPro, namePro, desPro);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    } 
    public PromotionDTO getOnePromotion(String idPro) throws Exception{
        String namePro,desPro;
        PromotionDTO dto = null ;
        try {
            String sql = " SELECT namePro,desPro FROM dbo.Promotion WHERE IDPro = ? ";
            conn = MyConnection.GetMyconnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, idPro);
            rs = preStm.executeQuery();
            if (rs.next()) {
                namePro = rs.getString("namePro");
                desPro = rs.getString("desPro");
                dto = new PromotionDTO(idPro, namePro, desPro);
            }
        }finally{
            closeConnection();
        }
        return dto;
    }
    public List<UserDTO> getUserInPromotion(String idPro) throws Exception{
        String idUser,userName,email,phone,photoName,rank;
        List<UserDTO> result = null;
        UserDTO dto = null;
        try {
            String sql = " SELECT userInPro.IDUser,userName,email,phone,photoName,rank FROM dbo.Users,dbo.userInPro \n" +
                        "WHERE dbo.Users.IDUser = dbo.userInPro.IDUser AND userInPro.IDPro = ? ORDER BY dbo.userInPro.rank DESC;";
            conn = MyConnection.GetMyconnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, idPro);
            rs = preStm.executeQuery();
            result = new ArrayList<>();
            while(rs.next()){
                idUser = rs.getString("IDUser");
                userName = rs.getString("userName");
                email = rs.getString("email");
                phone = rs.getString("phone");
                photoName = rs.getString("photoName");
                rank = rs.getString("rank");
                dto = new UserDTO(idUser, userName, email, phone, photoName, rank);
                result.add(dto);          
            }
        } finally{
            closeConnection();
        }
        return result;
    }
    public boolean checkUserinPro(String idPro,String userID) throws Exception{
        boolean check = false;
        try {
            String sql = "SELECT dbo.Users.IDUser FROM dbo.Users,dbo.userInPro \n" +
            "	WHERE dbo.Users.IDUser = dbo.userInPro.IDUser AND userInPro.IDUser = ? AND IDPro = ? ;";
            conn = MyConnection.GetMyconnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, userID);
            preStm.setString(2, idPro);
            rs = preStm.executeQuery();
            if (rs.next()) {
                check = true;
            }
        }finally{
            closeConnection();
        }
        return check;
    }

    public boolean insertUserintoPro(String idUser, String idPro, String date, String rank) throws Exception {
        boolean check = false;
        String idDetail = idUser + idPro;
        try {
            String sql = " INSERT dbo.userInPro VALUES (?,?,?,?,?)";
            conn = MyConnection.GetMyconnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, rank);
            preStm.setString(2, idUser);
            preStm.setString(3, idPro);
            preStm.setString(4, idDetail);
            preStm.setString(5, date);
            if (preStm.executeUpdate() > 0) {
                if (insertDateDetail(idUser, idPro, date, rank)) {
                    check = true;
                }
            }
        } finally {
            closeConnection();
        }
        return check;
    }

    public boolean insertDateDetail(String idUser, String idPro, String date, String rank) throws Exception {
        boolean check = false;
        String idDetail = idUser + idPro;
        try {
            String sql = "INSERT dbo.dateDetail VALUES (?,?,?)";
            conn = MyConnection.GetMyconnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, date);
            preStm.setString(2, idDetail);
            preStm.setString(3, rank);
            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    public boolean updateRank(String idUser, String idPro, String date, String rank) throws Exception {
        boolean check = false;
        try {
            String sql = "UPDATE dbo.userInPro SET rank = ?, date = ? WHERE IDUser = ? AND IDPro = ? ";
            if (insertDateDetail(idUser, idPro, date, rank)) {
                conn = MyConnection.GetMyconnection();
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, rank);
                preStm.setString(2, date);
                preStm.setString(3, idUser);
                preStm.setString(4, idPro);
                check = preStm.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return check;
    }
    public  boolean deleteUserInPro(String idUser,String idPro) throws Exception{
        boolean check = false;
        try {
            String sql = "DELETE FROM dbo.userInPro WHERE IDUser = ? AND IDPro = ? ";
            if (deleteDetail(idUser, idPro)) {
                conn = MyConnection.GetMyconnection();
                preStm = conn.prepareStatement(sql);
                preStm.setString(1, idUser);
                preStm.setString(2, idPro);
                check = preStm.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return check;
    }
    public boolean deleteDetail(String idUser,String idPro) throws Exception{
        boolean check = false;
        String idDetail = idUser + idPro;
        try {
            String sql = "DELETE FROM dbo.dateDetail WHERE IDDateDetail = ? ";
            conn = MyConnection.GetMyconnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, idDetail);
            check = preStm.executeUpdate() > 0;
        }finally{
            closeConnection();
        }
        return check;
    }
    
    public List<PromotionDTO> getListProByUser(String userID) throws Exception{
        String idPro,namePro,desPro,rank,date;
        PromotionDTO dto;
        List<PromotionDTO> result = null;
        try {
            String sql = "SELECT Promotion.IDPro,namePro,desPro,rank,date FROM dbo.userInPro,dbo.Promotion \n" +
                        "WHERE dbo.userInPro.IDPro = dbo.Promotion.IDPro AND IDUser = ? ";
            conn = MyConnection.GetMyconnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, userID);
            rs = preStm.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {                
                idPro = rs.getString("IDPro");
                namePro = rs.getString("namePro");
                desPro = rs.getString("desPro");
                rank = rs.getString("rank");
                date = rs.getString("date");
                dto = new PromotionDTO(idPro, namePro, desPro, rank, date);
                result.add(dto);
            }
        }finally{
            closeConnection();
        }
        return result;
    }
    public List<PromotionDTO> getListHistory(String idUser) throws Exception{
        String idPro,namePro,date,rank;
        List<PromotionDTO> result = null;
        PromotionDTO dto;
        try {
            String sql = "SELECT Promotion.IDPro,namePro,dbo.dateDetail.date,dbo.dateDetail.oldRank FROM dbo.userInPro,dbo.dateDetail,dbo.Promotion\n" +
                "WHERE dbo.userInPro.IDDateDetail = dbo.dateDetail.IDDateDetail AND dbo.userInPro.IDPro = dbo.Promotion.IDPro AND IDUser = ?";
            conn = MyConnection.GetMyconnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, idUser);
            rs = preStm.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {   
                idPro = rs.getString("IDPro");
                namePro = rs.getString("namePro");
                date = rs.getString("date");
                rank = rs.getString("oldRank");
                dto = new PromotionDTO(idPro, namePro, rank, date);
                result.add(dto);
            }
        }finally{
            closeConnection();
        }
        return result;
    }
}
