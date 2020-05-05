package uniandes.isis2304.parranderos.negocio;

import java.util.Date;

/**
 * Interfaz para los métodos get de GUSTAN.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Germán Bravo
 */
public interface VOReserva
{
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * @return El idBebedor
	 */
	public long getId();

	public long getIdCliente();

	public long getIdAlojamiento();

	public long getPrecioTotal();

	public Integer getPersonas();

	public Date getFechaConfirmacion();
	
	public Date getFechaCheckIn();
	
	public Date getFechaCheckOut();
	
	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString();
	
}
