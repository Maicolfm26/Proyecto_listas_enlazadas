package co.edu.uniquindio.listas.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import co.edu.uniquindio.listas.exceptions.DosTareasOpcionalesException;
import co.edu.uniquindio.listas.exceptions.PosicionInvalidaTareaException;
import co.edu.uniquindio.listas.exceptions.TareaNoExisteException;
import co.edu.uniquindio.listas.model.Actividad;
import co.edu.uniquindio.listas.model.Requerida;
import co.edu.uniquindio.listas.model.Tarea;
import co.edu.uniquindio.listas.model.listas.Cola;

public class ActividadTest {

	private Actividad actividad;
	private Cola<Tarea> listaTareas;

	@Before
	public void setUp() throws Exception {
		actividad = new Actividad("implementacion", "implementacion", Requerida.OBLIGATORIA);
		listaTareas = actividad.getListaTareas();
	}

	@Test
	public void testAgregarTarea() {
		boolean agregado = true;
		try {
			actividad.agregarTarea(new Tarea("1", Requerida.OPCIONAL, 5), 0, 0);
		} catch (DosTareasOpcionalesException | PosicionInvalidaTareaException e) {
			agregado = false;
		}
		
		assertEquals(true, agregado);
		
		try {
			//no se debe agregar porque serian dos tareas opcionales seguidas
			actividad.agregarTarea(new Tarea("2", Requerida.OPCIONAL, 5), 0, 0);
			agregado = true;
		} catch (DosTareasOpcionalesException | PosicionInvalidaTareaException e) {
			agregado = false;
		}
		
		assertEquals(false, agregado);
		assertEquals(1, listaTareas.getTamanio());
		
		try {
			actividad.agregarTarea(new Tarea("2", Requerida.OBLIGATORIA, 5), 0, 0);
			actividad.agregarTarea(new Tarea("3", Requerida.OPCIONAL, 5), 0, 0);
			actividad.agregarTarea(new Tarea("4", Requerida.OBLIGATORIA, 5), 0, 0);
			actividad.agregarTarea(new Tarea("5", Requerida.OPCIONAL, 5), 0, 0);
			agregado = true;
		} catch (DosTareasOpcionalesException | PosicionInvalidaTareaException e) {
			agregado = false;
		}
		
		assertEquals(true, agregado);
		
		try {
			//No se debe agregar
			actividad.agregarTarea(new Tarea("6", Requerida.OPCIONAL, 5), 6, 1);
			agregado = true;
		} catch (DosTareasOpcionalesException | PosicionInvalidaTareaException e) {
			agregado = false;
		}
		
		assertEquals(false, agregado);
		assertEquals(5, listaTareas.getTamanio());
		
		try {
			//No se debe agregar
			actividad.agregarTarea(new Tarea("0", Requerida.OPCIONAL, 5), 1, 1);
			agregado = true;
		} catch (DosTareasOpcionalesException | PosicionInvalidaTareaException e) {
			agregado = false;
		}
		
		assertEquals(false, agregado);
		assertEquals(5, listaTareas.getTamanio());
		
		try {
			//No se debe agregar
			actividad.agregarTarea(new Tarea("0", Requerida.OPCIONAL, 5), 3, 1);
			agregado = true;
		} catch (DosTareasOpcionalesException | PosicionInvalidaTareaException e) {
			agregado = false;
		}
		
		assertEquals(false, agregado);
		assertEquals(5, listaTareas.getTamanio());
		
		try {
			//No se debe agregar
			actividad.agregarTarea(new Tarea("0", Requerida.OBLIGATORIA, 5), 3, 1);
			agregado = true;
		} catch (DosTareasOpcionalesException | PosicionInvalidaTareaException e) {
			agregado = false;
		}
		
		assertEquals(true, agregado);
		assertEquals(6, listaTareas.getTamanio());
	}

	@Test
	public void testEliminarTarea() {
		boolean eliminado = true;
		try {
			actividad.agregarTarea(new Tarea("1", Requerida.OBLIGATORIA, 5), 0, 0);
			actividad.agregarTarea(new Tarea("2", Requerida.OBLIGATORIA, 5), 0, 0);
			actividad.agregarTarea(new Tarea("3", Requerida.OPCIONAL, 5), 0, 0);
			actividad.agregarTarea(new Tarea("4", Requerida.OBLIGATORIA, 5), 0, 0);
			actividad.agregarTarea(new Tarea("5", Requerida.OPCIONAL, 5), 0, 0);
		} catch (DosTareasOpcionalesException | PosicionInvalidaTareaException e) {
		}
		
		try {
			actividad.eliminarTarea(new Tarea("1", Requerida.OBLIGATORIA, 5));
		} catch (TareaNoExisteException | DosTareasOpcionalesException e) {
			eliminado = false;
		}
		
		assertEquals(true, eliminado);
		assertEquals(4, listaTareas.getTamanio());
		
		try {
			actividad.eliminarTarea(new Tarea("4", Requerida.OBLIGATORIA, 5));
			eliminado = true;
		} catch (TareaNoExisteException | DosTareasOpcionalesException e) {
			eliminado = false;
		}
		
		assertEquals(false, eliminado);
		assertEquals(4, listaTareas.getTamanio());
	}

	@Test
	public void testEditarTarea() {
		boolean editado = true;
		try {
			actividad.agregarTarea(new Tarea("1", Requerida.OBLIGATORIA, 5), 0, 0);
			actividad.agregarTarea(new Tarea("2", Requerida.OBLIGATORIA, 5), 0, 0);
			actividad.agregarTarea(new Tarea("3", Requerida.OPCIONAL, 5), 0, 0);
			actividad.agregarTarea(new Tarea("4", Requerida.OBLIGATORIA, 5), 0, 0);
			actividad.agregarTarea(new Tarea("5", Requerida.OPCIONAL, 5), 0, 0);
		} catch (DosTareasOpcionalesException | PosicionInvalidaTareaException e) {
		}
		
		try {
			actividad.editarTarea(new Tarea("1", Requerida.OBLIGATORIA, 5), new Tarea("1", Requerida.OPCIONAL, 5));
		} catch (DosTareasOpcionalesException e) {
			editado = false;
		}
		
		assertEquals(true, editado);
		assertEquals("Opcional", listaTareas.getvalorNodo(0).getRequerida().toString());
		
		try {
			actividad.editarTarea(new Tarea("2", Requerida.OBLIGATORIA, 5), new Tarea("2", Requerida.OPCIONAL, 5));
		} catch (DosTareasOpcionalesException e) {
			editado = false;
		}
		
		assertEquals(false, editado);
		assertEquals("Obligatoria", listaTareas.getvalorNodo(1).getRequerida().toString());
		
		try {
			actividad.editarTarea(new Tarea("4", Requerida.OBLIGATORIA, 5), new Tarea("4", Requerida.OPCIONAL, 5));
		} catch (DosTareasOpcionalesException e) {
			editado = false;
		}
		
		assertEquals(false, editado);
		assertEquals("Obligatoria", listaTareas.getvalorNodo(3).getRequerida().toString());
	}

}
