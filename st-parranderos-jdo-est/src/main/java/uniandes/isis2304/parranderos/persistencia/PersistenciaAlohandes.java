/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Parranderos Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.parranderos.persistencia;



import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import uniandes.isis2304.parranderos.negocio.Alojamiento;
import uniandes.isis2304.parranderos.negocio.AptoTemporada;

import uniandes.isis2304.parranderos.negocio.Cliente;
import uniandes.isis2304.parranderos.negocio.Empresa;
import uniandes.isis2304.parranderos.negocio.Habitacion;
import uniandes.isis2304.parranderos.negocio.Hostal;
import uniandes.isis2304.parranderos.negocio.Hotel;
import uniandes.isis2304.parranderos.negocio.Propietario;
import uniandes.isis2304.parranderos.negocio.Proveedor;
import uniandes.isis2304.parranderos.negocio.Servicio;

/**
 * Clase para el manejador de persistencia del proyecto Parranderos
 * Traduce la información entre objetos Java y tuplas de la base de datos, en ambos sentidos
 * Sigue un patrón SINGLETON (Sólo puede haber UN objeto de esta clase) para comunicarse de manera correcta
 * con la base de datos
 * Se apoya en las clases SQLBar, SQLBebedor, SQLBebida, SQLGustan, SQLSirven, SQLServicio y SQLVisitan, que son 
 * las que realizan el acceso a la base de datos
 * 
 * @author Germán Bravo
 */
