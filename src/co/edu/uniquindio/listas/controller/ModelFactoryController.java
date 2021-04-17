package co.edu.uniquindio.listas.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import co.edu.uniquindio.listas.exceptions.ActividadNoExisteException;
import co.edu.uniquindio.listas.exceptions.ProcesoNoExisteException;
import co.edu.uniquindio.listas.exceptions.YaExisteActividadException;
import co.edu.uniquindio.listas.exceptions.YaExisteProcesoException;
import co.edu.uniquindio.listas.model.Actividad;
import co.edu.uniquindio.listas.model.Contenedor;
import co.edu.uniquindio.listas.model.Proceso;
import co.edu.uniquindio.listas.model.listas.ListaDoble;
import co.edu.uniquindio.listas.model.listas.ListaSimple;
import co.edu.uniquindio.listas.persistencia.Persistencia;

public class ModelFactoryController {

	private static ModelFactoryController instancia;
	private Contenedor contenedor;

	private ModelFactoryController() {
		if (contenedor == null) {
			contenedor = new Contenedor();
		}
	}

	public static ModelFactoryController getInstance() {
		if (instancia == null) {
			instancia = new ModelFactoryController();
		}
		return instancia;
	}
	
	public void guardarContenedor(String rutaArchivo) throws FileNotFoundException, IOException {
		Persistencia.guardarRecursoContenedorBinario(contenedor, rutaArchivo);
	}
	
	public void cargarContenedor(String ruta) throws IOException {
		contenedor = Persistencia.cargarRecursoContenedorBinario(ruta);
	}
	
	public ListaSimple<Proceso> getListadoProcesos() {
		return contenedor.getListaProcesos();
	}

	public void agregarProceso(Proceso proceso) throws YaExisteProcesoException {
		contenedor.agregarProceso(proceso);
	}
	
	public ModelFactoryController nuevoContenedor() {
		instancia = null;
		contenedor = null;
		return getInstance();
	}

	public void eliminarProceso(Proceso proceso) {
		contenedor.eliminarProceso(proceso);
	}

	public void editarProceso(Proceso proceso, Proceso procesoActualizado) throws ProcesoNoExisteException, YaExisteProcesoException {
		contenedor.editarProceso(proceso, procesoActualizado);
	}
	
	public ListaDoble<Actividad> getActividadesProceso(Proceso proceso) {
		return contenedor.getActividadesProceso(proceso);
	}
	
	public void agregarActividad(Proceso proceso, Actividad actividad, Actividad actividadAnterior, int opcion) throws YaExisteActividadException, ActividadNoExisteException {
		contenedor.agregarActividad(proceso, actividad, actividadAnterior, opcion);
	}
	
	public void eliminarActividad(Proceso proceso, Actividad actividad) throws ActividadNoExisteException {
		contenedor.eliminarActividad(proceso, actividad);
	}
	
	public void editarActividad(Proceso proceso, Actividad actividad, Actividad actividadActualizada) throws ActividadNoExisteException, YaExisteActividadException {
		contenedor.editarActividad(proceso, actividad, actividadActualizada);
	}
}
