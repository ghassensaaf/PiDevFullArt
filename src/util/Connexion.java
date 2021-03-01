package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    Connection conn = null;
    public static Connection conDB()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/keeptoo_systems", "root", "");
            return con;
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Connexion : "+ex.getMessage());
            return null;
        }
    }
}
