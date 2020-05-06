package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;

import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Alojamiento;

public class SQLAlojamiento {
	
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

	
	public SQLAlojamiento (PersistenciaAlohandes pp)
	{
		this.pp = pp;
	}
	
	
	public long adicionarAlojamiento (PersistenceManager pm, long id, char habilitado, String nombre, String tipo, String servicios) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaAlojamiento () + "(id,habilitado,nombre, tipo, servicios	) values ( ?, ?, ?, ?, ?)");
        q.setParameters(id,habilitado, nombre, tipo, servicios);
        return (long) q.executeUnique();
	}

	
	public long eliminarAlojamientosPorNombre (PersistenceManager pm, String nombreAlojamiento)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaAlojamiento () + " WHERE nombre = ?");
        q.setParameters(nombreAlojamiento);
        return (long) q.executeUnique();
	}

	
	public long eliminarAlojamientoPorId (PersistenceManager pm, long idAlojamiento)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaAlojamiento () + " WHERE id = ?");
        q.setParameters(idAlojamiento);
        return (long) q.executeUnique();
	}

	
	public Alojamiento darAlojamientoPorId (PersistenceManager pm, long idAlojamiento) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaAlojamiento () + " WHERE id = ?");
		q.setResultClass(Alojamiento.class);
		q.setParameters(idAlojamiento);
		return (Alojamiento) q.executeUnique();
	}


	public List<Alojamiento> darAlojamientosPorNombre (PersistenceManager pm, String nombreAlojamiento) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaAlojamiento () + " WHERE nombre = ?");
		q.setResultClass(Alojamiento.class);
		q.setParameters(nombreAlojamiento);
		return (List<Alojamiento>) q.executeList();
	}

	
	public List<Alojamiento> darAlojamientos (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaAlojamiento ());
		q.setResultClass(Alojamiento.class);
		return (List<Alojamiento>) q.executeList();
	}
	
	public List<Alojamiento> darAlojamientosPorTipo (PersistenceManager pm, String tipo)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaAlojamiento () + " WHERE tipo = ?");
		q.setResultClass(Alojamiento.class);
		q.setParameters(tipo);
		return (List<Alojamiento>) q.executeList();
	}





}
