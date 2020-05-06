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

import java.util.Date;

/**
 * Clase para modelar el concepto BEBEDOR del negocio de los Parranderos
 *
 * @author Germán Bravo
 */
public class Reserva implements VOReserva
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO del bebedor
	 */
	private long id;
	
	private long idCliente;
	
	private long idAlojamiento;
	
	private Integer descuento;
	/**
	 * El nombre del bebedor
	 */
	private long precioTotal;
	
	/**
	 * La ciudad del bebedor
	 */
	private Date fechaCheckIn;
	private Date fechaCheckOut;
	private Date fechaConfirmacion;


	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public Reserva() 
	{
		this.id = 0;
		this.idCliente= 0;
		this.idAlojamiento= 0;
		this.fechaCheckIn= new Date();
		this.fechaCheckOut= new Date();
		this.fechaConfirmacion= new Date();
		this.descuento= 0;
		this.precioTotal= 0;

	}

	/**
	 * Constructor con valores
	 * @param id - El id del bebedor
	 * @param nombre - El nombre del bebedor
	 * @param ciudad - La ciudad del bebedor
	 * @param presupuesto - El presupuesto del bebedor (ALTO, MEDIO, BAJO)
	 */
	public Reserva(long id,long idCliente,long idAloja, Date fechaConfir, Date fechaCheckOut, Date fechaCheckIn, Integer descuento, long precioTotal)
	{
		this.idCliente = idCliente;
		this.idAlojamiento = idAloja;
		this.id = id;
		this.descuento= descuento;
		this.precioTotal= precioTotal;
		this.fechaCheckIn= fechaCheckIn;
		this.fechaCheckOut= fechaCheckOut;
		this.fechaConfirmacion= fechaConfir;
	}

	/**
	 * @return El id del bebedor
	 */
	public long getId() 
	{
		return id;
	}

	/**
	 * @param id - El nuevo id del bebedor
	 */
	public void setId(long id) 
	{
		this.id= id;
	}
	
	public long getIdCliente() 
	{
		return idCliente;
	}

	/**
	 * @param id - El nuevo id del bebedor
	 */
	public void setIdCliente(long idCliente) 
	{
		this.idCliente= idCliente;
	}
	
	public long getIdAlojamiento() 
	{
		return idAlojamiento;
	}

	/**
	 * @param id - El nuevo id del bebedor
	 */
	public void setIdAlojamiento(long idAlojamiento) 
	{
		this.idAlojamiento= idAlojamiento;
	}
	

	/**
	 * @return El nombre del bebedor
	 */
	public Integer getDescuento() 
	{
		return descuento;
	}

	/**
	 * @param nombre - El nuevo nombre del bebedor
	 */
	public void setDescuento(Integer descuento) 
	{
		this.descuento= descuento;
	}
	
	public long getPrecioTotal() 
	{
		return precioTotal;
	}

	/**
	 * @param nombre - El nuevo nombre del bebedor
	 */
	public void setPrecioTotal(Integer descuento) 
	{
		this.precioTotal= precioTotal;
	}

	/**
	 * @return La ciudad del bebedor
	 */
	public Date getFechaCheckIn() 
	{
		return fechaCheckIn;
	}

	/**
	 * @param ciudad - La nueva ciudad del bebedor
	 */
	public void setFechaCheckIn(Date fechaCheckIn) 
	{
		this.fechaCheckIn= fechaCheckIn;
	}
	
	public Date getFechaCheckOut() 
	{
		return fechaCheckOut;
	}

	/**
	 * @param ciudad - La nueva ciudad del bebedor
	 */
	public void setFechaConfirmacion(Date fechaConfirmacion) 
	{
		this.fechaConfirmacion= fechaConfirmacion;
	}

	public Date getFechaConfirmacion() 
	{
		return fechaConfirmacion;
	}

	/**
	 * @param ciudad - La nueva ciudad del bebedor
	 */
	public void setFechaCheckOut(Date fechaCheckOut) 
	{
		this.fechaCheckOut= fechaCheckOut;
	}
	
	/**
	 * @return La ciudad del bebedor
	 */

	
	
	/**
	 * @return Una cadena de caracteres con la información básica del bebedor
	 */
	@Override
	public String toString() 
	{
		return "Bebedor [id=" + id + ", idCliente=" + idCliente + ", idAlojamiento=" + idAlojamiento + ", precioTotal=" + precioTotal+ 
				", descuento=" + descuento+ ", fechaCheckIn=" + fechaCheckIn + ", fechaCheckOut=" + fechaCheckOut+ "]";
	}
	
	 
    public int compareTo(Reserva o) { 
    return getFechaConfirmacion().compareTo(o.getFechaConfirmacion()); 
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


	
}
