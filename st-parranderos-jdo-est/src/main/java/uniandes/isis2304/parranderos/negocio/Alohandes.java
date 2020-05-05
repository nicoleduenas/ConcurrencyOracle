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
	public Alojamiento adicionarAlojamiento (String nombre, String ubicacion, long tipooferta, long tipo)
	{
		log.info ("Adicionando Alojamiento " + nombre);
		Alojamiento alojamiento = pp.adicionarAlojamiento (nombre, ubicacion, tipooferta, tipo);
		log.info ("Adicionando alojamiento: " + alojamiento);
		return alojamiento;
	}

	/**
	 * Elimina una Alojamiento por su nombre
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param nombre - El nombre de la Alojamiento a eliminar
	 * @return El n√∫mero de tuplas eliminadas
	 */
	public long eliminarAlojamientoPorNombre (String nombre)
	{
		log.info ("Eliminando Alojamiento por nombre: " + nombre);
		long resp = pp.eliminarAlojamientoPorNombre (nombre);
		log.info ("Eliminando Alojamiento por nombre: " + resp + " tuplas eliminadas");
		return resp;
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

	/**
	 * Elimina las Alojamientos que no son servidas en ning√∫n bar (No son referenciadas en ninguna tupla de SIRVEN)
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @return El n√∫mero de Alojamientos eliminadas
	 */
	public Alojamiento  darAlojamientosPorNombre(String nombre)
	{
		log.info ("Buscando Alojamiento por nombre: " + nombre);
		List<Alojamiento> tb = pp.darAlojamientoPorNombre (nombre);
		return !tb.isEmpty () ? tb.get (0) : null;
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
	public Cliente adicionarCliente (long documento,  String nombre, String ciudad, String contrasena, String email, long telefono, String genero, String vinculacion, Date fechaNacimiento)
	{
		log.info ("Adicionando cliente: " + nombre);
		Cliente cliente = pp.adicionarCliente (documento, nombre, ciudad, contrasena, email, telefono, genero, vinculacion, fechaNacimiento);
		log.info ("Adicionando cliente: " + cliente);
		return cliente;
	}

	/**
	 * Elimina un cliente por su nombre
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param nombre - El nombre del cliente a eliminar
	 * @return El n√∫mero de tuplas eliminadas
	 */
	public long eliminarClientePorNombre (String nombre)
	{
		log.info ("Eliminando cliente por nombre: " + nombre);
		long resp = pp.eliminarClientePorNombre (nombre);
		log.info ("Eliminando cliente por nombre: " + resp + " tuplas eliminadas");
		return resp;
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

	/**
	 * Encuentra la informaci√≥n b√°sica de los clientees, seg√∫n su nombre
	 * @param nombre - El nombre de cliente a buscar
	 * @return Una lista de Clientees con su informaci√≥n b√°sica, donde todos tienen el nombre buscado.
	 * 	La lista vac√≠a indica que no existen clientees con ese nombre
	 */
	public List<Cliente> darClientesPorNombre (String nombre)
	{
		log.info ("Dar informaci√≥n de clientes por nombre: " + nombre);
		List<Cliente> clientes = pp.darClientesPorNombre (nombre);
		log.info ("Dar informaci√≥n de Clientes por nombre: " + clientes.size() + " clientes con ese nombre existentes");
		return clientes;
	}

	/**
	 * Encuentra la informaci√≥n b√°sica de los clientees, seg√∫n su nombre y los devuelve como VO
	 * @param nombre - El nombre de cliente a buscar
	 * @return Una lista de Clientees con su informaci√≥n b√°sica, donde todos tienen el nombre buscado.
	 * 	La lista vac√≠a indica que no existen clientees con ese nombre
	 */
	public List<VOCliente> darVOClientePorNombre (String nombre)
	{
		log.info ("Generando VO de clientees por nombre: " + nombre);
		List<VOCliente> voClientees = new LinkedList<VOCliente> ();
		for (Cliente bdor : pp.darClienteesPorNombre (nombre))
		{
			voClientees.add (bdor);
		}
		log.info ("Generando los VO de Clientes: " + voClientees.size() + " clientees existentes");
		return voClientees;
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
			voClientees.add (bdor);
		}
		log.info ("Generando los VO de Clientes: " + voClientes.size() + " clientes existentes");
		return voClientes;
	}


	*****************************************************************/
	/**
	 * Adiciona de manera persistente una Alojamiento 
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param nombre - El nombre la AptoSemestre
	 * @param idServicio - El identificador del Servicio de la AptoSemestre - Debe existir un Servicio con este identificador
	 * @param gradoAlcohol - El grado de alcohol de la AptoSemestre (Mayor que 0)
	 * @return El objeto AptoSemestre adicionado. null si ocurre alguna Excepci√≥n
	 */
	public AptoSemestre adicionarAptoSemestre ( long id, Integer precioMes, Boolean amoblado, Integer habitaciones)
	{
		log.info ("Adicionando AptoSemestre " );
		AptoSemestre AptoSemestre = pp.adicionarAptoSemestre (id, precioMes, amoblado, habitaciones);
		log.info ("Adicionando AptoSemestre: " + AptoSemestre);
		return AptoSemestre;
	}


	/**
	 * Elimina una AptoSemestre por su identificador
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param idAptoSemestre - El identificador de la AptoSemestre a eliminar
	 * @return El n√∫mero de tuplas eliminadas (1 o 0)
	 */
	public long eliminarAptoSemestrePorId (long idAptoSemestre)
	{
		log.info ("Eliminando AptoSemestre por id: " + idAptoSemestre);
		long resp = pp.eliminarAptoSemestrePorId (idAptoSemestre);
		pp.eliminarAlojamientoPorId (idAptoSemestre);
		log.info ("Eliminando AptoSemestre por id: " + resp + " tuplas eliminadas");
		return resp;
	}

	/**
	 * Encuentra todas las AptoSemestre en Alohandes
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @return Una lista de objetos AptoSemestre con todos las AptoSemestres que conoce la aplicaci√≥n, llenos con su informaci√≥n b√°sica
	 */
	public List<AptoSemestre> darAptoSemestres ()
	{
		log.info ("Consultando AptoSemestres");
		List<AptoSemestre> AptoSemestres = pp.darAptoSemestres ();	
		log.info ("Consultando AptoSemestres: " + AptoSemestres.size() + " AptoSemestres existentes");
		return AptoSemestres;
	}

	/**
	 * Encuentra todos los Servicios en Alohandes y los devuelve como una lista de VOServicio
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @return Una lista de objetos VOAptoSemestre con todos las AptoSemestres que conoce la aplicaci√≥n, llenos con su informaci√≥n b√°sica
	 */
	public List<VOAptoSemestre> darVOAptoSemestres ()
	{
		log.info ("Generando los VO de las AptoSemestres");       
		List<VOAptoSemestre> voAptoSemestres = new LinkedList<VOAptoSemestre> ();
		for (AptoSemestre beb : pp.darAptoSemestres ())
		{
			voAptoSemestres.add (beb);
		}
		log.info ("Generando los VO de las AptoSemestres: " + voAptoSemestres.size() + " existentes");
		return voAptoSemestres;
	}


	public AptoSemestre darAptoSemestresPorId(long id)
	{
		log.info ("Dar informaci√≥n de un aptoSemestre por id: " + id);
		AptoSemestre aptoSemestre = pp.darAptoSemestrePorId (id);
		log.info ("Buscando aptoSemestre por Id: " + aptoSemestre != null ? aptoSemestre : "NO EXISTE");
		return aptoSemestre;
	}

	*****************************************************************/

	/**
	 * Adiciona de manera persistente una Alojamiento 
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param nombre - El nombre la HostalHabitacion
	 * @param idServicio - El identificador del Servicio de la HostalHabitacion - Debe existir un Servicio con este identificador
	 * @param gradoAlcohol - El grado de alcohol de la AptoTemporada (Mayor que 0)
	 * @return El objeto AptoTemporada adicionado. null si ocurre alguna Excepci√≥n
	 */
	public AptoTemporada adicionarAptoTemporada ( long id, Integer precio, String menaje, Integer habitaciones, long idAlojamiento, long idProveedor)
	{
		log.info ("Adicionando AptoTemporada " );
		AptoTemporada AptoTemporada = pp.adicionarAptoTemporada (id, precio, menaje, habitaciones);
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
		List<AptoTemporada> AptoTemporada = pp.darAptoTemporada ();	
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
	 * @param nombre - El nombre la HostalHabitacion
	 * @param idServicio - El identificador del Servicio de la HostalHabitacion - Debe existir un Servicio con este identificador
	 * @param gradoAlcohol - El grado de alcohol de la HostalHabitacion (Mayor que 0)
	 * @return El objeto HostalHabitacion adicionado. null si ocurre alguna Excepci√≥n
	 */
	public HostalHabitacion adicionarHostalHabitacion ( long id, String horario, int precioNoche, int capacidad, String tipoOferta, long idAlojamiento,long idHostal)
	{
		log.info ("Adicionando HostalHabitacion " );
		HostalHabitacion HostalHabitacion = pp.adicionarHostalHabitacion (id, horario,precioNoche, capacidad, tipoOferta);
		log.info ("Adicionando HostalHabitacion: " + HostalHabitacion);
		return HostalHabitacion;
	}


	/**
	 * Elimina una HostalHabitacion por su identificador
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param idHostalHabitacion - El identificador de la HostalHabitacion a eliminar
	 * @return El n√∫mero de tuplas eliminadas (1 o 0)
	 */
	public long eliminarHostalHabitacionPorId (long idHostalHabitacion)
	{
		log.info ("Eliminando HostalHabitacion por id: " + idHostalHabitacion);
		long resp = pp.eliminarHostalHabitacionPorId (idHostalHabitacion);
		pp.eliminarAlojamientoPorId (idHostalHabitacion);
		log.info ("Eliminando HostalHabitacion por id: " + resp + " tuplas eliminadas");
		return resp;
	}

	/**
	 * Encuentra todas las HostalHabitacion en Alohandes
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @return Una lista de objetos HostalHabitacion con todos las HostalHabitacions que conoce la aplicaci√≥n, llenos con su informaci√≥n b√°sica
	 */
	public List<HostalHabitacion> darHostalHabitacion ()
	{
		log.info ("Consultando HostalHabitacion");
		List<HostalHabitacion> HostalHabitacion = pp.darHostalHabitacion ();	
		log.info ("Consultando HostalHabitacion: " + HostalHabitacion.size() + " HostalHabitacion existentes");
		return HostalHabitacion;
	}

	/**
	 * Encuentra todos los Servicios en Alohandes y los devuelve como una lista de VOServicio
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @return Una lista de objetos VOHostalHabitacion con todos las HostalHabitacions que conoce la aplicaci√≥n, llenos con su informaci√≥n b√°sica
	 */
	public List<VOHostalHabitacion> darVOHostalHabitacion ()
	{
		log.info ("Generando los VO de las HostalHabitacion");       
		List<VOHostalHabitacion> voHostalHabitacion = new LinkedList<VOHostalHabitacion> ();
		for (HostalHabitacion beb : pp.darHostalHabitacions ())
		{
			voHostalHabitacion.add (beb);
		}
		log.info ("Generando los VO de las HostalHabitacion: " + voHostalHabitacion.size() + " existentes");
		return voHostalHabitacion;
	}


	public HostalHabitacion darHostalHabitacionPorId(long id)
	{
		log.info ("Dar informaci√≥n de un aptoSemestre por id: " + id);
		HostalHabitacion aptoTemporada = pp.darHostalHabitacionPorId (id);
		log.info ("Buscando habitacion hostal por Id: " + aptoTemporada != null ? aptoTemporada : "NO EXISTE");
		return aptoTemporada;
	}

	*****************************************************************/

	/**
	 * Adiciona de manera persistente una Alojamiento 
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param nombre - El nombre la HotelHabitacion
	 * @param idServicio - El identificador del Servicio de la HotelHabitacion - Debe existir un Servicio con este identificador
	 * @param gradoAlcohol - El grado de alcohol de la HotelHabitacion (Mayor que 0)
	 * @return El objeto HotelHabitacion adicionado. null si ocurre alguna Excepci√≥n
	 */
	public HotelHabitacion adicionarHotelHabitacion ( long id, Integer precio, String menaje, Integer habitaciones,long idAlojamiento, long idHotel)
	{
		log.info ("Adicionando HotelHabitacion " );
		HotelHabitacion HotelHabitacion = pp.adicionarHotelHabitacion (id, precio, menaje, habitaciones);
		log.info ("Adicionando HotelHabitacion: " + HotelHabitacion);
		return HotelHabitacion;
	}


	/**
	 * Elimina una HotelHabitacion por su identificador
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param idHotelHabitacion - El identificador de la HotelHabitacion a eliminar
	 * @return El n√∫mero de tuplas eliminadas (1 o 0)
	 */
	public long eliminarHotelHabitacionPorId (long idHotelHabitacion)
	{
		log.info ("Eliminando HotelHabitacion por id: " + idHotelHabitacion);
		long resp = pp.eliminarHotelHabitacionPorId (idHotelHabitacion);
		pp.eliminarAlojamientoPorId (idHotelHabitacion);
		log.info ("Eliminando HotelHabitacion por id: " + resp + " tuplas eliminadas");
		return resp;
	}

	/**
	 * Encuentra todas las HotelHabitacion en Alohandes
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @return Una lista de objetos HotelHabitacion con todos las HotelHabitacions que conoce la aplicaci√≥n, llenos con su informaci√≥n b√°sica
	 */
	public List<HotelHabitacion> darHotelHabitacion ()
	{
		log.info ("Consultando HotelHabitacion");
		List<HotelHabitacion> HotelHabitacion = pp.darHotelHabitacion ();	
		log.info ("Consultando HotelHabitacion: " + HotelHabitacion.size() + " HotelHabitacion existentes");
		return HotelHabitacion;
	}

	/**
	 * Encuentra todos los Servicios en Alohandes y los devuelve como una lista de VOServicio
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @return Una lista de objetos VOHotelHabitacion con todos las HotelHabitacions que conoce la aplicaci√≥n, llenos con su informaci√≥n b√°sica
	 */
	public List<VOHotelHabitacion> darVOHotelHabitacion ()
	{
		log.info ("Generando los VO de las HotelHabitacion");       
		List<VOHotelHabitacion> voHotelHabitacion = new LinkedList<VOHotelHabitacion> ();
		for (HotelHabitacion beb : pp.darHotelHabitacions ())
		{
			voHotelHabitacion.add (beb);
		}
		log.info ("Generando los VO de las HotelHabitacion: " + voHotelHabitacion.size() + " existentes");
		return voHotelHabitacion;
	}


	public HotelHabitacion darHotelHabitacionPorId(long id)
	{
		log.info ("Dar informaci√≥n de un aptoSemestre por id: " + id);
		HotelHabitacion aptoTemporada = pp.darHotelHabitacionPorId (id);
		log.info ("Buscando habitacion hotel por Id: " + aptoTemporada != null ? aptoTemporada: "NO EXISTE");
		return aptoTemporada;
	}

	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	/**
	 * Adiciona de manera persistente una vivienda universitaria
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param nombre - El nombre la ViviendaUniversitaria
	 * @param idServicio - El identificador del Servicio de la ViviendaUniversitaria - Debe existir un Servicio con este identificador
	 * @param gradoAlcohol - El grado de alcohol de la ViviendaUniversitaria (Mayor que 0)
	 * @return El objeto ViviendaUniversitaria adicionado. null si ocurre alguna Excepci√≥n
	 */
	public ViviendaUniversitaria adicionarViviendaUniversitaria ( long id, Integer precio, String menaje, Integer habitaciones, long idAlojamiento, long idProveedor)
	{
		log.info ("Adicionando HotelHabitacion " );
		ViviendaUniversitaria ViviendaUniversitaria = pp.adicionarViviendaUniversitaria (id, precio, menaje, habitaciones);
		log.info ("Adicionando ViviendaUniversitaria: " + ViviendaUniversitaria);
		return ViviendaUniversitaria;
	}


	/**
	 * Elimina una ViviendaUniversitaria por su identificador
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param idViviendaUniversitaria - El identificador de la ViviendaUniversitaria a eliminar
	 * @return El n√∫mero de tuplas eliminadas (1 o 0)
	 */
	public long eliminarViviendaUniversitariaPorId (long idViviendaUniversitaria)
	{
		log.info ("Eliminando ViviendaUniversitaria por id: " + idViviendaUniversitaria);
		long resp = pp.eliminarViviendaUniversitariaPorId (idViviendaUniversitaria);
		pp.eliminarAlojamientoPorId (idViviendaUniversitaria);
		log.info ("Eliminando ViviendaUniversitaria por id: " + resp + " tuplas eliminadas");
		return resp;
	}

	/**
	 * Encuentra todas las ViviendaUniversitaria en Alohandes
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @return Una lista de objetos ViviendaUniversitaria con todos las ViviendaUniversitarias que conoce la aplicaci√≥n, llenos con su informaci√≥n b√°sica
	 */
	public List<ViviendaUniversitaria> darViviendaUniversitaria ()
	{
		log.info ("Consultando ViviendaUniversitaria");
		List<ViviendaUniversitaria> ViviendaUniversitaria = pp.darViviendaUniversitaria ();	
		log.info ("Consultando ViviendaUniversitaria: " + ViviendaUniversitaria.size() + " ViviendaUniversitaria existentes");
		return ViviendaUniversitaria;
	}

	/**
	 * Encuentra todos los Servicios en Alohandes y los devuelve como una lista de VOServicio
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @return Una lista de objetos VOViviendaUniversitaria con todos las ViviendaUniversitarias que conoce la aplicaci√≥n, llenos con su informaci√≥n b√°sica
	 */
	public List<VOViviendaUniversitaria> darVOViviendaUniversitaria ()
	{
		log.info ("Generando los VO de las ViviendaUniversitaria");       
		List<VOViviendaUniversitaria> voViviendaUniversitaria = new LinkedList<VOViviendaUniversitaria> ();
		for (ViviendaUniversitaria beb : pp.darViviendaUniversitarias ())
		{
			voViviendaUniversitaria.add (beb);
		}
		log.info ("Generando los VO de las ViviendaUniversitaria: " + voViviendaUniversitaria.size() + " existentes");
		return voViviendaUniversitaria;
	}


	public ViviendaUniversitaria darViviendaUniversitariaPorId(long id)
	{
		log.info ("Dar informaci√≥n de un vivienda universitaria por id: " + id);
		ViviendaUniversitaria viviendaUniversitaria = pp.darViviendaUniversitariaPorId (id);
		log.info ("Buscando viviendaUniversitaria por Id: " + viviendaUniversitaria!= null ? viviendaUniversitaria: "NO EXISTE");
		return viviendaUniversitaria;
	}

	/**
	 * Adiciona de manera persistente una Alojamiento 
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param nombre - El nombre la Alojamiento
	 * @param idServicio - El identificador del Servicio de la Alojamiento - Debe existir un Servicio con este identificador
	 * @param gradoAlcohol - El grado de alcohol de la Alojamiento (Mayor que 0)
	 * @return El objeto Alojamiento adicionado. null si ocurre alguna Excepci√≥n
	 */
	public Hotel adicionarHotel (long idHotel, String nombre, String ubicacion, String email, long idProveedor, long telefono)
	{
		log.info ("Adicionando Hotel " + nombre);
		Hotel hotel = pp.adicionarHotel (idHotel, nombre, ubicacion, email, idProveedor, telefono);
		log.info ("Adicionando Hotel: " + Hotel);
		return hotel;
	}

	/**
	 * Elimina una Hotel por su nombre
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param nombre - El nombre de la Hotel a eliminar
	 * @return El n√∫mero de tuplas eliminadas
	 */
	public long eliminarHotelPorNombre (String nombre)
	{
		log.info ("Eliminando Hotel por nombre: " + nombre);
		long resp = pp.eliminarHotelPorNombre (nombre);
		log.info ("Eliminando Hotel por nombre: " + resp + " tuplas eliminadas");
		return resp;
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

	/**
	 * Elimina las Hotels que no son servidas en ning√∫n bar (No son referenciadas en ninguna tupla de SIRVEN)
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @return El n√∫mero de Hotels eliminadas
	 */
	public Hotel  darHotelesPorNombre(String nombre)
	{
		log.info ("Buscando Hotel por nombre: " + nombre);
		List<Hotel> tb = pp.darHotelPorNombre (nombre);
		return !tb.isEmpty () ? tb.get (0) : null;
	}

	public Hotel darHotelesPorId(long id)
	{
		log.info ("Dar informaci√≥n de un Hotel por id: " + id);
		Hotel hotel = pp.darHotelPorId (id);
		log.info ("Buscando Hotel por Id: " + hotel != null ? hotel : "NO EXISTE");
		return hotel;
	}

	*****************************************************************/
	/**
	 * Adiciona de manera persistente una Alojamiento 
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param nombre - El nombre la Alojamiento
	 * @param idServicio - El identificador del Servicio de la Alojamiento - Debe existir un Servicio con este identificador
	 * @param gradoAlcohol - El grado de alcohol de la Alojamiento (Mayor que 0)
	 * @return El objeto Alojamiento adicionado. null si ocurre alguna Excepci√≥n
	 */
	public Hostal adicionarHostal (long idHostal, String nombre, String horario,String ubicacion, String email, long idProveedor, long telefono)
	{
		log.info ("Adicionando Hostal " + nombre);
		Hostal hostal = pp.adicionarHostal (idHostal, nombre, horario, ubicacion, email, idProveedor, telefono);
		log.info ("Adicionando Hostal: " + Hostal);
		return hostal;
	}

	/**
	 * Elimina una Hostal por su nombre
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param nombre - El nombre de la Hostal a eliminar
	 * @return El n√∫mero de tuplas eliminadas
	 */
	public long eliminarHostalPorNombre (String nombre)
	{
		log.info ("Eliminando Hostal por nombre: " + nombre);
		long resp = pp.eliminarHostalPorNombre (nombre);
		log.info ("Eliminando Hostal por nombre: " + resp + " tuplas eliminadas");
		return resp;
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
		List<Hostal> hoteles = pp.darHostales ();	
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
		for (Hostal beb : pp.darHostales ())
		{
			voHostales.add (beb);
		}
		log.info ("Generando los VO de las Hostales: " + voHostales.size() + " existentes");
		return voHostales;
	}

	/**
	 * Elimina las Hostals que no son servidas en ning√∫n bar (No son referenciadas en ninguna tupla de SIRVEN)
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @return El n√∫mero de Hostals eliminadas
	 */
	public Hostal  darHostalesPorNombre(String nombre)
	{
		log.info ("Buscando Hostal por nombre: " + nombre);
		List<Hostal> tb = pp.darHostalPorNombre (nombre);
		return !tb.isEmpty () ? tb.get (0) : null;
	}

	public Hostal darHostalesPorId(long id)
	{
		log.info ("Dar informaci√≥n de un Hostal por id: " + id);
		Hostal hotel = pp.darHostalPorId (id);
		log.info ("Buscando Hostal por Id: " + hotel != null ? hotel : "NO EXISTE");
		return hotel;
	}



	*****************************************************************/
	/**
	 * Adiciona de manera persistente una Alojamiento 
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param nombre - El nombre la Alojamiento
	 * @param idServicio - El identificador del Servicio de la Alojamiento - Debe existir un Servicio con este identificador
	 * @param gradoAlcohol - El grado de alcohol de la Alojamiento (Mayor que 0)
	 * @return El objeto Alojamiento adicionado. null si ocurre alguna Excepci√≥n
	 */
	public Propietario adicionarPropietario (long documento, String nombre, String vinculacion, String correo, long telefono, long idProveedor)
	{
		log.info ("Adicionando Propietario " + nombre);
		Propietario hostal = pp.adicionarPropietario ( documento, correo , telefono, nombre, vinculacion, idProveedor);
		log.info ("Adicionando Propietario: " + Propietario);
		return hostal;
	}

	/**
	 * Elimina una Propietario por su nombre
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param nombre - El nombre de la Propietario a eliminar
	 * @return El n√∫mero de tuplas eliminadas
	 */
	public long eliminarPropietarioPorNombre (String nombre)
	{
		log.info ("Eliminando Propietario por nombre: " + nombre);
		long resp = pp.eliminarPropietarioPorNombre (nombre);
		log.info ("Eliminando Propietario por nombre: " + resp + " tuplas eliminadas");
		return resp;
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
	public Propietario  darPropietariosPorNombre(String nombre)
	{
		log.info ("Buscando Propietario por nombre: " + nombre);
		List<Propietario> tb = pp.darPropietarioPorNombre (nombre);
		return !tb.isEmpty () ? tb.get (0) : null;
	}

	public Propietario darPropietariosPorId(long id)
	{
		log.info ("Dar informaci√≥n de un Propietario por id: " + id);
		Propietario hotel = pp.darPropietarioPorId (id);
		log.info ("Buscando Propietario por Id: " + hotel != null ? hotel : "NO EXISTE");
		return hotel;
	}


	*****************************************************************/
	/**
	 * Adiciona de manera persistente una Alojamiento 
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param nombre - El nombre la Alojamiento
	 * @param idServicio - El identificador del Servicio de la Alojamiento - Debe existir un Servicio con este identificador
	 * @param gradoAlcohol - El grado de alcohol de la Alojamiento (Mayor que 0)
	 * @return El objeto Alojamiento adicionado. null si ocurre alguna Excepci√≥n
	 */
	public Proveedor adicionarProveedor (long id, String tipo)
	{
		log.info ("Adicionando Proveedor " );
		Proveedor Proveedor = pp.adicionarProveedor (id, tipo);
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




	*****************************************************************/
	/**
	 * Adiciona de manera persistente una Alojamiento 
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param nombre - El nombre la Alojamiento
	 * @param idServicio - El identificador del Servicio de la Alojamiento - Debe existir un Servicio con este identificador
	 * @param gradoAlcohol - El grado de alcohol de la Alojamiento (Mayor que 0)
	 * @return El objeto Alojamiento adicionado. null si ocurre alguna Excepci√≥n
	 */
	public Reserva adicionarReserva (long id, long idCliente, long idAlojamiento, long idReserva, int descuento, int cantpagos, Date fechaConfirmacion, 
			Date fechaCheckIn, Date fechaCheckOut, double precioTotal)
	{
		log.info ("Adicionando Reserva " );
		Reserva Reserva = pp.adicionarReserva (id, idCliente, idAlojamiento, cantpagos, fechaConfirmacion, fechaCheckIn, fechaCheckOut,descuento, precioTotal);
		log.info ("Adicionando Reserva: " + Reserva);
		return Reserva;
	}

	/**
	 * Elimina una Reserva por su nombre
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param nombre - El nombre de la Reserva a eliminar
	 * @return El n√∫mero de tuplas eliminadas
	 */
	public long eliminarReservaPorFecha (String fecha)
	{
		log.info ("Eliminando Reserva por fecha: " + fecha);
		long resp = pp.eliminarReservaPorFecha (fecha);
		log.info ("Eliminando Reserva por fecha: " + resp + " tuplas eliminadas");
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
	public Reserva  darReservasPorFecha(String fechaCheckOut)
	{
		log.info ("Buscando Reserva por nombre: " + fechaCheckOut);
		List<Reserva> tb = pp.darReservaPorFecha (fechaCheckOut);
		return !tb.isEmpty () ? tb.get (0) : null;
	}

	public Reserva darReservasPorId(long id)
	{
		log.info ("Dar informaci√≥n de un proveedor por id: " + id);
		Reserva reserva = pp.darReservaPorId (id);
		log.info ("Buscando reserva por Id: " + reserva != null ? reserva : "NO EXISTE");
		return reserva;
	}




	*****************************************************************/
	/**
	 * Adiciona de manera persistente una Alojamiento 
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param nombre - El nombre la Alojamiento
	 * @param idServicio - El identificador del Servicio de la Alojamiento - Debe existir un Servicio con este identificador
	 * @param gradoAlcohol - El grado de alcohol de la Alojamiento (Mayor que 0)
	 * @return El objeto Alojamiento adicionado. null si ocurre alguna Excepci√≥n
	 */
	public Empresa adicionarEmpresa (long nit, String nombre, String registrado, String correo, long idProveedor)
	{
		log.info ("Adicionando Empresa " + nombre);
		Empresa empresa = pp.adicionarEmpresa (nit, nombre, registrado, correo, idProveedor);
		log.info ("Adicionando Empresa: " + empresa);
		return empresa;
	}

	/**
	 * Elimina una Empresa por su nombre
	 * Adiciona entradas al log de la aplicaci√≥n
	 * @param nombre - El nombre de la Empresa a eliminar
	 * @return El n√∫mero de tuplas eliminadas
	 */
	public long eliminarEmpresaPorNombre (String nombre)
	{
		log.info ("Eliminando Empresa por nombre: " + nombre);
		long resp = pp.eliminarEmpresaPorNombre (nombre);
		log.info ("Eliminando Empresa por nombre: " + resp + " tuplas eliminadas");
		return resp;
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
		log.info ("Consultando Empresas: " + Empresas.size() + " Empresas existentes");
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
