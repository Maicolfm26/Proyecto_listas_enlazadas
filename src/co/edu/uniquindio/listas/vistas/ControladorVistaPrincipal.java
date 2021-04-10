package co.edu.uniquindio.listas.vistas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTabPane;

import co.edu.uniquindio.listas.aplicacion.Aplicacion;
import co.edu.uniquindio.listas.controller.ModelFactoryController;
import co.edu.uniquindio.listas.model.Contenedor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

public class ControladorVistaPrincipal implements Initializable {

	ModelFactoryController singleton;
	@FXML
	private JFXTabPane tabPane;
	@FXML
	private TableView<Contenedor> tablaContenedor;
	@FXML
	private TableColumn<Contenedor, String> identificacionColumn;
	private final ObservableList<Contenedor> listadoContenedor = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		singleton = ModelFactoryController.getInstance();
		identificacionColumn.setCellValueFactory(new PropertyValueFactory<>("IdentificacionContenedor"));
		// adicionar los datos a la tabla
		listadoContenedor.addAll(singleton.getlistaContenedores());
		tablaContenedor.setItems(listadoContenedor);
	}

	@FXML
	private void mostrarVistaListaEnlazadas() {
		Aplicacion.mostrarVistaListasEnlazadas();
	}

	@FXML
	private void crearContenedor() {
		Contenedor contenedor = new Contenedor();
		tablaContenedor.getItems().add(contenedor);
		singleton.crearContenedor(contenedor);
	}

	@FXML
	private void eliminarContenedor() {
		if (tablaContenedor.getSelectionModel().getSelectedIndex() >= 0) {
			if (Aplicacion.mostrarMensajeRespuesta(AlertType.WARNING, "Eliminar contenedor",
					"Estas seguro que deseas eliminar el contenedor")) {
				int seleccion = tablaContenedor.getSelectionModel().getSelectedIndex();
				Contenedor contenedor = tablaContenedor.getSelectionModel().getSelectedItem();
				tablaContenedor.getItems().remove(seleccion);
				singleton.eliminarContenedor(contenedor);
			}
		} else {
			Aplicacion.mostrarMensaje("", AlertType.WARNING, "Error", "", "No se ha seleccionado ningun contenedor");
		}
	}

	@FXML
	private void guardarContenedor() {
		if (tablaContenedor.getSelectionModel().getSelectedIndex() >= 0) {
			Contenedor contenedor = tablaContenedor.getSelectionModel().getSelectedItem();
			String ruta = Aplicacion.mostrarFileChooserSave();
			if (ruta != "") {
				try {
					singleton.guardarContenedor(contenedor, ruta);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			Aplicacion.mostrarMensaje("", AlertType.WARNING, "Error", "", "No se ha seleccionado ningun contenedor");
		}
		SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
		selectionModel.select(0);
	}

	@FXML
	private void cargarContenedor() {
		String ruta = Aplicacion.mostrarFileChooserOpen();
		if (ruta != "") {
			try {
				Contenedor contenedor = singleton.cargarContenedor(ruta);
				tablaContenedor.getItems().add(contenedor);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
		selectionModel.select(0);
	}

}
