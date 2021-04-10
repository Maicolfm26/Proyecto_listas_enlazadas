package co.edu.uniquindio.listas.model;

import java.io.Serializable;

public class Tarea implements Serializable {

	private static final long serialVersionUID = 1L;
	private String descripcion;
	private Requerida requerida;
	private int duracion;
	
	public Tarea(String descripcion, Requerida requerida, int duracion) {
		this.descripcion = descripcion;
		this.requerida = requerida;
		this.duracion = duracion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Requerida getRequerida() {
		return requerida;
	}

	public void setRequerida(Requerida requerida) {
		this.requerida = requerida;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
}
