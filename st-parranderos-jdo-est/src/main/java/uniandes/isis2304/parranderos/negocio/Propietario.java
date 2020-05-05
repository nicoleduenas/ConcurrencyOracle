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
public class Propietario implements VOPropietario
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO de los bares
	 */
	private long id;
	
	

	/**
	 * La email donde se encuentra el bar
	 */
	private String email;
	
	
	/**
	 * El número de sedes del bar en la email
	 */
	private int telefono;



	private String nombre;

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public Propietario() 
    {
    	this.id = 0;
    	this.setNombre("");
		this.email = "";
		this.telefono = 0;
	}

	/**
	 * Constructor con valores
	 * @param id - El id del bart
	 * @param nombre - El nombre del bar
	 * @param email - La email del bar
	 * @param registrado - El registrado del bar (ALTO, MEDIO, BAJO)
	 * @param telefono - Las sedes del bar (Mayor que 0)
	 */
    public Propietario(long id, String nombre, String email, int telefono) 
    {
    	this.id = id;
    	this.setNombre(nombre);
		this.email = email;
		this.telefono = telefono;
	}

    /**
	 * @return El id del bar
	 */
	public long getId() 
	{
		return id;
	}
	
	/**
	 * @param id - El nuevo id del bar
	 */
	public void setId(long id) 
	{
		this.id = id;
	}
	

	
	/**
	 * @return la email del bar
	 */
	public String getEmail() 
	{
		return email;
	}
	
	/**
	 * @param email - La nueva email del bar
	 */
	public void setEmail(String email) 
	{
		this.email = email;
	}
	
	
	
	/**
	 * @return la telefono del bar
	 */
	public int getTelefono() 
	{
		return telefono;
	}
	
	/**
	 * @param telefono - la nueva cantidad de sedes del bar
	 */
	public void setTelefono(int telefono) 
	{
		this.telefono = telefono;
	}
	

	/**
	 * @return Una cadena de caracteres con todos los atributos del bar
	 */
	public String toString() 
	{
		return "Bar [id=" + id +  ", email=" + email +  ", telefono=" + telefono + "]";
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	

}
