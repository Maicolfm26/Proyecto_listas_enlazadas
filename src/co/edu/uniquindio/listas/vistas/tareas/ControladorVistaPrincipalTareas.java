package co.edu.uniquindio.listas.vistas.tareas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javax.swing.text.TabableView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import co.edu.uniquindio.listas.aplicacion.Aplicacion;
import co.edu.uniquindio.listas.controller.ModelFactoryController;
import co.edu.uniquindio.listas.exceptions.ActividadNoExisteException;
import co.edu.uniquindio.listas.exceptions.DosTareasOpcionalesException;
import co.edu.uniquindio.listas.exceptions.TareaNoExisteException;
import co.edu.uniquindio.listas.model.Actividad;
import co.edu.uniquindio.listas.model.Proceso;
import co.edu.uniquindio.listas.model.Requerida;
import co.edu.uniquindio.listas.model.Tarea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControladorVistaPrincipalTareas implements Initializable {

	private ModelFactoryController singleton;
	private Proceso proceso;
	private Actividad actividad;

	@FXML
	private StackPane rootPane;

	@FXML
	private BorderPane rootBorderPane;

	@FXML
	private TableView<Tarea> tablaTareas;

	@FXML
	private TableColumn<Tarea, String> descripcionColumn;
	
	@FXML
	private TableColumn<Tarea, Requerida> requeridaColumn;
	
	@FXML
	private TableColumn<Tarea, Integer> duracionColumn;

	@FXML
	private JFXTextField buscarTextField;

	private final ObservableList<Tarea> listadoTareas = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		singleton = ModelFactoryController.getInstance();
	}

	public Proceso getProceso() {
		return proceso;
	}

	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}

	public Actividad getActividad() {
		return actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}

	public void inicializarTabla() {
		descripcionColumn.setCellValueFactory(new PropertyValueFactory<>("Descripcion"));
		requeridaColumn.setCellValueFactory(new PropertyValueFactory<>("Requerida"));
		duracionColumn.setCellValueFactory(new PropertyValueFactory<>("Duracion"));
		listadoTareas.addAll(singleton.getTarea(proceso, actividad).creadorTablas());
		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<Tarea> filteredData = new FilteredList<>(listadoTareas, p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		buscarTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(proc -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (proc.getDescripcion().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches first name.
				}
				return false; // Does not match.
			});
		});

		// 3. Wrap the FilteredList in a SortedList.
		SortedList<Tarea> sortedData = new SortedList<>(filteredData);

		// 4. Bind the SortedList comparator to the TableView comparator.
		sortedData.comparatorProperty().bind(tablaTareas.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		tablaTareas.setItems(sortedData);
	}

	@FXML
	private void abrirContenedor(ActionEvent event) {
		String ruta = Aplicacion.mostrarFileChooserOpen();
		if (ruta != "") {
			try {
				singleton.cargarContenedor(ruta);
				Aplicacion.mostrarVistaPrincipalProcesos();
			} catch (IOException e) {
				Aplicacion.mostrarMensaje(rootPane, rootBorderPane, "Error al cargar el archivo");
			}
		}
	}

	@FXML
	private void crearContenedor(ActionEvent event) {
		singleton = singleton.nuevoContenedor();
		Aplicacion.mostrarVistaPrincipalProcesos();
	}

	@FXML
	private void guardarContenedor(ActionEvent event) {
		String ruta = Aplicacion.mostrarFileChooserSave();
		if (ruta != "") {
			try {
				singleton.guardarContenedor(ruta);
			} catch (IOException e) {
				Aplicacion.mostrarMensaje(rootPane, rootBorderPane, "Error al guardar el archivo");
			}
		}

	}
	
	@FXML
    private void pulsadoVolver() {
		Aplicacion.mostrarVistaPrincipalActividades(proceso);
    }

	@FXML
	private void pulsadoNuevo() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Aplicacion.class.getResource("../vistas/tareas/guardarTareaVista.fxml"));
			StackPane vistaRegistro = (StackPane) loader.load();
			Scene scene = new Scene(vistaRegistro);
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Crear proceso");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(Aplicacion.escenarioPrincipal);
			dialogStage.setScene(scene);
			// Acceso al controlador.
			ControladorVistaGuardarTareas miControlador = loader.getController();
			miControlador.setEscenario(dialogStage);
			miControlador.setProceso(proceso);
			miControlador.setActividad(actividad);
			if(listadoTareas.size() == 0) {
				miControlador.desactivarItems();
			}
			dialogStage.showAndWait();
			if (miControlador.isOkClicked()) {
				if(miControlador.getOpcion() == 1) {
					listadoTareas.add(miControlador.getPosicion() - 1, miControlador.getTarea());
				} else {
					listadoTareas.add(miControlador.getTarea());
				}
				Aplicacion.mostrarMensaje(rootPane, rootBorderPane, "Agregado correctamente");
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@FXML
	private void pulsadoEditarTarea() {
		int posicion = tablaTareas.getSelectionModel().getSelectedIndex();
		if (posicion >= 0) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Aplicacion.class.getResource("../vistas/tareas/editarTareaVista.fxml"));
				StackPane vistaRegistro = (StackPane) loader.load();
				Scene scene = new Scene(vistaRegistro);
				Stage dialogStage = new Stage();
				dialogStage.setTitle("Editar tarea");
				dialogStage.initModality(Modality.WINDOW_MODAL);
				dialogStage.initOwner(Aplicacion.escenarioPrincipal);
				dialogStage.setScene(scene);
				// Acceso al controlador.
				ControladorVistaEditarTareas miControlador = loader.getController();
				miControlador.setEscenario(dialogStage);
				miControlador.setProceso(proceso);
				miControlador.setActividad(actividad);
				miControlador.setTarea(tablaTareas.getSelectionModel().getSelectedItem());
				miControlador.actualizarCampos();
				dialogStage.showAndWait();
				if (miControlador.isOkClicked()) {
					listadoTareas.set(posicion, miControlador.getTareaActualizada());
					Aplicacion.mostrarMensaje(rootPane, rootBorderPane, "Tarea actualizada");
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		} else {
			Aplicacion.mostrarMensaje(rootPane, rootBorderPane, "No se ha seleccionado ninguna tarea");
		}
	}

	@FXML
	private void eliminarTarea() {
		if (tablaTareas.getSelectionModel().getSelectedIndex() >= 0) {
			if (Aplicacion.mostrarMensajeRespuesta(rootPane, rootBorderPane,
					"Estas seguro que deseas eliminar la tarea")) {
				int seleccion = tablaTareas.getSelectionModel().getSelectedIndex();
				if (seleccion >= 0) {
					Tarea tarea = tablaTareas.getSelectionModel().getSelectedItem();
					
					try {
						singleton.eliminarTarea(proceso, actividad, tarea);
						Aplicacion.mostrarMensaje(rootPane, rootBorderPane, "Tarea eliminada");
						listadoTareas.remove(seleccion);
					} catch (TareaNoExisteException | DosTareasOpcionalesException e) {
						Aplicacion.mostrarMensaje(rootPane, rootBorderPane, e.getMessage());
					}
					
				}
			}
		} else {
			Aplicacion.mostrarMensaje(rootPane, rootBorderPane, "No se ha seleccionado ninguna tarea");
		}
	}
}
