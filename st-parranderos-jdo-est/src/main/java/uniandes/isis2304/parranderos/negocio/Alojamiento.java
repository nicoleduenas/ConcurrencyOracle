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


	private int habilitado;


	private String servicios;
	

	

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
		this.setHabilitado(0);
		this.setServicios("");

	}

	
    public Alojamiento(long id, String nombre, String tipoOferta, char habilitado, String servicios) 
    {
    	this.id = id;
		this.nombre = nombre;
		this.tipoOferta = tipoOferta;
		this.habilitado = habilitado;
		this.servicios = servicios;
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


	public String getServicios() {
		return servicios;
	}


	public void setServicios(String servicios) {
		this.servicios = servicios;
	}


	public int getHabilitado() {
		return habilitado;
	}


	public void setHabilitado(int habilitado) {
		this.habilitado = habilitado;
	}


	


}
