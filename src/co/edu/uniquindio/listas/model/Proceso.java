package co.edu.uniquindio.listas.model;

import java.io.Serializable;
import java.util.Iterator;

import co.edu.uniquindio.listas.exceptions.ActividadNoExisteException;
import co.edu.uniquindio.listas.exceptions.DosTareasOpcionalesException;
import co.edu.uniquindio.listas.exceptions.PosicionInvalidaTareaException;
import co.edu.uniquindio.listas.exceptions.ProcesoNoExisteException;
import co.edu.uniquindio.listas.exceptions.YaExisteActividadException;
import co.edu.uniquindio.listas.exceptions.YaExisteProcesoException;
import co.edu.uniquindio.listas.model.listas.Cola;
import co.edu.uniquindio.listas.model.listas.ListaDoble;

public class Proceso implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String nombre;
	private Actividad ultimaCreada;
	private ListaDoble<Actividad> listaActividades;

	public Proceso() {

	}

	public Proceso(String id, String nombre) {
		this.id = id;
		this.nombre = nombre;
		listaActividades = new ListaDoble<Actividad>();
		ultimaCreada = new Actividad();
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

	public void agregarActividad(Actividad actividad, Actividad actividaAnterior, int opcion)
			throws YaExisteActividadException, ActividadNoExisteException {
		if (listaActividades.obtenerPosicionNodo(actividad) == -1) {
			switch (opcion) {
			case 0:
				listaActividades.agregarfinal(actividad);
				break;
			case 1:
				try {
					listaActividades.agregarReferencia(actividad, actividaAnterior);
				} catch (Exception e) {
					throw new ActividadNoExisteException(e.getMessage());
				}
				break;
			case 2:
				try {
					listaActividades.agregarReferencia(actividad, ultimaCreada);
				} catch (Exception e) {
					throw new ActividadNoExisteException(e.getMessage());
				}
				break;
			default:
				break;
			}
			ultimaCreada = actividad;
		} else {
			throw new YaExisteActividadException("Ya hay una actividad con este nombre");
		}
	}

	public void eliminarActividad(Actividad actividad) throws ActividadNoExisteException {
		try {
			listaActividades.eliminar(actividad);
		} catch (Exception e) {
			throw new ActividadNoExisteException("La actividad no existe");
		}
	}

	public void editarActividad(Actividad actividad, Actividad actividadActualizada)
			throws ActividadNoExisteException, YaExisteActividadException {
		try {
			listaActividades.modificarNodo(actividad, actividadActualizada);
		} catch (Exception e) {
			if (e.getMessage().equals("0")) {
				throw new ActividadNoExisteException("La actividad a editar no ha sido encontrada");
			} else {
				throw new YaExisteActividadException("Ya existe una actividad con este nombre");
			}
		}
	}

	public Actividad obtenerActividad(Actividad actividad) {
		Iterator<Actividad> it = listaActividades.iterator();
		Actividad actividad2 = null;

		while (it.hasNext()) {
			actividad2 = it.next();
			if (actividad2.equals(actividad)) {
				return actividad2;
			}
		}
		return null;
	}

	public Cola<Tarea> getTareas(Actividad actividad) {
		return obtenerActividad(actividad).getListaTareas();
	}

	public void agregarTarea(Actividad actividad, Tarea tarea, int posicion, int opcion)
			throws DosTareasOpcionalesException, PosicionInvalidaTareaException {
		obtenerActividad(actividad).agregarTarea(tarea, posicion, opcion);
	}

	public int getTiempoMin() {
		int acomulador = 0;
		Iterator<Actividad> it = listaActividades.iterator();
		while (it.hasNext()) {
			Actividad actividad = it.next();
			acomulador += actividad.getTiempoMin();
		}
		return acomulador;
	}

	public int getTiempoMax() {
		int acomulador = 0;
		Iterator<Actividad> it = listaActividades.iterator();
		while (it.hasNext()) {
			Actividad actividad = it.next();
			acomulador += actividad.getTiempoMax();
		}
		return acomulador;
	}
}
