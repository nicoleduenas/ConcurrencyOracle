/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogot√°	- Colombia)
 * Departamento	de	Ingenier√≠a	de	Sistemas	y	Computaci√≥n
 * Licenciado	bajo	el	esquema	Academic Free License versi√≥n 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Alohandes Uniandes
 * @version 1.0
 * @author Germ√°n Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jim√©nez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import com.google.gson.JsonObject;
import uniandes.isis2304.parranderos.persistencia.PersistenciaAlohandes;

/**
 * Clase principal del negocio
 * Sarisface todos los requerimientos funcionales del negocio
 *
 * @author Germ√°n Bravo
 */
public class Alohandes
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecuci√≥n
	 */
	private static Logger log = Logger.getLogger(Alohandes.class.getName());

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia
	 */
	private PersistenciaAlohandes pp;

	/* ****************************************************************
	 * 			M√©todos
	 *****************************************************************/
	/**
	 * El constructor por defecto
	 */
	public Alohandes ()
	{
		pp = PersistenciaAlohandes.getInstance ();
	}

	/**
	 * El constructor qye recibe los nombres de las tablas en tableConfig
	 * @param tableConfig - Objeto Json con los nombres de las tablas y de la unidad de persistencia
	 */
	public Alohandes (JsonObject tableConfig)
	{
		pp = PersistenciaAlohandes.getInstance (tableConfig);
	}

	/**
	 * Cierra la conexi√≥n con la base de datos (Unidad de persistencia)
	 */
	public void cerrarUnidadPersistencia ()
	{
		pp.cerrarUnidadPersistencia ();
	}

	
	/* ****************************************************************
	 * 			M√©todos para manejar las AlojamientoS
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente una Alojamiento 
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param nombre - El nombre la Alojamiento
	 * @param idServicio - El identificador del Servicio de la Alojamiento - Debe existir un Servicio con este identificador
	 * @param gradoAlcohol - El grado de alcohol de la Alojamiento (Mayor que 0)
	 * @return El objeto Alojamiento adicionado. null si ocurre alguna Excepci√≥n
	 */
	public Alojamiento adicionarAlojamiento (long id, char habilitado,String nombre,  String tipo, String servicios)
	{
		log.info ("Adicionando Alojamiento " + nombre);
		Alojamiento alojamiento = pp.adicionarAlojamiento (id,habilitado, nombre, tipo, servicios);
		log.info ("Adicionando alojamiento: " + alojamiento);
		return alojamiento;
	}



	/**
	 * Elimina una Alojamiento por su identificador
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param idAlojamiento - El identificador de la Alojamiento a eliminar
	 * @return El n√∫mero de tuplas eliminadas (1 o 0)
	 */
	public long eliminarAlojamientoPorId (long idAlojamiento)
	{
		log.info ("Eliminando Alojamiento por id: " + idAlojamiento);
		long resp = pp.eliminarAlojamientoPorId (idAlojamiento);
		log.info ("Eliminando Alojamiento por id: " + resp + " tuplas eliminadas");
		return resp;
	}

	/**
	 * Encuentra todas las Alojamiento en Alohandes
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @return Una lista de objetos Alojamiento con todos las Alojamientos que conoce la aplicaci√≥n, llenos con su informaci√≥n b√°sica
	 */
	public List<Alojamiento> darAlojamientos ()
	{
		log.info ("Consultando Alojamientos");
		List<Alojamiento> alojamientos = pp.darAlojamientos ();	
		log.info ("Consultando Alojamientos: " + alojamientos.size() + " Alojamientos existentes");
		return alojamientos;
	}

	/**
	 * Encuentra todos los Servicios en Alohandes y los devuelve como una lista de VOServicio
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @return Una lista de objetos VOAlojamiento con todos las Alojamientos que conoce la aplicaci√≥n, llenos con su informaci√≥n b√°sica
	 */
	public List<VOAlojamiento> darVOAlojamientos ()
	{
		log.info ("Generando los VO de las Alojamientos");       
		List<VOAlojamiento> voAlojamientos = new LinkedList<VOAlojamiento> ();
		for (Alojamiento beb : pp.darAlojamientos ())
		{
			voAlojamientos.add (beb);
		}
		log.info ("Generando los VO de las Alojamientos: " + voAlojamientos.size() + " existentes");
		return voAlojamientos;
	}

	
	public Alojamiento darAlojamientosPorId(long id)
	{
		log.info ("Dar informaci√≥n de un alojamiento por id: " + id);
		Alojamiento alojamiento = pp.darAlojamientoPorId (id);
		log.info ("Buscando alojamiento por Id: " + alojamiento != null ? alojamiento : "NO EXISTE");
		return alojamiento;
	}

	/* ****************************************************************
	 * 			M√©todos para manejar los BEBEDORES
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente un cliente 
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param nombre - El nombre del cliente
	 * @param presupuesto - El presupuesto del cliente (ALTO, MEDIO, BAJO)
	 * @param ciudad - La ciudad del cliente
	 * @return El objeto BEBEDOR adicionado. null si ocurre alguna Excepci√≥n
	 */
	public Cliente adicionarCliente (String documento,  String nombre, String ciudad, String contrasena, String email, long telefono, String genero, String vinculacion, Date fechaNacimiento)
	{
		log.info ("Adicionando cliente: " + nombre);
		Cliente cliente = pp.adicionarCliente(documento, nombre, email, ciudad, contrasena, genero, fechaNacimiento, vinculacion);
				log.info ("Adicionando cliente: " + cliente);
		return cliente;
	}


	/**
	 * Elimina un cliente por su identificador
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param idCliente - El identificador del cliente a eliminar
	 * @return El n√∫mero de tuplas eliminadas
	 */
	public long eliminarClientePorId (long idCliente)
	{
		log.info ("Eliminando cliente por id: " + idCliente);
		long resp = pp.eliminarClientePorId (idCliente);
		log.info ("Eliminando cliente por Id: " + resp + " tuplas eliminadas");
		return resp;
	}

	/**
	 * Encuentra un cliente y su informaci√≥n b√°sica, seg√∫n su identificador
	 * @param idCliente - El identificador del cliente buscado
	 * @return Un objeto Cliente que corresponde con el identificador buscado y lleno con su informaci√≥n b√°sica
	 * 			null, si un cliente con dicho identificador no existe
	 */
	public Cliente darClientePorId (long idCliente)
	{
		log.info ("Dar informaci√≥n de un cliente por id: " + idCliente);
		Cliente cliente = pp.darClientePorId (idCliente);
		log.info ("Buscando cliente por Id: " + cliente != null ? cliente : "NO EXISTE");
		return cliente;
	}

	

	


	public List<Cliente> darClientes ()
	{
		log.info ("Listando Clientees");
		List<Cliente> clientes = pp.darClientes ();	
		log.info ("Listando Clientes: " + clientes.size() + " clientes existentes");
		return clientes;
	}

	/**
	 * Encuentra todos los clientees en Alohandes y los devuelve como VOCliente
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @return Una lista de objetos VOCliente con todos las clientees que conoce la aplicaci√≥n, llenos con su informaci√≥n b√°sica
	 */
	public List<VOCliente> darVOClientes ()
	{
		log.info ("Generando los VO de Clientees");
		List<VOCliente> voClientes = new LinkedList<VOCliente> ();
		for (Cliente bdor : pp.darClientes ())
		{
			voClientes.add (bdor);
		}
		log.info ("Generando los VO de Clientes: " + voClientes.size() + " clientes existentes");
		return voClientes;
	}