public class PersistenciaAlohandes 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(PersistenciaAlohandes.class.getName());

	/**
	 * Cadena para indicar el tipo de sentencias que se va a utilizar en una consulta
	 */
	public final static String SQL = "javax.jdo.query.SQL";

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Atributo privado que es el único objeto de la clase - Patrón SINGLETON
	 */
	private static PersistenciaAlohandes instance;

	/**
	 * Fábrica de Manejadores de persistencia, para el manejo correcto de las transacciones
	 */
	private PersistenceManagerFactory pmf;

	/**
	 * Arreglo de cadenas con los nombres de las tablas de la base de datos, en su orden:
	 * Secuenciador, Servicio, bebida, bar, bebedor, gustan, sirven y visitan
	 */
	private List <String> tablas;

	/**
	 * Atributo para el acceso a las sentencias SQL propias a PersistenciaParranderos
	 */
	private SQLUtil sqlUtil;

	/**
	 * Atributo para el acceso a la tabla BEBIDA de la base de datos
	 */
	private SQLAptoTemporada sqlAptoTemporada;

	/**
	 * Atributo para el acceso a la tabla BAR de la base de datos
	 */
	private SQLAlojamiento sqlAlojamiento;
	private SQLEmpresa sqlEmpresa;
	private SQLCliente sqlCliente;
	private SQLHostal sqlHostal;
	private SQLHabitacion sqlHabitacion;
	private SQLHotel sqlHotel;
	private SQLPropietario sqlPropietario;
	private SQLProveedor sqlProveedor;
	private SQLReserva sqlReserva;
	private SQLViviendaUniversitaria sqlViviendaUniversitaria;
	/**
	 * Atributo para el acceso a la tabla BEBIDA de la base de datos
	 */


	/* ****************************************************************
	 * 			Métodos del MANEJADOR DE PERSISTENCIA
	 *****************************************************************/

	/**
	 * Constructor privado con valores por defecto - Patrón SINGLETON
	 */
	private PersistenciaAlohandes ()
	{
		pmf = JDOHelper.getPersistenceManagerFactory("Parranderos");		
		crearClasesSQL ();

		// Define los nombres por defecto de las tablas de la base de datos
		tablas = new LinkedList<String> ();
		tablas.add ("Parranderos_sequence");
		tablas.add ("CLIENTE");
		tablas.add ("RESERVA");
		tablas.add ("ALOJAMIENTO");
		tablas.add ("HABITACION");
		tablas.add ("VIVIENDAUNIVERSITARIA");
		tablas.add ("APTOTEMPORADA");
		tablas.add ("HOSTAL");
		tablas.add ("HOTEL");
		tablas.add ("PROVEEDOR");
		tablas.add ("EMPRESA");
		tablas.add ("PROPIETARIO");
	}

	/**
	 * Constructor privado, que recibe los nombres de las tablas en un objeto Json - Patrón SINGLETON
	 * @param tableConfig - Objeto Json que contiene los nombres de las tablas y de la unidad de persistencia a manejar
	 */
	private PersistenciaAlohandes (JsonObject tableConfig)
	{
		crearClasesSQL ();
		tablas = leerNombresTablas (tableConfig);

		String unidadPersistencia = tableConfig.get ("unidadPersistencia").getAsString ();
		log.trace ("Accediendo unidad de persistencia: " + unidadPersistencia);
		pmf = JDOHelper.getPersistenceManagerFactory (unidadPersistencia);
	}

	/**
	 * @return Retorna el único objeto PersistenciaParranderos existente - Patrón SINGLETON
	 */
	public static PersistenciaAlohandes getInstance ()
	{
		if (instance == null)
		{
			instance = new PersistenciaAlohandes ();
		}
		return instance;
	}

	/**
	 * Constructor que toma los nombres de las tablas de la base de datos del objeto tableConfig
	 * @param tableConfig - El objeto JSON con los nombres de las tablas
	 * @return Retorna el único objeto PersistenciaParranderos existente - Patrón SINGLETON
	 */
	public static PersistenciaAlohandes getInstance (JsonObject tableConfig)
	{
		if (instance == null)
		{
			instance = new PersistenciaAlohandes (tableConfig);
		}
		return instance;
	}

	/**
	 * Cierra la conexión con la base de datos
	 */
	public void cerrarUnidadPersistencia ()
	{
		pmf.close ();
		instance = null;
	}

	/**
	 * Genera una lista con los nombres de las tablas de la base de datos
	 * @param tableConfig - El objeto Json con los nombres de las tablas
	 * @return La lista con los nombres del secuenciador y de las tablas
	 */
	private List <String> leerNombresTablas (JsonObject tableConfig)
	{
		JsonArray nombres = tableConfig.getAsJsonArray("tablas") ;

		List <String> resp = new LinkedList <String> ();
		for (JsonElement nom : nombres)
		{
			resp.add (nom.getAsString ());
		}

		return resp;
	}

	/**
	 * Crea los atributos de clases de apoyo SQL
	 */
	private void crearClasesSQL ()
	{
		sqlAlojamiento = new SQLAlojamiento(this);
		sqlCliente = new SQLCliente(this);
		setSqlReserva(new SQLReserva(this));
		sqlHabitacion = new SQLHabitacion(this);
	sqlViviendaUniversitaria=new SQLViviendaUniversitaria(this);	

		sqlAptoTemporada = new SQLAptoTemporada(this);		

		sqlHostal = new SQLHostal(this);		

		sqlHotel = new SQLHotel(this);		

		sqlProveedor = new SQLProveedor(this);	
		sqlEmpresa = new SQLEmpresa(this);		

		setSqlPropietario(new SQLPropietario(this));		


		sqlUtil = new SQLUtil(this);
	}

	/**
	 * @return La cadena de caracteres con el nombre del secuenciador de parranderos
	 */
	public String darSeqParranderos ()
	{
		return tablas.get (0);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Servicio de parranderos
	 */
	public String darTablaCliente ()
	{
		return tablas.get (1);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bebida de parranderos
	 */
	public String darTablaReserva ()
	{
		return tablas.get (2);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bar de parranderos
	 */
	public String darTablaAlojamiento ()
	{
		return tablas.get (3);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bebedor de parranderos
	 */
	public String darTablaHabitacion ()
	{
		return tablas.get (4);
	}



	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Sirven de parranderos
	 */
	public String darTablaViviendaUniversitaria ()
	{
		return tablas.get (5);
	}


	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Visitan de parranderos
	 */
	public String darTablaAptoTemporada ()
	{
		return tablas.get (6);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Visitan de parranderos
	 */
	public String darTablaServicio ()
	{
		return tablas.get (7);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Visitan de parranderos
	 */
	public String darTablaHostal ()
	{
		return tablas.get (8);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Visitan de parranderos
	 */
	public String darTablaHotel ()
	{
		return tablas.get (9);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Visitan de parranderos
	 */
	public String darTablaProveedor ()
	{
		return tablas.get (10);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Visitan de parranderos
	 */
	public String darTablaEmpresa ()
	{
		return tablas.get (11);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Visitan de parranderos
	 */
	public String darTablaPropietario ()
	{
		return tablas.get (12);
	}

	/**
	 * Transacción para el generador de secuencia de Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return El siguiente número del secuenciador de Parranderos
	 */
	private long nextval ()
	{
		long resp = sqlUtil.nextval (pmf.getPersistenceManager());
		log.trace ("Generando secuencia: " + resp);
		return resp;
	}

	/**
	 * Extrae el mensaje de la exception JDODataStoreException embebido en la Exception e, que da el detalle específico del problema encontrado
	 * @param e - La excepción que ocurrio
	 * @return El mensaje de la excepción JDO
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



	/* ****************************************************************
	 * 			Métodos para manejar ALOJAMIENTO
	 *****************************************************************/


	public Alojamiento adicionarAlojamiento(long id, char habilitado, String nombre, String tipo, String servicios)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long idServicio = nextval ();
			long tuplasInsertadas = sqlAlojamiento.adicionarAlojamiento(pm, id, habilitado, nombre, tipo, servicios);
			tx.commit();

			log.trace ("Inserción de tipo de bebida: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Alojamiento() {
				
				@Override
				public String getUbicacion() {
					return null;
				}
				
				@Override
				public String getTipo() {
					return tipo;
				}
			};
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	public long eliminarAlojamientoPorId (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlAlojamiento.eliminarAlojamientoPorId(pm, id);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//	        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	public List<Alojamiento> darAlojamientos ()
	{
		return sqlAlojamiento.darAlojamientos (pmf.getPersistenceManager());
	}




	public Alojamiento darAlojamientoPorId (long id)
	{
		return sqlAlojamiento.darAlojamientoPorId (pmf.getPersistenceManager(), id);
	}

	/* ****************************************************************
	 * 			Métodos para manejar APTOTEMPORADA
	 *****************************************************************/


	public AptoTemporada adicionarAptoTemporada(long id, int precio, int habitaciones, String menaje,String ubicacion, String tipoOferta)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long idServicio = nextval ();
			long tuplasInsertadas = sqlAptoTemporada.adicionarAptoTemporada(pm, id, precio, menaje, habitaciones, ubicacion, tipoOferta);
			tx.commit();

			log.trace ("Inserción de aptotemporada " + id + ": " + tuplasInsertadas + " tuplas insertadas");

			return new AptoTemporada();
		}
		catch (Exception e)
		{
			//	        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}




	public long eliminarAptoTemporadaPorId (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlAptoTemporada.eliminarAptoTemporadaPorId(pm, id);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//	        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	public List<AptoTemporada> darAptoTemporadas ()
	{
		return sqlAptoTemporada.darAptoTemporadaes (pmf.getPersistenceManager());
	}




	public AptoTemporada darAptoTemporadaPorId (long id)
	{
		return sqlAptoTemporada.darAptoTemporadaPorId (pmf.getPersistenceManager(), id);
	}

	public Cliente adicionarCliente( String documento, String nombre,String email, String ciudad, String contrasena, String genero, Date fechaNacimiento,String vinculacion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long idServicio = nextval ();
			long tuplasInsertadas = sqlCliente.adicionarCliente(pm,   documento,  nombre, email,  ciudad,  contrasena,  genero,  fechaNacimiento, vinculacion);
			tx.commit();

			log.trace ("Inserción de tipo de cliente: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Cliente();
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}




	public long eliminarClientePorId (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlCliente.eliminarClientePorId(pm, id);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	public List<Cliente> darClientes ()
	{
		return sqlCliente.darClientes (pmf.getPersistenceManager());
	}




	public Cliente darClientePorId (long id)
	{
		return sqlCliente.darClientePorId (pmf.getPersistenceManager(), id);
	}

	/* ****************************************************************
	 * 			Métodos para manejar EMPRESA
	 *****************************************************************/


	public Empresa adicionarEmpresa(String id, String nombre, String email )
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long idServicio = nextval ();
			long tuplasInsertadas = sqlEmpresa.adicionarEmpresa(pm, id, nombre, email);
			tx.commit();

			log.trace ("Inserción de tipo de bebida: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Empresa();
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}




	public long eliminarEmpresaPorId (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlEmpresa.eliminarEmpresaPorId(pm, id);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	public List<Empresa> darEmpresas ()
	{
		return sqlEmpresa.darEmpresas (pmf.getPersistenceManager());
	}




	public Empresa darEmpresaPorId (long id)
	{
		return sqlEmpresa.darEmpresaPorId (pmf.getPersistenceManager(), id);
	}

	

	/* ****************************************************************
	 * 			Métodos para manejar SERVICIOS
	 *****************************************************************/


	public Habitacion adicionarHabitacion(String id,String horario, int precioNoche, int capacidad, String tipoOferta)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long idServicio = nextval ();
			long tuplasInsertadas = sqlHabitacion.adicionarHabitacion(pm, id, horario, precioNoche, capacidad, tipoOferta);
			tx.commit();

			log.trace ("Inserción de tipo de bebida: " + id + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Habitacion();
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}




	public long eliminarHabitacionPorId (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlHabitacion.eliminarHabitacionPorId(pm, id);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	public List<Habitacion> darHabitaciones ()
	{
		return sqlHabitacion.darHabitaciones (pmf.getPersistenceManager());
	}




	public Habitacion darHabitacionPorId (long id)
	{
		return sqlHabitacion.darHabitacionPorId (pmf.getPersistenceManager(), id);
	}/* ****************************************************************
	 * 			Métodos para manejar HOSTAL
	 *****************************************************************/


	public Hostal adicionarHostal(long idHostal, String nombre, String ubicacion, String horario, int telefono)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long idServicio = nextval ();
			long tuplasInsertadas = sqlHostal.adicionarHostal(pm, idHostal, nombre, ubicacion, horario, telefono);
			tx.commit();

			log.trace ("Inserción de tipo de bebida: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Hostal();
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}




	public long eliminarHostalPorId (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlHostal.eliminarHostalPorId(pm, id);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	public List<Hostal> darHostals ()
	{
		return sqlHostal.darHostals (pmf.getPersistenceManager());
	}




	public Hostal darHostalPorId (long id)
	{
		return sqlHostal.darHostalPorId (pmf.getPersistenceManager(), id);
	}
	/* ****************************************************************
	 * 			Métodos para manejar HOTEL
	 *****************************************************************/


	public Hotel adicionarHotel(long idHotel, String nombre, String ubicacion, String horario, int telefono)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long idServicio = nextval ();
			long tuplasInsertadas = sqlHotel.adicionarHotel(pm, idHotel, nombre, ubicacion, horario, telefono);
			tx.commit();

			log.trace ("Inserción de tipo de bebida: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Hotel();
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	public long eliminarHotelPorId (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlHotel.eliminarHotelPorId(pm, id);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	public List<Hotel> darHoteles ()
	{
		return sqlHotel.darHotels (pmf.getPersistenceManager());
	}




	public Hotel darHotelPorId (long id)
	{
		return sqlHotel.darHotelPorId (pmf.getPersistenceManager(), id);
	}

	/* ****************************************************************
	 * 			Métodos para manejar PROVEEDOR
	 *****************************************************************/


	public Propietario adicionarPropietario(String idPropietario,String tipo, String nombre, int telefono )
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long idServicio = nextval ();
			long tuplasInsertadas = sqlPropietario.adicionarPropietario(pm, idPropietario, tipo, nombre, telefono);
			tx.commit();

			log.trace ("Inserción de tipo de bebida: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Propietario();
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}




	public long eliminarPropietarioPorId (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlPropietario.eliminarPropietarioPorId(pm, id);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	public List<Propietario> darPropietarios ()
	{
		return sqlPropietario.darPropietarios (pmf.getPersistenceManager());
	}




	public Propietario darPropietarioPorId (long id)
	{
		return sqlPropietario.darPropietarioPorId (pmf.getPersistenceManager(), id);
	}
	/* ****************************************************************
	 * 			Métodos para manejar PROVEEDOR
	 *****************************************************************/


	public Proveedor adicionarProveedor(String idProveedor,String tipo, String nombre )
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long idServicio = nextval ();
			long tuplasInsertadas = sqlProveedor.adicionarProveedor(pm, idProveedor, tipo, nombre);
			tx.commit();

			log.trace ("Inserción de tipo de bebida: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Proveedor();
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}




	public long eliminarProveedorPorId (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlProveedor.eliminarProveedorPorId(pm, id);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	public List<Proveedor> darProveedores ()
	{
		return sqlProveedor.darProveedors (pmf.getPersistenceManager());
	}




	public Proveedor darProveedorPorId (long id)
	{
		return sqlProveedor.darProveedorPorId (pmf.getPersistenceManager(), id);
	}

	


	public long [] limpiarParranderos ()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long [] resp = sqlUtil.limpiarAlohandes(pm) ;
			tx.commit ();
			log.info ("Borrada la base de datos");
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return new long[] {-1, -1, -1, -1, -1, -1, -1};
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

	}

	public SQLReserva getSqlReserva() {
		return sqlReserva;
	}

	public void setSqlReserva(SQLReserva sqlReserva) {
		this.sqlReserva = sqlReserva;
	}

	public SQLViviendaUniversitaria getSqlViviendaUniversitaria() {
		return sqlViviendaUniversitaria;
	}

	public void setSqlViviendaUniversitaria(SQLViviendaUniversitaria sqlViviendaUniversitaria) {
		this.sqlViviendaUniversitaria = sqlViviendaUniversitaria;
	}

	public SQLPropietario getSqlPropietario() {
		return sqlPropietario;
	}

	public void setSqlPropietario(SQLPropietario sqlPropietario) {
		this.sqlPropietario = sqlPropietario;
	}

	public String darSeqAlohandes() {
		return null;
	}


}
