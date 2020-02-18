/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnd.db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Duc Linh
 */
public class MyConnection {
    public static Connection GetMyconnection() throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost:1433;databaseName=ManagerUser";
        Connection conn = DriverManager.getConnection(url, "sa", "123456");
        return conn;
    }
}
