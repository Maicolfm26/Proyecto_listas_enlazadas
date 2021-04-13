package co.edu.uniquindio.listas.model;

import java.io.Serializable;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import co.edu.uniquindio.listas.exceptions.ProcesoNoExisteException;
import co.edu.uniquindio.listas.exceptions.YaExisteProcesoException;
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

}
