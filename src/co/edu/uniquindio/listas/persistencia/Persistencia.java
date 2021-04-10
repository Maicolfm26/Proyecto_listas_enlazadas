package co.edu.uniquindio.listas.persistencia;

import java.io.FileNotFoundException;
import java.io.IOException;

import co.edu.uniquindio.listas.model.Contenedor;

public class Persistencia {

	public static Contenedor cargarRecursoContenedorBinario(String rutaArchivo) {
		return (Contenedor) ArchivoUtil.cargarRecursoSerializado(rutaArchivo);
	}

	public static void guardarRecursoContenedorBinario(Contenedor contenedor, String rutaArchivo) throws FileNotFoundException, IOException {
		ArchivoUtil.guardarRecursoSerializado(rutaArchivo, contenedor);
	}
}
