package co.edu.uniquindio.listas.model.listas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * 
 * Definici�n de la clase lista Simple de tipo Generics
 * 
 * @param <T>
 * 
 **/

public class ListaSimple<T> implements Iterable<T>, Serializable {

	private static final long serialVersionUID = 1L;
	private Nodo<T> nodoPrimero;
	private Nodo<T> nodoUltimo;
	private int tamanio;

	public ListaSimple() {
		nodoPrimero = null;
		nodoUltimo = null;
		tamanio = 0;
	}

	// Metodos basicos

	// Agregar al inicio de la lista
	public void agregarInicio(T valorNodo) {

		Nodo<T> nuevoNodo = new Nodo<>(valorNodo);

		if (estaVacia()) {
			nodoPrimero = nodoUltimo = nuevoNodo;
		} else {
			nuevoNodo.setSiguienteNodo(nodoPrimero);
			nodoPrimero = nuevoNodo;
		}
		tamanio++;
	}

	// Agregar al final de la lista
	public void agregarfinal(T valorNodo) {

		Nodo<T> nodo = new Nodo<>(valorNodo);

		if (estaVacia()) {
			nodoPrimero = nodoUltimo = nodo;
		} else {
			nodoUltimo.setSiguienteNodo(nodo);
			nodoUltimo = nodo;
		}

		tamanio++;
	}

	// Obtener Nodo el valor de un Nodo
	public T obtenerValorNodo(int indice) {

		Nodo<T> nodoTemporal = null;
		int contador = 0;

		if (indiceValido(indice)) {
			nodoTemporal = nodoPrimero;

			while (contador < indice) {

				nodoTemporal = nodoTemporal.getSiguienteNodo();
				contador++;
			}
		}

		if (nodoTemporal != null)
			return nodoTemporal.getValorNodo();
		else
			return null;
	}

	// Verificar si indice es valido
	private boolean indiceValido(int indice) {
		if (indice >= 0 && indice < tamanio) {
			return true;
		}
		throw new RuntimeException("�ndice no v�lido");
	}

	// Verificar si la lista esta vacia
	public boolean estaVacia() {
		return (nodoPrimero == null) ? true : false;
	}

	/**
	 * Imprime en consola la lista enlazada
	 */
	public void imprimirLista() {

		Nodo<T> aux = nodoPrimero;

		while (aux != null) {
			System.out.print(aux.getValorNodo() + "\t");
			aux = aux.getSiguienteNodo();
		}

		System.out.println();
	}

	// Eliminar dado el valor de un nodo
	public T eliminar(T dato) {
		Nodo<T> nodo = nodoPrimero;
		Nodo<T> previo = null;
		Nodo<T> siguiente = null;
		boolean encontrado = false;

		// buscar el nodo previo
		while (nodo != null) {
			if (nodo.getValorNodo() == dato) {
				encontrado = true;
				break;
			}
			previo = nodo;
			nodo = nodo.getSiguienteNodo();
		}

		if (encontrado) {
			siguiente = nodo.getSiguienteNodo();
			if (previo == null) {
				nodoPrimero = siguiente;
			} else {
				previo.setSiguienteNodo(siguiente);
			}

			if (siguiente == null) {
//				nodoUltimo = previo;
			} else {
				nodo.setSiguienteNodo(null);
			}

			nodo = null;
			tamanio--;
			return dato;
		}
		throw new RuntimeException("El elemento no existe");
	}

	// Elimina el primer nodo de la lista
	public T eliminarPrimero() {

		if (!estaVacia()) {
			Nodo<T> n = nodoPrimero;
			T valor = n.getValorNodo();
			nodoPrimero = n.getSiguienteNodo();

			if (nodoPrimero == null) {
//				nodoUltimo = null;
			}

			tamanio--;
			return valor;
		}

		throw new RuntimeException("Lista vac�a");
	}

	private Nodo<T> obtenerNodo(int indice) {

		if (indice >= 0 && indice < tamanio) {

			Nodo<T> nodo = nodoPrimero;

			for (int i = 0; i < indice; i++) {
				nodo = nodo.getSiguienteNodo();
			}

			return nodo;
		}

		return null;
	}

	private Nodo<T> obtenerNodo(T valor) {

		Nodo<T> nodo = nodoPrimero;

		while (nodo != null) {
			if (nodo.getValorNodo().equals(valor)) {
				return nodo;
			}
			nodo = nodo.getSiguienteNodo();
		}
		return null;
	}

	/**
	 * Cambia el valor de un nodo dada su posici�n en la lista
	 * 
	 * @param indice posici�n donde se va a cambiar el dato
	 * @param nuevo  nuevo valor por el que se actualizar� el nodo
	 */
	public void modificarNodo(int indice, T nuevo) {

		if (indiceValido(indice)) {
			Nodo<T> nodo = obtenerNodo(indice);
			nodo.setValorNodo(nuevo);
		}

	}

	public void modificarNodo(T nodo, T nuevo) throws Exception {
		Nodo<T> nodoAntiguo = obtenerNodo(nodo);
		if(nodoAntiguo == null) {
			throw new Exception("Nodo no encontrado");
		}
		nodoAntiguo.setValorNodo(nuevo);
	}

	/**
	 * Retorna la primera posici�n donde est� guardado el dato
	 * 
	 * @param dato valor a buscar dentro de la lista
	 * @return primera posici�n del dato
	 */
	public int obtenerPosicionNodo(T dato) {

		int i = 0;

		for (Nodo<T> aux = nodoPrimero; aux != null; aux = aux.getSiguienteNodo()) {
			if (aux.getValorNodo().equals(dato)) {
				return i;
			}
			i++;
		}

		return -1;
	}

	@Override
	public Iterator<T> iterator() {
		return new IteradorListaSimple(nodoPrimero);
	}

	protected class IteradorListaSimple implements Iterator<T> {

		private Nodo<T> nodo;
		private int posicion;

		/**
		 * Constructor de la clase Iterador
		 * 
		 * @param aux Primer Nodo de la lista
		 */
		public IteradorListaSimple(Nodo<T> nodo) {
			this.nodo = nodo;
			this.posicion = 0;
		}

		@Override
		public boolean hasNext() {
			return nodo != null;
		}

		@Override
		public T next() {
			T valor = nodo.getValorNodo();
			nodo = nodo.getSiguienteNodo();
			posicion++;
			return valor;
		}

		/**
		 * Posici�n actual de la lista
		 * 
		 * @return posici�n
		 */
		public int getPosicion() {
			return posicion;
		}

	}

	public Collection<T> creadorTablas() {
		Collection<T> lista = new ArrayList<>();

		Nodo<T> aux = nodoPrimero;

		while (aux != null) {
			lista.add(aux.getValorNodo());
			aux = aux.getSiguienteNodo();
		}
		return lista;
	}

	// Metodos get y set de la clase ListaSimple

	public Nodo<T> getNodoPrimero() {
		return nodoPrimero;
	}

	public void setNodoPrimero(Nodo<T> nodoPrimero) {
		this.nodoPrimero = nodoPrimero;
	}

	public int getTamanio() {
		return tamanio;
	}

	public void setTamanio(int tama�o) {
		this.tamanio = tama�o;
	}

}
