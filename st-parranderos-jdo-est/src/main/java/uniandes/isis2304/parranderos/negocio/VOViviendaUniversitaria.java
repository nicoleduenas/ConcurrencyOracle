package uniandes.isis2304.parranderos.negocio;

public interface VOViviendaUniversitaria {
	 
		public long getId();
		
		
		public String getTipoOferta();
		
		
		public Integer getTelefono();
		public Integer getPrecioNoche();
		
		public Boolean getAmoblado();

		@Override
		
		public String toString();

	}