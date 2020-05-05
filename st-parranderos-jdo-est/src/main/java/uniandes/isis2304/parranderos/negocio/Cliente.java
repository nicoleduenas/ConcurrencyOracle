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

import java.util.ArrayList;
import java.util.Date;

/**
 * Clase para modelar el concepto BEBEDOR del negocio de los Parranderos
 *
 * @author Germán Bravo
 */
public class Cliente implements VOCliente
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO del bebedor
	 */
	private long documento;	
	
	/**
	 * El nombre del bebedor
	 */
	private String nombre;
	
	/**
	 * La ciudad del bebedor
	 */
	private String ciudad;
	
	/**
	 * El presupuesto del bebedor (ALTO, MEDIO, BAJO)
	 */
	private String email;
	
	private long telefono;
	
	private String contrasena;
	private String genero;
	private Date fechaNacimiento;
	private String vinculacion;

	private ArrayList<Reserva> reservas;


	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public Cliente() 
	{
		this.documento = 0;
		this.nombre = "";
		this.ciudad = "";
		this.email = "";
		this.telefono = 0;
		this.contrasena = "";
		this.genero = "";
		this.fechaNacimiento = new Date();
		this.vinculacion = "";

		reservas = new ArrayList<Reserva>() {
		};

	}

	/**
	 * Constructor con valores
	 * @param id - El id del bebedor
	 * @param nombre - El nombre del bebedor
	 * @param ciudad - La ciudad del bebedor
	 * @param presupuesto - El presupuesto del bebedor (ALTO, MEDIO, BAJO)
	 */
	public Cliente(long documento, String nombre, String ciudad, int telefono, String email, String contrasena, String genero, Date fechaNacimiento, String vinculacion) 
	{
		this.documento = documento;
		this.nombre = nombre;
		this.ciudad = ciudad;
		this.telefono = telefono;
		this.email = email;
		this.contrasena = contrasena;
		this.genero = genero;
		this.fechaNacimiento = fechaNacimiento;
		this.vinculacion = vinculacion;
		// Estos valores no se conocen en el momento de la construcción del bebedor
		reservas = new ArrayList<>() ;

	}


	/**
	 * @return El nombre del bebedor
	 */
	public String getNombre() 
	{
		return nombre;
	}

	/**
	 * @param nombre - El nuevo nombre del bebedor
	 */
	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	/**
	 * @return La ciudad del bebedor
	 */
	public String getCiudad() 
	{
		return ciudad;
	}

	/**
	 * @param ciudad - La nueva ciudad del bebedor
	 */
	public void setCiudad(String ciudad) 
	{
		this.ciudad = ciudad;
	}

	/**
	 * @return La ciudad del bebedor
	 */

	
	/**
	 * @return La ciudad del bebedor
	 */
	public String getEmail() 
	{
		return email;
	}


	/**
	 * @return La lista de visitasRealizadas por el bebedor 
	 */
	public ArrayList<Reserva> getReservas() 
	{
		return reservas;
	}

	/**
	 * @param visitasRealizadas - La nueva lista de visitas del bebedor
	 */
	public void setReservas (ArrayList<Reserva> reservas) 
	{
		this.reservas = reservas;
	}



	/**
	 * @return Una cadena de caracteres con la información básica del bebedor
	 */
	@Override
	public String toString() 
	{
		return "Bebedor [id=" + documento + ", nombre=" + nombre + ", ciudad=" + ciudad + ", telefono=" + telefono + 
				", contraseña=" + contrasena+ ", fechaNacimiento=" + fechaNacimiento + ", genero=" + genero + ", vinculacion=" + vinculacion 
				+ ", email=" + email+ "]";
	}

	/**
	 * @return Una cadena de caracteres con la información COMPLEtA del bebedor.
	 * Además de la información básica, contiene las visitas realizadas (una por línea) y 
	 * las bebidas que le gustan al bebedor (una por línea)
	 
	public String toStringCompleto () 
	{
		String resp =  this.toString();
		resp += "\n --- Visitas realizadas\n";
		int i = 1;
		for (Object [] visita : visitasRealizadas)
		{
			 bar = (Bar) visita [0];
			Timestamp fecha = (Timestamp) visita [1];
			String horario = (String) visita [2];
			resp += i++ + ". " + "[" +bar.toString() + ", fecha= " + fecha + ", horario= " + horario + "]\n";
		}
		resp += "\n\n --- Bebidas que le gustan\n";
		i = 1;
		for (Object [] gusta : bebidasQueLeGustan)
		{
			Bebida bebida = (Bebida) gusta [0];
			String tipoBebida = (String) gusta [1];
			resp += i++ + ". " + "[" + bebida.toString() + ", Tipo Bebida= " + tipoBebida + "]\n";
		}
		return resp;
	}
	*/

	public long getTelefono() {
		return telefono;
	}

	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}

	public String getVinculacion() {
		return vinculacion;
	}

	public void setVinculacion(String vinculacion) {
		this.vinculacion = vinculacion;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	@Override
	public long getDocumento() {
		// TODO Auto-generated method stub
		return 0;
	}


	
}
