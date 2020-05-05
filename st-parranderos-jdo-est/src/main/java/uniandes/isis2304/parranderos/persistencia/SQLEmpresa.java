package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;

import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Empresa;

public class SQLEmpresa {
	
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

	
	public SQLEmpresa (PersistenciaAlohandes pp)
	{
		this.pp = pp;
	}
	
	
	public long adicionarEmpresa (PersistenceManager pm, long id,  String email, long idProveedor, char registrado) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaEmpresa () + "(id,  email,idProveedor,registrado) values (?, ?,? ?)");
        q.setParameters(id, email,idProveedor,registrado);
        return (long) q.executeUnique();
	}

	
	public long eliminarEmpresasPorNombre (PersistenceManager pm, String nombreEmpresa)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaEmpresa () + " WHERE nombre = ?");
        q.setParameters(nombreEmpresa);
        return (long) q.executeUnique();
	}

	
	public long eliminarEmpresaPorId (PersistenceManager pm, long idEmpresa)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaEmpresa () + " WHERE id = ?");
        q.setParameters(idEmpresa);
        return (long) q.executeUnique();
	}

	
	public Empresa darEmpresaPorId (PersistenceManager pm, long idEmpresa) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaEmpresa () + " WHERE id = ?");
		q.setResultClass(Empresa.class);
		q.setParameters(idEmpresa);
		return (Empresa) q.executeUnique();
	}


	public List<Empresa> darEmpresasPorNombre (PersistenceManager pm, String nombreEmpresa) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaEmpresa () + " WHERE nombre = ?");
		q.setResultClass(Empresa.class);
		q.setParameters(nombreEmpresa);
		return (List<Empresa>) q.executeList();
	}

	
	public List<Empresa> darEmpresas (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaEmpresa ());
		q.setResultClass(Empresa.class);
		return (List<Empresa>) q.executeList();
	}




}