//+++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	/**
	 * Adiciona de manera persistente una Alojamiento 
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param nombre - El nombre la HostalHabitacion
	 * @param idServicio - El identificador del Servicio de la HostalHabitacion - Debe existir un Servicio con este identificador
	 * @param gradoAlcohol - El grado de alcohol de la AptoTemporada (Mayor que 0)
	 * @return El objeto AptoTemporada adicionado. null si ocurre alguna Excepci√≥n
	 */
	public AptoTemporada adicionarAptoTemporada (  long idAlojamiento, long idProveedor, Integer precio, String menaje, Integer habitaciones)
	{
		log.info ("Adicionando AptoTemporada " );
		AptoTemporada AptoTemporada = pp.adicionarAptoTemporada(idAlojamiento, idProveedor, precio, habitaciones, menaje);
		log.info ("Adicionando AptoTemporada: " + AptoTemporada);
		return AptoTemporada;
	}


	/**
	 * Elimina una AptoTemporada por su identificador
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param idAptoTemporada - El identificador de la AptoTemporada a eliminar
	 * @return El n√∫mero de tuplas eliminadas (1 o 0)
	 */
	public long eliminarAptoTemporadaPorId (long idAptoTemporada)
	{
		log.info ("Eliminando AptoTemporada por id: " + idAptoTemporada);
		long resp = pp.eliminarAptoTemporadaPorId (idAptoTemporada);
		pp.eliminarAlojamientoPorId (idAptoTemporada);
		log.info ("Eliminando AptoTemporada por id: " + resp + " tuplas eliminadas");
		return resp;
	}

	/**
	 * Encuentra todas las AptoTemporada en Alohandes
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @return Una lista de objetos AptoTemporada con todos las AptoTemporadas que conoce la aplicaci√≥n, llenos con su informaci√≥n b√°sica
	 */
	public List<AptoTemporada> darAptoTemporada ()
	{
		log.info ("Consultando AptoTemporada");
		List<AptoTemporada> AptoTemporada = pp.darAptoTemporadas ();	
		log.info ("Consultando AptoTemporada: " + AptoTemporada.size() + " AptoTemporada existentes");
		return AptoTemporada;
	}

	/**
	 * Encuentra todos los Servicios en Alohandes y los devuelve como una lista de VOServicio
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @return Una lista de objetos VOAptoTemporada con todos las AptoTemporadas que conoce la aplicaci√≥n, llenos con su informaci√≥n b√°sica
	 */
	public List<VOAptoTemporada> darVOAptoTemporada ()
	{
		log.info ("Generando los VO de las AptoTemporada");       
		List<VOAptoTemporada> voAptoTemporada = new LinkedList<VOAptoTemporada> ();
		for (AptoTemporada beb : pp.darAptoTemporadas ())
		{
			voAptoTemporada.add (beb);
		}
		log.info ("Generando los VO de las AptoTemporada: " + voAptoTemporada.size() + " existentes");
		return voAptoTemporada;
	}


	public AptoTemporada darAptoTemporadaPorId(long id)
	{
		log.info ("Dar informaci√≥n de un aptoSemestre por id: " + id);
		AptoTemporada aptoTemporada = pp.darAptoTemporadaPorId (id);
		log.info ("Buscando aptoTemporada por Id: " + aptoTemporada != null ? aptoTemporada : "NO EXISTE");
		return aptoTemporada;
	}

	//*********************************************************


	/**
	 * Adiciona de manera persistente una Alojamiento 
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param nombre - El nombre la Habitacion
	 * @param idServicio - El identificador del Servicio de la Habitacion - Debe existir un Servicio con este identificador
	 * @param gradoAlcohol - El grado de alcohol de la Habitacion (Mayor que 0)
	 * @return El objeto Habitacion adicionado. null si ocurre alguna Excepci√≥n
	 */
	public Habitacion adicionarHabitacion ( String id, String horario, int precioNoche, int capacidad, String tipoOferta,  long idHotel, long idAlojamiento)
	{
		log.info ("Adicionando Habitacion " );
		Habitacion Habitacion = pp.adicionarHabitacion(id, horario, precioNoche, capacidad, tipoOferta, idAlojamiento, idHotel);
				log.info ("Adicionando Habitacion: " + Habitacion);
		return Habitacion;
	}


	/**
	 * Elimina una Habitacion por su identificador
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param idHabitacion - El identificador de la Habitacion a eliminar
	 * @return El n√∫mero de tuplas eliminadas (1 o 0)
	 */
	public long eliminarHabitacionPorId (long idHabitacion)
	{
		log.info ("Eliminando Habitacion por id: " + idHabitacion);
		long resp = pp.eliminarHabitacionPorId (idHabitacion);
		pp.eliminarAlojamientoPorId (idHabitacion);
		log.info ("Eliminando Habitacion por id: " + resp + " tuplas eliminadas");
		return resp;
	}

	/**
	 * Encuentra todas las Habitacion en Alohandes
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @return Una lista de objetos Habitacion con todos las Habitacions que conoce la aplicaci√≥n, llenos con su informaci√≥n b√°sica
	 */
	public List<Habitacion> darHabitacion ()
	{
		log.info ("Consultando Habitacion");
		List<Habitacion> Habitacion = pp.darHabitaciones ();	
		log.info ("Consultando Habitacion: " + Habitacion.size() + " Habitacion existentes");
		return Habitacion;
	}

	/**
	 * Encuentra todos los Servicios en Alohandes y los devuelve como una lista de VOServicio
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @return Una lista de objetos VOHabitacion con todos las Habitacions que conoce la aplicaci√≥n, llenos con su informaci√≥n b√°sica
	 */
	public List<VOHabitacion> darVOHabitacion ()
	{
		log.info ("Generando los VO de las Habitacion");       
		List<VOHabitacion> voHabitacion = new LinkedList<VOHabitacion> ();
		for (Habitacion beb : pp.darHabitaciones ())
		{
			voHabitacion.add (beb);
		}
		log.info ("Generando los VO de las Habitacion: " + voHabitacion.size() + " existentes");
		return voHabitacion;
	}


	public Habitacion darHabitacionPorId(long id)
	{
		log.info ("Dar informaci√≥n de un aptoSemestre por id: " + id);
		Habitacion aptoTemporada = pp.darHabitacionPorId (id);
		log.info ("Buscando habitacion hotel por Id: " + aptoTemporada != null ? aptoTemporada: "NO EXISTE");
		return aptoTemporada;
	}

	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//	
