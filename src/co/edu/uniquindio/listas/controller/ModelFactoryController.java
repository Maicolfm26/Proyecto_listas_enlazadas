package co.edu.uniquindio.listas.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import co.edu.uniquindio.listas.model.Contenedor;
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

	public void guardarRecursoBinario(String rutaArchivo) throws FileNotFoundException, IOException {
		System.out.println("Guardando..");
		Persistencia.guardarRecursoContenedorBinario(contenedor, rutaArchivo);
	}
	
	public void cargarRecursoBinario(String rutaArchivo) {
		contenedor = Persistencia.cargarRecursoContenedorBinario(rutaArchivo);
	}
}
