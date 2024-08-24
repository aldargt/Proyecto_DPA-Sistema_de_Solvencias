/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hp
 */
public class Conexion {
    private final String base = "registros_solvencias";
    private final String user = "root";
    private final String password = "1234";
    private final String url = "jdbc:mysql://localhost:3306/" + base + "?autoReconnect=true&useSSL=false";
    private Connection con = null;

    public Connection getConexion()
    {

        try{

            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(this.url, this.user, this.password);

        }
        catch(SQLException e){

            System.err.println(e);

        }
        catch (ClassNotFoundException ex) {

            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);

        }
      return con;
    }
}
