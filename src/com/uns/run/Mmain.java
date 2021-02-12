package com.uns.run;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.uns.GUI.Almacen;
import com.uns.almacen.Productos;
import com.uns.mysql.ConexionMysql;

public class Mmain {

	public static void main(String[] args) {
	com.uns.GUI.Almacen almacen = new Almacen();
	almacen.setVisible(true);
		/*
	ConexionMysql mysql = new ConexionMysql();
	
	
	
	try {
		Connection con = mysql.ConexionMysqlX("localhost","almacen","3306","root","");
		PreparedStatement st = con.prepareStatement("INSERT INTO productos( codigo, marca, descripcion, contenido,piezas, unidad, precio, imagen) VALUES (?,?,?,?,?,?,?,?)");
		st.setString(1, "0123");
		st.setString(2, "bombones");
		st.setString(3, "caja de bombones");
		st.setString(4, "2.00");
		st.setString(5, "2.0");
		st.setBoolean(6, false);
		st.setDouble(7, 5263.23);
		try {
			st.setBinaryStream(8, new FileInputStream("3.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	int  i =	st.executeUpdate();
	if(i>0) {
		System.out.println("se insertaron correctamente");
	}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}*/
	

		
		/*Productos azucar = new Productos("123456", "Morena", "azucar refinada", 1000.00, 45.00);
		Productos pastaDental = new Productos("236514", "Colgate", "pasta dental 200g", 1, 30.00);
		Productos frijolesEnlatados = new Productos("561561", "la costeña", "frijoles enlatados", 1, 450, 50.00);
		
		List<Productos> producto=new ArrayList();
		producto.add(azucar);
		producto.add(pastaDental);
		producto.add(frijolesEnlatados);
		
		for(Productos elementos: producto) {
			System.out.println(elementos);
		}
		Productos pasta = producto.get(1);
		System.out.println("la pasta recuperada de la lista de objectos: \n"+pasta);
*/

		
	
		
	}

}
