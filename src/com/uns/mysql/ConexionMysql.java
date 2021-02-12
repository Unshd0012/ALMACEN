package com.uns.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Statement;
import com.uns.GUI.Almacen;

public class ConexionMysql {
	

    // Librería de MySQL
    private String driver = "com.mysql.jdbc.Driver";

    // Nombre de la base de datos
    private String database;

    // Host
    private String hostname;

    // Puerto
    private String port;

    // Ruta de nuestra base de datos (desactivamos el uso de SSL con "?useSSL=false")
    private String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=false";

    // Nombre de usuario
    private String username;

    // Clave de usuario
    private String password;
    
    private int v = 0;
    

    public Connection ConexionMysqlX(String ip, String db, String port,String usuario, String pass) {
    	
        Connection conn = null;
        String urlx = "jdbc:mysql://" + ip + ":" + port + "/" + db + "?useSSL=false";
        try {
            Class.forName(driver);
        
            conn = DriverManager.getConnection(urlx, usuario, pass);
            
        	System.out.println("conexion correcta clase conexionmysql");
        } catch (ClassNotFoundException | SQLException e) {

   System.out.println("conexion incorrecta");
            v =1;

        }

        return conn;
    }

    
        public Connection conectarsql() {
            
            
        Connection conn = null;

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("conexion correcta");
            
            v = 1;

        } catch (ClassNotFoundException | SQLException e) {

            System.out.println("conexion incorrecta");
            v=0;

        }

        return conn;
    }
}

	


