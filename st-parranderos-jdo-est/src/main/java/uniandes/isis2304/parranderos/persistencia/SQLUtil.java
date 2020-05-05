/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Alohandes Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.parranderos.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto BAR de Alohandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Germán Bravo
 */
class SQLUtil
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaAlohandes.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaAlohandes pp;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/

	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLUtil (PersistenciaAlohandes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para obtener un nuevo número de secuencia
	 * @param pm - El manejador de persistencia
	 * @return El número de secuencia generado
	 */
	public long nextval (PersistenceManager pm)
	{
        Query q = pm.newQuery(SQL, "SELECT "+ pp.darSeqAlohandes () + ".nextval FROM DUAL");
        q.setResultClass(Long.class);
        long resp = (long) q.executeUnique();
        return resp;
	}

	/**
	 * Crea y ejecuta las sentencias SQL para cada tabla de la base de datos - EL ORDEN ES IMPORTANTE 
	 * @param pm - El manejador de persistencia
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarAlohandes (PersistenceManager pm)
	{
        Query qCliente = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCliente ());          
        Query qReserva = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaReserva ());
        Query qAlojamiento = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaAlojamiento ());
        Query qProveedor = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProveedor ());
        Query qServicio = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaServicio ());
        Query qHotel = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaHotel());
        Query qHostal = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaHostal ());
        Query qViviUniversitaria = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaViviendaUniversitaria());
        Query qHabitacion = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaHabitacion());
       
        Query qAptoTemporada= pm.newQuery(SQL, "DELETE FROM " + pp.darTablaAptoTemporada());
        Query qEmpresa = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaEmpresa ());
        Query qPropietario = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPropietario ());
        

        long clientesEliminados = (long) qCliente.executeUnique ();
        long reservasEliminados = (long) qReserva.executeUnique ();
        long alojamientosEliminadas = (long) qAlojamiento.executeUnique ();
        long proveedoresEliminadas = (long) qProveedor.executeUnique ();
        long serviciosBebidaEliminados = (long) qServicio.executeUnique ();
        long habEliminados = (long) qHabitacion.executeUnique ();
        long hostalesEliminados = (long) qHostal.executeUnique ();
        long hotelesEliminados = (long) qHotel.executeUnique ();
        long viviUniversitariasEliminados = (long) qViviUniversitaria.executeUnique ();
        long aptoTempEliminados = (long) qAptoTemporada.executeUnique ();
        long empresaEliminados = (long) qEmpresa.executeUnique ();
        long propietarioEliminados = (long) qPropietario.executeUnique ();
        return new long[] {clientesEliminados, reservasEliminados, alojamientosEliminadas
        		, propietarioEliminados, serviciosBebidaEliminados, habEliminados
        		, hotelesEliminados, hostalesEliminados, viviUniversitariasEliminados,
        	     aptoTempEliminados, empresaEliminados, proveedoresEliminadas};
	}

}
