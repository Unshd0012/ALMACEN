package com.uns.access;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;

import com.uns.GUI.Almacen;


public class ConexionAccess {
	
	private Connection conexion;
	private Statement statement;
	
	
	public ConexionAccess() {
		
	}
	
	
	public int hacerConexionAccess(String ruta) {
		int c = 0;
	try {
		
		String url = "jdbc:ucanaccess://"+ruta;
		conexion= DriverManager.getConnection(url);
		statement = conexion.createStatement();
		
		System.out.println("Conexion Correcta");
		Almacen.estatusConexion.setText("CONEXION EXITOSA");
		Almacen.estatusConexion.setBackground(Color.GREEN);
		c=1;
		
	}catch(SQLException ex){
		
		ex.printStackTrace(System.out);
		Almacen.estatusConexion.setText("ERROR AL CONECTAR");
		Almacen.estatusConexion.setBackground(Color.RED);
	}
	
	return c;
	
		
	}
	
	
	
	public Connection devuelveConexionAccess(String ruta) {
		
	try {
		
		String url = "jdbc:ucanaccess://"+ruta;
		conexion= DriverManager.getConnection(url);
		statement = conexion.createStatement();
		
		System.out.println("Conexion Correcta");
		
	}catch(SQLException ex){
		
		ex.printStackTrace(System.out);
		
	}
	
	return conexion;
	
		
	}
	
	public Image obtenerImagenAccess(int indice,String ruta) {
		Image imagen = null;
		
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs= null;
		try {
			con = devuelveConexionAccess(ruta);
			st = con.prepareStatement("SELECT productos.imagen from productos where id="+indice);
			rs = st.executeQuery();
			
			
			while(rs.next()) {
				Blob blob = rs.getBlob("imagen");
				byte bit [] = blob.getBytes(1,(int) blob.length());
				BufferedImage bu = null;
			
				try {
					bu= ImageIO.read(new ByteArrayInputStream(bit));
					imagen = bu.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
					if(imagen==null) {
						System.out.println("imagen nula");
					}
					imagen.flush();
					
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			
			}
			
		}catch(SQLException e) {
			e.getMessage();
		}finally {
			
			try {
				st.close();
				rs.close();
				con.close();
				this.conexion.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		return imagen;
		
	}
	
	
	
	public void insertarImagenAccess(String ruta, FileInputStream imagen, int i) {
		

		
		Connection con = null;
		PreparedStatement st = null;
		
		con = devuelveConexionAccess(ruta);
		
		try {
			st = conexion.prepareStatement("UPDATE productos SET imagen=? WHERE id=?");
			st.setBinaryStream(1, imagen);
			st.setInt(2, i);
			int j = st.executeUpdate();
			if(j>0) {
				System.out.println("Insercion correcta de imagen en access");
			}
			con.close();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public void buscarStock(String ruta, String datos) {
		
		Connection con = null;
		PreparedStatement st = null;
	    ResultSet rs = null;
	    
	    
	    con = devuelveConexionAccess(ruta);
	    try {
			st= con.prepareStatement("SELECT id, codigo, marca, descripcion, contenido, piezas, unidad, precio FROM productos WHERE id LIKE'%"+datos+"%' OR marca LIKE'%"+datos
					+"%' OR descripcion LIKE'%"+datos+"%' OR contenido LIKE'%"+datos+"%' OR piezas LIKE'%"+datos+"%' OR unidad LIKE'%"+datos+"%' OR precio LIKE'%"+datos+"%'");
			rs = st.executeQuery();
			String dato[] = new String[8];
			
			while(rs.next()) {
				dato[0] = rs.getString("id");
				dato[1] = rs.getString("codigo");
				dato[2] = rs.getString("marca");
				dato[3] = rs.getString("descripcion");
				dato[4] = rs.getString("contenido");
				dato[5] = rs.getString("piezas");
				dato[6] = rs.getString("unidad");
				dato[7] = rs.getString("precio");
				Almacen.modeloStock.addRow(dato);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public Connection getConexion() {
		
		return this.conexion;
	}
	
	public boolean close() {
		boolean cerrada=false;
		
		try {
			this.conexion.close();
			this.statement.close();
			System.out.println("Conexion y Statement Cerrados correctamente...");
		} catch (SQLException e) {
		
			e.printStackTrace(System.out);
		}
		
		
		return cerrada;
		
	}

}
