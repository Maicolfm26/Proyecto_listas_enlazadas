package co.edu.uniquindio.listas.vistas.actividades;

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
import co.edu.uniquindio.listas.model.Actividad;
import co.edu.uniquindio.listas.model.Proceso;
import co.edu.uniquindio.listas.model.Requerida;
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

public class ControladorVistaPrincipalActividades implements Initializable {

	private ModelFactoryController singleton;
	private Proceso proceso;

	@FXML
	private StackPane rootPane;

	@FXML
	private BorderPane rootBorderPane;

	@FXML
	private TableView<Actividad> tablaActividades;

	@FXML
	private TableColumn<Actividad, String> nombreColumn;

	@FXML
	private TableColumn<Actividad, String> descripcionColumn;

	@FXML
	private TableColumn<Actividad, Requerida> requeridaColumn;
	
	@FXML
    private TableColumn<Actividad, Integer> tiempoMinimoColumn;

    @FXML
    private TableColumn<Actividad, Integer> tiempoMaximoColumn;

	@FXML
	private JFXTextField buscarTextField;

	private final ObservableList<Actividad> listadoActividades = FXCollections.observableArrayList();

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

	public void inicializarTabla() {
		nombreColumn.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
		descripcionColumn.setCellValueFactory(new PropertyValueFactory<>("Descripcion"));
		requeridaColumn.setCellValueFactory(new PropertyValueFactory<>("Requerida"));
		tiempoMinimoColumn.setCellValueFactory(new PropertyValueFactory<>("TiempoMin"));
		tiempoMaximoColumn.setCellValueFactory(new PropertyValueFactory<>("TiempoMax"));
		listadoActividades.addAll(singleton.getActividadesProceso(proceso).creadorTablas());
		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<Actividad> filteredData = new FilteredList<>(listadoActividades, p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		buscarTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(proc -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (proc.getNombre().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches first name.
				}
				return false; // Does not match.
			});
		});

		// 3. Wrap the FilteredList in a SortedList.
		SortedList<Actividad> sortedData = new SortedList<>(filteredData);

		// 4. Bind the SortedList comparator to the TableView comparator.
		sortedData.comparatorProperty().bind(tablaActividades.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		tablaActividades.setItems(sortedData);
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
				rootPane.setEffect(null);
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
				rootPane.setEffect(null);
			}
		}

	}
	
	@FXML
    private void pulsadoVolver() {
		Aplicacion.mostrarVistaPrincipalProcesos();
    }

	@FXML
	private void pulsadoNuevo() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Aplicacion.class.getResource("../vistas/actividades/guardarActvidadVista.fxml"));
			StackPane vistaRegistro = (StackPane) loader.load();
			Scene scene = new Scene(vistaRegistro);
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Crear proceso");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(Aplicacion.escenarioPrincipal);
			dialogStage.setScene(scene);
			// Acceso al controlador.
			ControladorVistaGuardarActividad miControlador = loader.getController();
			miControlador.setEscenario(dialogStage);
			miControlador.setProceso(proceso);
			if(listadoActividades.size() == 0) {
				miControlador.desactivarItems();
			}
			dialogStage.showAndWait();
			if (miControlador.isOkClicked()) {
				if(miControlador.getTipo() == 1 || miControlador.getTipo() == 2) {
					listadoActividades.clear();
					listadoActividades.addAll(singleton.getActividadesProceso(proceso).creadorTablas());
				} else {
					listadoActividades.add(miControlador.getActividad());
				}
				Aplicacion.mostrarMensaje(rootPane, rootBorderPane, "Agregado correctamente");
				rootPane.setEffect(null);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@FXML
	private void pulsadoEditarActividad() {
		int posicion = tablaActividades.getSelectionModel().getSelectedIndex();
		if (posicion >= 0) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Aplicacion.class.getResource("../vistas/actividades/editarActividadVista.fxml"));
				StackPane vistaRegistro = (StackPane) loader.load();
				Scene scene = new Scene(vistaRegistro);
				Stage dialogStage = new Stage();
				dialogStage.setTitle("Editar cuestionario");
				dialogStage.initModality(Modality.WINDOW_MODAL);
				dialogStage.initOwner(Aplicacion.escenarioPrincipal);
				dialogStage.setScene(scene);
				// Acceso al controlador.
				ControladorVistaEditarActividad miControlador = loader.getController();
				miControlador.setEscenario(dialogStage);
				miControlador.setProceso(proceso);
				miControlador.setActividad(tablaActividades.getSelectionModel().getSelectedItem());
				miControlador.actualizarCampos();
				dialogStage.showAndWait();
				if (miControlador.isOkClicked()) {
					listadoActividades.set(posicion, miControlador.getActividadActualizada());
					Aplicacion.mostrarMensaje(rootPane, rootBorderPane, "Actividad actualizada");
					rootPane.setEffect(null);
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		} else {
			Aplicacion.mostrarMensaje(rootPane, rootBorderPane, "No se ha seleccionado ninguna actividad");
			rootPane.setEffect(null);
		}
	}

	@FXML
	private void eliminarActividad() {
		if (tablaActividades.getSelectionModel().getSelectedIndex() >= 0) {
			if (Aplicacion.mostrarMensajeRespuesta(rootPane, rootBorderPane,
					"Estas seguro que deseas eliminar la actividad")) {
				rootPane.setEffect(null);
				int seleccion = tablaActividades.getSelectionModel().getSelectedIndex();
				if (seleccion >= 0) {
					Actividad actividad = tablaActividades.getSelectionModel().getSelectedItem();
					listadoActividades.remove(seleccion);
					try {
						singleton.eliminarActividad(proceso, actividad);
						Aplicacion.mostrarMensaje(rootPane, rootBorderPane, "Actividad eliminada");
						rootPane.setEffect(null);
					} catch (ActividadNoExisteException e) {
						Aplicacion.mostrarMensaje(rootPane, rootBorderPane, e.getMessage());
						rootPane.setEffect(null);
					}
					
				}
			}
		} else {
			Aplicacion.mostrarMensaje(rootPane, rootBorderPane, "No se ha seleccionado ninguna actividad");
			rootPane.setEffect(null);
		}
	}
	
	@FXML
    private void pulsadoVerTarea() {
		int posicion = tablaActividades.getSelectionModel().getSelectedIndex();
		if (posicion >= 0) {
			Aplicacion.mostrarVistaPrincipalTareas(proceso, tablaActividades.getSelectionModel().getSelectedItem());
		} else {
			Aplicacion.mostrarMensaje(rootPane, rootBorderPane, "No se ha seleccionado ninguna actividad");
			rootPane.setEffect(null);
		}
    }
}
