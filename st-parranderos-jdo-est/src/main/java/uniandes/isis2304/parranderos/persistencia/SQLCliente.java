package uniandes.isis2304.parranderos.persistencia;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;

import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Cliente;

public class SQLCliente {
	
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

	
	public SQLCliente (PersistenciaAlohandes pp)
	{
		this.pp = pp;
	}
	
	
	public long adicionarCliente (PersistenceManager pm, String documento, String nombre,String email, String ciudad, String contrasena, String genero, Date fechaNacimiento,String vinculacion) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaCliente () + "(id, nombre, ubicacion, tipooferta	, tipo) values (?, ?, ?, ?, ?)");
        q.setParameters(documento, nombre,email, ciudad, contrasena, genero, fechaNacimiento, vinculacion);
        return (long) q.executeUnique();
	}

	
	public long eliminarClientesPorNombre (PersistenceManager pm, String nombreCliente)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCliente () + " WHERE nombre = ?");
        q.setParameters(nombreCliente);
        return (long) q.executeUnique();
	}

	
	public long eliminarClientePorId (PersistenceManager pm, long idCliente)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCliente () + " WHERE id = ?");
        q.setParameters(idCliente);
        return (long) q.executeUnique();
	}

	
	public Cliente darClientePorId (PersistenceManager pm, long idCliente) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCliente () + " WHERE id = ?");
		q.setResultClass(Cliente.class);
		q.setParameters(idCliente);
		return (Cliente) q.executeUnique();
	}


	public List<Cliente> darClientesPorNombre (PersistenceManager pm, String nombreCliente) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCliente () + " WHERE nombre = ?");
		q.setResultClass(Cliente.class);
		q.setParameters(nombreCliente);
		return (List<Cliente>) q.executeList();
	}

	
	public List<Cliente> darClientes (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCliente ());
		q.setResultClass(Cliente.class);
		return (List<Cliente>) q.executeList();
	}




}
