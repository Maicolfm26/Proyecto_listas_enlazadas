package co.edu.uniquindio.listas.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import co.edu.uniquindio.listas.controller.ModelFactoryController;
import co.edu.uniquindio.listas.exceptions.ActividadNoExisteException;
import co.edu.uniquindio.listas.exceptions.YaExisteActividadException;
import co.edu.uniquindio.listas.model.Actividad;
import co.edu.uniquindio.listas.model.Contenedor;
import co.edu.uniquindio.listas.model.Proceso;
import co.edu.uniquindio.listas.model.Requerida;
import co.edu.uniquindio.listas.model.listas.ListaDoble;

public class ProcesoTest {

	private Proceso proceso;
	private ListaDoble<Actividad> listaActividades;

	@Before
	public void setUp() throws Exception {
		proceso = new Proceso("1", "Generar recibo");
		listaActividades = proceso.getListaActividades();
	}

	@Test
	public void testAgregarActividad() {
		boolean agregado = true;
		try {
			proceso.agregarActividad(new Actividad("Preparar cafe", "Se prepara el cafe", Requerida.OBLIGATORIA), null,
					0);
		} catch (YaExisteActividadException | ActividadNoExisteException e) {
			agregado = false;
		}

		assertEquals(true, agregado);

		try {
			proceso.agregarActividad(new Actividad("Preparar cafe", "Se le agrega azucar", Requerida.OPCIONAL), null,
					0);
			agregado = true;
		} catch (YaExisteActividadException | ActividadNoExisteException e) {
			agregado = false;
		}

		assertEquals(false, agregado);

		try {
			// no se debe agregar ya que la referencia no existe
			proceso.agregarActividad(new Actividad("Preparar cafe", "Se le agrega azucar", Requerida.OPCIONAL),
					new Actividad("Fritar huevos", "comprar docena de huevos a 1800", Requerida.OBLIGATORIA), 1);
			agregado = true;
		} catch (YaExisteActividadException | ActividadNoExisteException e) {
			agregado = false;
		}

		assertEquals(false, agregado);

		listaActividades.eliminarPrimero();

		try {
			// se debe agregar despues de la ultima actividad creada pero como no hay
			// ninguna quedara de ultima
			proceso.agregarActividad(new Actividad("analisis", "analisis", Requerida.OBLIGATORIA), null, 1);
			agregado = true;
		} catch (YaExisteActividadException | ActividadNoExisteException e) {
			agregado = false;
		}

		assertEquals(true, agregado);
		assertEquals("analisis", listaActividades.obtener(0).getDescripcion());

		try {
			proceso.agregarActividad(new Actividad("diseño", "diseño", Requerida.OBLIGATORIA), null, 0);
			proceso.agregarActividad(new Actividad("pruebas", "pruebas", Requerida.OBLIGATORIA), null, 0);
			agregado = true;
		} catch (YaExisteActividadException | ActividadNoExisteException e) {
			agregado = false;
		}

		assertEquals(true, agregado);
		assertEquals("analisis diseño pruebas ", listaActividades.toString());

		try {
			proceso.agregarActividad(new Actividad("implementacion", "implementacion", Requerida.OPCIONAL),
					new Actividad("diseño", "diseño", Requerida.OBLIGATORIA), 1);
			agregado = true;
		} catch (YaExisteActividadException | ActividadNoExisteException e) {
			agregado = false;
		}

		assertEquals(true, agregado);
		assertEquals("analisis diseño implementacion pruebas ", listaActividades.toString());

		try {
			proceso.agregarActividad(new Actividad("documentacion", "documentacion", Requerida.OBLIGATORIA), null, 2);
			agregado = true;
		} catch (YaExisteActividadException | ActividadNoExisteException e) {
			agregado = false;
		}

		assertEquals(true, agregado);
		assertEquals("analisis diseño implementacion documentacion pruebas ", listaActividades.toString());

	}

