package co.edu.uniquindio.listas.persistencia;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ArchivoUtil {
	
	public static Object cargarRecursoSerializadoXML(String rutaArchivo) throws IOException {
		XMLDecoder decodificadorXML;
		Object objetoXML;
		decodificadorXML = new XMLDecoder(new FileInputStream(rutaArchivo));
		objetoXML = decodificadorXML.readObject();
		decodificadorXML.close();
		return objetoXML;
	}

	public static void salvarRecursoSerializadoXML(String rutaArchivo, Object objeto, boolean adicionar) throws IOException {
		XMLEncoder encoder;

		encoder = new XMLEncoder(new FileOutputStream(rutaArchivo, adicionar));

		encoder.writeObject(objeto);
		encoder.flush();
		
		encoder.close();

	}
}
