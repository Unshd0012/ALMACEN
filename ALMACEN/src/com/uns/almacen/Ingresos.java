package com.uns.almacen;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.uns.GUI.Almacen;
import com.uns.access.ConexionAccess;
import com.uns.mysql.ConexionMysql;

public class Ingresos {
	
	public Image buscarImagenIngresosAccess(int indice, String ruta) {
		
Image imagen = null;
		
		Connection conexion = null;
		PreparedStatement st = null;
		ResultSet rs= null;
		ConexionAccess con = new ConexionAccess();
		con.hacerConexionAccess(ruta);
		try {
			conexion = con.getConexion(); 
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
			e.getMessage();
		}finally {
			
			try {
				st.close();
				rs.close();
				conexion.close();
				con.close();
			
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		return imagen;
	}
	
	public String[] buscarDatosIngresosAccess(int indice, String base) {
		String datos[] = new String[8];
		
		Connection conexion;
		PreparedStatement st;
		ResultSet rs;
		ConexionAccess con = new ConexionAccess();
		con.hacerConexionAccess(base);
		
		try {
			conexion = con.getConexion();
			st = conexion.prepareStatement("SELECT * FROM productos WHERE id="+indice);
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
			}
			System.out.println("datos encontrados panel ingresos access");
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return datos;
		
	}
	
	
	public void insertarProductoAccess(String ruta,String codigo,String marca,String desc, int contenido,int piezas, boolean unidad, double precio, FileInputStream file ) {
		
		ConexionAccess con = null;
		Connection conexion = null;
		PreparedStatement statement = null;
		
		try {
			con = new ConexionAccess();
			con.hacerConexionAccess(ruta);
			conexion = con.getConexion();
			statement = conexion.prepareStatement("INSERT INTO productos( codigo, marca, descripcion, contenido,piezas, unidad, precio, imagen) VALUES (?,?,?,?,?,?,?,?)");
			statement.setString(1, codigo);
			statement.setString(2, marca);
			statement.setString(3, desc);
			statement.setInt(4, contenido);
			statement.setInt(5, piezas);
			statement.setBoolean(6, unidad);
			statement.setDouble(7, precio);
			statement.setBinaryStream(8, file);
			
			int i = statement.executeUpdate();
			if(i>0) {
				System.out.print("se insertaron correctamente los datos");
			}
			
			
			con.close();
			conexion.close();
			statement.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void actualizarProductoAccess(String ruta,String codigo,String marca,String desc, int contenido,int piezas, boolean unidad, double precio, int id) {
		ConexionAccess con = new ConexionAccess();
		Connection conexion;
		PreparedStatement statement;
	
		try {
			conexion = con.devuelveConexionAccess(ruta);
			statement= conexion.prepareStatement("UPDATE productos SET codigo=?, marca=?, descripcion=?, contenido=?,piezas=?, unidad=?, precio=? WHERE id = ?");
			statement.setString(1, codigo);
			statement.setString(2, marca);
			statement.setString(3, desc);
			statement.setInt(4, contenido);
			statement.setInt(5, piezas);
			statement.setBoolean(6, unidad);
			statement.setDouble(7, precio);
			statement.setInt(8, id);
			
			int i = statement.executeUpdate();
			if(i>0) {
				System.out.println("datos agregados correctamente");
				
			}
			con = null;
			conexion.close();
			statement.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	
	}
	
	public void actualizarImagenAccess(String ruta, FileInputStream imagen, int i) {
		
		Connection con = null;
		PreparedStatement st = null;
		ConexionAccess conn = new ConexionAccess();
		con = conn.devuelveConexionAccess(ruta);
		
		try {
			st = con.prepareStatement("UPDATE productos SET imagen=? WHERE id=?");
			st.setBinaryStream(1, imagen);
			st.setInt(2, i);
			int j = st.executeUpdate();
			if(j>0) {
				System.out.println("Actualizacion correcta de imagen en access");
			}
			con.close();
			st.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void eliminarIngresosAccess(String ruta, int i) {
		
	    ConexionAccess con = new ConexionAccess();
	    Connection conexion;
	    PreparedStatement  statement; 
		
		try {
			conexion = con.devuelveConexionAccess(ruta);
			statement= conexion.prepareStatement("Delete FROM productos WHERE id= ?");
			statement.setInt(1, i);
			int ii = statement.executeUpdate();
			if(ii>0) {
				System.out.println("datos eliminados correctamente: " + ii);
			}
			
			conexion.close();
			statement.close();
			con.close();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
		//****************************************************acciones de mysql*****************************************

		public void guardarDatosMysql(String datos[],FileInputStream file,String ip, String db, String port,String usuario, String pass,JPanel panel) {
			
			try {
				ConexionMysql con = new ConexionMysql();
				Connection conexion = con.ConexionMysqlX("localhost","almacen","3306","root","");
				PreparedStatement st = conexion.prepareStatement("INSERT INTO productos( codigo, marca, descripcion, contenido,piezas, unidad, precio, imagen) VALUES (?,?,?,?,?,?,?,?)");
				st.setString(1, datos[0]);
				st.setString(2, datos[1]);
				st.setString(3, datos[2]);
				st.setString(4, datos[3]);
				st.setString(5, datos[4]);
				st.setBoolean(6,Boolean.valueOf( datos[5]));
				st.setDouble(7, Double.parseDouble(datos[6]));
				st.setBinaryStream(8, file);
				
				
			int  i =	st.executeUpdate();
			if(i>0) {
				System.out.println("se insertaron correctamente");
				JOptionPane.showMessageDialog(panel, "se insertaron correctamente los datos");
			}
				conexion.close();
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void editarDatosMysql(String datos[],String ip, String db, String port,String usuario, String pass,JPanel panel) {
			
			
			try {
				ConexionMysql con = new ConexionMysql();
				Connection conexion = con.ConexionMysqlX("localhost","almacen","3306","root","");
				PreparedStatement st = conexion.prepareStatement("UPDATE productos SET codigo=?, marca=?, descripcion=?, contenido=?,piezas=?, unidad=?, precio=? WHERE id = ?");
				boolean s = true;
				st.setString(1, datos[0]);
				st.setString(2, datos[1]);
				st.setString(3, datos[2]);
				st.setString(4, datos[3]);
				st.setString(5, datos[4]);
				if(datos[5].equals("FALSE")) {
					s=false;
				}
				st.setBoolean(6, s);
				st.setString(7, datos[6]);
				st.setString(8, datos[7]);
				
				int i = st.executeUpdate();
				if(i>0) {
					JOptionPane.showMessageDialog(panel, "se actualizaron: " + i +" registros");
				}
				
				conexion.close();
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		

		public String[] buscarIngresosMysql(String ip, String db, String port,String usuario, String pass,String indice,JPanel panel) {
			Connection conexion;
			PreparedStatement st;
			ResultSet rs;
			ConexionMysql con = new ConexionMysql();
			String datos[] = new String[8]; 
			try {
			
				conexion = con.ConexionMysqlX(ip, db, port, usuario, pass);
				st = conexion.prepareStatement("SELECT * FROM productos WHERE id="+indice);
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
					
				}
				
				conexion.close();
				st.close();
				rs.close();
				
			}catch(SQLException ee) {
				ee.printStackTrace();
				JOptionPane.showMessageDialog(panel, "Error al cargar datos: " + ee.getMessage());
			}
			
			return datos;
			
		}
	

		public void actualizarImagenMysql(FileInputStream file, String indice,String ip, String db, String port,String usuario, String pass,JPanel panel) {
			
			Connection conexion;
			PreparedStatement st;
			ConexionMysql con = new ConexionMysql();
			
			try {
				conexion = con.ConexionMysqlX(ip, db, port, usuario, pass);
				st = conexion.prepareStatement("UPDATE productos SET imagen=? WHERE id=?");
				st.setBinaryStream(1, file);
				st.setString(2, indice);
				
				int  i = st.executeUpdate();
				if(i>0) {
					JOptionPane.showMessageDialog(panel, "datos actualizados correctamente");
				}
				
			}catch(SQLException E) {
				JOptionPane.showMessageDialog(panel, E.getMessage());
				E.printStackTrace();
			}
			
			
		}
		
		


}




