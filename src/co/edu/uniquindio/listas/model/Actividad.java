package co.edu.uniquindio.listas.model;

import java.io.Serializable;
import java.util.Iterator;

import co.edu.uniquindio.listas.exceptions.DosTareasOpcionalesException;
import co.edu.uniquindio.listas.exceptions.PosicionInvalidaTareaException;
import co.edu.uniquindio.listas.exceptions.TareaNoExisteException;
import co.edu.uniquindio.listas.model.listas.Cola;

public class Actividad implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nombre;
	private String descripcion;
	private Requerida requerida;
	private Cola<Tarea> listaTareas;

	public Actividad() {
	}

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

	public Requerida getRequerida() {
		return requerida;
	}

	public void setRequerida(Requerida requeria) {
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

	@Override
	public String toString() {
		return nombre;
	}

	public void agregarTarea(Tarea tarea, int posicion, int opcion)
			throws DosTareasOpcionalesException, PosicionInvalidaTareaException {
		switch (opcion) {
		case 0:
			if (listaTareas.getTamanio() != 0) {
				if (listaTareas.getNodoUltimo().getRequerida() == Requerida.OBLIGATORIA
						|| tarea.getRequerida() == Requerida.OBLIGATORIA) {
					listaTareas.agregar(tarea);
				} else {
					throw new DosTareasOpcionalesException("No se pueden crear dos tareas opcionales seguidas");
				}
			} else {
				listaTareas.agregar(tarea);
			}
			break;
		case 1:
			if (posicion == 0) {
				Tarea tareaSiguinte = listaTareas.getNodoPrimero();
				if (tareaSiguinte.getRequerida() == Requerida.OBLIGATORIA
						|| tarea.getRequerida() == Requerida.OBLIGATORIA) {
					try {
						listaTareas.agregarPosicion(tarea, posicion);
					} catch (Exception e) {
						throw new PosicionInvalidaTareaException(e.getMessage());
					}
				} else {
					throw new DosTareasOpcionalesException("No se pueden crear dos tareas opcionales seguidas");
				}
			} else if (posicion == listaTareas.getTamanio()) {
				Tarea tareaAnterior = listaTareas.getNodoUltimo();
				if (tareaAnterior.getRequerida() == Requerida.OBLIGATORIA
						|| tarea.getRequerida() == Requerida.OBLIGATORIA) {
					try {
						listaTareas.agregarPosicion(tarea, posicion);
					} catch (Exception e) {
						throw new PosicionInvalidaTareaException(e.getMessage());
					}
				} else {
					throw new DosTareasOpcionalesException("No se pueden crear dos tareas opcionales seguidas");
				}
			} else {
				if (posicion > listaTareas.getTamanio()) {
					throw new PosicionInvalidaTareaException("Posicion invalida");
				}
				int contador = 0;
				Iterator<Tarea> it = listaTareas.iterator();
				Tarea tareaActual = null;
				Tarea tareaAnterior = null;
				while (it.hasNext()) {
					tareaActual = it.next();
					if (contador == posicion) {
						if (tareaActual.getRequerida() == Requerida.OBLIGATORIA
								&& tareaAnterior.getRequerida() == Requerida.OBLIGATORIA
								|| tarea.getRequerida() == Requerida.OBLIGATORIA) {
							try {
								listaTareas.agregarPosicion(tarea, posicion);
								break;
							} catch (Exception e) {
								throw new PosicionInvalidaTareaException(e.getMessage());
							}
						} else {
							throw new DosTareasOpcionalesException("No se pueden crear dos tareas opcionales seguidas");
						}
					}
					tareaAnterior = tareaActual;
					contador++;
				}
			}
			break;
		default:
			break;
		}
	}

	public int getTiempoMin() {
		int acomulador = 0;
		Iterator<Tarea> it = listaTareas.iterator();
		while (it.hasNext()) {
			Tarea tarea = it.next();
			if (tarea.getRequerida() == Requerida.OBLIGATORIA) {
				acomulador += tarea.getDuracion();
			}
		}
		return acomulador;
	}

	public int getTiempoMax() {
		int acomulador = 0;
		Iterator<Tarea> it = listaTareas.iterator();
		while (it.hasNext()) {
			Tarea tarea = it.next();
			acomulador += tarea.getDuracion();
		}
		return acomulador;
	}
	
	public void eliminarTarea(Tarea tarea) throws TareaNoExisteException, DosTareasOpcionalesException {
		Iterator<Tarea> it = listaTareas.iterator();
		boolean eliminado = false;
		Tarea tareaActual = null;
		Tarea tareaAnterior = null;
		int contador = 0;
		while(it.hasNext()) {
			tareaActual = it.next();
			if(tareaActual.equals(tarea)) {
				if(tareaAnterior != null) {
					if(it.hasNext()) {
						Tarea tareaSiguiente = it.next();
						if(tareaSiguiente.getRequerida() == Requerida.OBLIGATORIA || tareaAnterior.getRequerida() == Requerida.OBLIGATORIA) {
							listaTareas.eliminar(contador);
							eliminado = true;
							break;
						} else {
							throw new DosTareasOpcionalesException("No pueden haber dos tareas opcionales seguidas");
						}
					} else {
						if(tareaAnterior.getRequerida() == Requerida.OBLIGATORIA || tareaActual.getRequerida() == Requerida.OBLIGATORIA) {
							listaTareas.eliminar(contador);
							eliminado = true;
							break;
						} else {
							throw new DosTareasOpcionalesException("No pueden haber dos tareas opcionales seguidas");
						}
					}
				} else {
					listaTareas.eliminar();
					eliminado = true;
					break;
				}
			}
			tareaAnterior = tareaActual;
			contador++;
		}
		if(!eliminado) {
			throw new TareaNoExisteException("No se a econtrado la tarea a eliminar");
		}
	}
}
