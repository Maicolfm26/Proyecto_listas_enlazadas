package co.edu.uniquindio.listas.exceptions;

public class TareaNoExisteException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public TareaNoExisteException(String mensaje) {
		super(mensaje);
	}
	
}