	@Test
	public void testEliminarActividad() {
		boolean eliminado = true;
		try {
			proceso.agregarActividad(new Actividad("diseño", "diseño", Requerida.OBLIGATORIA), null, 0);
			proceso.agregarActividad(new Actividad("pruebas", "pruebas", Requerida.OBLIGATORIA), null, 0);
			proceso.agregarActividad(new Actividad("implementacion", "implementacion", Requerida.OBLIGATORIA), null, 0);
			proceso.agregarActividad(new Actividad("documentacion", "documentacion", Requerida.OBLIGATORIA), null, 0);
		} catch (YaExisteActividadException | ActividadNoExisteException e) {
		}

		try {
			proceso.eliminarActividad(new Actividad("diseño", "diseño", Requerida.OBLIGATORIA));
		} catch (ActividadNoExisteException e) {
			eliminado = false;
		}

		assertEquals(true, eliminado);
		assertEquals("pruebas implementacion documentacion ", listaActividades.toString());

		try {
			proceso.eliminarActividad(new Actividad("planteamiento", "planteamiento", Requerida.OBLIGATORIA));
			eliminado = true;
		} catch (ActividadNoExisteException e) {
			eliminado = false;
		}

		assertEquals(false, eliminado);
		assertEquals("pruebas implementacion documentacion ", listaActividades.toString());

		try {
			proceso.eliminarActividad(new Actividad("implementacion", "implementacion", Requerida.OBLIGATORIA));
			proceso.eliminarActividad(new Actividad("documentacion", "documentacion", Requerida.OBLIGATORIA));
			eliminado = true;
		} catch (ActividadNoExisteException e) {
			eliminado = false;
		}

		assertEquals(true, eliminado);
		assertEquals("pruebas ", listaActividades.toString());
	}

	@Test
	public void testEditarActividad() {
		boolean editado = true;
		try {
			proceso.agregarActividad(new Actividad("diseño", "diseño", Requerida.OBLIGATORIA), null, 0);
			proceso.agregarActividad(new Actividad("pruebas", "pruebas", Requerida.OBLIGATORIA), null, 0);
			proceso.agregarActividad(new Actividad("implementacion", "implementacion", Requerida.OBLIGATORIA), null, 0);
			proceso.agregarActividad(new Actividad("documentacion", "documentacion", Requerida.OBLIGATORIA), null, 0);
		} catch (YaExisteActividadException | ActividadNoExisteException e) {
		}

		try {
			proceso.editarActividad(new Actividad("diseño", "diseño", Requerida.OBLIGATORIA),
					new Actividad("planteamiento", "planteamiento", Requerida.OPCIONAL));
		} catch (ActividadNoExisteException | YaExisteActividadException e) {
			editado = false;
		}

		assertEquals(true, editado);
		Actividad actividad = listaActividades.obtenerValorNodo(0);
		assertEquals("planteamiento planteamiento Opcional",
				actividad.getNombre() + " " + actividad.getDescripcion() + " " + actividad.getRequerida().toString());

		try {
			proceso.editarActividad(new Actividad("diseño", "diseño", Requerida.OBLIGATORIA),
					new Actividad("justificacion", "justificacion", Requerida.OPCIONAL));
			editado = true;
		} catch (ActividadNoExisteException | YaExisteActividadException e) {
			editado = false;
		}

		assertEquals(false, editado);
		assertEquals("planteamiento pruebas implementacion documentacion ", listaActividades.toString());

		try {
			proceso.editarActividad(new Actividad("pruebas", "pruebas", Requerida.OBLIGATORIA),
					new Actividad("analizar", "analizar", Requerida.OPCIONAL));
			proceso.editarActividad(new Actividad("documentacion", "documentacion", Requerida.OBLIGATORIA),
					new Actividad("evaluacion", "evaluacion", Requerida.OPCIONAL));
			editado = true;
		} catch (ActividadNoExisteException | YaExisteActividadException e) {
			editado = false;
		}

		assertEquals(true, editado);
		assertEquals("planteamiento analizar implementacion evaluacion ", listaActividades.toString());
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

		Contenedor contenedor = singleton.getContenedor();
		ListaDoble<Actividad> actividades = contenedor.getActividadesProceso(new Proceso("1", "proceso 1"));

		assertEquals(5, actividades.obtener(0).getTiempoMin());
		assertEquals(14, actividades.obtener(1).getTiempoMin());
		assertEquals(2, actividades.obtener(2).getTiempoMin());

		actividades = contenedor.getActividadesProceso(new Proceso("2", "proceso 2"));

		assertEquals(37, actividades.obtener(0).getTiempoMin());
		assertEquals(0, actividades.obtener(1).getTiempoMin());
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

		Contenedor contenedor = singleton.getContenedor();
		ListaDoble<Actividad> actividades = contenedor.getActividadesProceso(new Proceso("1", "proceso 1"));

		assertEquals(8, actividades.obtener(0).getTiempoMax());
		assertEquals(25, actividades.obtener(1).getTiempoMax());
		assertEquals(2, actividades.obtener(2).getTiempoMax());

		actividades = contenedor.getActividadesProceso(new Proceso("2", "proceso 2"));

		assertEquals(37, actividades.obtener(0).getTiempoMax());
		assertEquals(12, actividades.obtener(1).getTiempoMax());
	}

}
