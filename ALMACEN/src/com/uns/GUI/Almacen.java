package com.uns.GUI;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.Timer;
import com.uns.almacen.*;
import com.uns.mysql.ConexionMysql;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import com.uns.access.ConexionAccess;
import net.miginfocom.swing.MigLayout;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JProgressBar;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JTextArea;

public class Almacen extends JFrame {

	
	
	public static JPanel panelPrincipal;
	private JTabbedPane tabbedPrincipal; 
	private JPanel panelIngresos;
	private JPanel panelEgresos;
	private JPanel panelStock;
	private JPanel panelConexion;
	private JLabel logo;
	private JTable tablaStock;
	private JLabel lbl;
	private JButton btnNewButton;
	private JPanel panelStockSur;
	private JButton  buscar;
	private JTextField inBuscar;
	public static DefaultTableModel modeloStock;
	public static DefaultTableModel tablaEgresosModelo;
	private JLabel lblCodigo;
	private JLabel lblMarca;
	private JLabel lblDesc;
	private JLabel lblContenido;
	private JLabel lblPiezas;
	private JLabel lblPrecio;
	private JLabel lblGranel;
	private JTextField inCodigo;
	private JTextField inDesc;
	private JTextField inContenido;
	private JTextField inPrecio;
	private JCheckBox checkGranel;
	private JLabel lblImagen;
	private JButton Eliminar;
	private JButton editar;
	private GridBagConstraints gbc_lblImagen;
	private JLabel lblId;
	private JButton guardar;
	private JButton nuevo;
	private JButton busccar;
	private JTextField inId;
	private JButton cambiarImagen;
	private JTextField inPiezas;
	private JTextField inMarca;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panelEgresosNorth;
	private JTextField textField;
	private JButton btnBuscar;
	private JPanel panelEgresosSur;
	private JTable tablaEgresos;
	private JButton btnEliminarSalida;
	private JButton btnCancelarSalida;
	private JButton btnRegistrarSalida;
	private JComboBox comboBoxTipoConector;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JTextField inIp;
	private JTextField inBaseDatos;
	private JTextField inTabla;
	private JTextField inUsuario;
	private JTextField inAccessDB;
	private JLabel lblNewLabel_4;
	private JTextField inContrasena;
	private JButton btnProbarConexion;
	private JButton btnGuardarConexion;
	private JButton btnCargarConexion;
	public static JLabel estatusConexion;
	private JButton btnEstablecePredeterminada;
	private JTextField inConsulta;
	private JButton btnEjecutarConsulta;
	private JProgressBar barConexion;
	private Timer t;
	public static JTextArea executeQuery;

	
	
	public Almacen() {
		
		//caracteristicas de la ventana***************
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000,800);
		setLocationRelativeTo(null);
		setTitle("ALMACEN");
		
		//*******************************************
		
		JMenuBar barraMenu = new JMenuBar();
		JMenu Archivo = new JMenu("Archivo");
		JMenu Herramientas = new JMenu("Herramientas");
		JMenu Ayuda = new JMenu("Ayuda");
		
		barraMenu.add(Archivo);
		barraMenu.add(Herramientas);
		barraMenu.add(Ayuda);
		
		setJMenuBar(barraMenu);
		
