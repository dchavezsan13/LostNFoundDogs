import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author daniChavez
 */
public class MyConection {

    final private static String URL = "jdbc:mysql://localhost/perros";
    final private static String USER = "root";
    final private static String PASSWORD = "";

    
    public static Connection getConectionToDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
             Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             return conn;
        } catch (SQLException ex) {

            System.out.println("Theres an exception with SQL " + ex.getMessage());
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MyConection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
    
    
}
