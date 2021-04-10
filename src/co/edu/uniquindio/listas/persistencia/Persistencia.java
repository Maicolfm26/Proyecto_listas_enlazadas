package co.edu.uniquindio.listas.persistencia;

import java.io.IOException;

import co.edu.uniquindio.listas.model.Contenedor;

public class Persistencia {
	
	public static void guardarRecursoContenedorXML(Contenedor contenedor, String rutaArchivo) throws IOException {
		ArchivoUtil.salvarRecursoSerializadoXML(rutaArchivo, contenedor, false);
	}

	public static Contenedor cargarRecursoContenedorXML(String rutaArchivo) throws IOException {
		return (Contenedor) ArchivoUtil.cargarRecursoSerializadoXML(rutaArchivo);
	}
}