		panelPrincipal = new JPanel();
		panelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelPrincipal);
		panelPrincipal.setLayout(new BorderLayout(5, 2));
		
		tabbedPrincipal= new JTabbedPane(JTabbedPane.TOP);
		panelPrincipal.add(tabbedPrincipal, BorderLayout.CENTER);
		
		
		
		//*********************************************************PANEL STOCK***************************************************************************
		
		/*
		 * contiene la informacion de los productos que esta actualmente registrados 
		 * al igual se actualiza cada que sucede o se realiza alguna accion que afecte 
		 * los registros de esta base
		 */
		 
		panelStock= new JPanel();
		panelStock.setLayout(new BorderLayout(5, 5));
		tabbedPrincipal.addTab("Stock", null, panelStock, "Inventario actual");
		
		logo = new JLabel("LOGO");
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		logo.setSize(100, 100);
		Image img2 = new ImageIcon("capture.jpg").getImage().getScaledInstance(logo.getWidth(), logo.getHeight(), Image.SCALE_SMOOTH);
		logo.setIcon(new ImageIcon(img2));
		
		panelPrincipal.add(logo, BorderLayout.NORTH);
		
		

		//la sobreescritura del metodo es para que las celdas no sean editables
	    modeloStock = new DefaultTableModel() {
	    	public boolean isCellEditable(int row, int column) {
	    	return false;
	    	}
	    };
	    
		modeloStock.addColumn("ID interno");
		modeloStock.addColumn("CODIGO");
		modeloStock.addColumn("MARCA");
		modeloStock.addColumn("DESCRIPCION");
		modeloStock.addColumn("CONTENIDO");
		modeloStock.addColumn("PIEZAS");
		modeloStock.addColumn("VTA POR UNIDAD");
		modeloStock.addColumn("PRECIO");
		
	
		tablaStock = new JTable(modeloStock);
		tablaStock.addMouseListener(tablaStockMouseL);
		JScrollPane scrollTablaStock = new JScrollPane(tablaStock);
		panelStock.add(scrollTablaStock,BorderLayout.CENTER);
		
		ImageIcon image = new ImageIcon("capture.jpg");
		Image img = image.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		lbl = new JLabel("");
		
		lbl.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
		lbl.setIcon(new ImageIcon(img));
		lbl.setHorizontalAlignment(0);
		panelStock.add(lbl, BorderLayout.NORTH);
		
		//panel para la zona sur del panelStock con formacion de elementos en el eje x
		panelStockSur = new JPanel();
		panelStockSur.setLayout(new BoxLayout(panelStockSur,BoxLayout.X_AXIS));
		
		
		buscar = new JButton("BUSCAR");
		buscar.addActionListener(buscarL);
		
		inBuscar = new JTextField(20);
		
		
		panelStockSur.add(inBuscar);
		panelStockSur.add(buscar);
		panelStock.add(panelStockSur,BorderLayout.SOUTH);
		
		//****************************************************************************FIN PANEL STOCK****************************************************
		
		//*********************************COMIENZO PANEL INGRESOS*******************************************************************
		panelIngresos = new JPanel();
		tabbedPrincipal.addTab("Ingresos", null, panelIngresos, "Ingresar productos");
		GridBagLayout gbl_panelIngresos = new GridBagLayout();
		//numero de columnas, un0 es una columna
		gbl_panelIngresos.columnWidths = new int[]{0,0,0,0,0,0};
		//numero de filas, 0 es una fila
		gbl_panelIngresos.rowHeights = new int[]{0,0,0,0,0,0,0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0,0};
		gbl_panelIngresos.columnWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panelIngresos.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		panelIngresos.setLayout(gbl_panelIngresos);
		
		lblCodigo = new JLabel("CODIGO:");
		GridBagConstraints gbc_lblId = new GridBagConstraints();
		gbc_lblId.ipadx = 20;
		gbc_lblId.anchor = GridBagConstraints.WEST;
		gbc_lblId.insets = new Insets(0, 0, 5, 5);
		gbc_lblId.gridx = 0;
		gbc_lblId.gridy = 0;
		panelIngresos.add(lblCodigo, gbc_lblId);
		
		inCodigo = new JTextField();
		GridBagConstraints gbc_inCodigo = new GridBagConstraints();
		gbc_inCodigo.fill = GridBagConstraints.BOTH;
		gbc_inCodigo.insets = new Insets(0, 0, 5, 5);
		gbc_inCodigo.gridx = 1;
		gbc_inCodigo.gridy = 0;
		panelIngresos.add(inCodigo, gbc_inCodigo);
		inCodigo.setColumns(50);
		
		lblMarca = new JLabel("MARCA:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		panelIngresos.add(lblMarca, gbc_lblNewLabel_1);
		
		inMarca = new JTextField();
		GridBagConstraints gbc_inMarca = new GridBagConstraints();
		gbc_inMarca.fill = GridBagConstraints.BOTH;
		gbc_inMarca.insets = new Insets(0, 0, 5, 5);
		gbc_inMarca.gridx = 1;
		gbc_inMarca.gridy = 2;
		panelIngresos.add(inMarca, gbc_inMarca);
		inMarca.setColumns(50);
		
		nuevo = new JButton("NUEVO");
		nuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			clickNuevoIngresos();
			}
		});
		
		nuevo.setBackground(Color.GRAY);
		nuevo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			nuevo.setBackground(Color.WHITE);
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
			nuevo.setBackground(Color.GRAY);
			}
		});
		
		GridBagConstraints gbc_nuevo = new GridBagConstraints();
		gbc_nuevo.fill = GridBagConstraints.BOTH;
		gbc_nuevo.insets = new Insets(0, 0, 5, 5);
		gbc_nuevo.gridx = 2;
		gbc_nuevo.gridy = 0;
		panelIngresos.add(nuevo, gbc_nuevo);
		
		guardar = new JButton("GUARDAR");
		guardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			ingresosGuardar();
			}
		});
		
		guardar.setBackground(Color.GRAY);
		guardar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				guardar.setBackground(Color.WHITE);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				guardar.setBackground(Color.GRAY);
			}
		});
		
		GridBagConstraints gbc_guardar = new GridBagConstraints();
		gbc_guardar.fill = GridBagConstraints.BOTH;
		gbc_guardar.insets = new Insets(0, 0, 5, 5);
		gbc_guardar.gridx = 2;
		gbc_guardar.gridy = 2;
		panelIngresos.add(guardar, gbc_guardar);
		
		lblDesc = new JLabel("DESCRIPCION:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 4;
		panelIngresos.add(lblDesc, gbc_lblNewLabel_2);
		
		inDesc = new JTextField();
		GridBagConstraints gbc_inDesc = new GridBagConstraints();
		gbc_inDesc.fill = GridBagConstraints.BOTH;
		gbc_inDesc.insets = new Insets(0, 0, 5, 5);
		gbc_inDesc.gridx = 1;
		gbc_inDesc.gridy = 4;
		panelIngresos.add(inDesc, gbc_inDesc);
		inDesc.setColumns(50);
		
		editar = new JButton("EDITAR");
		editar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			clickEditar();
			}
		});
		
		editar.setBackground(Color.GRAY);
		editar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			editar.setBackground(Color.WHITE);
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
			editar.setBackground(Color.GRAY);
			}
		});
		
		GridBagConstraints gbc_editar = new GridBagConstraints();
		gbc_editar.fill = GridBagConstraints.BOTH;
		gbc_editar.insets = new Insets(0, 0, 5, 5);
		gbc_editar.gridx = 2;
		gbc_editar.gridy = 4;
		panelIngresos.add(editar, gbc_editar);
		
		lblContenido = new JLabel("CONTENIDO:");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 6;
		panelIngresos.add(lblContenido, gbc_lblNewLabel_3);
		
		inContenido = new JTextField();
		inContenido.setToolTipText("1000 = 1 KG");
		GridBagConstraints gbc_inContenido = new GridBagConstraints();
		gbc_inContenido.fill = GridBagConstraints.BOTH;
		gbc_inContenido.insets = new Insets(0, 0, 5, 5);
		gbc_inContenido.gridx = 1;
		gbc_inContenido.gridy = 6;
		panelIngresos.add(inContenido, gbc_inContenido);
		inContenido.setColumns(50);
		
		Eliminar = new JButton("ELIMINAR");
		Eliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			borrarIngresos();
			}
		});
		
		Eliminar.setBackground(Color.GRAY);
		Eliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			Eliminar.setBackground(Color.WHITE);
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
			Eliminar.setBackground(Color.GRAY);
			}
		});
		
		GridBagConstraints gbc_Eliminar = new GridBagConstraints();
		gbc_Eliminar.insets = new Insets(0, 0, 5, 5);
		gbc_Eliminar.fill = GridBagConstraints.BOTH;
		gbc_Eliminar.gridx = 2;
		gbc_Eliminar.gridy = 6;
		panelIngresos.add(Eliminar, gbc_Eliminar);
		
		lblPiezas = new JLabel("PIEZAS:");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 8;
		panelIngresos.add(lblPiezas, gbc_lblNewLabel_4);
		
		inPiezas = new JTextField();
		GridBagConstraints gbc_inPiezas = new GridBagConstraints();
		gbc_inPiezas.fill = GridBagConstraints.BOTH;
		gbc_inPiezas.insets = new Insets(0, 0, 5, 5);
		gbc_inPiezas.gridx = 1;
		gbc_inPiezas.gridy = 8;
		panelIngresos.add(inPiezas, gbc_inPiezas);
		inPiezas.setColumns(50);
		
		lblPrecio = new JLabel("PRECIO:");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 0;
		gbc_lblNewLabel_5.gridy = 10;
		panelIngresos.add(lblPrecio, gbc_lblNewLabel_5);
		
		inPrecio = new JTextField();
		GridBagConstraints gbc_inPrecio = new GridBagConstraints();
		gbc_inPrecio.fill = GridBagConstraints.BOTH;
		gbc_inPrecio.insets = new Insets(0, 0, 5, 5);
		gbc_inPrecio.gridx = 1;
		gbc_inPrecio.gridy = 10;
		panelIngresos.add(inPrecio, gbc_inPrecio);
		inPrecio.setColumns(50);
		
		lblGranel = new JLabel("VENTA A GRANEL?");
		GridBagConstraints gbc_lblGranel = new GridBagConstraints();
		gbc_lblGranel.anchor = GridBagConstraints.WEST;
		gbc_lblGranel.insets = new Insets(0, 0, 5, 5);
		gbc_lblGranel.gridx = 0;
		gbc_lblGranel.gridy = 12;
		panelIngresos.add(lblGranel, gbc_lblGranel);
		
		checkGranel = new JCheckBox("");
		GridBagConstraints gbc_checkGranel = new GridBagConstraints();
		gbc_checkGranel.insets = new Insets(0, 0, 5, 5);
		gbc_checkGranel.fill = GridBagConstraints.VERTICAL;
		gbc_checkGranel.anchor = GridBagConstraints.WEST;
		gbc_checkGranel.gridx = 1;
		gbc_checkGranel.gridy = 12;
		panelIngresos.add(checkGranel, gbc_checkGranel);
		GridBagConstraints gbl;
		
		lblId = new JLabel("ID INTERNO");
		GridBagConstraints gbc_lblId12 = new GridBagConstraints();
		gbc_lblId12.anchor = GridBagConstraints.WEST;
		gbc_lblId12.insets = new Insets(0, 0, 5, 5);
		gbc_lblId12.gridx = 0;
		gbc_lblId12.gridy = 13;
		panelIngresos.add(lblId, gbc_lblId12);
		
		busccar = new JButton("BUSCAR");
		busccar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			buscarIngresos();
				
			}
		});
		
		busccar.setBackground(Color.GRAY);
		busccar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			busccar.setBackground(Color.WHITE);
			
			}
			@Override
			public void mouseExited(MouseEvent e) {
			busccar.setBackground(Color.GRAY);
			}
		});
		
		inId = new JTextField();
		GridBagConstraints gbc_inId = new GridBagConstraints();
		gbc_inId.fill = GridBagConstraints.BOTH;
		gbc_inId.insets = new Insets(0, 0, 5, 5);
		gbc_inId.gridx = 1;
		gbc_inId.gridy = 13;
		panelIngresos.add(inId, gbc_inId);
		inId.setColumns(10);
		GridBagConstraints gbc_busccar = new GridBagConstraints();
		gbc_busccar.fill = GridBagConstraints.BOTH;
		gbc_busccar.insets = new Insets(0, 0, 5, 5);
		gbc_busccar.gridx = 2;
		gbc_busccar.gridy = 13;
		panelIngresos.add(busccar, gbc_busccar);
		
		lblImagen = new JLabel("Click para buscar Imagen");
		lblImagen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			lblImagen.setBackground(Color.WHITE);
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
			lblImagen.setBackground(Color.GRAY);
			}
		});
	
		lblImagen.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		
		cambiarImagen = new JButton("CAMBIAR IMAGEN");
		cambiarImagen.setEnabled(false);
		cambiarImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			actualizarImagenIngresos();
			}
		});
		
		cambiarImagen.setBackground(Color.GRAY);
		cambiarImagen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			cambiarImagen.setBackground(Color.WHITE);
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
			cambiarImagen.setBackground(Color.GRAY);
			}
		});
		
		GridBagConstraints gbc_cambiarImagen = new GridBagConstraints();
		gbc_cambiarImagen.fill = GridBagConstraints.HORIZONTAL;
		gbc_cambiarImagen.insets = new Insets(0, 0, 5, 5);
		gbc_cambiarImagen.gridx = 2;
		gbc_cambiarImagen.gridy = 14;
		panelIngresos.add(cambiarImagen, gbc_cambiarImagen);
	
		
		gbc_lblImagen = new GridBagConstraints();
		gbc_lblImagen.insets = new Insets(0, 0, 5, 5);
		gbc_lblImagen.fill = GridBagConstraints.BOTH;
		gbc_lblImagen.gridx = 1;
		gbc_lblImagen.gridy = 14;
		panelIngresos.add(lblImagen, gbc_lblImagen);
		
		
		//*************************************************************FIN PANEL INGRESOS*************************************************************************************
		
		//*************************************************************COMIENZA PANEL EGRESOS*********************************************************************************
		panelEgresos = new JPanel();
		tabbedPrincipal.addTab("Egresos", null, panelEgresos, "Retirar productos");
		panelEgresos.setLayout(new BorderLayout(13, 17));
		
		panelEgresosNorth = new JPanel();
		panelEgresos.add(panelEgresosNorth, BorderLayout.NORTH);
		panelEgresosNorth.setLayout(new BoxLayout(panelEgresosNorth, BoxLayout.X_AXIS));
		
		textField = new JTextField();
		panelEgresosNorth.add(textField);
		textField.setColumns(70);
		
		btnBuscar = new JButton("BUSCAR PRODUCTO");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			anadirProductosEgresos();	
			}	
		});
		
		btnBuscar.setBackground(Color.GRAY);
		btnBuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			btnBuscar.setBackground(Color.WHITE);
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
			btnBuscar.setBackground(Color.GRAY);
			}
		});
		
		panelEgresosNorth.add(btnBuscar);
		panelEgresosSur = new JPanel();
		panelEgresos.add(panelEgresosSur, BorderLayout.SOUTH);
		panelEgresosSur.setLayout(new GridLayout(0, 4, 30, 0));
		
		btnEliminarSalida = new JButton("ELIMINAR PRODUCTO");
		btnEliminarSalida.setBackground(Color.GRAY);
		btnEliminarSalida.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			eliminarSalidaProducto();
			}
		});
		
		btnEliminarSalida.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			btnEliminarSalida.setBackground(Color.WHITE);
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
			btnEliminarSalida.setBackground(Color.GRAY);
			}
		});
		
		panelEgresosSur.add(btnEliminarSalida);
		btnCancelarSalida = new JButton("CANCELAR SALIDA");
		btnCancelarSalida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			cancelarSalida();
			}
		});
		
		btnCancelarSalida.setBackground(Color.GRAY);
		
		btnCancelarSalida.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			btnCancelarSalida.setBackground(Color.WHITE);
			}
			@Override
			public void mouseExited(MouseEvent e) {
			btnCancelarSalida.setBackground(Color.GRAY);
			}
		});
		
		panelEgresosSur.add(btnCancelarSalida);
		btnRegistrarSalida = new JButton("REGISTRAR SALIDA");
		btnRegistrarSalida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registrarSalidaEgresosAccess();
			}
		});
		
		btnRegistrarSalida.setBackground(Color.GRAY);
		btnRegistrarSalida.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			btnRegistrarSalida.setBackground(Color.WHITE);
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
			btnRegistrarSalida.setBackground(Color.GRAY);
			}
		});
		
		totalEgresos = new JTextField();
		totalEgresos.setEditable(false);
		panelEgresosSur.add(totalEgresos);
		totalEgresos.setColumns(10);
		totalEgresos.setFont(new Font("Arial", Font.BOLD, 25));
		panelEgresosSur.add(btnRegistrarSalida);
		
		//se bloquean las celdas para que no se puedan editar
		tablaEgresosModelo= new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
		        return false;
		    }	
		};
		
		tablaEgresosModelo.addColumn("CODIGO");
		tablaEgresosModelo.addColumn("DESCRIPCION");
		tablaEgresosModelo.addColumn("MARCA");
		tablaEgresosModelo.addColumn("CANTIDAD");
		tablaEgresosModelo.addColumn("PRECIO");
		tablaEgresosModelo.addColumn("TOTAL");
		
	
		tablaEgresos = new JTable(tablaEgresosModelo);
		tablaEgresos.setRowHeight(30);
		tablaEgresos.setFont(new Font("Arial",Font.BOLD,25));
		tablaEgresos.setEditingColumn(3);
		tablaEgresos.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			int sel = tablaEgresos.getSelectedRow();
			System.out.println("row seleccionada: " + sel);
				
			}
		});
		
		JScrollPane scrollEgresos = new JScrollPane(tablaEgresos);
		panelEgresos.add(scrollEgresos, BorderLayout.CENTER);
		
		//******************************************************TERMINA PANEL EGRESOS*************************************************************************************
		
		//***************************************************************PANEL CONEXION***********************************************************************************
		
		panelConexion = new JPanel();
		tabbedPrincipal.addTab("Conexion", null, panelConexion, "Configuracion Base De Datos");
		panelConexion.setLayout(new MigLayout("", "[grow][grow]", "[][][][][][][][][grow]"));
		
		comboBoxTipoConector = new JComboBox();
		comboBoxTipoConector.addItem("jdbc:ucanaccess://");
		comboBoxTipoConector.addItem("jdbc:mysql ");
		comboBoxTipoConector.addItemListener(comboListener);
		panelConexion.add(comboBoxTipoConector, "cell 0 0,growx");
		
		inAccessDB = new JTextField();
		inAccessDB.setEditable(false);
		inAccessDB.addMouseListener(buscarFileAccess);
		inAccessDB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			inAccessDB.setBackground(Color.WHITE);	
			}
			@Override
			public void mouseExited(MouseEvent e) {
			inAccessDB.setBackground(Color.GRAY);
			}
		});
		
		inAccessDB.setText("click para buscar base de datos Access");
		panelConexion.add(inAccessDB, "cell 1 0,growx");
		inAccessDB.setColumns(10);
		
		lblNewLabel_4 = new JLabel("DIRECCION IP*");
		panelConexion.add(lblNewLabel_4, "cell 0 1,alignx right");
		
		inIp = new JTextField();
		panelConexion.add(inIp, "flowx,cell 1 1,growx");
		inIp.setColumns(10);
		
		lblNewLabel = new JLabel("NOMBRE DE BASE DE DATOS*");
		panelConexion.add(lblNewLabel, "cell 0 2,alignx right");
		
		inBaseDatos = new JTextField();
		panelConexion.add(inBaseDatos, "cell 1 2,growx");
		inBaseDatos.setColumns(10);
		
		lblNewLabel_1 = new JLabel("NOMBRE DE TABLA");
		panelConexion.add(lblNewLabel_1, "cell 0 3,alignx right");
		
		inTabla = new JTextField();
		panelConexion.add(inTabla, "cell 1 3,growx");
		inTabla.setColumns(10);
		
		lblNewLabel_2 = new JLabel("USUARIO*");
		panelConexion.add(lblNewLabel_2, "cell 0 4,alignx right");
		
		inUsuario = new JTextField();
		panelConexion.add(inUsuario, "cell 1 4,growx");
		inUsuario.setColumns(10);
		
		lblNewLabel_3 = new JLabel("CONTRASE\u00D1A");
		panelConexion.add(lblNewLabel_3, "cell 0 5,alignx right");
		
		inContrasena = new JTextField();
		panelConexion.add(inContrasena, "cell 1 5,growx");
		inContrasena.setColumns(10);
		
		estatusConexion = new JLabel("");
		panelConexion.add(estatusConexion, "cell 0 6,alignx center,aligny center");
		
		btnProbarConexion = new JButton("PROBAR CONEXION");
		btnProbarConexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			cargarConexionProbar();
			}
		});
		
		btnProbarConexion.setBackground(Color.GRAY);
		btnProbarConexion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			btnProbarConexion.setBackground(Color.WHITE);
			}
			@Override
			public void mouseExited(MouseEvent e) {
			btnProbarConexion.setBackground(Color.GRAY);
			}
		});
		
		panelConexion.add(btnProbarConexion, "flowx,cell 1 6,alignx center");
		inConsulta = new JTextField();
		panelConexion.add(inConsulta, "cell 0 7,growx");
		inConsulta.setColumns(10);
		
		btnEjecutarConsulta = new JButton("EJECUTAR CONSULTA SQL");
		btnEjecutarConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    if(estatusConexion.getText().equals("CONEXION EXITOSA")) {
					ejecutarConsulta(inConsulta.getText());
				}else {
					System.out.println("PRUEBA LA CONEXION PRIMERO");
				}
			}
		});
		
		btnEjecutarConsulta.setBackground(Color.GRAY);
		btnEjecutarConsulta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			btnEjecutarConsulta.setBackground(Color.WHITE);
			}
			@Override
			public void mouseExited(MouseEvent e) {
			btnEjecutarConsulta.setBackground(Color.GRAY);
			}
		});
		
		panelConexion.add(btnEjecutarConsulta, "flowx,cell 1 7");
		/*acciones pertinentes a crear un archivo properties que guarda la conexion actual
		*primero debe corroborarse que esta funciona correctamente y que el label
		*muestre la leyenda de CONEXION EXITOSA, ya que de caso contrario no se podra
		*generar el archivo
		*/
		btnGuardarConexion = new JButton("GUARDAR CONEXION");
		btnGuardarConexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBoxTipoConector.getSelectedIndex()==0) {
					//access
				if(estatusConexion.getText().equals("CONEXION EXITOSA")) {
					//evalua si la conexion fue exitosa
					Propiedades p = new Propiedades();
					String nombre = JOptionPane.showInputDialog(Almacen.this, "Nombre para el archivo");
					String comentario = JOptionPane.showInputDialog(Almacen.this, "añadir comentario al archivo");
					p.crearPropertiesAccess(inAccessDB.getText(), comentario, "config\\access\\"+nombre+".properties");
				}else {
					System.out.println("PRIMERO PRUEBA LA CONEXION");
				}
			}else if(comboBoxTipoConector.getSelectedIndex()==1) {
				//mysql
				if(estatusConexion.getText().equals("CONEXION EXITOSA")) {
					//evalua si la conexion fue exitosa
					Propiedades p = new Propiedades();
					String nombre = JOptionPane.showInputDialog(Almacen.this, "Nombre para el archivo");
					String comentario = JOptionPane.showInputDialog(Almacen.this, "añadir comentario al archivo");
					p.crearPropertiesMysql(inIp.getText(), inPuerto.getText(), inBaseDatos.getText(), inTabla.getText(), inUsuario.getText(), inContrasena.getText(), comentario, "config\\mysql\\"+nombre+".properties");
				}else {
					//en caso de no haber evaluado la conexion arrojara una alerta
					System.out.println("PRIMERO PRUEBA LA CONEXION");
				}
			}
		}});
		
		btnGuardarConexion.setBackground(Color.GRAY);
		btnGuardarConexion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			btnGuardarConexion.setBackground(Color.WHITE);
			}
			@Override
			public void mouseExited(MouseEvent e) {
			btnGuardarConexion.setBackground(Color.GRAY);
			}
		});
		panelConexion.add(btnGuardarConexion, "cell 1 6,alignx center");
		
		/**
		 * 
		 * carga un archivo properties a elegir por el usuario, se muestra la ruta dependiendo 
		 * del tipo de conector seleccionado en el comboBox del panel conexion
		 * 0 para la ruta donde se alojan los archivos properties de access y 1 para los archivos 
		 * de conexion de mysql
		 * 
		 **/
		btnCargarConexion = new JButton("CARGAR CONEXION");
		btnCargarConexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBoxTipoConector.getSelectedIndex()==0){
						//acess
						Propiedades p = new Propiedades();
						FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos properties", "properties");
						JFileChooser f = new JFileChooser("config\\access");
						f.setFileFilter(filtro);
						f.showOpenDialog(Almacen.this);
						inAccessDB.setText(p.leerPropertiesAccess(f.getSelectedFile().getAbsolutePath()));
						conexionAccess(inAccessDB.getText());
				}else if(comboBoxTipoConector.getSelectedIndex()==1) {
					//mysql
					Propiedades p = new Propiedades();
					FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos properties", "properties");
					JFileChooser f = new JFileChooser("config\\mysql");
					f.setFileFilter(filtro);
					f.showOpenDialog(Almacen.this);
					String datos[] = p.leerPropertiesMysql(f.getSelectedFile().getAbsolutePath());
					inIp.setText(datos[0]);
					inPuerto.setText(datos[1]);
					inBaseDatos.setText(datos[2]);
					inBaseDatos.setText(datos[3]);
					inUsuario.setText(datos[4]);
					inContrasena.setText(datos[5]);	
				}
			}
		});
		
		btnCargarConexion.setBackground(Color.GRAY);
		btnCargarConexion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			btnCargarConexion.setBackground(Color.WHITE);
			}
			@Override
			public void mouseExited(MouseEvent e) {
			btnCargarConexion.setBackground(Color.GRAY);
			}
		});
		
		panelConexion.add(btnCargarConexion, "cell 1 6,alignx center");
		btnEstablecePredeterminada = new JButton("ESTABLECER PREDETERMINADA");
		btnEstablecePredeterminada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			//mas informacion en el metodo
			establecePrederteminada();	
			}
		});
		
		btnEstablecePredeterminada.setBackground(Color.GRAY);
		btnEstablecePredeterminada.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			btnEstablecePredeterminada.setBackground(Color.WHITE);	
			}
			@Override
			public void mouseExited(MouseEvent e) {
			btnEstablecePredeterminada.setBackground(Color.GRAY);
			}
		});
		
		panelConexion.add(btnEstablecePredeterminada, "cell 1 6,alignx center");

		barConexion = new JProgressBar();
		barConexion.setForeground(Color.GREEN);
		panelConexion.add(barConexion, "cell 1 7,growx");
		
		scrollPane = new JScrollPane();
		panelConexion.add(scrollPane, "cell 1 8,grow");
		
		executeQuery = new JTextArea();
		scrollPane.setViewportView(executeQuery);
		
		lblNewLabel_5 = new JLabel("PUERTO*");
		panelConexion.add(lblNewLabel_5, "cell 1 1");
		
		inPuerto = new JTextField();
		panelConexion.add(inPuerto, "cell 1 1");
		inPuerto.setColumns(10);
		
		//*************************************************************TERMINA PANEL CONEXION********************************************************************************
			
		
		//se llama a este metodo en esta parte, ya que es donde se terminan de crear todos los componente
		configuracionInicial();
		
	     }
	
	    public void configuracionInicial() {
		//Configuracion inicial
		
		//**********config panel conexion
		inBaseDatos.setEditable(false);
		inContrasena.setEditable(false);
		inUsuario.setEditable(false);
		inTabla.setEditable(false);
		inIp.setEditable(false);
		inPuerto.setEditable(false);
		
		//**********fin config panel conexion
				
				
		//*********leer properties por defecto
				
		/**
	    * en la ruta config/ se encuentra un archivo default quien es quien contiene la informacion 
		* acerca de que conector va a usar para cargar la canfiguracion y dependiendo de eso
		* lee el valor de archivo que es la ruta donde esta el archivo properties
		* que cargara con los datos de conexion, a su vez esto los inserta en los campos de panel Conexion.
		*
		*se hace una verificacion para el tamaño del array ya que en case de que sea igual a 2 
	    *se trata de un archivo access, por el contrario si es de 6 se tratara de una conexion tipo mysql
		*los tamaños son en relacion a los paramatros que requiere cada conexion. Y por ultimo si es menora a 1 se establece que no hay 
		*un archivo o configuracion predertminada, este valor lo puedes modificar manualmente en dicho archivo ya que 
		*en esta version no se trabajo para quitar una configuracion predeterminada
		*
		**/
				
		Propiedades p = new Propiedades();
		String datos[] = p.leerPredeterminada("config\\default.properties");
			if(datos.length==2) {
				//access
				comboBoxTipoConector.setSelectedIndex(Integer.valueOf(datos[1]));
				inAccessDB.setText(datos[0]);
				//conexionAccess(inAccessDB.getText());
				cargarConexionProbar();
					
			}else if(datos.length>=6) {
				//mysql
				comboBoxTipoConector.setSelectedIndex(1);
				inIp.setText(datos[0]);
				inPuerto.setText(datos[1]);
				inBaseDatos.setText(datos[2]);
				inTabla.setText(datos[3]);
				inUsuario.setText(datos[4]);
				inContrasena.setText(datos[5]);
				cargarConexionProbar();
					
			}else if(datos.length>=1) {
				/*si la no se encuentra configuracion predeterminada se pone el combobox en -1 para que no tenga seleccionada ninguna conexion
				*ya que en funcion de los datos que esten seleccionado en este se realizan consultas y de mas acciones que requieren 
				*base de datos.
				**/
				System.out.println("sin conexion predeterminada");
				comboBoxTipoConector.setSelectedIndex(-1);
				inAccessDB.removeMouseListener(buscarFileAccess);
				}
				
				//**************************************
				
				//********configuracion incial para panel stock****************//
				cargarDatosIniciales();
				
				
				
				
				
				
				//*************configuracion inicial panel ingresos
			    bloquearTodoIngresos();
		
	            }
	    public void cargarConexionProbar() {
        //switch para el control de conectores incia con access en el caso 0
		switch(comboBoxTipoConector.getSelectedIndex()) {
		 case 0:
			//access
				if(inAccessDB.getText().equals("click para buscar base de datos Access")||inAccessDB.getText().equals("")) {
					
				inAccessDB.setText(rutaAccess());
					
				}else {
				t = new Timer(10, null);
				t.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			barConexion.setValue(barConexion.getValue()+1);
			System.out.println(barConexion.getValue());
			if(barConexion.getValue()==100) {
				t.stop();
				System.out.println("cargando conexion");
				barConexion.setValue(0);
				conexionAccess(inAccessDB.getText().trim());
				t = null;
			}
		}
	   });
			t.start();
	
	}
	break;
	
		 case 1:
			//MYsql
			Acciones a = new Acciones();
			int i = a.comprobarCamposConexionMySQl(inIp.getText(), inPuerto.getText(),inBaseDatos.getText(), inTabla.getText(), inUsuario.getText(), inContrasena.getText());
			if(i==0) {
			t = new Timer(10, null);
			t.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		barConexion.setValue(barConexion.getValue()+1);
		System.out.println(barConexion.getValue());
		if(barConexion.getValue()==100) {
			t.stop();
			System.out.println("cargando conexion");
			System.out.println("MySQL");
			try {
				ConexionMysql con = new ConexionMysql();
				Connection conexion = con.ConexionMysqlX(inIp.getText(),inBaseDatos.getText(),inPuerto.getText(),inUsuario.getText(),inContrasena.getText());
				if(conexion ==null) {
					estatusConexion.setText("CONEXION ERROR");
				}
				Statement st =conexion.createStatement();
				estatusConexion.setText("CONEXION EXITOSA");
				conexion.close();
				st.close();
				JOptionPane.showMessageDialog(Almacen.this, "Conexion Correcta MySQL : "+inIp.getText()+" "+inBaseDatos.getText()+" "+inPuerto.getText());
				barConexion.setValue(0);
			} catch (SQLException e1) {
				estatusConexion.setText("CONEXION ERROR");
				barConexion.setValue(0);
				e1.printStackTrace();
			}
			t = null;
		}
	}
   });
		t.start();
		}else {
			System.out.println("rellena los campos Obligatorios faltantes: " + i);
		}
			break;
	}
	}
	

	
	ActionListener buscarL = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			//busca los datos para mostrar en la tabla del panel de Stock
			modeloStock.setRowCount(0);
			if(comboBoxTipoConector.getSelectedIndex()==0) {
				//access
			ConexionAccess con = new ConexionAccess();
			con.buscarStock(inAccessDB.getText(), inBuscar.getText());
			}else if(comboBoxTipoConector.getSelectedIndex()==1) {
				//mysql
				Acciones a = new Acciones();
				a.selectFiltrarStockMysql(inIp.getText(), inBaseDatos.getText(), inPuerto.getText(), inUsuario.getText(), inContrasena.getText(), inBuscar.getText(), panelPrincipal);
			}else {
				System.out.println("ningun conector para realizar busqueda");
			}
			}
	} ;
	
	ItemListener comboListener = new ItemListener() {
		@Override
		public void itemStateChanged(ItemEvent e) {
			//cambia las propiedades dependiendo de lo que esta seleccionado en el combobox, (bloquea o desbloquea campos).
			comoboSeleccion(comboBoxTipoConector.getSelectedIndex());
		}
	};
	
	MouseAdapter tablaStockMouseL = new MouseAdapter() {
		public void mousePressed(MouseEvent e) {
			/**
			 * listener para la tabla del panel stock aqui al hacer click en alguna de las filas de la
			 * tabla esta devuelve en un label de la parte norte del panel stock, la imagen y informacion referente al 
			 * producto seleccionado para su mejor visualizacion
			 * 
			 * **/
		int seleccion=tablaStock.getSelectedRow();
	
		lbl.setText(tablaStock.getValueAt(seleccion, 0)+"\n "+tablaStock.getValueAt(seleccion, 1)+"\n "+tablaStock.getValueAt(seleccion, 2)
		+"\n "+tablaStock.getValueAt(seleccion, 3)+"\n " + tablaStock.getValueAt(seleccion, 4)
		+"\n "+ tablaStock.getValueAt(seleccion, 5)+"\n "+tablaStock.getValueAt(seleccion, 6) );
		
		//aqui obtenemos la imagen alamcenada en la base de access o mysql
		if(comboBoxTipoConector.getSelectedIndex()==0) {
			//access
		ConexionAccess con = new ConexionAccess();
		//importante sumar 1 a seleccion ya que las filas de tabla empiezan en 0 y las tablas de las base de datos empiezan en 1
		Image imagen = con.obtenerImagenAccess( Integer.parseInt( tablaStock.getValueAt(seleccion, 0).toString()), inAccessDB.getText());
		if(imagen == null) {
		JOptionPane.showMessageDialog(Almacen.this, "NO se encontro imagen para el producto: id - " + seleccion);
		}else {
			//se pone el icono en label despues de ser evaluada por si era nula
		lbl.setIcon(new ImageIcon(imagen));
		}
	}else if(comboBoxTipoConector.getSelectedIndex()==1) {
		//mysql
		
		Acciones a = new Acciones();
		//importante sumar 1 a seleccion ya que las filas de tabla empiezan en 0 y las tablas de las base de datos empiezan en 1
		Image imagen = a.obtenerImagenStockMysql(inIp.getText(), inBaseDatos.getText(), inPuerto.getText(), inUsuario.getText(), inContrasena.getText(), Integer.parseInt( tablaStock.getValueAt(seleccion, 0).toString()), panelPrincipal);
		if(imagen == null) {
		JOptionPane.showMessageDialog(Almacen.this, "NO se encontro imagen para el producto: id - " + seleccion);
		}else {
			//se pone el icono en label despues de ser evaluada por si era nula
		lbl.setIcon(new ImageIcon(imagen));
		}
		
	}
		
		}
	};
	
	MouseListener buscarFileAccess = new MouseAdapter() {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			inAccessDB.setText(rutaAccess());
			estatusConexion.setText("");	
		}
	};
	private JScrollPane scrollPane;
	private JLabel lblNewLabel_5;
	private JTextField inPuerto;
	
	

	
	//metodos 
	
	//cambia propiedades de la interfaz en el panel conexion dependiendo del conector seleccionado 
	public void comoboSeleccion(int i) {
		if(i==0) {
			inAccessDB.removeMouseListener(buscarFileAccess);
			System.out.println("conector access");
			inAccessDB.setText("click para buscar base de datos Access");
			inAccessDB.addMouseListener(buscarFileAccess);
			inAccessDB.setEnabled(true);
			inBaseDatos.setEditable(false);
			inContrasena.setEditable(false);
			inUsuario.setEditable(false);
			inTabla.setEditable(false);
			inPuerto.setEditable(false);
			inIp.setEditable(false);
			inBaseDatos.setText("");
			inContrasena.setText("");
			inUsuario.setText("");
			inTabla.setText("");
			inIp.setText("");
			estatusConexion.setText("");
		}else if(i==1){
			System.out.println("conector MySQL");
			inAccessDB.setText("");
			inAccessDB.removeMouseListener(buscarFileAccess);
			inAccessDB.setEnabled(false);
			inBaseDatos.setEditable(true);
			//inBaseDatos.setText("");
			inContrasena.setEditable(true);
			inPuerto.setEditable(true);
			inUsuario.setEditable(true);
			inTabla.setEditable(true);
			inIp.setEditable(true);
			estatusConexion.setText("");
		}
	}
	
	public String rutaAccess() {
		//busca un archivo accdb que corresponde a la base de datos access
		String ruta= null;
		JFileChooser j = new JFileChooser();
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos Access", "accdb");
		j.setFileFilter(filtro);
		int seleccion = j.showOpenDialog(Almacen.this);
		System.out.println("seleccion del filechooser: " + seleccion);
		if(seleccion ==0) {
			ruta= j.getSelectedFile().getAbsolutePath();
		}else if(seleccion ==1) {
			JOptionPane.showMessageDialog(Almacen.this, "Accion cancelada por el usuario: No podras cargar la conexion sin el archivo accdb");
		}
		
		return ruta;
	}
	
	
	//genera la conexion Access con los datos proporcionado en el panel conexion, ruta = localizacion del archivo accdb
	
	public void conexionAccess(String ruta) {
		
		ConexionAccess con = new ConexionAccess();
		int respuesta = con.hacerConexionAccess(ruta);
		if(respuesta==1) {
			JOptionPane.showMessageDialog(Almacen.this, "conexion correcta: " + ruta);
		}
		con.close();
	}
	
	
	//se hace la consulta avanzada con la linea ingresada en el textfield de abajo del la pestaña conexion
	public String[] ejecutarConsulta(String sql) {
		String datos[]=null;
		if(sql.isEmpty()||sql.isBlank()) {
			JOptionPane.showMessageDialog(Almacen.this, "Necesitas ingresar informacion para realizar la consulta");
			return datos;
		}else {
		Acciones a = new Acciones();
		
		datos = a.selectTest(inConsulta.getText(), inAccessDB.getText(), panelConexion);
		
		return datos;
		}
	}
	
	
	//metodo para poner propertie predeterminada
	public void establecePrederteminada() {
		Propiedades p = new Propiedades();
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos properties", "properties");
		JFileChooser f =null;
		if(comboBoxTipoConector.getSelectedIndex()==0) {
			f = new JFileChooser("config\\access\\");
		}else if(comboBoxTipoConector.getSelectedIndex()==1){
			f = new JFileChooser("config\\mysql\\");
		}
		f.setFileFilter(filtro);
		int i = f.showOpenDialog(Almacen.this);
		if(i==0) {
		p.establecerPredeterminada(comboBoxTipoConector.getSelectedIndex(), f.getSelectedFile().getAbsolutePath());
		}
		
		
	}
	
	//***********************************************metodos y listener para el panel Stock******************************************//
	
	//carga los datos en cuanto inicia el programa
	public void cargarDatosIniciales() {
		modeloStock.setRowCount(0);
		//indentifica que tipo de conexion esta usuando
		if(comboBoxTipoConector.getSelectedIndex()==0) {
			//Access
			System.out.println("panel stock usando Access");
			Connection conexion;
			PreparedStatement statement;
			ResultSet result;
			String datos[] = new String[8];
			ConexionAccess con = new ConexionAccess();
			conexion = con.devuelveConexionAccess(inAccessDB.getText());
			try {
				statement = conexion.prepareStatement("SELECT * FROM productos");
				result = statement.executeQuery();
				while(result.next()) {
					datos[0]=result.getString("id");
					datos[1]=result.getString("codigo");
					datos[2]=result.getString("marca");
					datos[3]=result.getString("descripcion");
					datos[4]=result.getString("contenido");
					datos[5]=result.getString("piezas");
					datos[6]=result.getString("unidad");
					datos[7]="$"+result.getString("precio");
					modeloStock.addRow(datos);
					
				}
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			con.close();
			
			
			
		}else if(comboBoxTipoConector.getSelectedIndex()==1) {
			//MySQL
			System.out.println("panel stock usando MySQL");
			Acciones a = new Acciones();
			a.selectAllStockMysql(inIp.getText(), inBaseDatos.getText(), inPuerto.getText(), inUsuario.getText(), inContrasena.getText(), panelPrincipal);
			
			
		}
		
		
	}
	
	//***********************************************fin metodos y listener panel Stock************************************************//
	
	//**************************************metodos panel ingresos********************************************//
	
	MouseAdapter buscaImagenI = new MouseAdapter() {
		
		  public void mouseClicked(MouseEvent e) {
			  buscarImagenIngresos();
			  
		  }
		
	};
	private JTextField totalEgresos;
	public void activarTodoIngresos() {
		inCodigo.setEnabled(true);
		inMarca.setEnabled(true);
		inDesc.setEnabled(true);
		inContenido.setEnabled(true);
		inPiezas.setEnabled(true);
		inPrecio.setEnabled(true);
		checkGranel.setEnabled(true);
		guardar.setEnabled(true);
		editar.setEnabled(true);
		Eliminar.setEnabled(true);
		cambiarImagen.setEnabled(true);
	}
	
	public void bloquearTodoIngresos() {
		inCodigo.setEnabled(false);
		inMarca.setEnabled(false);
		inDesc.setEnabled(false);
		inContenido.setEnabled(false);
		inPiezas.setEnabled(false);
		inPrecio.setEnabled(false);
		checkGranel.setEnabled(false);
		guardar.setEnabled(false);
		editar.setEnabled(false);
		Eliminar.setEnabled(false);
	}
	
	public void clickNuevoIngresos() {
		
		limpiarCamposIngresos();
		lblImagen.addMouseListener(buscaImagenI);
		lblImagen.setText("Click para buscar Imagen");
		activarTodoIngresos();
		cambiarImagen.setEnabled(false);
		Eliminar.setEnabled(false);
		editar.setEnabled(false);
		buscar.setEnabled(false);
		
	
	}
	
	public void limpiarCamposIngresos() {
		inCodigo.setText("");
		inMarca.setText("");
		inDesc.setText("");
		inContenido.setText("");
		inPiezas.setText("");
		inPrecio.setText("");
		inId.setText("");
		checkGranel.setSelected(false);
		lblImagen.setIcon(null);
		lblImagen.removeMouseListener(buscaImagenI);
	}
	
	public void clickBuscarIngresos() {
		
		if(inCodigo.getText().isBlank()) {
		
		}else {
			editar.setEnabled(true);
			Eliminar.setEnabled(true);
		}
	
	}
	public void ingresosGuardar() {
		Acciones acciones = new Acciones();
		if(comboBoxTipoConector.getSelectedIndex()==0) {
			//access
			System.out.println("ingresos guardar metodo en access");
			if(editar.isEnabled()) {
				//editar
				lblImagen.removeMouseListener(buscaImagenI);
				Ingresos ingresos = new Ingresos();
				ingresos.actualizarProductoAccess("db\\BaseAccessAlmacen.accdb", inCodigo.getText(), inMarca.getText(), inDesc.getText(),Integer.valueOf(inContenido.getText()),Integer.valueOf(inPiezas.getText()), checkGranel.isSelected(), Double.valueOf(inPrecio.getText()),Integer.valueOf(inId.getText()));
				ingresos= null;
				cargarDatosIniciales();
				limpiarCamposIngresos();
				bloquearTodoIngresos();
				JOptionPane.showMessageDialog(Almacen.this, "Datos editados y Guardados correctamente");
			
			}else {
				//guardar
		           int i =	acciones.comprobarCamposIngresos(inCodigo.getText(), inMarca.getText(), inDesc.getText(), inContenido.getText(), inPiezas.getText(), inPrecio.getText(), lblImagen.getText());
					if(i==0) {
						System.out.println("campos completos");
						FileInputStream file = null;
						try {
							file = new FileInputStream(lblImagen.getText());
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						Ingresos ingresos = new Ingresos();
						ingresos.insertarProductoAccess(inAccessDB.getText(),inCodigo.getText(), inMarca.getText(), inDesc.getText(), Integer.valueOf(inContenido.getText()),Integer.valueOf(inPiezas.getText()) , checkGranel.isSelected(),Double.valueOf(inPrecio.getText()), file);
						cargarDatosIniciales();
						lblImagen.removeMouseListener(buscaImagenI);
						JOptionPane.showMessageDialog(Almacen.this, "Datos Guardados correctamente");
						
					}else {
						JOptionPane.showMessageDialog(Almacen.this, "Necesitas rellenar los todos los campos: Campos faltantes: " + i);
						
					}
			}
			
			
		}else if(comboBoxTipoConector.getSelectedIndex()==1) {
			//mysql
			if(editar.isEnabled()) {
				//editar
				Ingresos ingresos = new Ingresos();
				String datos[] = new String[8];
				datos[0] = inCodigo.getText(); 
				datos[1] = inMarca.getText();
				datos[2] = inDesc.getText();
				datos[3] = inContenido.getText();
				datos[4] = inPiezas.getText();
				datos[5] = String.valueOf(checkGranel.isSelected());
				datos[6] = inPrecio.getText();
				datos[7] = inId.getText();
				ingresos.editarDatosMysql(datos, inIp.getText(), inBaseDatos.getText(), inPuerto.getText(), inUsuario.getText(), inContrasena.getText(), panelPrincipal);
				cargarDatosIniciales();
				limpiarCamposIngresos();
				bloquearTodoIngresos();
			}else {
				//guardar
				int i =	acciones.comprobarCamposIngresos(inCodigo.getText(), inMarca.getText(), inDesc.getText(), inContenido.getText(), inPiezas.getText(), inPrecio.getText(), lblImagen.getText());
				if(i==0) {
				String datos[] = new String[7];
				datos[0]=inCodigo.getText();
				datos[1]=inMarca.getText();
				datos[2]=inDesc.getText();
				datos[3]=inContenido.getText();
				datos[4]=inPiezas.getText();
				datos[5]=String.valueOf(checkGranel.isSelected());
				datos[6]=inPrecio.getText();
				FileInputStream file = null;
				try {
					file = new FileInputStream(lblImagen.getText());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Ingresos a = new Ingresos();
				a.guardarDatosMysql(datos, file, inIp.getText(), inBaseDatos.getText(), inPuerto.getText(), inUsuario.getText(), inContrasena.getText(), panelPrincipal);
				
				}else {
					System.out.println("rellena todos los campos falntantes: " + i);
				}
				cargarDatosIniciales();
				limpiarCamposIngresos();
				bloquearTodoIngresos();
				lblImagen.setText("");
			}
		}
	}
	
	public void buscarImagenIngresos() {
		JFileChooser f = new JFileChooser();
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imagen jpg", "jpg");
		f.setFileFilter(filtro);
		
		
		int i = f.showOpenDialog(panelIngresos);
		if(i==0) {
			lblImagen.setText(f.getSelectedFile().getAbsolutePath());
		}else {
			lblImagen.setText("Click para buscar Imagen");
		}
	}
	
	public void buscarIngresos() {
		
		if(comboBoxTipoConector.getSelectedIndex()==0) {
			Ingresos ingresos = new Ingresos();
			if(inId.getText().isBlank()||inId.getText().isEmpty()) {
				JOptionPane.showMessageDialog(Almacen.this, "Necesitas ingresar informacion para realizar la consulta: No se ingresaron datos para buscar");
			}else {
				Image imagen =ingresos.buscarImagenIngresosAccess(Integer.valueOf(inId.getText()),inAccessDB.getText());
				if(imagen!=null) {
				lblImagen.setIcon(new ImageIcon(imagen));
				lblImagen.setText("");
				}else {
					JOptionPane.showMessageDialog(Almacen.this, "No se devolvio imagen del producto: Imagen nula");
				lblImagen.setIcon(null);
				
				}
				
			String datos[]=ingresos.buscarDatosIngresosAccess(Integer.valueOf(inId.getText()), inAccessDB.getText());
			inId.setText(datos[0]);
			inCodigo.setText(datos[1]);
			inMarca.setText(datos[2]);
			inDesc.setText(datos[3]);
			inContenido.setText(datos[4]);
			inPiezas.setText(datos[5]);
			checkGranel.setSelected(Boolean.valueOf(datos[6]));
			inPrecio.setText(datos[7]);
clickBuscarIngresos();
			}
			ingresos=null;
		}else if(comboBoxTipoConector.getSelectedIndex()==1) {
			//mysql
			Ingresos ingresos = new Ingresos();
			if(inId.getText().isBlank()||inId.getText().isEmpty()) {
				JOptionPane.showMessageDialog(Almacen.this, "Necesitas ingresar informacion para realizar la consulta: No se ingresaron datos para buscar");
			}else {
				Acciones a = new Acciones();
				Image imagen = a.obtenerImagenStockMysql(inIp.getText(), inBaseDatos.getText(), inPuerto.getText(), inUsuario.getText(), inContrasena.getText(),Integer.valueOf(inId.getText()), panelPrincipal);
				if(imagen!=null) {
				lblImagen.setIcon(new ImageIcon(imagen));
				lblImagen.setText("");
				}else {
					JOptionPane.showMessageDialog(Almacen.this, "No se devolvio imagen del producto: Imagen nula");
					lblImagen.setIcon(null);
					lblImagen.setText("Click para buscar Imagen");
				}
				
			String datos[]=ingresos.buscarIngresosMysql(inIp.getText(), inBaseDatos.getText(), inPuerto.getText(), inUsuario.getText(), inContrasena.getText(),inId.getText(), panelPrincipal);
			inId.setText(datos[0]);
			inCodigo.setText(datos[1]);
			inMarca.setText(datos[2]);
			inDesc.setText(datos[3]);
			inContenido.setText(datos[4]);
			inPiezas.setText(datos[5]);
			checkGranel.setSelected(Boolean.valueOf(datos[6]));
			inPrecio.setText(datos[7]);
clickBuscarIngresos();
			}
			ingresos=null;
			
			
		}
	}
	
	
	
	//****************************actualiza la imagen del producto, primero tiene que buscar un producto con el id interno
	//ya que valida el campo del textfield de codigo para poder hacer la actualizacion
	
	public void actualizarImagenIngresos() {
		
		String code = inCodigo.getText();
		if(code.isBlank()||code.isEmpty()) {
			JOptionPane.showMessageDialog(Almacen.this, "Ingresa un id interno para buscar un producto: Ingresa un id para actualizar la imagen de este");
		}else{
			if(inId.getText().isBlank()||inId.getText().isEmpty()||lblImagen.getText().equals("Click para buscar Imagen")) {
				JOptionPane.showMessageDialog(Almacen.this, "ES necesario completar todos los campos requeridos");
			}else {
				System.out.println("haciendo la insercion de la actualizacion de imagen");
				//se valida que el label de la imagen contenga la ruta de la imagen para realizar la insercion
				if(!lblImagen.getText().isBlank()||!lblImagen.getText().isEmpty()) {
					JOptionPane.showMessageDialog(Almacen.this, "no se encuentra ruta para crear binarios: Da click en \"Click para buscar Imagen\"");
					
				try {
				FileInputStream file = new FileInputStream(lblImagen.getText());
				
				Ingresos ingresos = new Ingresos();
				if(comboBoxTipoConector.getSelectedIndex()==0){
					//access
				ingresos.actualizarImagenAccess(inAccessDB.getText(), file, Integer.valueOf(inId.getText()));
				}else if(comboBoxTipoConector.getSelectedIndex()==1){
					//mysql
					ingresos.actualizarImagenMysql(file, inId.getText(), inIp.getText(), inBaseDatos.getText(), inPuerto.getText(), inUsuario.getText(), inContrasena.getText(), panelPrincipal);
					
				}
				file.close();
				limpiarCamposIngresos();
				bloquearTodoIngresos();
				lblImagen.setText("Click para buscar Imagen");
				lblImagen.removeMouseListener(buscaImagenI);
				
				
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
				}
		}
	}
	
	
	public void clickEditar() {
		activarTodoIngresos();
		lblImagen.addMouseListener(buscaImagenI);
		
	}
	
	public void borrarIngresos() {
		if(inId.getText().isBlank()||inId.getText().isEmpty()) {
			JOptionPane.showMessageDialog(Almacen.this, "No hay informacion para eliminar: Busca un producot con id interno");
		}else {
			Ingresos ingresos = new Ingresos();
			ingresos.eliminarIngresosAccess(inAccessDB.getText(), Integer.valueOf(inId.getText()));
		}
	}
	
	
	//************************************fin de metodos panel infresos***************************************//
	
	//************************************metodos y acciones del panel egresos*******************************//
	
	public void eliminarSalidaProducto() {
		
		String codigo = JOptionPane.showInputDialog(Almacen.this, "Ingrese codigo de producto a eliminar");
		System.out.println("codigo a eliminar: " + codigo);
		int seEncontro = 0;
		
		DefaultTableModel model = (DefaultTableModel) tablaEgresos.getModel();
		//menos 1 porque la ultima fila solo se usa para mostyrar el total;
		String datos[] = new String[model.getRowCount()];
		
		//asigna los valores de la fila y columna al array
		for(int i =0;i<datos.length;i++) {
			
			datos[i]=model.getValueAt(i,0 ).toString();
			
		}
		
		
		
		//busca coincidencias del arraya con el codigo ingresado en la variable codigo
		for(int j =0;j<datos.length;j++) {
			System.out.println("buscando datos: " + datos[j]);
			boolean si = datos[j].equals(codigo);
			if(si) {
				String datos2[] = new String[model.getRowCount()];
				
				/*for(int h =0;h<datos2.length;h++) {
					datos2[h]= model.getValueAt(h, 3).toString();
					System.out.println("eliminar dentro del for h: " + datos2[h]);
				}*/
				datos2[0]= model.getValueAt(j, 3).toString();
				double d = Double.parseDouble(datos2[0]);
				if(d>1) {
					seEncontro=1;
					model.setValueAt((d-1), j, 3);
					multiplicarCantidadPorPrecioEgresos();
					sumaTotalIngresos();
				}else {
				seEncontro=1;
				
				model.removeRow(j);
				multiplicarCantidadPorPrecioEgresos();
				sumaTotalIngresos();
				JOptionPane.showMessageDialog(Almacen.this, "Se elimino la fila: "+j+" con el codigo: " + codigo);
			}
				}
		}
		
		//lanza mensaje en caso de no encontrar nada
		if(seEncontro==0) {
			JOptionPane.showMessageDialog(Almacen.this, "No se encontro ninguna coincidencia con el codigo ingresado: No se registro el producto para venta");
		}
		
		for(String ele : datos) {
			System.out.println(ele);
		}
		
	}
	
	
	
	
	public void anadirProductosEgresos() {
		
		Egresos eg = new Egresos();
		String datos[]=null;
			if(comboBoxTipoConector.getSelectedIndex()==0) {
				datos=eg.buscarDatosEgresosAccess(textField.getText().toString(), inAccessDB.getText());
				}else if(comboBoxTipoConector.getSelectedIndex()==1) {
				datos = eg.buscarDatosEgresosMysql(textField.getText(), inIp.getText(), inBaseDatos.getText(), inPuerto.getText(), inUsuario.getText(), inContrasena.getText(), panelPrincipal);
				}
			
		
		int primeraCoincidencia =0, ii=0;
		
		
		//comparamos el array por si en caso es nulo no agregamos nada a la tabla
		if(datos[0]==null||datos==null) {
			
			JOptionPane.showMessageDialog(Almacen.this, "No se encontro el codigo: El producto no tiene inventario o no existe codigo: "+datos[0] );
		
		}else {
			
			//añadimos el primer registro que devuelve la base d edatos
			tablaEgresosModelo.addRow(datos);
			
			
			
			//obtenemos el modelo de la tabla para comparar el codigo de los productos y al 
			//encontrar coincidencias le sumamos 1 mas a la cantidad actual
			
			DefaultTableModel model = (DefaultTableModel) tablaEgresos.getModel();
			
			int  filasEgresos = model.getRowCount();
			System.out.println("numero de filas: " + filasEgresos);
			 
			 
			 String datos2[] = new String[filasEgresos];
			
			 
			 for(int i = 0; i<filasEgresos;i++) {
				 datos2[i]=model.getValueAt(i, 0).toString();
				// System.out.println("datos almacenas en datos2: " + datos2[i].toString());
			 }
			 
			 
			if(filasEgresos==1) {
				System.out.println("primer fila");
				 multiplicarCantidadPorPrecioEgresos() ;
			}else {
				
				 
			 for(int j =0;j<datos2.length;j++) {
				 System.out.println("valor de j: " + j);
				 boolean s = datos2[j].toString().equals(datos[0].toString());
				 System.out.println("coincidencias: " + s+" datos2 "+j+" " +datos2[j].toString()+"  " +datos[0].toString());
				
				 if(s) {
					 primeraCoincidencia=primeraCoincidencia+1;
					 
					 if(primeraCoincidencia<=1) {
					 ii=j;
					 }
				 }
				
				
				 if(primeraCoincidencia>1) {
						
						double p = Double.parseDouble((String) model.getValueAt(ii, 3));
						double pp = 1.0;
						double ppp = p+pp;
						
						model.setValueAt(String.valueOf( ppp), ii, 3);
						model.removeRow(j);
						
						
					
						
						
					}
			
			 }
			
			
			 
			 multiplicarCantidadPorPrecioEgresos() ;
		
			 
			 }
			
			
			
		}
		
		
		
		textField.setText("");
		
		
		
		
	}
	
	public void multiplicarCantidadPorPrecioEgresos() {
		
		
		DefaultTableModel model = (DefaultTableModel) tablaEgresos.getModel();
	
		
		int totalFilas = model.getRowCount();
		System.out.println("total de filas en sumar: " + totalFilas);
		String datos[] = new String[totalFilas];
		
		
		
		System.out.println("total de filas en sumar: " + totalFilas);
		
		//se recopila las canitdades de los productos ingresados en la tabla de egresos
		for(int i =0;i<totalFilas;i++) {
			
			datos[i]=model.getValueAt(i, 3).toString();
			System.out.println("valor obtenido: "+ datos[i].toString());
			
		}
		
   
		
		
		//obtenemos el precio de cada fila de los productos
		String datos2[] = new String[totalFilas];
		for(int j =0; j<totalFilas;j++) {
			datos2[j]=model.getValueAt(j, 4).toString();
			System.out.println("datos obtenidos en precios: " + datos2[j]);
			
		}
		
		
		
		//multiplicamos la cantida de productos por el precio
		for(int h = 0;h<totalFilas;h++) {
			double multiplicacion =Double.parseDouble((String)datos[h]) * Double.parseDouble((String) datos2[h]);
			System.out.println("la multiplicacion de egresos: " + multiplicacion);
			//mandamos el resultado a la tabla
			model.setValueAt(multiplicacion, h, 5);
			
		}
		
		
		sumaTotalIngresos();
		
	}
	
	
	public void sumaTotalIngresos() {
		
		DefaultTableModel model = (DefaultTableModel) tablaEgresos.getModel();
		int filas = model.getRowCount();
		Double datos[] = new Double[filas];
		
		for(int i = 0; i<filas;i++) {
			datos[i]= Double.parseDouble( model.getValueAt(i, 5).toString());
			
		}
		double suma = 0;
		for(int j = 0; j <filas;j++) {
			  
			suma = (suma + datos[j]);
			System.out.println("suma total: " + suma);
		}
		
		totalEgresos.setText("$" + String.valueOf( suma));
		
	}
	
	public void cancelarSalida() {
		//cancela y elemina todos los productos de la tabla egresos del mismo panel
		
		int sel = JOptionPane.showConfirmDialog(Almacen.this, "Desea cancelar el pedido actual","Cancelar Salida",JOptionPane.INFORMATION_MESSAGE);
		
		if(sel==0) {
			DefaultTableModel model = (DefaultTableModel) tablaEgresos.getModel();
			model.setRowCount(0);
			totalEgresos.setText("");
		}
		
	}
	
	public void registrarSalidaEgresosAccess() {
		
		/***
		 * se genera la actualizacion de los stock en la base de datos y se limpia la tanla del panel egresos
		 * **/
		
		DefaultTableModel model = (DefaultTableModel) tablaEgresos.getModel();
		int filas = model.getRowCount();
		
		String codigo[] = new String[filas];
		String cantidad[] = new String[filas];
		
		//recogemos todos los codigo de los prodcutos de la tabla egresos
		for(int i = 0;i<filas;i++) {
			codigo[i]=model.getValueAt(i, 0).toString();
			System.out.println("codigo registrar salida: " + codigo[i]);
		}
		
		//recogemos todos las cantidades de productos de la tabla egresos
		for(int i = 0;i<filas;i++) {
			cantidad[i]=model.getValueAt(i, 3).toString();
			System.out.println("cantidad registrar salida: " + cantidad[i]);
		}
		
		Egresos e = new Egresos();
		for(int i = 0;i<filas;i++) {
			if(comboBoxTipoConector.getSelectedIndex()==0) {
				//access
		e.updateSalidaAccess(inAccessDB.getText(), codigo[i], Double.parseDouble(cantidad[i]));
		cargarDatosIniciales();
			}else if(comboBoxTipoConector.getSelectedIndex()==1) {
				//mysql
				e.updateSalidaMysql(inIp.getText(), inBaseDatos.getText(), inPuerto.getText(), inUsuario.getText(), inContrasena.getText(), panelPrincipal, codigo[i],Double.valueOf( cantidad[i]));
				cargarDatosIniciales();
			}
			
			}
		
		model.setRowCount(0);
		JOptionPane.showMessageDialog(Almacen.this, "Salida Registrada con exito","Salida Exitosa",JOptionPane.INFORMATION_MESSAGE);
		model = null;
		
	}
	
	
	//********************************************fin metodos de panel egresos***********************************//
	
}