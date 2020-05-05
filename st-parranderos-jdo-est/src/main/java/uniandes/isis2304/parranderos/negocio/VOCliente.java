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
import java.util.List;

/**
 * Interfaz para los métodos get de BEBEDOR.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Germán Bravo
 */
public interface VOCliente 
{
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * @return El id del bebedor
	 */
	public long getDocumento();

	/**
	 * @return El nombre del bebedor
	 */
	public String getNombre();

	/**
	 * @return La ciudad del bebedor
	 */
	public String getCiudad();

	/**
	 * @return El presupuesto del bebedor
	 */
	public long getTelefono();
	public String getVinculacion();
	public String getEmail();
	public String getGenero();
	public Date getFechaNacimiento();
	public String getContrasena();
	

	/**
	 * @return Las bebidasQueLeGustan al bebedor
	 */
	public ArrayList<Reserva> getReservas();

	/**
	 * @return Una cadena de caracteres con la información básica del bebedor
	 */
	@Override
	public String toString();

	/**
	 * @return Una cadena de caracteres con la información COMPLEtA del bebedor.
	 * Además de la información básica, contiene las visitas realizadas (una por línea) y 
	 * las bebidas que le gustan al bebedor (una por línea)
	 */
	//public String toStringCompleto ();

}
