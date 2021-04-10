package co.edu.uniquindio.listas.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import co.edu.uniquindio.listas.model.Contenedor;
import co.edu.uniquindio.listas.model.listas.ListaSimple;
import co.edu.uniquindio.listas.persistencia.Persistencia;

public class ModelFactoryController {

	private static ModelFactoryController instancia;
	private ListaSimple<Contenedor> listaContenedores;

	private ModelFactoryController() {
		if (listaContenedores == null) {
			listaContenedores = new ListaSimple<Contenedor>();
		}
	}

	public static ModelFactoryController getInstance() {
		if (instancia == null) {
			instancia = new ModelFactoryController();
		}
		return instancia;
	}

	public Collection<Contenedor> getlistaContenedores() {
		return listaContenedores.creadorTablas();
	}

	public void crearContenedor(Contenedor contenedor) {
		listaContenedores.agregarfinal(contenedor);
	}
	
	public void eliminarContenedor(Contenedor contenedor) {
		listaContenedores.eliminar(contenedor);
	}
	
	public void guardarContenedor(Contenedor contenedor, String rutaArchivo) throws FileNotFoundException, IOException {
		Persistencia.guardarRecursoContenedorXML(contenedor, rutaArchivo);
	}
	
	public Contenedor cargarContenedor(String ruta) throws IOException {
		Contenedor contenedor = Persistencia.cargarRecursoContenedorXML(ruta);
		listaContenedores.agregarfinal(contenedor);
		return contenedor;
	}
//	public void guardarRecursoBinario(String rutaArchivo) throws FileNotFoundException, IOException {
//		System.out.println("Guardando..");
//		Persistencia.guardarRecursoContenedorBinario(contenedor, rutaArchivo);
//	}
//	
//	public void cargarRecursoBinario(String rutaArchivo) {
//		contenedor = Persistencia.cargarRecursoContenedorBinario(rutaArchivo);
//	}
}
