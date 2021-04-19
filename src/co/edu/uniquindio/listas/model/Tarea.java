package co.edu.uniquindio.listas.model;

import java.io.Serializable;

public class Tarea implements Serializable {

	private static final long serialVersionUID = 1L;
	private String descripcion;
	private Requerida requerida;
	private int duracion;
	
	public Tarea() {
	}
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + duracion;
		result = prime * result + ((requerida == null) ? 0 : requerida.hashCode());
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
		Tarea other = (Tarea) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (duracion != other.duracion)
			return false;
		if (requerida != other.requerida)
			return false;
		return true;
	}
}
