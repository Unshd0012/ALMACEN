package com.uns.almacen;

import java.awt.Image;
import java.io.FileInputStream;

public class Productos {
	
	private String idInterno;
	private  String codigo;
	private String marca;
	private String descripcion;
	private double contenido; 
	private int piezas;
	private double precio;
	private boolean unidad;
	private Image imagen;
	private FileInputStream input;
	
	public Productos(String codigo, String marca, String desc, int piezas, double precio, Image imagen, FileInputStream input) {
		this.codigo=codigo;
		this.marca=marca;
		this.descripcion=desc;
		this.piezas=piezas;
		this.precio=precio;
		this.unidad=true;
		this.imagen=imagen;
		this.input=input;
	}
	public Productos(String id,String codigo, String marca, String desc, int piezas, double precio, Image imagen) {
		this.idInterno=id;
		this.codigo=codigo;
		this.marca=marca;
		this.descripcion=desc;
		this.piezas=piezas;
		this.precio=precio;
		this.unidad=true;
		this.imagen=imagen;
		this.input=input;
	}
    public Productos(String codigo, String marca, String desc, double contenido, double precio, Image imagen, FileInputStream input) {
    	
    	this.codigo=codigo;
		this.marca=marca;
		this.descripcion=desc;
		this.contenido=contenido;
		this.precio=precio;
		this.unidad=false;
		this.imagen=imagen;
		this.input=input;
		
	}
    public Productos(String id,String codigo, String marca, String desc, double contenido, double precio, Image imagen) {
    	this.idInterno=id;
    	this.codigo=codigo;
		this.marca=marca;
		this.descripcion=desc;
		this.contenido=contenido;
		this.precio=precio;
		this.unidad=false;
		this.imagen=imagen;
	
		
	}
    public Productos(String codigo, String marca, String desc,int piezas, double contenido, double precio, Image imagen, FileInputStream input) {
    	this.codigo=codigo;
		this.marca=marca;
		this.descripcion=desc;
		this.piezas=piezas;
		this.contenido=contenido;
		this.precio=precio;
		this.unidad=false;
		this.imagen=imagen;
		this.input=input;
		
	}
    public Productos(String id,String codigo, String marca, String desc,int piezas, double contenido, double precio, Image imagen) {
    	this.idInterno=id;
    	this.codigo=codigo;
		this.marca=marca;
		this.descripcion=desc;
		this.piezas=piezas;
		this.contenido=contenido;
		this.precio=precio;
		this.unidad=false;
		this.imagen=imagen;
		
		
	}

	public String getIdInterno() {
		return idInterno;
	}

	

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getContenido() {
		return contenido;
	}

	public void setContenido(double contenido) {
		this.contenido = contenido;
	}

	public int getPiezas() {
		return piezas;
	}

	public void setPiezas(int piezas) {
		this.piezas = piezas;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public boolean isUnidad() {
		return unidad;
	}

	public void setUnidad(boolean unidad) {
		this.unidad = unidad;
	}
	public Image getImagen() {
		return imagen;
	}
	public void setImagen(Image imagen) {
		this.imagen=imagen;
	}
	public FileInputStream getInput() {
		return input;
	}
	public void setInput(FileInputStream input) {
		this.input=input;
	}

	@Override
	public String toString() {
		return "Productos [idInterno=" + idInterno + ", codigo=" + codigo + ", marca=" + marca + ", descripcion="
				+ descripcion + ", contenido=" + contenido + ", piezas=" + piezas + ", precio=$" + precio + ", unidad="
				+ unidad +", Imagen= "+imagen+ "]";
	}
    
    
	
	

}
