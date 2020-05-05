

package uniandes.isis2304.parranderos.interfazApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.jdo.Query;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.parranderos.negocio.Alohandes;
import uniandes.isis2304.parranderos.negocio.Cliente;
import uniandes.isis2304.parranderos.negocio.Habitacion;
import uniandes.isis2304.parranderos.negocio.Hostal;
import uniandes.isis2304.parranderos.negocio.Hotel;
import uniandes.isis2304.parranderos.negocio.Reserva;
import uniandes.isis2304.parranderos.negocio.Proveedor;
import uniandes.isis2304.parranderos.negocio.Propietario;
import uniandes.isis2304.parranderos.negocio.Empresa;
import uniandes.isis2304.parranderos.negocio.Alojamiento;
import uniandes.isis2304.parranderos.negocio.AptoTemporada;
import uniandes.isis2304.parranderos.negocio.VOAptoTemporada;
import uniandes.isis2304.parranderos.negocio.VOHabitacion;
import uniandes.isis2304.parranderos.negocio.VOPropietario;
import uniandes.isis2304.parranderos.negocio.VOHostal;
import uniandes.isis2304.parranderos.negocio.VOHotel;
import uniandes.isis2304.parranderos.negocio.VOEmpresa;
import uniandes.isis2304.parranderos.negocio.VOProveedor;
import uniandes.isis2304.parranderos.negocio.VOAlojamiento;
import uniandes.isis2304.parranderos.negocio.VOCliente;
import uniandes.isis2304.parranderos.negocio.ViviendaUniversitaria;

/**
 * Clase principal de la interfaz
 * @author Antonio Aspite y Julio César Ruiz
 */
@SuppressWarnings("serial")

