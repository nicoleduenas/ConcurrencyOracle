/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Parranderos Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.parranderos.negocio;

/**
 * Clase para modelar el concepto BAR del negocio de los Parranderos
 *
 * @author Germán Bravo
 */
public abstract class Alojamiento implements VOAlojamiento
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/

	private long id;
	

	private String nombre;

	private String tipoOferta;
	

	

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public Alojamiento() 
    {
    	this.id = 0;
		this.nombre = "";
		this.tipoOferta = "";

	}

	
    public Alojamiento(long id, String nombre, String tipoOferta, String ubicacion) 
    {
    	this.id = id;
		this.nombre = nombre;
		this.tipoOferta = tipoOferta;
	}

    
	public long getId() 
	{
		return id;
	}
	
	
	public void setId(long id) 
	{
		this.id = id;
	}
	
	
	public String getNombre() 
	{
		return nombre;
	}
	

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}
	
	
	
	
	
	public void setTipoOferta(String tipoOferta) 
	{
		this.tipoOferta = tipoOferta;
	}
	
	public String getTipoOferta() 
	{
		return tipoOferta;
	}


	

	
//	
//	@Override
//	/**
//	 * @return Una cadena de caracteres con todos los atributos del bar
//	 */
//	public String toString() 
//	{
//		return "Alojamiento [id=" + id + ", nombre=" + nombre + ", tipoOferta=" + tipoOferta + ", tipo=" + tipo + ", ubicacion=" + ubicacion
//				+ "]";
//	}
//	

}
