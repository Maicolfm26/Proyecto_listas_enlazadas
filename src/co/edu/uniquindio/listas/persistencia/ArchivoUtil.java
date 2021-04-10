package co.edu.uniquindio.listas.persistencia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ArchivoUtil {

	public static void guardarRecursoSerializado(String rutaArchivo, Object object)
			throws FileNotFoundException, IOException {
		// Se crea un Stream para guardar archivo
		ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(rutaArchivo));
		// Se escribe el objeto en archivo
		file.writeObject(object);
		// se cierra archivo
		file.flush();
		file.close();
	}

	public static Object cargarRecursoSerializado(String rutaArchivo) {
		Object aux = null;
		ObjectInputStream ois = null;

		try {
			// Stream para leer archivo
			ois = new ObjectInputStream(new FileInputStream(rutaArchivo));
			// Se lee el objeto de archivo y este debe convertirse al tipo de clase que
			// corresponde
			aux = ois.readObject();
			// se cierra archivo
			ois.close();
		} catch (ClassNotFoundException ex) {
			 System.out.println(ex);
		} catch (IOException ex) {
			 System.out.println(ex);
		}

		return aux;
	}
}