//	/**
//	 * Adiciona de manera persistente una vivienda universitaria
//	 * Adiciona entradas al log de la aplicaci√≥n
//	 * @param amoblado 
//	 * @param nombre - El nombre la ViviendaUniversitaria
//	 * @param idServicio - El identificador del Servicio de la ViviendaUniversitaria - Debe existir un Servicio con este identificador
//	 * @param gradoAlcohol - El grado de alcohol de la ViviendaUniversitaria (Mayor que 0)
//	 * @return El objeto ViviendaUniversitaria adicionado. null si ocurre alguna Excepci√≥n
//	 */
//	public ViviendaUniversitaria adicionarViviendaUniversitaria ( long idAlojamiento,long idProveedor, Integer precioMes,char amoblado, Integer habitaciones, long telefono, String menaje, String tipoOferta)
//	{
//		log.info ("Adicionando Habitacion " );
//		ViviendaUniversitaria ViviendaUniversitaria = pp.adicionarViviendaUniversitari
//		log.info ("Adicionando ViviendaUniversitaria: " + ViviendaUniversitaria);
//		return ViviendaUniversitaria;
//	}
//
//
//	/**
//	 * Elimina una ViviendaUniversitaria por su identificador
//	 * Adiciona entradas al log de la aplicaci√≥n
//	 * @param idViviendaUniversitaria - El identificador de la ViviendaUniversitaria a eliminar
//	 * @return El n√∫mero de tuplas eliminadas (1 o 0)
//	 */
//	public long eliminarViviendaUniversitariaPorId (long idViviendaUniversitaria)
//	{
//		log.info ("Eliminando ViviendaUniversitaria por id: " + idViviendaUniversitaria);
//		long resp = pp.
//				pp.eliminarAlojamientoPorId (idViviendaUniversitaria);
//		log.info ("Eliminando ViviendaUniversitaria por id: " + resp + " tuplas eliminadas");
//		return resp;
//	}
//
//	/**
//	 * Encuentra todas las ViviendaUniversitaria en Alohandes
//	 * Adiciona entradas al log de la aplicaci√≥n
//	 * @return Una lista de objetos ViviendaUniversitaria con todos las ViviendaUniversitarias que conoce la aplicaci√≥n, llenos con su informaci√≥n b√°sica
//	 */
//	public List<ViviendaUniversitaria> darViviendaUniversitaria ()
//	{
//		log.info ("Consultando ViviendaUniversitaria");
//		List<ViviendaUniversitaria> ViviendaUniversitaria = pp.darViviendaUniversitaria ();	
//		log.info ("Consultando ViviendaUniversitaria: " + ViviendaUniversitaria.size() + " ViviendaUniversitaria existentes");
//		return ViviendaUniversitaria;
//	}
//
//	/**
//	 * Encuentra todos los Servicios en Alohandes y los devuelve como una lista de VOServicio
//	 * Adiciona entradas al log de la aplicaci√≥n
//	 * @return Una lista de objetos VOViviendaUniversitaria con todos las ViviendaUniversitarias que conoce la aplicaci√≥n, llenos con su informaci√≥n b√°sica
//	 */
//	public List<VOViviendaUniversitaria> darVOViviendaUniversitaria ()
//	{
//		log.info ("Generando los VO de las ViviendaUniversitaria");       
//		List<VOViviendaUniversitaria> voViviendaUniversitaria = new LinkedList<VOViviendaUniversitaria> ();
//		for (ViviendaUniversitaria beb : pp.darViviendasUniversitarias ())
//		{
//			voViviendaUniversitaria.add (beb);
//		}
//		log.info ("Generando los VO de las ViviendaUniversitaria: " + voViviendaUniversitaria.size() + " existentes");
//		return voViviendaUniversitaria;
//	}
//
//
//	public ViviendaUniversitaria darViviendaUniversitariaPorId(long id)
//	{
//		log.info ("Dar informaci√≥n de un vivienda universitaria por id: " + id);
//		ViviendaUniversitaria viviendaUniversitaria = pp.darViviendaUniversitariaPorId (id);
//		log.info ("Buscando viviendaUniversitaria por Id: " + viviendaUniversitaria!= null ? viviendaUniversitaria: "NO EXISTE");
//		return viviendaUniversitaria;
//	}

	/**
	 * Adiciona de manera persistente una Alojamiento 
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param nombre - El nombre la Alojamiento
	 * @param idServicio - El identificador del Servicio de la Alojamiento - Debe existir un Servicio con este identificador
	 * @param gradoAlcohol - El grado de alcohol de la Alojamiento (Mayor que 0)
	 * @return El objeto Alojamiento adicionado. null si ocurre alguna Excepci√≥n
	 */
	public Hotel adicionarHotel (long idHotel, String nombre, String ubicacion, String email, long idProveedor, int telefono, String horario)
	{
		log.info ("Adicionando Hotel " + nombre);
		Hotel hotel = pp.adicionarHotel(idHotel, nombre, ubicacion, horario, telefono, email, idProveedor);
		log.info ("Adicionando Hotel: " + nombre);
		return hotel;
	}

	

	/**
	 * Elimina una Hotel por su identificador
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param idHotel - El identificador de la Hotel a eliminar
	 * @return El n√∫mero de tuplas eliminadas (1 o 0)
	 */
	public long eliminarHotelPorId (long idHotel)
	{
		log.info ("Eliminando Hotel por id: " + idHotel);
		long resp = pp.eliminarHotelPorId (idHotel);
		log.info ("Eliminando Hotel por id: " + resp + " tuplas eliminadas");
		return resp;
	}

	/**
	 * Encuentra todas las Hotel en Alohandes
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @return Una lista de objetos Hotel con todos las Hotels que conoce la aplicaci√≥n, llenos con su informaci√≥n b√°sica
	 */
	public List<Hotel> darHoteles ()
	{
		log.info ("Consultando Hoteles");
		List<Hotel> hoteles = pp.darHoteles ();	
		log.info ("Consultando Hoteles: " + hoteles.size() + " Hoteles existentes");
		return hoteles;
	}

	/**
	 * Encuentra todos los Servicios en Alohandes y los devuelve como una lista de VOServicio
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @return Una lista de objetos VOHotel con todos las Hotels que conoce la aplicaci√≥n, llenos con su informaci√≥n b√°sica
	 */
	public List<VOHotel> darVOHoteles ()
	{
		log.info ("Generando los VO de las Hoteles");       
		List<VOHotel> voHoteles = new LinkedList<VOHotel> ();
		for (Hotel beb : pp.darHoteles ())
		{
			voHoteles.add (beb);
		}
		log.info ("Generando los VO de las Hoteles: " + voHoteles.size() + " existentes");
		return voHoteles;
	}


	public Hotel darHotelesPorId(long id)
	{
		log.info ("Dar informaci√≥n de un Hotel por id: " + id);
		Hotel hotel = pp.darHotelPorId (id);
		log.info ("Buscando Hotel por Id: " + hotel != null ? hotel : "NO EXISTE");
		return hotel;
	}

