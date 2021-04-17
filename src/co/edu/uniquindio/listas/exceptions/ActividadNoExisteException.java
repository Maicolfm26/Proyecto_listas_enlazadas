package co.edu.uniquindio.listas.exceptions;

public class ActividadNoExisteException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ActividadNoExisteException(String mensaje) {
		super(mensaje);
	}
	
}
