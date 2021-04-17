package co.edu.uniquindio.listas.model.listas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Cola<T> implements Iterable<T>, Serializable {

	private static final long serialVersionUID = 1L;
	private Nodo<T> nodoPrimero;
	private Nodo<T> nodoUltimo;
	private int tamanio;

	public Cola() {
		super();
		this.nodoPrimero = null;
		this.nodoUltimo = null;
		this.tamanio = 0;
	}

	public void agregar(T valorNodo) {

		Nodo<T> nodo = new Nodo<>(valorNodo);

		if (estaVacia()) {
			nodoPrimero = nodoUltimo = nodo;
		} else {
			nodoUltimo.setSiguienteNodo(nodo);
			nodoUltimo = nodo;
		}

		tamanio++;
	}

	public int getTamanio() {
		return tamanio;
	}

	public void agregarPosicion(T valorNodo, int posicion) throws Exception {
		Nodo<T> nodo = new Nodo<>(valorNodo);
		
		if (posicion == tamanio + 1) {
			nodoUltimo.setSiguienteNodo(nodo);
			nodoUltimo = nodo;
		} else if (posicion < tamanio) {
			Nodo<T> aux = nodoPrimero;
			int contador = 0;
			while (aux != null) {
				if (contador == posicion - 1) {
					Nodo<T> siguiente = aux.getSiguienteNodo();
					aux.setSiguienteNodo(nodo);
					nodo.setSiguienteNodo(siguiente);
					break;
				}
				aux = aux.getSiguienteNodo();
			}
		} else {
			throw new Exception("Posicion invalida");
		}
	}

	public boolean estaVacia() {
		return (nodoPrimero == null) ? true : false;
	}

	public void imprimirLista() {

		Nodo<T> aux = nodoPrimero;

		while (aux != null) {
			System.out.print(aux.getValorNodo() + "\t");
			aux = aux.getSiguienteNodo();
		}

		System.out.println();
	}

	public void eliminar(T dato) {
		Nodo<T> nodo = nodoPrimero;

		if (!(nodo == null)) {
			nodoPrimero = nodo.getSiguienteNodo();
		} else {
			System.out.println("La cola esta vacía.");
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

	@Override
	public Iterator<T> iterator() {
		return new IteradorCola(nodoPrimero);
	}

	protected class IteradorCola implements Iterator<T> {

		private Nodo<T> nodo;
		private int posicion;

		/**
		 * Constructor de la clase Iterador
		 * 
		 * @param aux Primer Nodo de la lista
		 */
		public IteradorCola(Nodo<T> nodo) {
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
		 * Posición actual de la lista
		 * 
		 * @return posición
		 */
		public int getPosicion() {
			return posicion;
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
	}

	public T getNodoUltimo() {
		return nodoUltimo.getValorNodo();
	}

	public T getNodoPrimero() {
		return nodoPrimero.getValorNodo();
	}

	public T getvalorNodo(int posicion) {
		if (posicion == 0) {
			return nodoPrimero.getValorNodo();
		} else if (posicion == tamanio - 1) {
			return nodoUltimo.getValorNodo();
		} else {
			Nodo<T> aux = nodoPrimero;
			int contador = 0;
			while (aux != null) {
				if (contador == posicion) {
					return aux.getValorNodo();
				}
				aux = aux.getSiguienteNodo();
				contador++;
			}
		}
		return null;
	}
}
