package uniandes.isis2304.parranderos.negocio;

public class Habitacion  extends Alojamiento implements VOHabitacion 
{
	/* **********************
	 * 			Atributos
	 ***********************/

	private long id;

	
	private Integer capacidad;

	
	private Integer precioNoche;

	
	private String horario;
	private String tipoOferta;
	/* **********************
	 * 			Métodos
	 ***********************/
	/**
	 * Constructor por defecto
	 */
	public Habitacion() 
	{
		this.id = 0;
		this.precioNoche = 0;
		this.horario = "";
		this.capacidad = 0;
		this.tipoOferta = "";
	}

	/**
	 * Constructor con valores
	 * @param is - El identificador del b ebedor. Debe existir un bebedor con dicho identificador
	 * @param idBar - El identificador del bar. Debe exixtir un bar con dicho identificador
	 * @param fechaVisita - La fecha en la cual se realiza la visita
	 * @param horario - El horario en el que el bebedor vista el bar (DIURNO, NOCTURNO, TODOS)
	 * @param tipoOferta 
	 */
	public Habitacion(long idHabitacion, String horario, int precioNoche, int capacidad, String tipoOferta) 
	{
		this.id = idHabitacion;
		this.precioNoche = precioNoche;
		this.capacidad = capacidad;
		this.horario = horario;
		this.tipoOferta = tipoOferta;
	}

	/**
	 * @return El is
	 */
	public long getId() 
	{
		return id;
	}

	/**
	 * @param is - El nuevo is. Debe existir un bebedor con dicho identificador
	 */
	public void setId(long id) 
	{
		this.id = id;
	}



	/**
	 * @return El horario
	 */
	public String getHorario() 
	{
		return horario;
	}

	/**
	 * @param horario - El nuevo horario en que se realizó la visita (DIURNO, NOCTURNO, TODOS)
	 */
	public void setHorario(String horario) 
	{
		this.horario = horario;
	}

	/** 
	 * @return Una cadena con la información básica
	 */
	@Override
	public String toString() 
	{
		return "Visitan [id=" + id + ", horario=" + horario + ", tipoOferta=" + tipoOferta + ", precioNoche="
				+ precioNoche + "]";
	}

	@Override
	public Integer getPrecioNoche() {
		// TODO Auto-generated method stub
		return precioNoche;
	}

	@Override
	public Integer getCapacidad() {
		// TODO Auto-generated method stub
		return capacidad;
	}

	@Override
	public String getTipoOferta() {
		// TODO Auto-generated method stub
		return tipoOferta;
	}
	public void setTipoOferta(String tipoOferta) 
	{
		this.tipoOferta = tipoOferta;
	}
	public void setCapacidad(Integer capacidad) 
	{
		this.capacidad = capacidad;
	}
	public void setPrecioNoche(Integer precioNoche) 
	{
		this.precioNoche = precioNoche;
	}

	@Override
	public String getTipo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUbicacion() {
		// TODO Auto-generated method stub
		return null;
	}
}