//	*****************************************************************/
	/**
	 * Adiciona de manera persistente una Alojamiento 
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param nombre - El nombre la Alojamiento
	 * @param idServicio - El identificador del Servicio de la Alojamiento - Debe existir un Servicio con este identificador
	 * @param gradoAlcohol - El grado de alcohol de la Alojamiento (Mayor que 0)
	 * @return El objeto Alojamiento adicionado. null si ocurre alguna Excepci√≥n
	 */
	public Hostal adicionarHostal (long idHostal, String nombre, String horario,String ubicacion, String email, long idProveedor, int telefono)
	{
		log.info ("Adicionando Hostal " + nombre);
		Hostal hostal = pp.adicionarHostal(idHostal, nombre, ubicacion, horario, telefono, email, idProveedor);
		log.info ("Adicionando Hostal: " + nombre);
		return hostal;
	}

	
	/**
	 * Elimina una Hostal por su identificador
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param idHostal - El identificador de la Hostal a eliminar
	 * @return El n√∫mero de tuplas eliminadas (1 o 0)
	 */
	public long eliminarHostalPorId (long idHostal)
	{
		log.info ("Eliminando Hostal por id: " + idHostal);
		long resp = pp.eliminarHostalPorId (idHostal);
		log.info ("Eliminando Hostal por id: " + resp + " tuplas eliminadas");
		return resp;
	}

	/**
	 * Encuentra todas las Hostal en Alohandes
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @return Una lista de objetos Hostal con todos las Hostals que conoce la aplicaci√≥n, llenos con su informaci√≥n b√°sica
	 */
	public List<Hostal> darHostales ()
	{
		log.info ("Consultando Hostales");
		List<Hostal> hoteles = pp.darHostals ();	
		log.info ("Consultando Hostales: " + hoteles.size() + " Hostales existentes");
		return hoteles;
	}

	/**
	 * Encuentra todos los Servicios en Alohandes y los devuelve como una lista de VOServicio
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @return Una lista de objetos VOHostal con todos las Hostals que conoce la aplicaci√≥n, llenos con su informaci√≥n b√°sica
	 */
	public List<VOHostal> darVOHostales ()
	{
		log.info ("Generando los VO de las Hostales");       
		List<VOHostal> voHostales = new LinkedList<VOHostal> ();
		for (Hostal beb : pp.darHostals ())
		{
			voHostales.add (beb);
		}
		log.info ("Generando los VO de las Hostales: " + voHostales.size() + " existentes");
		return voHostales;
	}



	public Hostal darHostalesPorId(long id)
	{
		log.info ("Dar informaci√≥n de un Hostal por id: " + id);
		Hostal hotel = pp.darHostalPorId (id);
		log.info ("Buscando Hostal por Id: " + hotel != null ? hotel : "NO EXISTE");
		return hotel;
	}



	//*****************************************************************/
	/**
	 * Adiciona de manera persistente una Alojamiento 
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param nombre - El nombre la Alojamiento
	 * @param idServicio - El identificador del Servicio de la Alojamiento - Debe existir un Servicio con este identificador
	 * @param gradoAlcohol - El grado de alcohol de la Alojamiento (Mayor que 0)
	 * @return El objeto Alojamiento adicionado. null si ocurre alguna Excepci√≥n
	 */
	public Propietario adicionarPropietario (long id, long idProveedor, String email, int telefono, String vinculacion,String nombre, String tipo)
	{
		log.info ("Adicionando Propietario " + id);
		Propietario hostal = pp.adicionarPropietario(id, idProveedor, email, tipo, vinculacion, nombre, telefono);
		log.info ("Adicionando Propietario: " + id);
		return hostal;
	}

	

	/**
	 * Elimina una Propietario por su identificador
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param idPropietario - El identificador de la Propietario a eliminar
	 * @return El n√∫mero de tuplas eliminadas (1 o 0)
	 */
	public long eliminarPropietarioPorId (long idPropietario)
	{
		log.info ("Eliminando Propietario por id: " + idPropietario);
		long resp = pp.eliminarPropietarioPorId (idPropietario);
		log.info ("Eliminando Propietario por id: " + resp + " tuplas eliminadas");
		return resp;
	}

	/**
	 * Encuentra todas las Propietario en Alohandes
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @return Una lista de objetos Propietario con todos las Propietarios que conoce la aplicaci√≥n, llenos con su informaci√≥n b√°sica
	 */
	public List<Propietario> darPropietarios ()
	{
		log.info ("Consultando Propietarios");
		List<Propietario> hoteles = pp.darPropietarios ();	
		log.info ("Consultando Propietarios: " + hoteles.size() + " Propietarios existentes");
		return hoteles;
	}

	/**
	 * Encuentra todos los Servicios en Alohandes y los devuelve como una lista de VOServicio
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @return Una lista de objetos VOPropietario con todos las Propietarios que conoce la aplicaci√≥n, llenos con su informaci√≥n b√°sica
	 */
	public List<VOPropietario> darVOPropietarios ()
	{
		log.info ("Generando los VO de las Propietarios");       
		List<VOPropietario> voPropietarios = new LinkedList<VOPropietario> ();
		for (Propietario beb : pp.darPropietarios ())
		{
			voPropietarios.add (beb);
		}
		log.info ("Generando los VO de las Propietarios: " + voPropietarios.size() + " existentes");
		return voPropietarios;
	}

	/**
	 * Elimina las Propietarios que no son servidas en ning√∫n bar (No son referenciadas en ninguna tupla de SIRVEN)
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @return El n√∫mero de Propietarios eliminadas
	 */
	

	public Propietario darPropietariosPorId(long id)
	{
		log.info ("Dar informaci√≥n de un Propietario por id: " + id);
		Propietario hotel = pp.darPropietarioPorId (id);
		log.info ("Buscando Propietario por Id: " + hotel != null ? hotel : "NO EXISTE");
		return hotel;
	}


