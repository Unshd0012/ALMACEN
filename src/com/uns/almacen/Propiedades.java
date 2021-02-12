package com.uns.almacen;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import com.uns.GUI.Almacen;

public class Propiedades {
	
	public void crearPropertiesAccess(String rutaAcccess,String comentario, String rutaProperties) {
		
		Properties p = new Properties();
		p.setProperty("archivo", rutaAcccess);
		try {
			FileOutputStream f = new FileOutputStream(rutaProperties);
			p.store(f, comentario);
			System.out.println("Propiedades guardadas con exito");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	//metodo para leer la pripiedades de access
	public String leerPropertiesAccess(String rutaDelArchivo) {
		String ruta="click para buscar base de datos Access";
		try {
			Properties p = new Properties();
			FileInputStream i = new FileInputStream(rutaDelArchivo);
			p.load(i);
			ruta=p.getProperty("archivo");
			i.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ruta;
	}

	
	
	public void crearPropertiesMysql(String ip, String puerto,String base,String tabla, String usuario, String password,String comentario, String rutaProperties) {
		
		Properties p = new Properties();
		p.setProperty("ip", ip);
		p.setProperty("puerto", puerto);
		p.setProperty("base", base);
		p.setProperty("tabla", tabla);
		p.setProperty("usuario", usuario);
		p.setProperty("password", password);
		
		
		try {
			FileOutputStream f = new FileOutputStream(rutaProperties);
			p.store(f, comentario);
			System.out.println("Propiedades guardadas con exito");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public String[] leerPropertiesMysql(String rutaDelArchivo) {
		String ruta[]=new String[6];
		try {
			Properties p = new Properties();
			FileInputStream i = new FileInputStream(rutaDelArchivo);
			p.load(i);
			ruta[0]=p.getProperty("ip");
			ruta[1]=p.getProperty("puerto");
			ruta[2]=p.getProperty("base");
			ruta[3]=p.getProperty("tabla");
			ruta[4]=p.getProperty("usuario");
			ruta[5]=p.getProperty("password");
			
			i.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ruta;
	}
	
	
	public String[] leerPredeterminada(String rutaDefault) {
		String datos[] = null;
		int item = -1;
		String file=null;
		try {
			Properties p = new Properties();
			FileInputStream ii = new FileInputStream(rutaDefault);
			p.load(ii);
			
			item=Integer.valueOf(p.getProperty("tipoconexion"));
			file = p.getProperty("archivo");
			ii.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		if(item==0) {
		file = leerPropertiesAccess(file);
		datos = new String[2];
		datos[0]=file;
		datos[1]=String.valueOf(item);
		
		
		}
		if(item==1) {
		datos = leerPropertiesMysql(file);	
	
		}
		if(item==-1) {
			datos = new String[1];
			datos[0]="sin conexion";
		
			}
		
		
		return datos;
	}
	public void establecerPredeterminada(int item,String file) {
		Properties p = new Properties();
		String itemm = String.valueOf(item);
		p.setProperty("tipoconexion", itemm);
		p.setProperty("archivo", file);
		
		
		
		try {
			FileOutputStream f = new FileOutputStream("config\\default.properties");
			p.store(f, "properties por defecto");
			System.out.println("Propiedades guardadas con exito");
		}catch(Exception e) {
			e.printStackTrace();
		
		
	}

}
	}
