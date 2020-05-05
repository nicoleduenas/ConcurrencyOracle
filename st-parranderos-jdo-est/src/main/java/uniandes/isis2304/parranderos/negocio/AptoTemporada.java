

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


	private int precio;
	/* **********************
	 * 			Métodos
	 ***********************/




	private long idAlojamiento;


	private long idProveedor;

	public AptoTemporada() 
	{
		this.idAlojamiento = 0;
		this.idProveedor=0;
		this.precio = 0;
		this.menaje = "";
		this.habitaciones = 0;
	}

	/**
	 * Constructor con valores
	 * @param menaje2 
	 */
	public AptoTemporada(long idAlojamiento, long idProveedor, int precio, int habitaciones, String menaje) 
	{
		this.idAlojamiento = idAlojamiento;
		this.idProveedor=idProveedor;
		this.precio = precio;
		this.menaje = menaje;
		this.habitaciones = habitaciones;
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

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
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