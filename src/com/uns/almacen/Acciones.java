package com.uns.almacen;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.Document;

import  com.uns.GUI.Almacen;
import com.uns.access.ConexionAccess;
import com.uns.mysql.ConexionMysql;

import net.ucanaccess.jdbc.UcanaccessSQLException;

public class Acciones {
	
	public int comprobarCamposConexionMySQl(String ip, String puerto,String base, String tabla, String usuario, String password) {
		
		int i =0;
		
		if(ip.isEmpty()&&ip.isBlank()) {
			i=i+1;
		}
		if(puerto.isEmpty()&&puerto.isBlank()) {
			i=i+1;
		}
		if(base.isEmpty()&&base.isBlank()) {
			i=i+1;
		}
		if(tabla.isEmpty()&&tabla.isBlank()) {
			//i=i+1;
		}
		if(usuario.isEmpty()&&usuario.isBlank()) {
			i=i+1;
		}
		if(password.isEmpty()&&password.isBlank()) {
			
		}
		
		return i;
		
		
	}
	
	//select para la pestaña de conexion de test comprueba si esta bien realizada la conexion con una consulta de tipo select
	public String[] selectTest(String sql, String base,JPanel panel) {
		String datos[]=new String[7];
			Connection conexion=null;
			PreparedStatement statement=null; 
			ResultSet result = null;
			ConexionAccess con = new ConexionAccess();
			
			try{
				con.hacerConexionAccess(base);
				conexion = con.getConexion();
				statement = conexion.prepareStatement(sql);
				
				result=statement.executeQuery();
				String d = "";
				
				while(result.next()) {
					d="";
					datos[0]=result.getString("id");
					datos[1]=result.getString("codigo");
					datos[2]=result.getString("marca");
					datos[3]=result.getString("descripcion");
					datos[4]=result.getString("contenido");
					datos[5]=result.getString("piezas");
					datos[6]=result.getString("precio");
					for(int i =0;i<6;i++) {
						
					d+=datos[i]+"      ";
					
					}
					Almacen.executeQuery.append(d+"\n");
				
					
				}
				
			}catch(SQLException e) {
				e.printStackTrace();
			
			}finally {
				
				try {
					
					con.close();
					conexion.close();
					statement.close();
					result.close();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
				
			}
		
		
		return datos;
	}
	
	
	public String totalDatos(String sql, String base,JPanel panel) {
		String total=null;
		Connection conexion=null;
		PreparedStatement statement=null; 
		ResultSet result = null;
		ConexionAccess con = new ConexionAccess();
		
		try{
			con.hacerConexionAccess(base);
			conexion = con.getConexion();
			statement = conexion.prepareStatement(sql);
			result = statement.executeQuery();
			total = result.toString();
		
		}catch(SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(panel, "Error al realizar consulta: Comando no valido:" + sql);
		}finally {
			
			try {
				
				con.close();
				conexion.close();
				statement.close();
				result.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			
		}
		
		return total;
		
	}
	
	
	public int comprobarCamposIngresos(String codigo, String marca, String des, String contenido, String piezas, String precio, String imagen) {
		int campos = 0;
		
		if(codigo.isBlank()||codigo.isEmpty()) {
			campos=campos+1;
		}
		if(marca.isBlank()||marca.isEmpty()) {
			campos=campos+1;
		}
		if(des.isBlank()||des.isEmpty()) {
			campos=campos+1;
		}
		if(contenido.isBlank()||contenido.isEmpty()) {
			campos=campos+1;
		}
		if(piezas.isBlank()||piezas.isEmpty()) {
			campos=campos+1;
		}
		if(precio.isBlank()||precio.isEmpty()) {
			campos=campos+1;
		}
		if(imagen.equals("Click para buscar Imagen")||imagen.isEmpty()) {
			campos=campos+1;
		}
		
		
		
		return campos;
	}
	
	
	public void cargarMysqlDefault(String ip, String db, String port,String usuario, String pass,JPanel panel) {
		 Connection conn = null;
	        String urlx = "jdbc:mysql://" + ip + ":" + port + "/" + db + "?useSSL=false";
	        try {
	            Class.forName("com.mysql.jdbc.Driver");
	        
	            conn = DriverManager.getConnection(urlx, usuario, pass);
	            
	        	JOptionPane.showMessageDialog(panel, "Conexion Correcta: "+ ip+":"+db+":"+port);
	        } catch (ClassNotFoundException | SQLException e) {

	   System.out.println("conexion incorrecta");
	      e.printStackTrace();

	        }
		
	}
	
	public void selectAllStockMysql(String ip, String db, String port,String usuario, String pass,JPanel panel) {
		Connection conexion;
		PreparedStatement st;
		ResultSet rs;
		ConexionMysql con = new ConexionMysql();
		String datos[] = new String[8]; 
		try {
		
			conexion = con.ConexionMysqlX(ip, db, port, usuario, pass);
			st = conexion.prepareStatement("SELECT * FROM productos");
			rs = st.executeQuery();
			
			while(rs.next()) {
				datos[0]=rs.getString("id");
				datos[1]=rs.getString("codigo");
				datos[2]=rs.getString("marca");
				datos[3]=rs.getString("descripcion");
				datos[4]=rs.getString("contenido");
				datos[5]=rs.getString("piezas");
				datos[6]=rs.getString("unidad");
				datos[7]=rs.getString("precio");
				Almacen.modeloStock.addRow(datos);
			}
			
		}catch(SQLException ee) {
			ee.printStackTrace();
			JOptionPane.showMessageDialog(panel, "Error al cargar datos: " + ee.getMessage());
		}
		
	}
	
	public Image obtenerImagenStockMysql(String ip, String db, String port,String usuario, String pass,int indice,JPanel panel) {
		Image imagen = null;
		Connection conexion = null;
		PreparedStatement st = null;
		ResultSet rs= null;
		ConexionMysql con = new ConexionMysql();
		
		try {
			conexion = con.ConexionMysqlX(ip, db, port, usuario, pass);
			st = conexion.prepareStatement("SELECT productos.imagen from productos where id="+indice);
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
			e.printStackTrace();
			JOptionPane.showMessageDialog(panel, "Error al cargar datos: " + e.getMessage());
		}
		
		
		
		return imagen;
		
	}
	
	
	public void selectFiltrarStockMysql(String ip, String db, String port,String usuario, String pass,String busqueda,JPanel panel) {
		Connection conexion;
		PreparedStatement st;
		ResultSet rs;
		ConexionMysql con = new ConexionMysql();
		String datos[] = new String[8]; 
		try {
		
			conexion = con.ConexionMysqlX(ip, db, port, usuario, pass);
			st = conexion.prepareStatement("SELECT id, codigo, marca, descripcion, contenido, piezas, unidad, precio FROM productos WHERE id LIKE'%"+busqueda+"%' OR marca LIKE'%"+datos
					+"%' OR descripcion LIKE'%"+busqueda+"%' OR contenido LIKE'%"+busqueda+"%' OR piezas LIKE'%"+busqueda+"%' OR unidad LIKE'%"+busqueda+"%' OR precio LIKE'%"+busqueda+"%'");
			rs = st.executeQuery();
			
			while(rs.next()) {
				datos[0]=rs.getString("id");
				datos[1]=rs.getString("codigo");
				datos[2]=rs.getString("marca");
				datos[3]=rs.getString("descripcion");
				datos[4]=rs.getString("contenido");
				datos[5]=rs.getString("piezas");
				datos[6]=rs.getString("unidad");
				datos[7]=rs.getString("precio");
				Almacen.modeloStock.addRow(datos);
			}
			
		}catch(SQLException ee) {
			ee.printStackTrace();
			JOptionPane.showMessageDialog(panel, "Error al cargar datos: " + ee.getMessage());
		}
		
	}
	
	

}
