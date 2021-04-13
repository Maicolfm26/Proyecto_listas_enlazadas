package co.edu.uniquindio.listas.persistencia;

import java.io.FileNotFoundException;
import java.io.IOException;

import co.edu.uniquindio.listas.model.Contenedor;

public class Persistencia {
	
	public static Contenedor cargarRecursoContenedorBinario(String ruta) {
		return (Contenedor) ArchivoUtil.cargarRecursoSerializado(ruta);
	}

	public static void guardarRecursoContenedorBinario(Contenedor contenedor, String ruta) throws FileNotFoundException, IOException {
		ArchivoUtil.guardarRecursoSerializado(ruta, contenedor);
	}
	
	public static void guardarRecursoContenedorXML(Contenedor contenedor, String rutaArchivo) throws IOException {
		ArchivoUtil.salvarRecursoSerializadoXML(rutaArchivo, contenedor, false);
	}

	public static Contenedor cargarRecursoContenedorXML(String rutaArchivo) throws IOException {
		return (Contenedor) ArchivoUtil.cargarRecursoSerializadoXML(rutaArchivo);
	}
}
