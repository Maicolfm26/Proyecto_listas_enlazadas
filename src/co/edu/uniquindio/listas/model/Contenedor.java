package co.edu.uniquindio.listas.model;

import java.io.Serializable;
import java.util.Iterator;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import co.edu.uniquindio.listas.exceptions.ActividadNoExisteException;
import co.edu.uniquindio.listas.exceptions.DosTareasOpcionalesException;
import co.edu.uniquindio.listas.exceptions.PosicionInvalidaTareaException;
import co.edu.uniquindio.listas.exceptions.ProcesoNoExisteException;
import co.edu.uniquindio.listas.exceptions.TareaNoExisteException;
import co.edu.uniquindio.listas.exceptions.YaExisteActividadException;
import co.edu.uniquindio.listas.exceptions.YaExisteProcesoException;
import co.edu.uniquindio.listas.model.listas.Cola;
import co.edu.uniquindio.listas.model.listas.ListaDoble;
import co.edu.uniquindio.listas.model.listas.ListaSimple;

public class Contenedor extends RecursiveTreeObject<Contenedor> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private ListaSimple<Proceso> listaProcesos;

	public Contenedor() {
		listaProcesos = new ListaSimple<Proceso>();
	}

	public ListaSimple<Proceso> getListaProcesos() {
		return listaProcesos;
	}

	public void setListaProcesos(ListaSimple<Proceso> listaProcesos) {
		this.listaProcesos = listaProcesos;
	}

	public void agregarProceso(Proceso proceso) throws YaExisteProcesoException {
		if(listaProcesos.obtenerPosicionNodo(proceso) == -1) {
			listaProcesos.agregarfinal(proceso);
		} else {
			throw new YaExisteProcesoException("Ya hay un proceso con este id");
		}
	}

	public void eliminarProceso(Proceso proceso) {
		listaProcesos.eliminar(proceso);
	}

	public void editarProceso(Proceso proceso, Proceso procesoActualizado) throws ProcesoNoExisteException, YaExisteProcesoException {
		try {
			listaProcesos.modificarNodo(proceso, procesoActualizado);
		} catch (Exception e) {
			if(e.getMessage().equals("0")) {
				throw new ProcesoNoExisteException("El proceso a editar no ha sido encontrado");
			} else {
				throw new YaExisteProcesoException("Ya existe un proceso con este ID");
			}
		}
	}
	
	public Proceso obtenerProceso(Proceso proceso) {
		Iterator<Proceso> it = listaProcesos.iterator();
		Proceso proc = null;
		
		while(it.hasNext()) {
			proc = it.next();
			if(proc.equals(proceso)) {
				return proc;
			}
		}
		return null;
	}
	
	public ListaDoble<Actividad> getActividadesProceso(Proceso proceso) {
		return obtenerProceso(proceso).getListaActividades();
	}
	
	public void agregarActividad(Proceso proceso, Actividad actividad, Actividad actividadAnterior, int opcion) throws YaExisteActividadException, ActividadNoExisteException {
		obtenerProceso(proceso).agregarActividad(actividad, actividadAnterior, opcion);
	}
	
	public void eliminarActividad(Proceso proceso, Actividad actividad) throws ActividadNoExisteException {
		obtenerProceso(proceso).eliminarActividad(actividad);
	}
	
	public void editarActividad(Proceso proceso, Actividad actividad, Actividad actividadActualizada) throws ActividadNoExisteException, YaExisteActividadException {
		obtenerProceso(proceso).editarActividad(actividad, actividadActualizada);
	}
	
	public Cola<Tarea> getTareas(Proceso proceso, Actividad actividad) {
		return obtenerProceso(proceso).getTareas(actividad);
	}
	
	public void agregarTarea(Proceso proceso, Actividad actividad, Tarea tarea, int posicion, int opcion) throws DosTareasOpcionalesException, PosicionInvalidaTareaException {
		obtenerProceso(proceso).agregarTarea(actividad, tarea, posicion, opcion);
	}
	
	public void eliminarTarea(Proceso proceso, Actividad actividad, Tarea tarea) throws TareaNoExisteException, DosTareasOpcionalesException {
		obtenerProceso(proceso).eliminarTarea(actividad, tarea);
	}
	
	public void editarTarea(Proceso proceso, Actividad actividad, Tarea tarea, Tarea tareaActualizada) throws DosTareasOpcionalesException {
		obtenerProceso(proceso).editarTarea(actividad, tarea, tareaActualizada);
	}
}
