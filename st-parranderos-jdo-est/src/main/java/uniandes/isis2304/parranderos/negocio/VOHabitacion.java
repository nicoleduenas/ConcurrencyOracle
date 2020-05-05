package uniandes.isis2304.parranderos.negocio;
public interface VOHabitacion
{
	/* **********************
	 * 			Métodos 
	 ***********************/
     /**
	 * @return El id del HostalHabitacion
	 */
	public long getId();
	
	/**
	 * @return el nombre del HostalHabitacion
	 */
	public String getHorario();
	
	/**
	 * @return El presupuesto del HostalHabitacion
	 */
	public Integer getPrecioNoche();
	
	/**
	 * @return la cantSedes del HostalHabitacion
	 */
	public Integer getCapacidad();
	public String getTipoOferta();
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del HostalHabitacion
	 */
	public String toString();

}