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

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.AptoTemporada;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto AptoTemporada de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Germán Bravo
 */
class SQLAptoTemporada 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaAlohandes.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaAlohandes pp;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/

	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLAptoTemporada (PersistenciaAlohandes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un AptoTemporada a la base de datos de Parranderos

	 */
	public long adicionarAptoTemporada (PersistenceManager pm, long idAlojamiento, long idProveedor, Integer precio, Integer habitaciones, String menaje) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaAptoTemporada () + "(idAlojamiento, precio, habitaciones, menaje, idProveedor) values (?, ?, ?, ?, ?)");
        q.setParameters(idAlojamiento, precio, habitaciones, menaje, idProveedor);
        return (long) q.executeUnique();
	}
//
//	/**
//	 * Crea y ejecuta la sentencia SQL para eliminar AptoTemporadaES de la base de datos de Parranderos, por su nombre
//	 
//	 */
//	public long eliminarAptoTemporadaesPorNombre (PersistenceManager pm, String nombreAptoTemporada)
//	{
//        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaAptoTemporada () + " WHERE nombre = ?");
//        q.setParameters(nombreAptoTemporada);
//        return (long) q.executeUnique();
//	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN AptoTemporada de la base de datos de Parranderos, por su identificador
	 
	 */
	public long eliminarAptoTemporadaPorId (PersistenceManager pm, long idAptoTemporada)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaAptoTemporada () + " WHERE id = ?");
        q.setParameters(idAptoTemporada);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN AptoTemporada de la 
	 * base de datos de Parranderos, por su identificador
	 
	 */
	public AptoTemporada darAptoTemporadaPorId (PersistenceManager pm, long idAptoTemporada) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaAptoTemporada () + " WHERE id = ?");
		q.setResultClass(AptoTemporada.class);
		q.setParameters(idAptoTemporada);
		return (AptoTemporada) q.executeUnique();
	}

//	/**
//	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS AptoTemporadaES de la 
//	 * base de datos de Parranderos, por su nombre
//	
//	 */
//	public List<AptoTemporada> darAptoTemporadaesPorNombre (PersistenceManager pm, String nombreAptoTemporada) 
//	{
//		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaAptoTemporada () + " WHERE nombre = ?");
//		q.setResultClass(AptoTemporada.class);
//		q.setParameters(nombreAptoTemporada);
//		return (List<AptoTemporada>) q.executeList();
//	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS AptoTemporadaES de la 
	 * base de datos de Parranderos
	 
	 */
	public List<AptoTemporada> darAptoTemporadaes (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaAptoTemporada ());
		q.setResultClass(AptoTemporada.class);
		return (List<AptoTemporada>) q.executeList();
	}

//	/**
//	 * Crea y ejecuta la sentencia SQL para aumentar en uno el número de sedes de los AptoTemporadaes de la 
//	 * base de datos de Parranderos
//	 
//	 */
//	public long aumentarSedesAptoTemporadaesCiudad (PersistenceManager pm, String ciudad)
//	{
//        Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaAptoTemporada () + " SET cantsedes = cantsedes + 1 WHERE ciudad = ?");
//        q.setParameters(ciudad);
//        return (long) q.executeUnique();
//	}
	
}
