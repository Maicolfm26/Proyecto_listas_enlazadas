package co.edu.uniquindio.listas.model;

import java.io.Serializable;

import co.edu.uniquindio.listas.model.listas.Cola;

public class Actividad implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nombre;
	private String descripcion;
	private Requerida requerida;
	private Cola<Tarea> listaTareas;
	
	public Actividad(String nombre, String descripcion, Requerida requeria) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.requerida = requeria;
		listaTareas = new Cola<Tarea>();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Requerida getRequeria() {
		return requerida;
	}

	public void setRequeria(Requerida requeria) {
		this.requerida = requeria;
	}

	public Cola<Tarea> getListaTareas() {
		return listaTareas;
	}

	public void setListaTareas(Cola<Tarea> listaTareas) {
		this.listaTareas = listaTareas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		Actividad other = (Actividad) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}
	
}
