package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;

import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Propietario;

public class SQLPropietario {
	
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

	
	public SQLPropietario (PersistenciaAlohandes pp)
	{
		this.pp = pp;
	}
	
	
	public long adicionarPropietario (PersistenceManager pm, long id,  long idProveedor, String email, long telefono, String vinculacion) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaPropietario () + "(id, idProveedor, email, telefono, vinculacion) values (?,?,?, ?, ?)");
        q.setParameters(id, idProveedor, email, telefono, vinculacion);
        return (long) q.executeUnique();
	}

	
	public long eliminarPropietariosPorNombre (PersistenceManager pm, String nombrePropietario)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPropietario () + " WHERE nombre = ?");
        q.setParameters(nombrePropietario);
        return (long) q.executeUnique();
	}

	
	public long eliminarPropietarioPorId (PersistenceManager pm, long idPropietario)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPropietario () + " WHERE id = ?");
        q.setParameters(idPropietario);
        return (long) q.executeUnique();
	}

	
	public Propietario darPropietarioPorId (PersistenceManager pm, long idPropietario) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPropietario () + " WHERE id = ?");
		q.setResultClass(Propietario.class);
		q.setParameters(idPropietario);
		return (Propietario) q.executeUnique();
	}


	public List<Propietario> darPropietariosPorNombre (PersistenceManager pm, String nombrePropietario) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPropietario () + " WHERE nombre = ?");
		q.setResultClass(Propietario.class);
		q.setParameters(nombrePropietario);
		return (List<Propietario>) q.executeList();
	}

	
	public List<Propietario> darPropietarios (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPropietario ());
		q.setResultClass(Propietario.class);
		return (List<Propietario>) q.executeList();
	}




}
