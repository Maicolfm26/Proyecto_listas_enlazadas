package co.edu.uniquindio.listas.model;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import co.edu.uniquindio.listas.model.listas.ListaSimple;

public class Contenedor extends RecursiveTreeObject<Contenedor> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String identificacionContenedor;
	private ListaSimple<Proceso> listaProcesos;

	public Contenedor() {
		identificacionContenedor = cadenaAleatoria(15);
		listaProcesos = new ListaSimple<Proceso>();
	}

	public ListaSimple<Proceso> getListaProcesos() {
		return listaProcesos;
	}

	public void setListaProcesos(ListaSimple<Proceso> listaProcesos) {
		this.listaProcesos = listaProcesos;
	}
	
	public String getIdentificacionContenedor() {
		return identificacionContenedor;
	}

	public void setIdentificacionContenedor(String identificacionContenedor) {
		this.identificacionContenedor = identificacionContenedor;
	}

	private static String cadenaAleatoria(int longitud) {
	    // El banco de caracteres
	    String banco = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	    // La cadena en donde iremos agregando un carácter aleatorio
	    String cadena = "";
	    for (int x = 0; x < longitud; x++) {
	        int indiceAleatorio = numeroAleatorioEnRango(0, banco.length() - 1);
	        char caracterAleatorio = banco.charAt(indiceAleatorio);
	        cadena += caracterAleatorio;
	    }
	    return cadena;
	}
	
	private static int numeroAleatorioEnRango(int minimo, int maximo) {
        // nextInt regresa en rango pero con límite superior exclusivo, por eso sumamos 1
        return ThreadLocalRandom.current().nextInt(minimo, maximo + 1);
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((identificacionContenedor == null) ? 0 : identificacionContenedor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contenedor other = (Contenedor) obj;
		if (identificacionContenedor == null) {
			if (other.identificacionContenedor != null)
				return false;
		} else if (!identificacionContenedor.equals(other.identificacionContenedor))
			return false;
		return true;
	}

}