//	*****************************************************************/
	/**
	 * Adiciona de manera persistente una Alojamiento 
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param nombre - El nombre la Alojamiento
	 * @param idServicio - El identificador del Servicio de la Alojamiento - Debe existir un Servicio con este identificador
	 * @param gradoAlcohol - El grado de alcohol de la Alojamiento (Mayor que 0)
	 * @return El objeto Alojamiento adicionado. null si ocurre alguna Excepci√≥n
	 */
	public Proveedor adicionarProveedor (long id, String tipo, String nombre)
	{
		log.info ("Adicionando Proveedor " );
		Proveedor Proveedor = pp.adicionarProveedor (id, tipo, nombre);
		log.info ("Adicionando Proveedor: " + Proveedor);
		return Proveedor;
	}



	/**
	 * Elimina una Proveedor por su identificador
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param idProveedor - El identificador de la Proveedor a eliminar
	 * @return El n√∫mero de tuplas eliminadas (1 o 0)
	 */
	public long eliminarProveedorPorId (long idProveedor)
	{
		log.info ("Eliminando Proveedor por id: " + idProveedor);
		long resp = pp.eliminarProveedorPorId (idProveedor);
		log.info ("Eliminando Proveedor por id: " + resp + " tuplas eliminadas");
		return resp;
	}

	/**
	 * Encuentra todas las Proveedor en Alohandes
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @return Una lista de objetos Proveedor con todos las Proveedores que conoce la aplicaci√≥n, llenos con su informaci√≥n b√°sica
	 */
	public List<Proveedor> darProveedores ()
	{
		log.info ("Consultando Proveedores");
		List<Proveedor> Proveedores = pp.darProveedores ();	
		log.info ("Consultando Proveedores: " + Proveedores.size() + " Proveedores existentes");
		return Proveedores;
	}

	/**
	 * Encuentra todos los Servicios en Alohandes y los devuelve como una lista de VOServicio
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @return Una lista de objetos VOProveedor con todos las Proveedores que conoce la aplicaci√≥n, llenos con su informaci√≥n b√°sica
	 */
	public List<VOProveedor> darVOProveedores ()
	{
		log.info ("Generando los VO de las Proveedores");       
		List<VOProveedor> voProveedores = new LinkedList<VOProveedor> ();
		for (Proveedor beb : pp.darProveedores ())
		{
			voProveedores.add (beb);
		}
		log.info ("Generando los VO de las Proveedores: " + voProveedores.size() + " existentes");
		return voProveedores;
	}



	public Proveedor darProveedoresPorId(long id)
	{
		log.info ("Dar informaci√≥n de un alojamiento por id: " + id);
		Proveedor proveedor = pp.darProveedorPorId (id);
		log.info ("Buscando proveedor por Id: " + proveedor != null ? proveedor : "NO EXISTE");
		return proveedor;
	}

	
