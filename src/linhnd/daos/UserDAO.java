/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnd.daos;

import java.util.List;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.util.ArrayList;
import linhnd.db.MyConnection;
import linhnd.dtos.UserDTO;
import linhnd.hash.SHA_256;

/**
 *
 * @author Duc Linh
 */
public class UserDAO implements Serializable{
    Connection conn;
    PreparedStatement preStm;
    ResultSet rs;
    
    public void closeConection() throws Exception{
        if (rs != null) {
            rs.close();
        }
        if (preStm != null) {
            preStm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
    
    public String checkLogin(String userName,String passWord) throws Exception{
        String role = "failed";
        SHA_256 hash = new SHA_256();
        String passHash = hash.hashPassword(passWord);
        try {
            String sql = "SELECT role FROM dbo.Users WHERE IDUser = ? AND passWord = ? AND status = ? ";
            conn = MyConnection.GetMyconnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, userName);
            preStm.setString(2, passHash);
            preStm.setString(3, "active");
            rs = preStm.executeQuery();
            if (rs.next()) {
                role = rs.getString("role");
            }
        }finally{
            closeConection();
        }
        return role;
    }
    public List<UserDTO> getAllUser()throws Exception{
        String idUser,userName,email,phone,photoName;
        UserDTO dto;
        List<UserDTO> result = null;
        try {
            String sql = "SELECT IDUser,userName,email,phone,photoName FROM dbo.Users WHERE Users.status = 'active' ";
            conn = MyConnection.GetMyconnection();
            preStm = conn.prepareStatement(sql);
            rs = preStm.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {                
                idUser = rs.getString("IDUser");
                userName = rs.getString("userName");
                email = rs.getString("email");
                phone = rs.getString("phone");
                photoName = rs.getString("photoName");
                dto = new UserDTO(idUser, userName, email, phone, photoName);
                result.add(dto);
            }          
        }finally{
            closeConection();
        }
        return  result;
    }
    public List<UserDTO> getListAdmin() throws Exception{
        String idUser,userName,email,phone,photoName;
        UserDTO dto = null;
        List<UserDTO> result = null;
        try {
            String sql = "SELECT IDUser,userName,email,phone,photoName FROM dbo.Users WHERE Users.role = 'admin' AND Users.status = 'active'";
            conn = MyConnection.GetMyconnection();
            preStm = conn.prepareStatement(sql);
            rs = preStm.executeQuery();
            result = new ArrayList<>();
            while(rs.next()){
                idUser = rs.getString("IDUser");
                userName = rs.getString("userName");
                email = rs.getString("email");
                phone = rs.getString("phone");
                photoName = rs.getString("photoName");
                dto = new UserDTO(idUser, userName, email, phone, photoName);
                result.add(dto);
            }
        }finally{
            closeConection();
        }
        return result;
    }
    public List<UserDTO> getListUser() throws Exception{
        String idUser,userName,email,phone,photoName;
        UserDTO dto = null;
        List<UserDTO> result = null;
        try {
            String sql = "SELECT IDUser,userName,email,phone,photoName FROM dbo.Users WHERE Users.role = 'user' AND Users.status = 'active'";
            conn = MyConnection.GetMyconnection();
            preStm = conn.prepareStatement(sql);
            rs = preStm.executeQuery();
            result = new ArrayList<>();
            while(rs.next()){
                idUser = rs.getString("IDUser");
                userName = rs.getString("userName");
                email = rs.getString("email");
                phone = rs.getString("phone");
                photoName = rs.getString("photoName");
                dto = new UserDTO(idUser, userName, email, phone, photoName);
                result.add(dto);
            }
        }finally{
            closeConection();
        }
        return result;
    }
    public List<UserDTO> searhByName(String textSeach) throws Exception{
        String idUser,userName,email,phone,photoName;
        List<UserDTO> result = null;
        UserDTO dto =  null;
        try {
            String sql = "SELECT IDUser,userName,email,phone,photoName FROM dbo.Users WHERE Users.userName LIKE ? AND Users.status = 'active' ";
            conn = MyConnection.GetMyconnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, "%"+textSeach+"%");
            rs = preStm.executeQuery();
            result = new ArrayList<>();
            while(rs.next()){
                idUser = rs.getString("IDUser");
                userName = rs.getString("userName");
                email = rs.getString("email");
                phone = rs.getString("phone");
                photoName = rs.getString("photoName");
                dto = new UserDTO(idUser, userName, email, phone, photoName);
                result.add(dto);
            }
        }finally{
            closeConection();
        }
        return result;
    }
    public UserDTO getOneUser(String userID) throws Exception{
        String idUser,userName,email,phone,photoName;
        UserDTO dto = null;
        try {
            String sql = "SELECT IDUser,userName,email,phone,role,photoName FROM dbo.Users WHERE dbo.Users.IDUser = ? ";
            conn = MyConnection.GetMyconnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, userID);
            rs = preStm.executeQuery();
            if (rs.next()) {
                idUser = rs.getString("IDUser");
                userName = rs.getString("userName");
                email = rs.getString("email");
                phone = rs.getString("phone");              
                photoName = rs.getString("photoName");
                dto = new UserDTO(idUser, userName, email, phone, photoName);
            }
        } finally{
            closeConection();
        }
        return dto;
    }
    public String getRoleByuserID(String userID) throws Exception{
        String role = null;
        try {
            String sql = "SELECT role FROM dbo.Users WHERE IDUser = ? ";
            conn = MyConnection.GetMyconnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, userID);
            rs = preStm.executeQuery();
            if (rs.next()) {
                role = rs.getString("role");
            }
        }finally{
            closeConection();
        }
        return role;
    }
    public boolean deleteUser(String userId) throws Exception{
        boolean check = false;
        try {
            String sql = "UPDATE dbo.Users SET status = 'noActive' WHERE IDUser = ? "; 
            conn = MyConnection.GetMyconnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, userId);
            check = preStm.executeUpdate() > 0;
        }finally{
            closeConection();
        }
        return check;
    }
    public boolean updateUser(UserDTO dto, String role) throws Exception{
        boolean check = false;
        try {
            String sql = "UPDATE dbo.Users SET userName = ? , email = ?, phone = ?, photoName = ?, role = ? WHERE IDUser = ?";
            conn = MyConnection.GetMyconnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, dto.getUserName());
            preStm.setString(2, dto.getEmail());
            preStm.setString(3, dto.getPhone());
            preStm.setString(4, dto.getPhotoName());
            preStm.setString(5, role);
            preStm.setString(6, dto.getIdUser());
            check = preStm.executeUpdate() > 0;
        }finally{
            closeConection();
        }
        return check;
    }
    public boolean checkUserID(String userId) throws Exception{
        boolean check = false;
        try {
            String sql = "SELECT IDUser FROM dbo.Users WHERE IDUser = ? ";
            conn = MyConnection.GetMyconnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, userId);
            rs = preStm.executeQuery();
            if (rs.next()) {
                check = true;
            }
        }finally{
            closeConection();
        }
        return check;
    }
    public boolean createUser(UserDTO dto, String password, String role) throws Exception{
        boolean check = false;
        SHA_256 hash = new SHA_256();
        String passHash = hash.hashPassword(password);
        try {
            String sql = "INSERT INTO dbo.Users VALUES (?,?,?,?,?,?,?,?) ";
            conn = MyConnection.GetMyconnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, dto.getIdUser());
            preStm.setString(2, dto.getUserName());
            preStm.setString(3, passHash);
            preStm.setString(4, dto.getEmail());
            preStm.setString(5, dto.getPhone());
            preStm.setString(6, role);
            preStm.setString(7, "active");
            preStm.setString(8, dto.getPhotoName());
            check = preStm.executeUpdate() > 0;
        }finally{
            closeConection();
        }
        return check;
    }
    public boolean checkoldPass(String userId,String Pass) throws Exception{
        boolean check = false;
        try {
            String sql = "SELECT dbo.Users.IDUser FROM dbo.Users WHERE IDUser = ? AND password = ?;";
            conn = MyConnection.GetMyconnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, userId);
            preStm.setString(2, Pass);
            rs = preStm.executeQuery();
            if (rs.next()) {
                check = true;
            }
        } finally {
            closeConection();
        }
        return check;
    }

    public boolean updatePass(String userID, String pass) throws Exception {
        boolean check = false;
        try {
            String sql = "UPDATE dbo.Users SET password = ? WHERE IDUser = ? ";
            conn = MyConnection.GetMyconnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, pass);
            preStm.setString(2, userID);
            check = preStm.executeUpdate() > 0;
        } finally {
            closeConection();
        }
        return check;
    }
}
