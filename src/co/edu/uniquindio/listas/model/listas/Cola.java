package co.edu.uniquindio.listas.model.listas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.sun.org.apache.bcel.internal.generic.ANEWARRAY;

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

	// Verificar si indice es valido
	private boolean indiceValido(int indice) {
		if (indice >= 0 && indice <= tamanio) {
			return true;
		}
		throw new RuntimeException("Índice no válido");
	}

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

	public void agregarPosicion(T dato, int indice) throws Exception {

		if (indiceValido(indice)) {

			if (indice == 0) {
				agregarInicio(dato);
			} else {
				Nodo<T> nuevo = new Nodo<>(dato);
				Nodo<T> anterior = obtenerNodo(indice-1);

				nuevo.setSiguienteNodo(anterior.getSiguienteNodo());
				anterior.setSiguienteNodo(nuevo);
				if(indice == tamanio) {
					nodoUltimo = nuevo;
				}
				tamanio++;
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

	public void eliminar() {
		Nodo<T> nodo = nodoPrimero;

		if (!(nodo == null)) {
			nodoPrimero = nodo.getSiguienteNodo();
			tamanio--;
			if(tamanio == 0) {
				nodoUltimo = null;
			}
		} else {
			System.out.println("La cola esta vacía.");
		}

	}

	public void eliminar(int posicion) {
		int contador = 0;
		Nodo<T> nodo = nodoPrimero;

		if (posicion == 0 && nodoPrimero != null) {
			nodoPrimero = nodo.getSiguienteNodo();
			tamanio--;
		} else {
			Nodo<T> aux = nodoPrimero;
			Nodo<T> anterior = null;
			while (aux != null) {
				if (contador == posicion) {
					anterior.setSiguienteNodo(aux.getSiguienteNodo());
					if(contador == tamanio) {
						nodoUltimo = anterior;
					}
					tamanio--;
				}

				contador++;
				anterior = aux;
				aux = aux.getSiguienteNodo();
			}
		}
		if(tamanio == 0) {
			nodoPrimero = nodoUltimo = null;
		}
	}
	
	public void editar(int indice, T valor) {
		Nodo<T> nodo = new Nodo<T>(valor);
		Nodo<T> aux = nodoPrimero;
		Nodo<T> nodoAnterior = null;
		int contador = 0;
		while(aux != null) {
			if(contador == indice) {
				nodo.setSiguienteNodo(aux.getSiguienteNodo());
				if(nodoAnterior == null) {
					nodoPrimero = nodo;
				} else {
					nodoAnterior.setSiguienteNodo(nodo);
					nodo.setSiguienteNodo(aux.getSiguienteNodo());
				}	
			}
			nodoAnterior = aux;
			aux = aux.getSiguienteNodo();
			contador++;
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

		public void remove() {
			eliminar(posicion - 1);
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
