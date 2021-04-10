package co.edu.uniquindio.listas.model;

import java.io.Serializable;

import co.edu.uniquindio.listas.model.listas.ListaDoble;

public class Proceso implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String nombre;
	private ListaDoble<Actividad> listaActividades;
	
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
	
}