public class InterfazAlohandesApp extends JFrame implements ActionListener
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(InterfazAlohandesApp.class.getName());
	
	/**
	 * Ruta al archivo de configuración de la interfaz
	 */
	private static final String CONFIG_INTERFAZ = "./src/main/resources/config/interfaceConfigApp.json"; 
	
	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos
	 */
	private static final String CONFIG_TABLAS = "./src/main/resources/config/TablasBD_A.json"; 
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
    /**
     * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
     */
    private JsonObject tableConfig;
    
    /**
     * Asociación a la clase principal del negocio.
     */
    private Alohandes alohandes;
    
	/* ****************************************************************
	 * 			Atributos de interfaz
	 *****************************************************************/
    /**
     * Objeto JSON con la configuración de interfaz de la app.
     */
    private JsonObject guiConfig;
    
    /**
     * Panel de despliegue de interacción para los requerimientos
     */
    private PanelDatos panelDatos;
    
    /**
     * Menú de la aplicación
     */
    private JMenuBar menuBar;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
    /**
     * Construye la ventana principal de la aplicación. <br>
     * <b>post:</b> Todos los componentes de la interfaz fueron inicializados.
     */
    public InterfazAlohandesApp( )
    {
        // Carga la configuración de la interfaz desde un archivo JSON
        guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ);
        
        // Configura la apariencia del frame que contiene la interfaz gráfica
        configurarFrame ( );
        if (guiConfig != null) 	   
        {
     	   crearMenu( guiConfig.getAsJsonArray("menuBar") );
        }
        
        tableConfig = openConfig ("Tablas BD", CONFIG_TABLAS);
        alohandes = new Alohandes (tableConfig);
        
        panelDatos = new PanelDatos ( );

        setLayout (new BorderLayout());
        add (new JLabel (new ImageIcon ("./src/main/resources/config/logo.png")), BorderLayout.NORTH );          
        add( panelDatos, BorderLayout.CENTER );        
    }
    
	/* ****************************************************************
	 * 			Métodos de configuración de la interfaz
	 *****************************************************************/
    /**
     * Lee datos de configuración para la aplicació, a partir de un archivo JSON o con valores por defecto si hay errores.
     * @param tipo - El tipo de configuración deseada
     * @param archConfig - Archivo Json que contiene la configuración
     * @return Un objeto JSON con la configuración del tipo especificado
     * 			NULL si hay un error en el archivo.
     */
    private JsonObject openConfig (String tipo, String archConfig)
    {
    	JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontró un archivo de configuración válido: " + tipo);
		} 
		catch (Exception e)
		{
//			e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de interfaz válido: " + tipo, "Alohandes App", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }
    
    /**
     * Método para configurar el frame principal de la aplicación
     */
    private void configurarFrame(  )
    {
    	int alto = 0;
    	int ancho = 0;
    	String titulo = "";	
    	
    	if ( guiConfig == null )
    	{
    		log.info ( "Se aplica configuración por defecto" );			
			titulo = "Alohandes APP Default";
			alto = 300;
			ancho = 500;
    	}
    	else
    	{
			log.info ( "Se aplica configuración indicada en el archivo de configuración" );
    		titulo = guiConfig.get("title").getAsString();
			alto= guiConfig.get("frameH").getAsInt();
			ancho = guiConfig.get("frameW").getAsInt();
    	}
    	
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLocation (50,50);
        setResizable( true );
        setBackground( Color.WHITE );

        setTitle( titulo );
		setSize ( ancho, alto);        
    }

    /**
     * Método para crear el menú de la aplicación con base em el objeto JSON leído
     * Genera una barra de menú y los menús con sus respectivas opciones
     * @param jsonMenu - Arreglo Json con los menùs deseados
     */
    private void crearMenu(  JsonArray jsonMenu )
    {    	
    	// Creación de la barra de menús
        menuBar = new JMenuBar();       
        for (JsonElement men : jsonMenu)
        {
        	// Creación de cada uno de los menús
        	JsonObject jom = men.getAsJsonObject(); 

        	String menuTitle = jom.get("menuTitle").getAsString();        	
        	JsonArray opciones = jom.getAsJsonArray("options");
        	
        	JMenu menu = new JMenu( menuTitle);
        	
        	for (JsonElement op : opciones)
        	{       	
        		// Creación de cada una de las opciones del menú
        		JsonObject jo = op.getAsJsonObject(); 
        		String lb =   jo.get("label").getAsString();
        		String event = jo.get("event").getAsString();
        		
        		JMenuItem mItem = new JMenuItem( lb );
        		mItem.addActionListener( this );
        		mItem.setActionCommand(event);
        		
        		menu.add(mItem);
        	}       
        	menuBar.add( menu );
        }        
        setJMenuBar ( menuBar );	
    }
    
	/* ****************************************************************
	 * 			CRUD de Usuario
	 *****************************************************************/
    /**
     * Adiciona un tipo de usuario con la información dada por el usuario
     * Se crea una nueva tupla de usuario en la base de datos, si un tipo de usuario con esa cedula no existía
     */
    public void adicionarCliente( )
    {
    	try 
    	{
			String documento = (JOptionPane.showInputDialog (this, "Cedula", "Adicionar cliente", JOptionPane.QUESTION_MESSAGE));
			String nombre = JOptionPane.showInputDialog (this, "Nombre", "Adicionar cliente", JOptionPane.QUESTION_MESSAGE);
			String genero = JOptionPane.showInputDialog (this, "Tipo", "Adicionar cliente", JOptionPane.QUESTION_MESSAGE);
			String email = JOptionPane.showInputDialog (this, "email", "Adicionar cliente", JOptionPane.QUESTION_MESSAGE);
			String vinculacion= JOptionPane.showInputDialog (this, "vinculacion", "Adicionar cliente", JOptionPane.QUESTION_MESSAGE);
			String contrasena = JOptionPane.showInputDialog (this, "contraseña", "Adicionar cliente", JOptionPane.QUESTION_MESSAGE);
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			Date fechaNacimiento = formato.parse(JOptionPane.showInputDialog (this, "fecha Nacimiento", "Adicionar cliente", JOptionPane.QUESTION_MESSAGE));
			String ciudad= JOptionPane.showInputDialog (this, "ciudad", "Adicionar Cliente", JOptionPane.QUESTION_MESSAGE);
			long telefono= Long.parseLong(JOptionPane.showInputDialog (this, "telefono", "Adicionar cliente", JOptionPane.QUESTION_MESSAGE));


    		if (nombre != null &!nombre.equals("")&fechaNacimiento!= null& vinculacion!= null)
    		{
        		VOCliente tb = (VOCliente) alohandes.adicionarCliente(documento, nombre, ciudad, contrasena, email, telefono, genero, vinculacion, fechaNacimiento);
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear un cliente con nombre: " + nombre);
        		}
        		String resultado = "En adicionarCliente\n\n";
        		resultado += "Cliente adicionado exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el cliente");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

    /**
     * Consulta en la base de datos los tipos de cliente existentes y los muestra en el panel de datos de la aplicación
     */
    public void listarCliente( )
    {
    	try 
    	{
			List <Cliente> lista = alohandes.darClientes();

			String resultado = "En listarCliente";
			resultado +=  "\n" + listarClientes (lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

    /**
     * Borra de la base de datos el tipo de bebida con el identificador dado po el cliente
     * Cuando dicho tipo de bebida no existe, se indica que se borraron 0 registros de la base de datos
     */
    public void eliminarClientePorId( )
    {
    	try 
    	{
    		String idTipoStr = JOptionPane.showInputDialog (this, "Id del cliente?", "Borrar cliente por Id", JOptionPane.QUESTION_MESSAGE);
    		if (idTipoStr != null)
    		{
    			long idTipo = Long.valueOf (idTipoStr);
    			long tbEliminados = alohandes.eliminarClientePorId(idTipo);

    			String resultado = "En eliminar Cliente\n\n";
    			resultado += tbEliminados + " clientes eliminados\n";
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el cliente");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

  
    
    
    /**
     * Busca el cliente con el id indicado por el cliente y lo muestra en el panel de datos
     */
    public void buscarClientePorId( )
    {
    	try 
    	{
    		long Id = Integer.parseInt(JOptionPane.showInputDialog (this, "Id del cliente?", "Buscar cliente por Id", JOptionPane.QUESTION_MESSAGE));
    		if (Id > 0)
    		{
    			VOCliente Cliente = (VOCliente) alohandes.darClientePorId(Id);
    			String resultado = "En buscar cliente por nombre\n\n";
    			if (Cliente != null)
    			{
        			resultado += "El cliente es: " + Cliente;
    			}
    			else
    			{
        			resultado += "Un cliente con Id: " + Id + " NO EXISTE\n";
    			}
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el cliente");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
  
    
    //
    //
    //
    //
 
    //
    //
    //
    //
    /* ****************************************************************
	 * 			CRUD de AptoSemestre
	 *****************************************************************/
    /**
     * Adiciona un tipo de cliente con la información dada por el cliente
     * Se crea una nueva tupla de cliente en la base de datos, si un tipo de cliente con esa cedula no existía
  
    //
    //
    //
    //
    //
    
    //
    /* ****************************************************************
	 * 			CRUD de AptoTemporada
	 *****************************************************************/
    /**
     * Adiciona un tipo de cliente con la información dada por el cliente
     * Se crea una nueva tupla de cliente en la base de datos, si un tipo de cliente con esa cedula no existía
     */
    public void adicionarAptoTemporada( )
    {
    	try 
    	{
    		long idAlojamiento = Long.parseLong(JOptionPane.showInputDialog (this, "idAlojamiento", "Adicionar aptoTemporada", JOptionPane.QUESTION_MESSAGE));
			long idProveedor = Long.parseLong(JOptionPane.showInputDialog (this, "idProveedor", "Adicionar aptoTemporada", JOptionPane.QUESTION_MESSAGE));
			String menaje = JOptionPane.showInputDialog (this, "menaje", "Adicionar aptoTemporada", JOptionPane.QUESTION_MESSAGE);
			Integer precio = Integer.parseInt(JOptionPane.showInputDialog (this, "precio mes", "Adicionar aptoTemporada", JOptionPane.QUESTION_MESSAGE));
			Integer habitaciones = Integer.parseInt(JOptionPane.showInputDialog (this, "cantidad habitaciones", "Adicionar aptoTemporada", JOptionPane.QUESTION_MESSAGE));


    		if (idAlojamiento > 0 &idProveedor> 0&habitaciones> 0)
    		{
        		VOAptoTemporada tb = (VOAptoTemporada) alohandes.adicionarAptoTemporada(idAlojamiento, idProveedor, precio, menaje, habitaciones);
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear un aptoTemporada con id: " + idAlojamiento);
        		}
        		String resultado = "En adicionarApartamento\n\n";
        		resultado += "aptoTemporada adicionado exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el aptoTemporada");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

    /**
     * Consulta en la base de datos los tipos de cliente existentes y los muestra en el panel de datos de la aplicación
     */
    public void listaApartamentosTemporada()
    {
    	try 
    	{
			List <AptoTemporada> lista = alohandes.darAptoTemporada();

			String resultado = "En listarCliente";
			resultado +=  "\n" + listarAptoTemporada (lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

    /**
     * Borra de la base de datos el tipo de bebida con el identificador dado po el cliente
     * Cuando dicho tipo de bebida no existe, se indica que se borraron 0 registros de la base de datos
     */
    public void eliminarApartamentoTemporadaPorId( )
    {
    	try 
    	{
    		String idTipoStr = JOptionPane.showInputDialog (this, "Id del aptoTemporada?", "Borrar aptoTemporada por Id", JOptionPane.QUESTION_MESSAGE);
    		if (idTipoStr != null)
    		{
    			long idTipo = Long.valueOf (idTipoStr);
    			long tbEliminados = alohandes.eliminarAptoTemporadaPorId(idTipo);

    			String resultado = "En eliminar aptoTemporada\n\n";
    			resultado += tbEliminados + " aptoTemporadas eliminados\n";
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el cliente");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    
    /**
     * Busca el cliente con el id indicado por el cliente y lo muestra en el panel de datos
     */
    public void buscarApartamentoTemporadaPorId( )
    {
    	try 
    	{
    		long Id = Integer.parseInt(JOptionPane.showInputDialog (this, "Id del aptoTemporada?", "Buscar aptoTemporada por Id", JOptionPane.QUESTION_MESSAGE));
    		if (Id > 0)
    		{
    			VOAptoTemporada aptoTemporada = (VOAptoTemporada) alohandes.darAptoTemporadaPorId(Id);
    			String resultado = "En buscar AptoTemporada por id\n\n";
    			if (aptoTemporada != null)
    			{
        			resultado += "El AptoSemestre es: " + aptoTemporada;
    			}
    			else
    			{
        			resultado += "Un aptoTemporada con Id: " + Id + " NO EXISTE\n";
    			}
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el cliente");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    //
    //
    //
    
    
    
    
    
    
  
    
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    /* ****************************************************************
	 * 			CRUD de Habitacion
	 *****************************************************************/
    /**
     * Adiciona un tipo de usuario con la información dada por el usuario
     * Se crea una nueva tupla de usuario en la base de datos, si un tipo de usuario con esa cedula no existía
     */
    public void adicionarHabitacion( )
    {
    	try 
    	{
			long id= Long.parseLong(JOptionPane.showInputDialog (this, "id", "Adicionar Habitacion", JOptionPane.QUESTION_MESSAGE));
			long idHotel= Long.parseLong(JOptionPane.showInputDialog (this, "idHotel", "Adicionar Habitacion", JOptionPane.QUESTION_MESSAGE));
			long idAlojamiento = Long.parseLong(JOptionPane.showInputDialog (this, " idAlojamiento", "Adicionar Habitacion", JOptionPane.QUESTION_MESSAGE));
			Integer precioNoche = Integer.parseInt(JOptionPane.showInputDialog (this, "precio", "Adicionar Habitacion", JOptionPane.QUESTION_MESSAGE));
			Integer capacidad = Integer.parseInt(JOptionPane.showInputDialog (this, "capacidad", "Adicionar Habitacion", JOptionPane.QUESTION_MESSAGE));
			String tipoOferta = (JOptionPane.showInputDialog (this, "tipo oferta", "Adicionar Habitacion", JOptionPane.QUESTION_MESSAGE));
			String horario = (JOptionPane.showInputDialog (this, "horario", "Adicionar Habitacion", JOptionPane.QUESTION_MESSAGE));

			
    		if (id > 0 &idAlojamiento > 0&precioNoche> 0&capacidad> 0)
    		{
    			VOHabitacion tb = (VOHabitacion) alohandes.adicionarHabitacion(horario, horario, precioNoche, capacidad, tipoOferta, idHotel, idAlojamiento);
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear un Habitacion con nit: " + idAlojamiento);
        		}
        		String resultado = "En adicionarHabitacion\n\n";
        		resultado += "Habitacion adicionado exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

    /**
     * Consulta en la base de datos los tipos de usuario existentes y los muestra en el panel de datos de la aplicación
     */
    public void listarHabitacion( )
    {
    	try 
    	{
			List <Habitacion> lista = alohandes.darHabitacion();

			String resultado = "En listarHabitacion";
			resultado +=  "\n" + listarHabitacion(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

    /**
     * Borra de la base de datos el tipo de bebida con el identificador dado po el usuario
     * Cuando dicho tipo de bebida no existe, se indica que se borraron 0 registros de la base de datos
     */
    public void eliminaHabitacionPorId( )
    {
    	try 
    	{
    		String idTipoStr = JOptionPane.showInputDialog (this, "Id de la habitacion?", "Borrar habitacion por Id", JOptionPane.QUESTION_MESSAGE);
    		if (idTipoStr != null)
    		{
    			long idTipo = Long.valueOf (idTipoStr);
    			long tbEliminados = alohandes.eliminarHabitacionPorId(idTipo);

    			String resultado = "En eliminar habitacion\n\n";
    			resultado += tbEliminados + " habitaciones eliminados\n";
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    
    /**
     * Busca el usuario con el id indicado por el usuario y lo muestra en el panel de datos
     */
    public void buscarHabitacionPorId( )
    {
    	try 
    	{
    		long Id = Integer.parseInt(JOptionPane.showInputDialog (this, "Id de la habitacion?", "Buscar habitacion por Id", JOptionPane.QUESTION_MESSAGE));
    		if (Id > 0)
    		{
    			VOHabitacion habitacion = (VOHabitacion) alohandes.darHabitacionPorId(Id);
    			String resultado = "En buscar habitacion por Id\n\n";
    			if (habitacion != null)
    			{
        			resultado += "la habitacion es: " + habitacion;
    			}
    			else
    			{
        			resultado += "Un habitacion con Id: " + Id + " NO EXISTE\n";
    			}
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
   
       //
       //
       //
       //
       //
       //
       //
       //
       //
       //
       //
       //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    /* ****************************************************************
	 * 			CRUD de Hostal
	 *****************************************************************/
    /**
     * Adiciona un tipo de usuario con la información dada por el usuario
     * Se crea una nueva tupla de usuario en la base de datos, si un tipo de usuario con esa cedula no existía
     */
    public void adicionarHostal( )
    {
    	try 
    	{
			long idHostal = Long.parseLong(JOptionPane.showInputDialog (this, "nit", "Adicionar Hostal", JOptionPane.QUESTION_MESSAGE));

			String nombre = (JOptionPane.showInputDialog (this, "nombre", "Adicionar Hostal", JOptionPane.QUESTION_MESSAGE));
			String horario = (JOptionPane.showInputDialog (this, "horario", "Adicionar Hostal", JOptionPane.QUESTION_MESSAGE));
			String email = (JOptionPane.showInputDialog (this, "email", "Adicionar Hostal", JOptionPane.QUESTION_MESSAGE));
			String ubicacion = (JOptionPane.showInputDialog (this, "ubicacion", "Adicionar Hostal", JOptionPane.QUESTION_MESSAGE));
			long idProveedor = Long.parseLong(JOptionPane.showInputDialog (this, "idProveedor", "Adicionar Hostal", JOptionPane.QUESTION_MESSAGE));
			int telefono = (int) Long.parseLong(JOptionPane.showInputDialog (this, "telefono", "Adicionar Hostal", JOptionPane.QUESTION_MESSAGE));

    		if (idHostal >0 & nombre!= null &!nombre.equals("") & !horario.equals("")& idProveedor>0)
    		{
        		VOHostal tb = (VOHostal) alohandes.adicionarHostal(idHostal, nombre, horario, ubicacion, email, idProveedor, telefono);
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear un Hostal con nit: " + idHostal);
        		}
        		String resultado = "En adicionarHostal\n\n";
        		resultado += "Hostal adicionado exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

    /**
     * Consulta en la base de datos los tipos de usuario existentes y los muestra en el panel de datos de la aplicación
     */
    public void listarHostales( )
    {
    	try 
    	{
			List <Hostal> lista = alohandes.darHostales();

			String resultado = "En listarHostal";
			resultado +=  "\n" + listarHostal(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

    /**
     * Borra de la base de datos el tipo de bebida con el identificador dado po el usuario
     * Cuando dicho tipo de bebida no existe, se indica que se borraron 0 registros de la base de datos
     */
    public void eliminarHostalPorNit( )
    {
    	try 
    	{
    		long idTipoStr = Long.parseLong(JOptionPane.showInputDialog (this, "Nit del hostal?", "Borrar hostal por Nit", JOptionPane.QUESTION_MESSAGE));
    		if (idTipoStr > 0)
    		{
    			long tbEliminados = alohandes.eliminarHostalPorId(idTipoStr);

    			String resultado = "En eliminar Hostal\n\n";
    			resultado += tbEliminados + " Hostales eliminados\n";
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    
    /**
     * Busca el usuario con el id indicado por el usuario y lo muestra en el panel de datos
     */
    public void buscarHostalPorId( )
    {
    	try 
    	{
    		long idTipoStr = Long.parseLong(JOptionPane.showInputDialog (this, "Nit del hostal?", "Borrar hostal por Nit", JOptionPane.QUESTION_MESSAGE));
    		if (idTipoStr >0 )
    		{
    			VOHostal hostal = (VOHostal) alohandes.darHostalesPorId(idTipoStr );
    			String resultado = "En buscar hostal por nit\n\n";
    			if (hostal != null)
    			{
        			resultado += "El hostal es: " + idTipoStr;
    			}
    			else
    			{
        			resultado += "Un hostal con nit: " + idTipoStr + " NO EXISTE\n";
    			}
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    /* ****************************************************************
	 * 			CRUD de Hotel
	 *****************************************************************/
    /**
     * Adiciona un tipo de usuario con la información dada por el usuario
     * Se crea una nueva tupla de usuario en la base de datos, si un tipo de usuario con esa cedula no existía
     */
    public void adicionarHotel( )
    {
    	try 
    	{
    		long nit = Long.parseLong(JOptionPane.showInputDialog (this, "nit", "Adicionar Hotel", JOptionPane.QUESTION_MESSAGE));
			String nombre = (JOptionPane.showInputDialog (this, "nombre", "Adicionar Hotel", JOptionPane.QUESTION_MESSAGE));
			String email = (JOptionPane.showInputDialog (this, "email", "Adicionar Hotel", JOptionPane.QUESTION_MESSAGE));
			String ubicacion = (JOptionPane.showInputDialog (this, "ubicacion", "Adicionar Hotel", JOptionPane.QUESTION_MESSAGE));
			String horario = (JOptionPane.showInputDialog (this, "horario", "Adicionar Hotel", JOptionPane.QUESTION_MESSAGE));

			long idProveedor = Long.parseLong(JOptionPane.showInputDialog (this, "idProveedor", "Adicionar Hotel", JOptionPane.QUESTION_MESSAGE));
			int telefono = (int)Long.parseLong(JOptionPane.showInputDialog (this, "telefono", "Adicionar Hotel", JOptionPane.QUESTION_MESSAGE));

    		if (nit >0 & nombre!= null &!nombre.equals("") & idProveedor>0)
    		{
        		VOHotel tb = (VOHotel) alohandes.adicionarHotel(nit, nombre, ubicacion, email, idProveedor, telefono, horario);
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear un c con nit: " + nit);
        		}
        		String resultado = "En adicionarHotel\n\n";
        		resultado += "Hotel adicionado exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

    /**
     * Consulta en la base de datos los tipos de usuario existentes y los muestra en el panel de datos de la aplicación
     */
    public void listarHotel( )
    {
    	try 
    	{
			List <Hotel> lista = alohandes.darHoteles();

			String resultado = "En listarHotel";
			resultado +=  "\n" + listarHotel(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

    /**
     * Borra de la base de datos el tipo de bebida con el identificador dado po el usuario
     * Cuando dicho tipo de bebida no existe, se indica que se borraron 0 registros de la base de datos
     */
    public void eliminarHotelPorId( )
    {
    	try 
    	{
    		long nit = Long.parseLong(JOptionPane.showInputDialog (this, "Nit del hostal?", "Borrar hostal por Nit", JOptionPane.QUESTION_MESSAGE));
    		if (nit >0)
    		{
    			long tbEliminados = alohandes.eliminarHotelPorId(nit);

    			String resultado = "En eliminar Hotel\n\n";
    			resultado += tbEliminados + " Hotel eliminados\n";
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    /**
     * Busca el usuario con el id indicado por el usuario y lo muestra en el panel de datos
     */
    public void buscarHotelPorId( )
    {
    	try 
    	{
    		long nit = Long.parseLong(JOptionPane.showInputDialog (this, "Nit del hostal?", "Borrar hostal por Nit", JOptionPane.QUESTION_MESSAGE));
    		if (nit>0)
    		{
    			VOHotel hotel = (VOHotel) alohandes.darHotelesPorId(nit);
    			String resultado = "En buscar hotel por nit\n\n";
    			if (hotel != null)
    			{
        			resultado += "El hitel es: " + hotel;
    			}
    			else
    			{
        			resultado += "Un hotel con nit: " + nit + " NO EXISTE\n";
    			}
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    /* ****************************************************************
	 * 			CRUD de Alojamiento
	 *****************************************************************/
    /**
     * Adiciona un tipo de usuario con la información dada por el usuario
     * Se crea una nueva tupla de usuario en la base de datos, si un tipo de usuario con esa cedula no existía
     */
    public void adicionarAlojamiento( )
    {
    	try 
    	{
			long id = Long.parseLong(JOptionPane.showInputDialog (this, "id", "Adicionar Alojamiento", JOptionPane.QUESTION_MESSAGE));
			String nombre = (JOptionPane.showInputDialog (this, "nombre", "Adicionar Alojamiento", JOptionPane.QUESTION_MESSAGE));
			String tipo= (JOptionPane.showInputDialog (this, "costo", "Adicionar Alojamiento", JOptionPane.QUESTION_MESSAGE));
			String servicios = (JOptionPane.showInputDialog (this, "servicios", "Adicionar Alojamiento", JOptionPane.QUESTION_MESSAGE));
			char habilitado = JOptionPane.showInputDialog (this,"está habilitado?", "Adicionar Empresa", JOptionPane.QUESTION_MESSAGE).charAt(0);

			
    		if (id > 0)
    		{
        		VOAlojamiento tb = (VOAlojamiento) alohandes.adicionarAlojamiento(id,habilitado,nombre, tipo, servicios);
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear un alojamiento con nombre: " + id);
        		}
        		String resultado = "En adicionarAlojamiento\n\n";
        		resultado += "Alojamiento adicionado exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

    /**
     * Consulta en la base de datos los tipos de usuario existentes y los muestra en el panel de datos de la aplicación
     */
    public void listarAlojamiento( )
    {
    	try 
    	{
			List<Alojamiento> lista = alohandes.darAlojamientos();

			String resultado = "En listarAlojamiento";
			resultado +=  "\n" + listarAlojamiento (lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

    /**
     * Borra de la base de datos el tipo de bebida con el identificador dado po el usuario
     * Cuando dicho tipo de bebida no existe, se indica que se borraron 0 registros de la base de datos
     */
    public void eliminarAlojamientoPorId( )
    {
    	try 
    	{
    		String idTipoStr = JOptionPane.showInputDialog (this, "Id de la Alojamiento?", "Borrar Alojamiento por Id", JOptionPane.QUESTION_MESSAGE);
    		if (idTipoStr != null)
    		{
    			long idTipo = Long.valueOf (idTipoStr);
    			long tbEliminados = alohandes.eliminarAlojamientoPorId(idTipo);

    			String resultado = "En eliminar Alojamiento\n\n";
    			resultado += tbEliminados + " Alojamientos eliminadas\n";
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    
    /**
     * Busca el usuario con el id indicado por el usuario y lo muestra en el panel de datos
     */
    public void buscarAlojamientoPorId( )
    {
    	try 
    	{
    		long Id = Integer.parseInt(JOptionPane.showInputDialog (this, "Id de la Alojamiento?", "Buscar Alojamiento por Id", JOptionPane.QUESTION_MESSAGE));
    		if (Id > 0)
    		{
    			VOAlojamiento Alojamiento = (VOAlojamiento) alohandes.darAlojamientosPorId(Id);
    			String resultado = "En buscar Alojamiento por id\n\n";
    			if (Alojamiento != null)
    			{
        			resultado += "La Alojamiento es: " + Alojamiento;
    			}
    			else
    			{
        			resultado += "Una Alojamiento con Id: " + Id + " NO EXISTE\n";
    			}
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
//    public void buscarAlojamientoPorNombre( )
//    {
//    	try 
//    	{
//    		String nombreTb = JOptionPane.showInputDialog (this, "Nombre del propietario?", "Buscar alojamiento por nombre", JOptionPane.QUESTION_MESSAGE);
//    		if (nombreTb != null)
//    		{
//    			VOAlojamiento alojamiento = (VOAlojamiento) alohandes.darAlojamientosPorNombre (nombreTb);
//    			String resultado = "En buscar alojamiento por nombre\n\n";
//    			if (alojamiento != null)
//    			{
//        			resultado += "El alojamiento es: " + alojamiento;
//    			}
//    			else
//    			{
//        			resultado += "Un alojamiento con nombre: " + nombreTb + " NO EXISTE\n";
//    			}
//    			resultado += "\n Operación terminada";
//    			panelDatos.actualizarInterfaz(resultado);
//    		}
//    		else
//    		{
//    			panelDatos.actualizarInterfaz("Operación cancelada por el alojamiento");
//    		}
//		} 
//    	catch (Exception e) 
//    	{
////			e.printStackTrace();
//			String resultado = generarMensajeError(e);
//			panelDatos.actualizarInterfaz(resultado);
//		}
//    }
    
//    /**
//     * Borra de la base de datos el cliente con el nombre dado po el cliente
//     * Cuando dicho cliente no existe, se indica que se borraron 0 registros de la base de datos
//     */
//    public void eliminarAlojamientoPorNombre( )
//    {
//    	try 
//    	{
//    		String nombre = JOptionPane.showInputDialog (this, "nombre del alojamiento?", "Borrar alojamiento por nombre", JOptionPane.QUESTION_MESSAGE);
//    		if (nombre != null)
//    		{
////    			long idTipo = Long.valueOf (nombre);
//    			long tbEliminados = alohandes.eliminarAlojamientoPorNombre(nombre);
//
//    			String resultado = "En eliminar Alojamiento\n\n";
//    			resultado += tbEliminados + " alojamientos eliminados\n";
//    			resultado += "\n Operación terminada";
//    			panelDatos.actualizarInterfaz(resultado);
//    		}
//    		else
//    		{
//    			panelDatos.actualizarInterfaz("Operación cancelada por el alojamiento");
//    		}
//		} 
//    	catch (Exception e) 
//    	{
////			e.printStackTrace();
//			String resultado = generarMensajeError(e);
//			panelDatos.actualizarInterfaz(resultado);
//		}
//    }
//    
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    /* ****************************************************************
	 * 			CRUD de ViviendaUniversitaria
	 *****************************************************************/
//    /**
//     * Adiciona un tipo de usuario con la información dada por el usuario
//     * Se crea una nueva tupla de usuario en la base de datos, si un tipo de usuario con esa cedula no existía
//     */
//    public void adicionarViviendaUniversitaria( )
//    {
//    	try 
//    	{
//    		long idAlojamiento = Long.parseLong(JOptionPane.showInputDialog (this, "idAlojamiento", "Adicionar Vivienda Universitaria", JOptionPane.QUESTION_MESSAGE));
//			long idProveedor = Long.parseLong(JOptionPane.showInputDialog (this, "idProveedor", "Adicionar Vivienda Universitaria", JOptionPane.QUESTION_MESSAGE));
//			long telefono = Long.parseLong(JOptionPane.showInputDialog (this, "telefono", "Adicionar Vivienda Universitaria", JOptionPane.QUESTION_MESSAGE));
//			Integer precioMes = Integer.parseInt(JOptionPane.showInputDialog (this, "precio mes", "Adicionar Vivienda Universitaria", JOptionPane.QUESTION_MESSAGE));
//			char amoblado = JOptionPane.showInputDialog (this, "amoblado??", "Adicionar Vivienda Universitaria", JOptionPane.QUESTION_MESSAGE).charAt(0);
//			Integer habitaciones = Integer.parseInt(JOptionPane.showInputDialog (this, "Presta administracion??", "Adicionar Vivienda Universitaria", JOptionPane.QUESTION_MESSAGE));
//			String tipoOferta = JOptionPane.showInputDialog (this, "tipo oferta", "Adicionar Vivienda Universitaria", JOptionPane.QUESTION_MESSAGE);
//			String menaje = JOptionPane.showInputDialog (this, "menaje", "Adicionar Vivienda Universitaria", JOptionPane.QUESTION_MESSAGE);
//
//
//    		if (idAlojamiento > 0 & amoblado==('1')|amoblado==('0')&idProveedor > 0 )
//    		{
//        		VOViviendaUniversitaria tb = (VOViviendaUniversitaria)alohandes.adicionarViviendaUniversitari
//        		if (tb == null)
//        		{
//        			throw new Exception ("No se pudo crear un viviendaUniversitaria con id de alojamiento: " + idAlojamiento);
//        		}
//        		String resultado = "En adicionarViviendaUniversitaria\n\n";
//        		resultado += "ViviendaUniversitaria adicionado exitosamente: " + tb;
//    			resultado += "\n Operación terminada";
//    			panelDatos.actualizarInterfaz(resultado);
//    		}
//    		else
//    		{
//    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
//    		}
//		} 
//    	catch (Exception e) 
//    	{
////			e.printStackTrace();
//			String resultado = generarMensajeError(e);
//			panelDatos.actualizarInterfaz(resultado);
//		}
//    }
//
//    /**
//     * Consulta en la base de datos los tipos de usuario existentes y los muestra en el panel de datos de la aplicación
//     */
//    public void listarViviendaUniversitaria( )
//    {
//    	try 
//    	{
//			List<ViviendaUniversitaria> lista = alohandes.darViviendaUniversitaria();
//
//			String resultado = "En listarViviendaUniversitaria";
//			resultado +=  "\n" + listarViviendaUniversitaria (lista);
//			panelDatos.actualizarInterfaz(resultado);
//			resultado += "\n Operación terminada";
//		} 
//    	catch (Exception e) 
//    	{
////			e.printStackTrace();
//			String resultado = generarMensajeError(e);
//			panelDatos.actualizarInterfaz(resultado);
//		}
//    }
//
//    /**
//     * Borra de la base de datos el tipo de bebida con el identificador dado po el usuario
//     * Cuando dicho tipo de bebida no existe, se indica que se borraron 0 registros de la base de datos
//     */
//    public void eliminarViviendaUniversitariaPorId( )
//    {
//    	try 
//    	{
//    		String idTipoStr = JOptionPane.showInputDialog (this, "Id del viviendaUniversitaria?", "Borrar viviendaUniversitaria por Id", JOptionPane.QUESTION_MESSAGE);
//    		if (idTipoStr != null)
//    		{
//    			long idTipo = Long.valueOf (idTipoStr);
//    			long tbEliminados = alohandes.eliminarViviendaUniversitariaPorId(idTipo);
//
//    			String resultado = "En eliminar viviendaUniversitaria\n\n";
//    			resultado += tbEliminados + " viviendaUniversitaria eliminados\n";
//    			resultado += "\n Operación terminada";
//    			panelDatos.actualizarInterfaz(resultado);
//    		}
//    		else
//    		{
//    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
//    		}
//		} 
//    	catch (Exception e) 
//    	{
////			e.printStackTrace();
//			String resultado = generarMensajeError(e);
//			panelDatos.actualizarInterfaz(resultado);
//		}
//    }
//    
//    
//    /**
//     * Busca el usuario con el id indicado por el usuario y lo muestra en el panel de datos
//     */
//    public void buscarViviendaUniversitariaPorId( )
//    {
//    	try 
//    	{
//    		long Id = Integer.parseInt(JOptionPane.showInputDialog (this, "Id del viviendaUniversitaria?", "Buscar viviendaUniversitaria por Id", JOptionPane.QUESTION_MESSAGE));
//    		if (Id > 0)
//    		{
//    			VOViviendaUniversitaria viviendaUniversitaria = (VOViviendaUniversitaria) alohandes.darViviendaUniversitariaPorId(Id);
//    			String resultado = "En buscar viviendaUniversitaria por id\n\n";
//    			if (viviendaUniversitaria != null)
//    			{
//        			resultado += "El viviendaUniversitaria es: " + viviendaUniversitaria;
//    			}
//    			else
//    			{
//        			resultado += "Un viviendaUniversitaria con Id: " + Id + " NO EXISTE\n";
//    			}
//    			resultado += "\n Operación terminada";
//    			panelDatos.actualizarInterfaz(resultado);
//    		}
//    		else
//    		{
//    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
//    		}
//		} 
//    	catch (Exception e) 
//    	{
////			e.printStackTrace();
//			String resultado = generarMensajeError(e);
//			panelDatos.actualizarInterfaz(resultado);
//		}
//    }
    
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    /* ****************************************************************
	 * 			CRUD de Propietario
	 *****************************************************************/
    /**
     * Adiciona un tipo de usuario con la información dada por el usuario
     * Se crea una nueva tupla de usuario en la base de datos, si un tipo de usuario con esa cedula no existía
     */
    public void adicionarPropietario( )
    {
    	try 
    	{
			long id = Long.parseLong(JOptionPane.showInputDialog (this, "id", "Adicionar Propietario", JOptionPane.QUESTION_MESSAGE));
    		String nombre = JOptionPane.showInputDialog (this, "nombre", "Adicionar Propietario", JOptionPane.QUESTION_MESSAGE);
    		String tipo = JOptionPane.showInputDialog (this, "tipo", "Adicionar Propietario", JOptionPane.QUESTION_MESSAGE);

    		String email = JOptionPane.showInputDialog (this, "email", "Adicionar Propietario", JOptionPane.QUESTION_MESSAGE);
			String vinculacion= JOptionPane.showInputDialog (this, "vinculacion", "Adicionar Propietario", JOptionPane.QUESTION_MESSAGE);
			long cedula = Long.parseLong(JOptionPane.showInputDialog (this, "documento", "Adicionar Propietario", JOptionPane.QUESTION_MESSAGE));
			int telefono =(int) Long.parseLong(JOptionPane.showInputDialog (this, "telefono", "Adicionar Propietario", JOptionPane.QUESTION_MESSAGE));
			long idProveedor = Long.parseLong(JOptionPane.showInputDialog (this, "idProveedor", "Adicionar Propietario", JOptionPane.QUESTION_MESSAGE));


			if (cedula>0&idProveedor >0& vinculacion!= null)
    		{
        		VOPropietario tb = (VOPropietario) alohandes.adicionarPropietario(id, idProveedor, email, telefono, vinculacion, nombre, tipo);
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear una persona natural con operador: " + cedula);
        		}
        		String resultado = "En adicionarPropietario\n\n";
        		resultado += "Cliente adicionado exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

    /**
     * Consulta en la base de datos los tipos de usuario existentes y los muestra en el panel de datos de la aplicación
     */
    public void listarPropietario( )
    {
    	try 
    	{
			List <Propietario> lista = alohandes.darPropietarios();

			String resultado = "En listar Propietario";
			resultado +=  "\n" + listarPropietario (lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

    /**
     * Borra de la base de datos el tipo de bebida con el identificador dado po el usuario
     * Cuando dicho tipo de bebida no existe, se indica que se borraron 0 registros de la base de datos
     */
    public void eliminarPropietarioPorId( )
    {
    	try 
    	{
    		String idTipoStr = JOptionPane.showInputDialog (this, "Id de la Propietario?", "Borrar Propietario por Id", JOptionPane.QUESTION_MESSAGE);
    		if (idTipoStr != null)
    		{
    			long idTipo = Long.valueOf (idTipoStr);
    			long tbEliminados = alohandes.eliminarPropietarioPorId(idTipo);

    			String resultado = "En eliminar Propietario\n\n";
    			resultado += tbEliminados + " Propietario eliminados\n";
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    
    /**
     * Busca el usuario con el id indicado por el usuario y lo muestra en el panel de datos
     */
    public void buscarPropietarioPorId( )
    {
    	try 
    	{
    		long Id = Long.parseLong(JOptionPane.showInputDialog (this, "Id de la Propietario?", "Buscar Propietario por Id", JOptionPane.QUESTION_MESSAGE));
    		if (Id > 0)
    		{
    			VOPropietario Propietario = (VOPropietario) alohandes.darPropietariosPorId(Id);
    			String resultado = "En buscar usuario por nombre\n\n";
    			if (Propietario != null)
    			{
        			resultado += "la Propietario es: " + Propietario;
    			}
    			else
    			{
        			resultado += "Una Propietario con Id: " + Id + " NO EXISTE\n";
    			}
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    

    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    /* ****************************************************************
	 * 			CRUD de Reserva
	 *****************************************************************/
//    /**
//     * Adiciona un tipo de usuario con la información dada por el usuario
//     * Se crea una nueva tupla de usuario en la base de datos, si un tipo de usuario con esa cedula no existía
//     */
//    public void adicionarReserva( )
//    {
//    	try 
//    	{
//			Timestamp fechaCheckIn = Timestamp.valueOf(JOptionPane.showInputDialog (this, "fecha y hora de llegada", "Adicionar Reserva", JOptionPane.QUESTION_MESSAGE));
//			Timestamp fechaCheckOut = Timestamp.valueOf(JOptionPane.showInputDialog (this, "fecha y hora de salida", "Adicionar Reserva", JOptionPane.QUESTION_MESSAGE));
//			Timestamp fechaConfirmacion = Timestamp.valueOf(JOptionPane.showInputDialog (this, "fecha y hora de confirmacion", "Adicionar Reserva", JOptionPane.QUESTION_MESSAGE));
//			long id = Long.parseLong(JOptionPane.showInputDialog (this, "id", "Adicionar Reserva", JOptionPane.QUESTION_MESSAGE));
//			Integer cantPagos = Integer.parseInt(JOptionPane.showInputDialog (this, "Presta administracion??", "Adicionar Reserva", JOptionPane.QUESTION_MESSAGE));
//			Integer descuento = Integer.parseInt(JOptionPane.showInputDialog (this, "Presta administracion??", "Adicionar Reserva", JOptionPane.QUESTION_MESSAGE));
//			long idCliente = Long.parseLong(JOptionPane.showInputDialog (this, "idProveedor", "Adicionar Reserva", JOptionPane.QUESTION_MESSAGE));
//    		long idAlojamiento = Long.parseLong(JOptionPane.showInputDialog (this, "idAlojamiento", "Adicionar Reserva", JOptionPane.QUESTION_MESSAGE));
//			Integer precioTotal = Integer.parseInt(JOptionPane.showInputDialog (this, "precio", "Adicionar Reserva", JOptionPane.QUESTION_MESSAGE));
//			Integer personas = Integer.parseInt(JOptionPane.showInputDialog (this, "personas", "Adicionar Reserva", JOptionPane.QUESTION_MESSAGE));
//
//			
//    		if (id >0 & fechaCheckIn!= null& fechaCheckOut!= null& fechaConfirmacion!= null & idCliente>0& idAlojamiento>0)
//    		{
//        		VOReserva tb = (VOReserva) alohandes.adicionarReserv
//        		if (tb == null)
//        		{
//        			throw new Exception ("No se pudo crear una reserva para el usuario: " + idCliente);
//        		}
//        		String resultado = "En adicionarReserva\n\n";
//        		resultado += "Reserva adicionada exitosamente: " + tb;
//    			resultado += "\n Operación terminada";
//    			panelDatos.actualizarInterfaz(resultado);
//    		}
//    		else
//    		{
//    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
//    		}
//		} 
//    	catch (Exception e) 
//    	{
////			e.printStackTrace();
//			String resultado = generarMensajeError(e);
//			panelDatos.actualizarInterfaz(resultado);
//		}
//    }
//
//    /**
//     * Consulta en la base de datos los tipos de usuario existentes y los muestra en el panel de datos de la aplicación
//     */
//    public void listarReservas( )
//    {
//    	try 
//    	{
//			List <Reserva> lista = alohandes.darReservas();
//
//			String resultado = "En listarReserva";
//			resultado +=  "\n" + listarReserva (lista);
//			panelDatos.actualizarInterfaz(resultado);
//			resultado += "\n Operación terminada";
//		} 
//    	catch (Exception e) 
//    	{
////			e.printStackTrace();
//			String resultado = generarMensajeError(e);
//			panelDatos.actualizarInterfaz(resultado);
//		}
//    }
//
//    /**
//     * Borra de la base de datos el tipo de bebida con el identificador dado po el usuario
//     * Cuando dicho tipo de bebida no existe, se indica que se borraron 0 registros de la base de datos
//     */
//    public void eliminarReservaPorId( )
//    {
//    	try 
//    	{
//    		String idTipoStr = JOptionPane.showInputDialog (this, "Id de la reserva?", "Borrar reserva por Id", JOptionPane.QUESTION_MESSAGE);
//    		if (idTipoStr != null)
//    		{
//    			long idTipo = Long.valueOf (idTipoStr);
//    			long tbEliminados = alohandes.eliminarReservaPorId(idTipo);
//
//    			String resultado = "En eliminar Reserva\n\n";
//    			resultado += tbEliminados + " Reserva eliminadas\n";
//    			resultado += "\n Operación terminada";
//    			panelDatos.actualizarInterfaz(resultado);
//    		}
//    		else
//    		{
//    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
//    		}
//		} 
//    	catch (Exception e) 
//    	{
////			e.printStackTrace();
//			String resultado = generarMensajeError(e);
//			panelDatos.actualizarInterfaz(resultado);
//		}
//    }
//
//    /**
//     * Busca el usuario con el nombre indicado por el usuario y lo muestra en el panel de datos
//     */
//    public void buscarReservaPorCliente( )
//    {
//    	try 
//    	{
//    		Long nombreTb = Long.parseLong(JOptionPane.showInputDialog (this, "id del de la reserva?", "Buscar reserva por usuario", JOptionPane.QUESTION_MESSAGE));
//    		if (nombreTb > 0)
//    		{
//    			VOReserva reserva = (VOReserva) alohandes.darReservasPorCliente(nombreTb);
//    			String resultado = "En buscar reserva por cliente\n\n";
//    			if (reserva != null)
//    			{
//        			resultado += "La reserva es: " + reserva;
//    			}
//    			else
//    			{
//        			resultado += "Una reserva con cliente: " + nombreTb + " NO EXISTE\n";
//    			}
//    			resultado += "\n Operación terminada";
//    			panelDatos.actualizarInterfaz(resultado);
//    		}
//    		else
//    		{
//    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
//    		}
//		} 
//    	catch (Exception e) 
//    	{
////			e.printStackTrace();
//			String resultado = generarMensajeError(e);
//			panelDatos.actualizarInterfaz(resultado);
//		}
//    }
    
//    
//    /**
//     * Busca el usuario con el id indicado por el usuario y lo muestra en el panel de datos
//     */
//    public void buscarReservaPorId( )
//    {
//    	try 
//    	{
//    		long Id = Integer.parseInt(JOptionPane.showInputDialog (this, "Id de la reserva?", "Buscar reserva por Id", JOptionPane.QUESTION_MESSAGE));
//    		if (Id > 0)
//    		{
//    			VOReserva reserva = (VOReserva) alohandes.darReservasPorId(Id);
//    			String resultado = "En buscar reserva por id\n\n";
//    			if (reserva != null)
//    			{
//        			resultado += "La reserva es: " + reserva;
//    			}
//    			else
//    			{
//        			resultado += "Un usuario con Id: " + Id + " NO EXISTE\n";
//    			}
//    			resultado += "\n Operación terminada";
//    			panelDatos.actualizarInterfaz(resultado);
//    		}
//    		else
//    		{
//    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
//    		}
//		} 
//    	catch (Exception e) 
//    	{
////			e.printStackTrace();
//			String resultado = generarMensajeError(e);
//			panelDatos.actualizarInterfaz(resultado);
//		}
//    }
//    
//    /**
//     * Borra de la base de datos el usuario con el nombre dado po el usuario
//     * Cuando dicho usuario no existe, se indica que se borraron 0 registros de la base de datos
//     */
//    public void eliminarReservaPorCliente( )
//    {
//    	try 
//    	{
//    		String cliente = JOptionPane.showInputDialog (this, "id del cliente de la reserva?", "Borrar reserva por cliente", JOptionPane.QUESTION_MESSAGE);
//    		if (cliente != null)
//    		{
//    			long idTipo = Long.valueOf (cliente);
//    			long tbEliminados = alohandes.eliminarReservaPorCliente(idTipo);
//
//    			String resultado = "En eliminar Reserva\n\n";
//    			resultado += tbEliminados + " reservas eliminados\n";
//    			resultado += "\n Operación terminada";
//    			panelDatos.actualizarInterfaz(resultado);
//    		}
//    		else
//    		{
//    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
//    		}
//		} 
//    	catch (Exception e) 
//    	{
////			e.printStackTrace();
//			String resultado = generarMensajeError(e);
//			panelDatos.actualizarInterfaz(resultado);
//		}
//    }
    
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    /* ****************************************************************
	 * 			CRUD de Empresa
	 *****************************************************************/
    /**
     * Adiciona un tipo de usuario con la información dada por el usuario
     * Se crea una nueva tupla de usuario en la base de datos, si un tipo de usuario con esa cedula no existía
     */
    public void adicionarEmpresa( )
    {
    	try 
    	{
    		long nit = Long.parseLong(JOptionPane.showInputDialog (this, "nit", "Adicionar Empresa", JOptionPane.QUESTION_MESSAGE));
			String email = (JOptionPane.showInputDialog (this, "email", "Adicionar Empresa", JOptionPane.QUESTION_MESSAGE));
			long idProveedor = Long.parseLong(JOptionPane.showInputDialog (this, "idProveedor", "Adicionar Empresa", JOptionPane.QUESTION_MESSAGE));
			char registrado = JOptionPane.showInputDialog (this,"está registrada?", "Adicionar Empresa", JOptionPane.QUESTION_MESSAGE).charAt(0);
			String nombre = (JOptionPane.showInputDialog (this, "nombre", "Adicionar Empresa", JOptionPane.QUESTION_MESSAGE));

    		if (nit >0 & registrado==('1')|registrado==('0')&& idProveedor>0)
    		{
        		VOEmpresa tb = (VOEmpresa)alohandes.adicionarEmpresa(nit, nombre, email, idProveedor, registrado);
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear una empresa con operador: " + idProveedor);
        		}
        		String resultado = "En adicionarEmpresa\n\n";
        		resultado += "Empresa adicionada exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

    /**
     * Consulta en la base de datos los tipos de usuario existentes y los muestra en el panel de datos de la aplicación
     */
    public void listarEmpresa( )
    {
    	try 
    	{
			List <Empresa> lista = alohandes.darEmpresas();

			String resultado = "En listarUsuario";
			resultado +=  "\n" + listarEmpresa (lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

    /**
     * Borra de la base de datos el tipo de bebida con el identificador dado po el usuario
     * Cuando dicho tipo de bebida no existe, se indica que se borraron 0 registros de la base de datos
     */
    public void eliminarEmpresaPorId( )
    {
    	try 
    	{
    		String idTipoStr = JOptionPane.showInputDialog (this, "Id de la Empresa?", "Borrar Empresa por Id", JOptionPane.QUESTION_MESSAGE);
    		if (idTipoStr != null)
    		{
    			long idTipo = Long.valueOf (idTipoStr);
    			long tbEliminados = alohandes.eliminarEmpresaPorId(idTipo);

    			String resultado = "En eliminar Empresa\n\n";
    			resultado += tbEliminados + " Empresas eliminados\n";
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    /**
     * Busca el usuario con el id indicado por el usuario y lo muestra en el panel de datos
     */
    public void buscarEmpresaPorId( )
    {
    	try 
    	{
    		long Id = Integer.parseInt(JOptionPane.showInputDialog (this, "Id de la Empresa?", "Buscar Empresa por Id", JOptionPane.QUESTION_MESSAGE));
    		if (Id > 0)
    		{
    			VOEmpresa VOEmpresa = (VOEmpresa) alohandes.darEmpresasPorId(Id);
    			String resultado = "En buscar VOEmpresa por id\n\n";
    			if (VOEmpresa != null)
    			{
        			resultado += "La Empresa es: " + VOEmpresa;
    			}
    			else
    			{
        			resultado += "Una Empresa con Id: " + Id + " NO EXISTE\n";
    			}
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
   
    
  
    
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    /* ****************************************************************
	 * 			CRUD de Proveedor
	 *****************************************************************/
    /**
     * Adiciona un tipo de usuario con la información dada por el usuario
     * Se crea una nueva tupla de usuario en la base de datos, si un tipo de usuario con esa cedula no existía
     */
    public void adicionarProveedor( )
    {
    	try 
    	{
			long id = Long.parseLong(JOptionPane.showInputDialog (this, "documento del proveedor(nit o cedula)", "Adicionar proveedor", JOptionPane.QUESTION_MESSAGE));
			String tipo = (JOptionPane.showInputDialog (this, "tipo", "Adicionar proveedor", JOptionPane.QUESTION_MESSAGE));
			String nombre = (JOptionPane.showInputDialog (this, "nombre", "Adicionar proveedor", JOptionPane.QUESTION_MESSAGE));


    		if (id > 0)
    		{
        		VOProveedor tb = (VOProveedor) alohandes.adicionarProveedor(id, tipo, nombre);
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear una proveedor con operador: " + id);
        		}
        		String resultado = "En adicionarProveedor\n\n";
        		resultado += "Proveedor adicionada exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

    /**
	     * Consulta en la base de datos los tipos de usuario existentes y los muestra en el panel de datos de la aplicación


	/**
     * Consulta en la base de datos los tipos de usuario existentes y los muestra en el panel de datos de la aplicación
     */
    public void listarProveedor( )
    {
    	try 
    	{
			List <Proveedor> lista = alohandes.darProveedores();

			String resultado = "En listarProveedor";
			resultado +=  "\n" + listarProveedor (lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

    /**
     * Borra de la base de datos el tipo de bebida con el identificador dado po el usuario
     * Cuando dicho tipo de bebida no existe, se indica que se borraron 0 registros de la base de datos
     */
    public void eliminarProveedorPorId( )
    {
    	try 
    	{
    		String idTipoStr = JOptionPane.showInputDialog (this, "Id de la Proveedor?", "Borrar Proveedor por Id", JOptionPane.QUESTION_MESSAGE);
    		if (idTipoStr != null)
    		{
    			long idTipo = Long.valueOf (idTipoStr);
    			long tbEliminados = alohandes.eliminarProveedorPorId(idTipo);

    			String resultado = "En eliminar Proveedor\n\n";
    			resultado += tbEliminados + " Viviendas Universitarias eliminados\n";
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    /**
     * Busca el usuario con el id indicado por el usuario y lo muestra en el panel de datos
     */
    public void buscarProveedorPorId( )
    {
    	try 
    	{
    		long Id = Integer.parseInt(JOptionPane.showInputDialog (this, "Id de la Proveedor?", "Buscar Proveedor por Id", JOptionPane.QUESTION_MESSAGE));
    		if (Id > 0)
    		{
    			VOProveedor Proveedor = (VOProveedor) alohandes.darProveedoresPorId(Id);
    			String resultado = "En buscar Proveedor por id\n\n";
    			if (Proveedor != null)
    			{
        			resultado += "La Proveedor es: " + Proveedor;
    			}
    			else
    			{
        			resultado += "Una Proveedor con Id: " + Id + " NO EXISTE\n";
    			}
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    
   
    
    

    
    
	/* ****************************************************************
	 * 			Métodos administrativos
	 *****************************************************************/
	/**
	 * Muestra el log de Alohandes
	 */
	public void mostrarLogAlohandes ()
	{
		mostrarArchivo ("alohandes.log");
	}
	
	/**
	 * Muestra el log de datanucleus
	 */
	public void mostrarLogDatanuecleus ()
	{
		mostrarArchivo ("datanucleus.log");
	}
	
	/**
	 * Limpia el contenido del log de parranderos
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogAlohandes ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("alohandes.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de parranderos ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}
	
	/**
	 * Limpia el contenido del log de datanucleus
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogDatanucleus ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("datanucleus.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de datanucleus ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}
	
	/**
	 * Limpia todas las tuplas de todas las tablas de la base de datos de parranderos
	 * Muestra en el panel de datos el número de tuplas eliminadas de cada tabla
	 */
	public void limpiarBD ()
	{
		try 
		{
    		// Ejecución de la demo y recolección de los resultados
			long eliminados [] = alohandes.limpiarAlohandes();
			
			// Generación de la cadena de caracteres con la traza de la ejecución de la demo
			String resultado = "\n\n************ Limpiando la base de datos ************ \n";
			resultado += eliminados [0] + " Gustan eliminados\n";
			resultado += eliminados [1] + " Sirven eliminados\n";
			resultado += eliminados [2] + " Visitan eliminados\n";
			resultado += eliminados [3] + " Bebidas eliminadas\n";
			resultado += eliminados [4] + " Tipos de bebida eliminados\n";
			resultado += eliminados [5] + " Bebedores eliminados\n";
			resultado += eliminados [6] + " Bares eliminados\n";
			resultado += "\nLimpieza terminada";
   
			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	/**
	 * Muestra la presentación general del proyecto
	 */
	public void mostrarPresentacionGeneral ()
	{
		mostrarArchivo ("data/00-ST-AlohandesJDO.pdf");
	}
	
	/**
	 * Muestra el modelo conceptual de Alohandes
	 */
	public void mostrarModeloConceptual ()
	{
		mostrarArchivo ("data/Modelo Conceptual Alohandes.pdf");
	}
	
	/**
	 * Muestra el esquema de la base de datos de Alohandes
	 */
	public void mostrarEsquemaBD ()
	{
		mostrarArchivo ("data/Esquema BD Alohandes.pdf");
	}
	
	/**
	 * Muestra el script de creación de la base de datos
	 */
	public void mostrarScriptBD ()
	{
		mostrarArchivo ("data/EsquemaAlohandes.sql");
	}
	
	/**
	 * Muestra la arquitectura de referencia para Alohandes
	 */
	public void mostrarArqRef ()
	{
		mostrarArchivo ("data/ArquitecturaReferencia.pdf");
	}
	
	/**
	 * Muestra la documentación Javadoc del proyectp
	 */
	public void mostrarJavadoc ()
	{
		mostrarArchivo ("doc/index.html");
	}
	
	/**
     * Muestra la información acerca del desarrollo de esta apicación
     */
    public void acercaDe ()
    {
		String resultado = "\n\n ************************************\n\n";
		resultado += " * Universidad	de	los	Andes	(Bogotá	- Colombia)\n";
		resultado += " * Departamento	de	Ingeniería	de	Sistemas	y	Computación\n";
		resultado += " * Licenciado	bajo	el	esquema	Academic Free License versión 2.1\n";
		resultado += " * \n";		
		resultado += " * Curso: isis2304 - Sistemas Transaccionales\n";
		resultado += " * Proyecto: Alohandes Uniandes\n";
		resultado += " * @version 1.0\n";
		resultado += " * @author Oswaldo Caceres y Nicole Dueñas\n";
		resultado += " * Mayo de 2020\n";
		resultado += " * \n";
		resultado += " \n";
		resultado += "\n ************************************\n\n";

		panelDatos.actualizarInterfaz(resultado);		
    }
    

	/* ****************************************************************
	 * 			Métodos privados para la presentación de resultados y otras operaciones
	 *****************************************************************/
    /**
     * Genera una cadena de caracteres con la lista de los tipos de bebida recibida: una línea por cada tipo de bebida
     * @param lista - La lista con los tipos de bebida
     * @return La cadena con una líea para cada tipo de bebida recibido
     */
    private String listarClientes(List<Cliente> lista)
    {
    	String resp = "los clientes existentes son:\n";
    	int i = 1;
        for (Cliente tb : lista)
        {
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}
    
  
    private String listarAptoTemporada(List<AptoTemporada> lista)
    {
    	String resp = "los aptoSemestres existentes son:\n";
    	int i = 1;
        for (AptoTemporada tb : lista)
        {
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}
    
    
    
    private String listarHabitacion(List<Habitacion> lista)
    {
    	String resp = "las habitaciones existentes son:\n";
    	int i = 1;
        for (Habitacion tb : lista)
        {
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}
    
    
    private String listarHostal(List<Hostal> lista)
    {
    	String resp = "los hostales existentes son:\n";
    	int i = 1;
        for (Hostal tb : lista)
        {
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}
    
    private String listarHotel(List<Hotel> lista)
    {
    	String resp = "los hoteles existentes son:\n";
    	int i = 1;
        for (Hotel tb : lista)
        {
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}
    
    private String listarAlojamiento(List<Alojamiento> lista)
    {
    	String resp = "los hoteles existentes son:\n";
    	int i = 1;
        for (Alojamiento tb : lista)
        {
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}
    
    private String listarProveedor(List<Proveedor> lista)
    {
    	String resp = "los operadores existentes son:\n";
    	int i = 1;
        for (Proveedor tb : lista)
        {
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}
    
    private String listarPropietario(List<Propietario> lista)
    {
    	String resp = "los operadores existentes son:\n";
    	int i = 1;
        for (Propietario tb : lista)
        {
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}
    
    private String listarReserva(List<Reserva> lista)
    {
    	String resp = "los operadores existentes son:\n";
    	int i = 1;
        for (Reserva tb : lista)
        {
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}
    
    private String listarEmpresa(List<Empresa> lista)
    {
    	String resp = "los operadores existentes son:\n";
    	int i = 1;
        for (Empresa tb : lista)
        {
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}
    
    private String listarViviendaUniversitaria(List<ViviendaUniversitaria> lista)
    {
    	String resp = "los operadores existentes son:\n";
    	int i = 1;
        for (ViviendaUniversitaria tb : lista)
        {
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}

    /**
     * Genera una cadena de caracteres con la descripción de la excepcion e, haciendo énfasis en las excepcionsde JDO
     * @param e - La excepción recibida
     * @return La descripción de la excepción, cuando es javax.jdo.JDODataStoreException, "" de lo contrario
     */
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}

	/**
	 * Genera una cadena para indicar al usuario que hubo un error en la aplicación
	 * @param e - La excepción generada
	 * @return La cadena con la información de la excepción y detalles adicionales
	 */
	private String generarMensajeError(Exception e) 
	{
		String resultado = "************ Error en la ejecución\n";
		resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
		resultado += "\n\nRevise datanucleus.log y parranderos.log para más detalles";
		return resultado;
	}

	/**
	 * Limpia el contenido de un archivo dado su nombre
	 * @param nombreArchivo - El nombre del archivo que se quiere borrar
	 * @return true si se pudo limpiar
	 */
	private boolean limpiarArchivo(String nombreArchivo) 
	{
		BufferedWriter bw;
		try 
		{
			bw = new BufferedWriter(new FileWriter(new File (nombreArchivo)));
			bw.write ("");
			bw.close ();
			return true;
		} 
		catch (IOException e) 
		{
//			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Abre el archivo dado como parámetro con la aplicación por defecto del sistema
	 * @param nombreArchivo - El nombre del archivo que se quiere mostrar
	 */
	private void mostrarArchivo (String nombreArchivo)
	{
		try
		{
			Desktop.getDesktop().open(new File(nombreArchivo));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/* ****************************************************************
	 * 			Métodos de la Interacción
	 *****************************************************************/
    /**
     * Método para la ejecución de los eventos que enlazan el menú con los métodos de negocio
     * Invoca al método correspondiente según el evento recibido
     * @param pEvento - El evento del usuario
     */
    @Override
	public void actionPerformed(ActionEvent pEvento)
	{
		String evento = pEvento.getActionCommand( );		
        try 
        {
			Method req = InterfazAlohandesApp.class.getMethod ( evento );
			req.invoke ( this );
		} 
        catch (Exception e) 
        {
			e.printStackTrace();
		} 
	}
    
	/* ****************************************************************
	 * 			Programa principal
	 *****************************************************************/
    /**
     * Este método ejecuta la aplicación, creando una nueva interfaz
     * @param args Arreglo de argumentos que se recibe por línea de comandos
     */
    public static void main( String[] args )
    {
        try
        {
        	
            // Unifica la interfaz para Mac y para Windows.
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
            InterfazAlohandesApp interfaz = new InterfazAlohandesApp( );
            interfaz.setVisible( true );
        }
        catch( Exception e )
        {
            e.printStackTrace( );
        }
    }
}
