package co.edu.uniquindio.listas.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import co.edu.uniquindio.listas.controller.ModelFactoryController;
import co.edu.uniquindio.listas.exceptions.ActividadNoExisteException;
import co.edu.uniquindio.listas.exceptions.DosTareasOpcionalesException;
import co.edu.uniquindio.listas.exceptions.PosicionInvalidaTareaException;
import co.edu.uniquindio.listas.exceptions.ProcesoNoExisteException;
import co.edu.uniquindio.listas.exceptions.YaExisteActividadException;
import co.edu.uniquindio.listas.exceptions.YaExisteProcesoException;
import co.edu.uniquindio.listas.model.Actividad;
import co.edu.uniquindio.listas.model.Contenedor;
import co.edu.uniquindio.listas.model.Proceso;
import co.edu.uniquindio.listas.model.Requerida;
import co.edu.uniquindio.listas.model.Tarea;
import co.edu.uniquindio.listas.model.listas.ListaSimple;

public class ContenedorTest {
	
	private Contenedor contenedor;
	private ListaSimple<Proceso> listaProcesos;
	
	@Before
	public void setUp() throws Exception {
		contenedor = new Contenedor();
		listaProcesos = contenedor.getListaProcesos();
	}
	
	@Test
	public void testAgregarProceso() {
		try {
			contenedor.agregarProceso(new Proceso("1", "Servir almuerzo"));
		} catch (YaExisteProcesoException e) {
		}
		
		try {
			//no se debe agregar porque ya exite el id
			contenedor.agregarProceso(new Proceso("1", "Llevar domicilio"));
		} catch (YaExisteProcesoException e) {
		}
		
		try {
			contenedor.agregarProceso(new Proceso("2", "Generar recibo"));
			contenedor.agregarProceso(new Proceso("3", "Entregar pedido"));
		} catch (YaExisteProcesoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		assertEquals(3, listaProcesos.getTamanio());
		
		assertEquals("Servir almuerzo", listaProcesos.obtenerValorNodo(0).getNombre());
		
		//debe ser 2 ya que el 1 que seguia no se agrega
		assertEquals("2", listaProcesos.obtenerValorNodo(1).getId());
	}

	@Test
	public void testEliminarProceso() {
		try {
			contenedor.agregarProceso(new Proceso("1", "Servir almuerzo"));
			contenedor.agregarProceso(new Proceso("2", "Tomar pedido"));
			contenedor.agregarProceso(new Proceso("3", "Generar recibo"));
			contenedor.agregarProceso(new Proceso("4", "LLevar domicilio"));
			contenedor.agregarProceso(new Proceso("5", "Reservar mesas"));
			contenedor.agregarProceso(new Proceso("6", "Tomar carta"));
			contenedor.agregarProceso(new Proceso("7", "Entregar almuerzo"));
		} catch (YaExisteProcesoException e) {
		}
		
		contenedor.eliminarProceso(new Proceso("1", "Servir almuerzo"));
		contenedor.eliminarProceso(new Proceso("7", "Servir almuerzo"));
		
		assertEquals(5, listaProcesos.getTamanio());
		
		assertEquals(null, contenedor.eliminarProceso(new Proceso("8", "Servir almuerzo")));
		
		assertEquals(5, listaProcesos.getTamanio());
		
		assertEquals("2", listaProcesos.obtenerValorNodo(0).getId());
		assertEquals("6", listaProcesos.obtenerValorNodo(4).getId());
	}

	@Test
	public void testEditarProceso() {
		boolean editado = true;
		try {
			contenedor.agregarProceso(new Proceso("1", "Servir almuerzo"));
			contenedor.agregarProceso(new Proceso("2", "Tomar pedido"));
			contenedor.agregarProceso(new Proceso("3", "Generar recibo"));
			contenedor.agregarProceso(new Proceso("4", "LLevar domicilio"));
			contenedor.agregarProceso(new Proceso("5", "Reservar mesas"));
			contenedor.agregarProceso(new Proceso("6", "Tomar carta"));
			contenedor.agregarProceso(new Proceso("7", "Entregar almuerzo"));
		} catch (YaExisteProcesoException e) {
		}
		
		try {
			contenedor.editarProceso(new Proceso("1", "Servir almuerzo"), new Proceso("7", "Entregar almuerzo"));
		} catch (ProcesoNoExisteException | YaExisteProcesoException e) {
			editado = false;
		}
		
		assertEquals(false, editado);
		assertEquals("1", listaProcesos.obtenerValorNodo(0).getId());
		
		try {
			contenedor.editarProceso(new Proceso("8", "Mirar ingredientes"), new Proceso("9", "Pesar ingredientes"));
			editado = true;
		} catch (ProcesoNoExisteException | YaExisteProcesoException e) {
			editado = false;
		}
		
		assertEquals(false, editado);
		
		try {
			contenedor.editarProceso(new Proceso("4", "LLevar domicilio"), new Proceso("9", "Pesar ingredientes"));
			editado = true;
		} catch (ProcesoNoExisteException | YaExisteProcesoException e) {
			editado = false;
		}
		
		assertEquals(true, editado);
		assertEquals("9", listaProcesos.obtenerValorNodo(3).getId());
		assertEquals("Pesar ingredientes", listaProcesos.obtenerValorNodo(3).getNombre());
	}
	
	@Test
	public void testgetTiempoMin() {
		ModelFactoryController singleton = ModelFactoryController.getInstance();
		try {
			String ruta = ContenedorTest.class.getResource("c2.dat").getPath();
			ruta = ruta.replaceAll("%20", " ");
			singleton.cargarContenedor(ruta);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		contenedor = singleton.getContenedor();
		
		assertEquals(7, contenedor.obtenerProceso(new Proceso("1", "proceso 1")).getTiempoMin());
		assertEquals(37, contenedor.obtenerProceso(new Proceso("2", "proceso 2")).getTiempoMin());
	}
	
	@Test
	public void testgetTiempoMax() {
		ModelFactoryController singleton = ModelFactoryController.getInstance();
		try {
			String ruta = ContenedorTest.class.getResource("c2.dat").getPath();
			ruta = ruta.replaceAll("%20", " ");
			singleton.cargarContenedor(ruta);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		contenedor = singleton.getContenedor();
		
		assertEquals(35, contenedor.obtenerProceso(new Proceso("1", "proceso 1")).getTiempoMax());
		assertEquals(49, contenedor.obtenerProceso(new Proceso("2", "proceso 2")).getTiempoMax());
	}

}
