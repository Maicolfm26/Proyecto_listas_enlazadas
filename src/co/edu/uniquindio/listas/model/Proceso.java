package co.edu.uniquindio.listas.model;

import java.io.Serializable;

import co.edu.uniquindio.listas.model.listas.ListaDoble;

public class Proceso implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String nombre;
	private ListaDoble<Actividad> listaActividades;
	
	public Proceso() {
	}
	
	public Proceso(String id, String nombre) {
		this.id = id;
		this.nombre = nombre;
		listaActividades = new ListaDoble<Actividad>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ListaDoble<Actividad> getListaActividades() {
		return listaActividades;
	}

	public void setListaActividades(ListaDoble<Actividad> listaActividades) {
		this.listaActividades = listaActividades;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Proceso other = (Proceso) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
