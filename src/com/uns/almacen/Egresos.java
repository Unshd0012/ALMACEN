package com.uns.almacen;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.uns.access.ConexionAccess;
import com.uns.mysql.ConexionMysql;

public class Egresos {

	
		public Image buscarImagenEgresosAccess(int indice, String ruta) {
			
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
								imagen = bu.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
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
		
		
		
		public String[] buscarDatosEgresosAccess(String indice, String base) {
			String datos[] = new String[6];
			
			Connection conexion;
			PreparedStatement st;
			ResultSet rs;
			ConexionAccess con = new ConexionAccess();
			con.hacerConexionAccess(base);
			
			try {
				conexion = con.getConexion();
				//importante poner comillas simples cuando la consulta compara string de lo contrario no funcionara
				st = conexion.prepareStatement("SELECT * FROM productos WHERE codigo='"+indice+"'");
				rs = st.executeQuery();
				
				while(rs.next()) {
					datos[0]=rs.getString("codigo");
					datos[1]=rs.getString("descripcion");
					datos[2]=rs.getString("marca");
					datos[3]="1";
					datos[4]= rs.getString("precio");
					datos[5]= rs.getString("piezas");
				
				}
				
				double i = Double.parseDouble(datos[5]);
				if(i<=0) {
					//si el total de piezas esta en ceros o en menos ponemos el espacio de codigo en null para que la parte
					//que llama al metodo al evaluar la posicion 0 la lea en null y no agregue el producto a la tabla 
					//hasta que actualize el stock
					datos[0]=null;
				}
				
				con.close();
				conexion.close();
				st.close();
				rs.close();
				
				System.out.println("datos encontrados panel egresos access");
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
			
			return datos;
			
		}
		
		
		public void updateSalidaAccess(String ruta, String codigo, double cantidad) {
			
			Connection conexion= null;
			PreparedStatement statement;
			ResultSet rs;
			ConexionAccess con = new ConexionAccess();
			Double piezasActuales[] = new Double[1];
			
			//recoge la spiezas registradas en la base de datos
			try {
				conexion = con.devuelveConexionAccess(ruta);
				statement = conexion.prepareStatement("SELECT piezas FROM productos WHERE codigo=?");
				statement.setString(1, codigo);
				
				rs = statement.executeQuery();
				while(rs.next()) {
					piezasActuales[0]= Double.parseDouble(rs.getString("piezas"));
					
					System.out.println("pieza sactuales encontrada: " + piezasActuales[0]);
				}
				
				con.close();
				conexion.close();
				statement.close();
				rs.close();
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			//actualiza las piezas restantes
			try {
				
				conexion = con.devuelveConexionAccess(ruta);
				
				statement = conexion.prepareStatement("UPDATE productos SET piezas=? WHERE codigo = ?");
				statement.setString(1, String.valueOf((piezasActuales[0]-cantidad)));
				System.out.println("piezas actializadas: "+String.valueOf((piezasActuales[0]-cantidad)));
				statement.setString(2, codigo);
				
				int i = statement.executeUpdate();
				if(i>=1) {
					System.out.println("se actualizaron: " + i + "registros de la tabla productos codigo: " + codigo);
				
				}
				
				
				con.close();
				conexion.close();
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			
		}
		
		
		
		public String[] buscarDatosEgresosMysql(String indice,String ip, String db, String port,String usuario, String pass,JPanel panel) {
			String datos[] = new String[6];
			
			Connection conexion;
			PreparedStatement st;
			ResultSet rs;
			ConexionMysql con = new ConexionMysql();
			
			try {
				conexion = con.ConexionMysqlX(ip, db, port, usuario, pass);
				//importante poner comillas simples cuando la consulta compara string de lo contrario no funcionara
				st = conexion.prepareStatement("SELECT * FROM productos WHERE codigo='"+indice+"'");
				rs = st.executeQuery();
				
				while(rs.next()) {
					datos[0]=rs.getString("codigo");
					datos[1]=rs.getString("descripcion");
					datos[2]=rs.getString("marca");
					datos[3]="1";
					datos[4]= rs.getString("precio");
					datos[5]= rs.getString("piezas");
				
				}
				
				double i = Double.parseDouble(datos[5]);
				if(i<=0) {
					//si el total de piezas esta en ceros o en menos ponemos el espacio de codigo en null para que la parte
					//que llama al metodo al evaluar la posicion 0 la lea en null y no agregue el producto a la tabla 
					//hasta que actualize el stock
					datos[0]=null;
				}
				
			
				conexion.close();
				st.close();
				rs.close();
				
				System.out.println("datos encontrados panel egresos access");
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
			
			return datos;
			
		}
		
		
public void updateSalidaMysql(String ip, String db, String port,String usuario, String pass,JPanel panel, String codigo,double cantidad) {
			
			Connection conexion= null;
			PreparedStatement statement;
			ResultSet rs = null;
			ConexionMysql con = new ConexionMysql();
			Double piezasActuales[] = new Double[1];
			
			//recoge la spiezas registradas en la base de datos
			try {
				conexion = con.ConexionMysqlX(ip, db, port, usuario, pass);
				statement = conexion.prepareStatement("SELECT piezas FROM productos WHERE codigo=?");
				statement.setString(1, codigo);
				
				rs = statement.executeQuery();
				while(rs.next()) {
					piezasActuales[0]= Double.parseDouble(rs.getString("piezas"));
					
					System.out.println("pieza sactuales encontrada: " + piezasActuales[0]);
				}
				
				
				conexion.close();
				statement.close();
				rs.close();
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			//actualiza las piezas restantes
			try {
				
				conexion = con.ConexionMysqlX(ip, db, port, usuario, pass);
				
				statement = conexion.prepareStatement("UPDATE productos SET piezas=? WHERE codigo = ?");
				statement.setString(1, String.valueOf((piezasActuales[0]-cantidad)));
				System.out.println("piezas actializadas: "+String.valueOf((piezasActuales[0]-cantidad)));
				statement.setString(2, codigo);
				
				int i = statement.executeUpdate();
				if(i>=1) {
					System.out.println("se actualizaron: " + i + "registros de la tabla productos codigo: " + codigo);
				
				}
				
				
				
				conexion.close();
				statement.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			
		}
}
