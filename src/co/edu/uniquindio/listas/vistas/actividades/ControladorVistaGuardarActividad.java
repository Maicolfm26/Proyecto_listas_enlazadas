package co.edu.uniquindio.listas.vistas.actividades;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import co.edu.uniquindio.listas.aplicacion.Aplicacion;
import co.edu.uniquindio.listas.controller.ModelFactoryController;
import co.edu.uniquindio.listas.exceptions.ActividadNoExisteException;
import co.edu.uniquindio.listas.exceptions.YaExisteActividadException;
import co.edu.uniquindio.listas.exceptions.YaExisteProcesoException;
import co.edu.uniquindio.listas.model.Actividad;
import co.edu.uniquindio.listas.model.Proceso;
import co.edu.uniquindio.listas.model.Requerida;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControladorVistaGuardarActividad implements Initializable {

	private ModelFactoryController singleton;
	private Stage dialogStageGuardar;
	private boolean okClicked;
	private Actividad actividad;
	private Proceso proceso;
	private int tipo;
	
	@FXML
	private StackPane rootPane;
	@FXML
	private AnchorPane rootAnchorPane;
	@FXML
	private JFXComboBox<String> formasAgregarComboBox;
	@FXML
	private JFXTextField nombreTextField;
	@FXML
	private JFXTextArea descripcionTextField;
	@FXML
	private ToggleGroup requeridaGroup;
	@FXML
	private JFXRadioButton obligatoriaRadio;
	@FXML
	private JFXRadioButton opcionalRadio;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		singleton = ModelFactoryController.getInstance();
		requeridaGroup = new ToggleGroup();
		formasAgregarComboBox.getItems().add("Agregar al final");
		formasAgregarComboBox.getItems().add("Agregar despues de una actividad indicada");
		formasAgregarComboBox.getItems().add("Agregar despues de la ultima actividad creada");
		obligatoriaRadio.setToggleGroup(requeridaGroup);
		opcionalRadio.setToggleGroup(requeridaGroup);
	}

	public void setEscenario(Stage dialogStage) {
		this.dialogStageGuardar = dialogStage;
	}

	public void setRootPane(StackPane rootPane) {
		this.rootPane = rootPane;
	}

	public void desactivarItems() {
		formasAgregarComboBox.getItems().remove(1);
		formasAgregarComboBox.getItems().remove(1);
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	public Actividad getActividad() {
		return actividad;
	}

	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}

	public int getTipo() {
		return tipo;
	}

	@FXML
	private void agregarActividad() {
		if (nombreTextField.getText().isEmpty() || descripcionTextField.getText().isEmpty()
				|| requeridaGroup.getSelectedToggle() == null || formasAgregarComboBox.getSelectionModel().isEmpty()) {
			Aplicacion.mostrarMensaje(rootPane, rootAnchorPane, "Debe llenar todos los campos");
			rootPane.setEffect(null);
		} else {
			String nombre = nombreTextField.getText();
			String descripcion = descripcionTextField.getText();

			if (requeridaGroup.getSelectedToggle().equals(obligatoriaRadio)) {
				actividad = new Actividad(nombre, descripcion, Requerida.OBLIGATORIA);
			} else {
				actividad = new Actividad(nombre, descripcion, Requerida.OPCIONAL);
			}

			tipo = formasAgregarComboBox.getSelectionModel().getSelectedIndex();
			
			if (tipo == 1) {
				mostrarSeleccionar();
				tipo = 1;
			} else {
				try {
					tipo = 0;
					singleton.agregarActividad(proceso, actividad, null, tipo);
					okClicked = true;
					dialogStageGuardar.close();
				} catch (YaExisteActividadException | ActividadNoExisteException e) {
					okClicked = false;
					Aplicacion.mostrarMensaje(rootPane, rootAnchorPane, e.getMessage());
				}
			}
		}
	}

	private void mostrarSeleccionar() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Aplicacion.class.getResource("../vistas/actividades/seleccionarActividadVista.fxml"));
			StackPane vistaRegistro = (StackPane) loader.load();
			Scene scene = new Scene(vistaRegistro);
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Seleccionar actividad");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(Aplicacion.escenarioPrincipal);
			dialogStage.setScene(scene);
			// Acceso al controlador.
			ControladorSeleccionarActividad miControlador = loader.getController();
			miControlador.setEscenario(dialogStage);
			miControlador.cargarActividades(proceso.getListaActividades());
			dialogStage.showAndWait();
			if (miControlador.isOkClicked()) {
				try {
					okClicked = true;
					dialogStage.close();
					dialogStageGuardar.close();
					singleton.agregarActividad(proceso, actividad, miControlador.getActividad(), 1);
				} catch (YaExisteActividadException | ActividadNoExisteException e) {
					okClicked = false;
					Aplicacion.mostrarMensaje(rootPane, rootAnchorPane, e.getMessage());
					rootPane.setEffect(null);
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@FXML
	private void cancelar() {
		okClicked = false;
		dialogStageGuardar.close();
		actividad = null;
	}
}
