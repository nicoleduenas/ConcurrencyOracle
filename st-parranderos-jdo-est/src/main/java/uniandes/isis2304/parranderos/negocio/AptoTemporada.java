

package uniandes.isis2304.parranderos.negocio;


public class AptoTemporada extends Alojamiento implements VOAptoTemporada
{
	/* **********************
	 * 			Atributos
	 ***********************/

	private long id;


	private Integer habitaciones;


	private String menaje;

	private String tipoOferta;
	private Integer precioTemporada;
	/* **********************
	 * 			Métodos
	 ***********************/

	public AptoTemporada() 
	{
		this.id = 0;

		this.menaje = "";
		this.habitaciones = 0;
		this.tipoOferta = "";
		this.setPrecioTemporada(0);
	}

	/**
	 * Constructor con valores
	 * @param precioTemporada 
	 */
	public AptoTemporada(long id, Integer habitaciones, String menaje,  String tipoOferta, Integer precioTemporada) 
	{
		this.id = id;
		this.precioTemporada=precioTemporada;
		this.habitaciones = habitaciones;
		this.menaje = menaje;
		this.tipoOferta = tipoOferta;
	}

	public String getTipoOferta() 
	{
		return tipoOferta;
	}


	public void setTipoOferta(String tipoOferta) 
	{
		this.tipoOferta = tipoOferta;
	}
	public Integer getHabitaciones() 
	{
		return habitaciones;
	}

	public void setHabitaciones(Integer habitaciones) 
	{
		this.habitaciones = habitaciones;
	}

	public long getId() 
	{
		return id;
	}

	public void setId(long id) 
	{
		this.id = id;
	}

	public String getMenaje() 
	{
		return menaje;
	}


	public void setMenaje(String menaje) 
	{
		this.menaje = menaje;
	}


	@Override
	public String toString() 
	{
		return "Visitan [id=" + id + ", menaje=" + menaje + ", tipoOferta=" + tipoOferta + ", habitaciones="
				+ habitaciones + "]";
	}

	public Integer getPrecioTemporada() {
		return precioTemporada;
	}

	public void setPrecioTemporada(Integer i) {
		this.precioTemporada = i;
	}

}