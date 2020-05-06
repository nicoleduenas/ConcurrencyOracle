package uniandes.isis2304.parranderos.persistencia;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;

import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Reserva;

public class SQLReserva {
	
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

	
	public SQLReserva (PersistenciaAlohandes pp)
	{
		this.pp = pp;
	}
	
	
	public long adicionarReserva (PersistenceManager pm, long id, long idAlojamiento, Integer numeroReservaCol, Integer descuento, Integer personas, Integer precioTotal, 
			Date fechaCheckIn, Date fechaCheckOut,Date fechaConfirmacion, Integer cantPagos, long idCliente) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaReserva () + "(id, idAlojamiento,numeroReservaCol, descuento, personas, precioTotal,fechaCheckIn,fechaCheckOut,fechaConfirmacion, cantPagos, idCliente) values (?, ?, ?, ?, ?,?, ?,?, ?, ?, ?)");
        q.setParameters(id, idAlojamiento, numeroReservaCol,descuento, personas, precioTotal,fechaCheckIn,fechaCheckOut,fechaConfirmacion, cantPagos, idCliente);
        return (long) q.executeUnique();
	}

	
	public long eliminarReservasPorNombre (PersistenceManager pm, String nombreReserva)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaReserva () + " WHERE nombre = ?");
        q.setParameters(nombreReserva);
        return (long) q.executeUnique();
	}

	
	public long eliminarReservaPorId (PersistenceManager pm, long idReserva)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaReserva () + " WHERE id = ?");
        q.setParameters(idReserva);
        return (long) q.executeUnique();
	}

	
	public Reserva darReservaPorId (PersistenceManager pm, long idReserva) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReserva () + " WHERE id = ?");
		q.setResultClass(Reserva.class);
		q.setParameters(idReserva);
		return (Reserva) q.executeUnique();
	}


	public List<Reserva> darReservasPorNombre (PersistenceManager pm, String nombreReserva) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReserva () + " WHERE nombre = ?");
		q.setResultClass(Reserva.class);
		q.setParameters(nombreReserva);
		return (List<Reserva>) q.executeList();
	}

	
	public List<Reserva> darReservas (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReserva ());
		q.setResultClass(Reserva.class);
		return (List<Reserva>) q.executeList();
	}

	public List<Reserva> darReservaPorIdAlojamiento (PersistenceManager pm, long aloj) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReserva () + " WHERE idAlojamiento = ?");
		q.setResultClass(Reserva.class);
		q.setParameters(aloj);
		return (List<Reserva>) q.executeList();
	}
	
	public List<Reserva> darReservasPorIdColectiva (PersistenceManager pm, long id) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReserva () + " WHERE numeroReservaCol = ?");
		q.setResultClass(Reserva.class);
		q.setParameters(id);
		return (List<Reserva>) q.executeList();
	}
	
	public void desligarReservaPorIdMasiva (PersistenceManager pm, long idAlojamiento, long idMasiva)
	{
        Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaReserva () + "SET numeroReservaCol = 0" + "WHERE idAlojamiento = ?"+ "AND"+"numeroReservaCol = ?");
        q.setParameters(idAlojamiento,idMasiva);
       q.executeUnique();
	}
	
	public void cambiarAlojamientoReserva (PersistenceManager pm, long id, long idAlojamiento)
	{
        Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaReserva () + "SET idAlojamiento = ?" + "WHERE id = ?");
        q.setParameters(idAlojamiento,id);
       q.executeUnique();
	}
	



}
