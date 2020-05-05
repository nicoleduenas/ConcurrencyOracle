

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


	private String ubicacion;

	public AptoTemporada() 
	{
		this.id = 0;
		this.setPrecio(0);
		this.habitaciones = 0;

		this.menaje = "";
		this.ubicacion = "";

		this.tipoOferta = "";
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