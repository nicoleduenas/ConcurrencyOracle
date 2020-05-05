package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;

import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Hostal;

public class SQLHostal {
	
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

	
	public SQLHostal (PersistenciaAlohandes pp)
	{
		this.pp = pp;
	}
	
	
	public long adicionarHostal (PersistenceManager pm, long idHostal, String nombre, String horario,String ubicacion, String email, long idProveedor, long telefono) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaHostal () + "(id, nombre, horario,email, ubicacion, idProveedor, telefono) values (?, ?,?,?, ?, ?, ?)");
        q.setParameters(idHostal, nombre, horario,email, ubicacion, idProveedor, telefono);
        return (long) q.executeUnique();
	}

	
	public long eliminarHostalsPorNombre (PersistenceManager pm, String nombreHostal)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaHostal () + " WHERE nombre = ?");
        q.setParameters(nombreHostal);
        return (long) q.executeUnique();
	}

	
	public long eliminarHostalPorId (PersistenceManager pm, long idHostal)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaHostal () + " WHERE id = ?");
        q.setParameters(idHostal);
        return (long) q.executeUnique();
	}

	
	public Hostal darHostalPorId (PersistenceManager pm, long idHostal) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaHostal () + " WHERE id = ?");
		q.setResultClass(Hostal.class);
		q.setParameters(idHostal);
		return (Hostal) q.executeUnique();
	}


	public List<Hostal> darHostalsPorNombre (PersistenceManager pm, String nombreHostal) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaHostal () + " WHERE nombre = ?");
		q.setResultClass(Hostal.class);
		q.setParameters(nombreHostal);
		return (List<Hostal>) q.executeList();
	}

	
	public List<Hostal> darHostals (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaHostal ());
		q.setResultClass(Hostal.class);
		return (List<Hostal>) q.executeList();
	}




}
