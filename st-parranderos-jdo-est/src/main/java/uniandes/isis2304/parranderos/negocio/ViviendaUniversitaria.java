package uniandes.isis2304.parranderos.negocio;

public class ViviendaUniversitaria implements VOServicio
{
	 private long id;	
		
	


		private boolean amoblado;


		private int telefono;


		private int precioNoche;


		private String ubicacion;


		private String tipoOferta;


	    public ViviendaUniversitaria(long id, boolean amoblado, int telefono, int precioNoche, String ubicacion, String tipoOferta ) {
	        this.id = id;
	        this.setAmoblado(amoblado);
	        this.setTelefono(telefono);
	        this.setPrecioNoche(precioNoche);
	        this.setUbicacion(ubicacion);
	        this.setTipoOferta(tipoOferta)
	        		;

	    }
	    
	    public ViviendaUniversitaria() {
	        this.id = 0;
	        this.setAmoblado(false);
	        this.setTelefono(0);
	        this.setPrecioNoche(0);
	        this.setUbicacion("");
	        this.setTipoOferta("");
	    }

	    public long getId() {
	        return id;
	    }

	    public void setId(long id) {
	        this.id = id;
	    }

	  

	    
	    
//	    @Override
//	    public String toString() {
//	        return "Servicio[" + "id=" + id + ", nombre=" +  + ", idAlojamiento=" +  + ']';
//	    }

		public boolean isAmoblado() {
			return amoblado;
		}

		public void setAmoblado(boolean amoblado) {
			this.amoblado = amoblado;
		}

		public int getTelefono() {
			return telefono;
		}

		public void setTelefono(int telefono) {
			this.telefono = telefono;
		}

		public int getPrecioNoche() {
			return precioNoche;
		}

		public void setPrecioNoche(int precioNoche) {
			this.precioNoche = precioNoche;
		}

		public String getUbicacion() {
			return ubicacion;
		}

		public void setUbicacion(String ubicacion) {
			this.ubicacion = ubicacion;
		}

		public String getTipoOferta() {
			return tipoOferta;
		}

		public void setTipoOferta(String tipoOferta) {
			this.tipoOferta = tipoOferta;
		}

		@Override
		public String getNombre() {
			// TODO Auto-generated method stub
			return null;
		}
	      
}
