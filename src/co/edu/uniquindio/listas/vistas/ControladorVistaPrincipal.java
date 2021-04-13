package co.edu.uniquindio.listas.vistas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXTextField;
import co.edu.uniquindio.listas.aplicacion.Aplicacion;
import co.edu.uniquindio.listas.controller.ModelFactoryController;
import co.edu.uniquindio.listas.model.Proceso;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControladorVistaPrincipal implements Initializable {

	ModelFactoryController singleton;
	@FXML
	private TableView<Proceso> tablaProcesos;

	@FXML
	private TableColumn<Proceso, String> idColumn;

	@FXML
	private TableColumn<Proceso, String> nombreColumn;

	@FXML
	private JFXTextField buscarTextField;

	private final ObservableList<Proceso> listadoProcesos = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		singleton = ModelFactoryController.getInstance();
		idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
		nombreColumn.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
		listadoProcesos.addAll(singleton.getListadoProcesos().creadorTablas());
		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<Proceso> filteredData = new FilteredList<>(listadoProcesos, p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		buscarTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(proc -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (proc.getId().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches first name.
				}
				return false; // Does not match.
			});
		});

		// 3. Wrap the FilteredList in a SortedList.
		SortedList<Proceso> sortedData = new SortedList<>(filteredData);

		// 4. Bind the SortedList comparator to the TableView comparator.
		sortedData.comparatorProperty().bind(tablaProcesos.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		tablaProcesos.setItems(sortedData);
	}

	@FXML
	private void abrirContenedor(ActionEvent event) {
		String ruta = Aplicacion.mostrarFileChooserOpen();
		if (ruta != "") {
			try {
				singleton.cargarContenedor(ruta);
				listadoProcesos.clear();
				listadoProcesos.addAll(singleton.getListadoProcesos().creadorTablas());
			} catch (IOException e) {
				Aplicacion.mostrarMensaje("", AlertType.ERROR, "ERROR", "", "Error al cargar el archivo");
			}
		}
	}

	@FXML
	private void crearContenedor(ActionEvent event) {
		singleton = singleton.nuevoContenedor();
		listadoProcesos.clear();
		tablaProcesos.getItems().clear();
	}

	@FXML
	private void guardarContenedor(ActionEvent event) {
		String ruta = Aplicacion.mostrarFileChooserSave();
		if (ruta != "") {
			try {
				singleton.guardarContenedor(ruta);
			} catch (IOException e) {
				Aplicacion.mostrarMensaje("", AlertType.ERROR, "ERROR", "", "Error al guardar el archivo");
			}
		}

	}

	@FXML
	private void pulsadoNuevo() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Aplicacion.class.getResource("../vistas/guardarProcesoVista.fxml"));
			AnchorPane vistaRegistro = (AnchorPane) loader.load();
			Scene scene = new Scene(vistaRegistro);
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Crear proceso");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(Aplicacion.escenarioPrincipal);
			dialogStage.setScene(scene);
			// Acceso al controlador.
			ControladorVistaGuardarProceso miControlador = loader.getController();
			miControlador.setEscenario(dialogStage);
			dialogStage.showAndWait();
			if (miControlador.isOkClicked()) {
				listadoProcesos.add(miControlador.getProceso());

			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@FXML
	private void pulsadoEditarProceso() {
		int posicion = tablaProcesos.getSelectionModel().getSelectedIndex();
		if (posicion >= 0) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Aplicacion.class.getResource("../vistas/editarProcesoVista.fxml"));
				AnchorPane vistaRegistro = (AnchorPane) loader.load();
				Scene scene = new Scene(vistaRegistro);
				Stage dialogStage = new Stage();
				dialogStage.setTitle("Editar cuestionario");
				dialogStage.initModality(Modality.WINDOW_MODAL);
				dialogStage.initOwner(Aplicacion.escenarioPrincipal);
				dialogStage.setScene(scene);
				// Acceso al controlador.
				ControladorVistaEditarProceso miControlador = loader.getController();
				miControlador.setEscenario(dialogStage);
				miControlador.setProceso(tablaProcesos.getSelectionModel().getSelectedItem());
				miControlador.actualizarCampos();
				dialogStage.showAndWait();
				if (miControlador.isOkClicked()) {
					listadoProcesos.set(posicion, miControlador.getProcesoActualizado());
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		} else {
			Aplicacion.mostrarMensaje("", AlertType.WARNING, "Error", "", "No se ha seleccionado ningun proceso");
		}
	}

	@FXML
	private void eliminarProceso() {
		if (tablaProcesos.getSelectionModel().getSelectedIndex() >= 0) {
			if (Aplicacion.mostrarMensajeRespuesta(AlertType.WARNING, "Eliminar proceso",
					"Estas seguro que deseas eliminar el proceso")) {
				int seleccion = tablaProcesos.getSelectionModel().getSelectedIndex();
				if (seleccion >= 0) {
					Proceso proceso = tablaProcesos.getSelectionModel().getSelectedItem();
					listadoProcesos.remove(seleccion);
					singleton.eliminarProceso(proceso);
				}
			}
		} else {
			Aplicacion.mostrarMensaje("", AlertType.WARNING, "Error", "", "No se ha seleccionado ningun proceso");
		}
	}
}