//
//
//	//*****************************************************************/
	/**
	 * Adiciona de manera persistente una Alojamiento 
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param personas 
	 * @param nombre - El nombre la Alojamiento
	 * @param idServicio - El identificador del Servicio de la Alojamiento - Debe existir un Servicio con este identificador
	 * @param gradoAlcohol - El grado de alcohol de la Alojamiento (Mayor que 0)
	 * @return El objeto Alojamiento adicionado. null si ocurre alguna Excepci√≥n
	 */
	public Reserva adicionarReserva (long id, long idAlojamiento, Integer descuento, Integer personas, Integer precioTotal, 
			Date fechaCheckIn, Date fechaCheckOut,Date fechaConfirmacion, Integer cantPagos, long idCliente)
	{
		log.info ("Adicionando Reserva " );
		Reserva Reserva = pp.adicionarReserva (id, idAlojamiento, descuento, personas, precioTotal,fechaCheckIn,fechaCheckOut,fechaConfirmacion, cantPagos, idCliente);
		log.info ("Adicionando Reserva: " + Reserva);
		return Reserva;
	}

	/**
	 * Elimina una Reserva por su nombre
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param nombre - El nombre de la Reserva a eliminar
	 * @return El n√∫mero de tuplas eliminadas
	 */
	public long eliminarReservaPorCliente (long idCliente)
	{
		log.info ("Eliminando Reserva por cliente: " + idCliente);
		long resp = pp.eliminarReservaPorCliente (idCliente);
		log.info ("Eliminando Reserva por cliente: " + resp + " tuplas eliminadas");
		return resp;
	}

	/**
	 * Elimina una Reserva por su identificador
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param idReserva - El identificador de la Reserva a eliminar
	 * @return El n√∫mero de tuplas eliminadas (1 o 0)
	 */
	public long eliminarReservaPorId (long idReserva)
	{
		log.info ("Eliminando Reserva por id: " + idReserva);
		long resp = pp.eliminarReservaPorId (idReserva);
		log.info ("Eliminando Reserva por id: " + resp + " tuplas eliminadas");
		return resp;
	}

	/**
	 * Encuentra todas las Reserva en Alohandes
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @return Una lista de objetos Reserva con todos las Reservas que conoce la aplicaci√≥n, llenos con su informaci√≥n b√°sica
	 */
	public List<Reserva> darReservas ()
	{
		log.info ("Consultando Reservas");
		List<Reserva> Reservas = pp.darReservas ();	
		log.info ("Consultando Reservas: " + Reservas.size() + " Reservas existentes");
		return Reservas;
	}

	/**
	 * Encuentra todos los Servicios en Alohandes y los devuelve como una lista de VOServicio
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @return Una lista de objetos VOReserva con todos las Reservas que conoce la aplicaci√≥n, llenos con su informaci√≥n b√°sica
	 */
	public List<VOReserva> darVOReservas ()
	{
		log.info ("Generando los VO de las Reservas");       
		List<VOReserva> voReservas = new LinkedList<VOReserva> ();
		for (Reserva beb : pp.darReservas ())
		{
			voReservas.add (beb);
		}
		log.info ("Generando los VO de las Reservas: " + voReservas.size() + " existentes");
		return voReservas;
	}

	/**
	 * Elimina las Reservas que no son servidas en ning√∫n bar (No son referenciadas en ninguna tupla de SIRVEN)
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @return El n√∫mero de Reservas eliminadas
	 */
	public Reserva  darReservasPorCliente(long cliente)
	{
		log.info ("Buscando Reserva por cliente: " + cliente);
		List<Reserva> tb = pp.darReservaPorCliente (cliente);
		return !tb.isEmpty () ? tb.get (0) : null;
	}

	public Reserva darReservasPorId(long id)
	{
		log.info ("Dar informaci√≥n de un proveedor por id: " + id);
		Reserva reserva = pp.darReservaPorId (id);
		log.info ("Buscando reserva por Id: " + reserva != null ? reserva : "NO EXISTE");
		return reserva;
	}
//



	//*****************************************************************/
	/**
	 * Adiciona de manera persistente una Alojamiento 
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param nombre - El nombre la Alojamiento
	 * @param idServicio - El identificador del Servicio de la Alojamiento - Debe existir un Servicio con este identificador
	 * @param gradoAlcohol - El grado de alcohol de la Alojamiento (Mayor que 0)
	 * @return El objeto Alojamiento adicionado. null si ocurre alguna Excepci√≥n
	 */
	public Empresa adicionarEmpresa (long id,  String nombre, String email, long idProveedor, char registrado)
	{
		log.info ("Adicionando Empresa " + id);
		Empresa empresa = pp.adicionarEmpresa(id, nombre, email, idProveedor, registrado);
		log.info ("Adicionando Empresa: " + id);
		return empresa;
	}



	/**
	 * Elimina una Empresa por su identificador
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param idEmpresa - El identificador de la Empresa a eliminar
	 * @return El n√∫mero de tuplas eliminadas (1 o 0)
	 */
	public long eliminarEmpresaPorId (long idEmpresa)
	{
		log.info ("Eliminando Empresa por id: " + idEmpresa);
		long resp = pp.eliminarEmpresaPorId (idEmpresa);
		log.info ("Eliminando Empresa por id: " + resp + " tuplas eliminadas");
		return resp;
	}

	/**
	 * Encuentra todas las Empresa en Alohandes
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @return Una lista de objetos Empresa con todos las Empresas que conoce la aplicaci√≥n, llenos con su informaci√≥n b√°sica
	 */
	public List<Empresa> darEmpresas ()
	{
		log.info ("Consultando Empresas");
		List<Empresa> empresas = pp.darEmpresas ();	
		log.info ("Consultando Empresas: " + empresas.size() + " Empresas existentes");
		return empresas;
	}

	/**
	 * Encuentra todos los Servicios en Alohandes y los devuelve como una lista de VOServicio
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @return Una lista de objetos VOEmpresa con todos las Empresas que conoce la aplicaci√≥n, llenos con su informaci√≥n b√°sica
	 */
	public List<VOEmpresa> darVOEmpresas ()
	{
		log.info ("Generando los VO de las Empresas");       
		List<VOEmpresa> voEmpresas = new LinkedList<VOEmpresa> ();
		for (Empresa beb : pp.darEmpresas ())
		{
			voEmpresas.add (beb);
		}
		log.info ("Generando los VO de las Empresas: " + voEmpresas.size() + " existentes");
		return voEmpresas;
	}

	

	public Empresa darEmpresasPorId(long id)
	{
		log.info ("Dar informaci√≥n de un empresa por id: " + id);
		Empresa empresa = pp.darEmpresaPorId (id);
		log.info ("Buscando empresa por Id: " + empresa != null ? empresa : "NO EXISTE");
		return empresa;
	}


	

	/* ****************************************************************
	 * 			M√©todos para administraci√≥n
	 *****************************************************************/

	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de Alohandes
	 * @return Un arreglo con 7 n√∫meros que indican el n√∫mero de tuplas borradas en las tablas GUSTAN, SIRVEEmpresa Alojamiento,
	 * Servicio, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarAlohandes ()
	{
		log.info ("Limpiando la BD de Alohandes");
		long [] borrrados = pp.limpiarAlohandes();	
		log.info ("Limpiando la BD de Alohandes: Listo!");
		return borrrados;
	}
}
