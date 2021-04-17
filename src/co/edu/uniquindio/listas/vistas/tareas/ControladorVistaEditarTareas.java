package co.edu.uniquindio.listas.vistas.tareas;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import co.edu.uniquindio.listas.aplicacion.Aplicacion;
import co.edu.uniquindio.listas.controller.ModelFactoryController;
import co.edu.uniquindio.listas.exceptions.ActividadNoExisteException;
import co.edu.uniquindio.listas.exceptions.ProcesoNoExisteException;
import co.edu.uniquindio.listas.exceptions.YaExisteActividadException;
import co.edu.uniquindio.listas.exceptions.YaExisteProcesoException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import co.edu.uniquindio.listas.model.Actividad;
import co.edu.uniquindio.listas.model.Proceso;
import co.edu.uniquindio.listas.model.Requerida;

public class ControladorVistaEditarTareas implements Initializable {

	private ModelFactoryController singleton;
	private Stage dialogStage;
	private boolean okClicked = false;
	private Actividad actividad;
	private Actividad actividadActualizada;
	private Proceso proceso;
	@FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private JFXTextField nombreTextField;
    @FXML
    private JFXTextArea descripcionTextArea;
    @FXML
    private JFXRadioButton obligatoriaRadio;
    @FXML
    private ToggleGroup requeridaGroup;
    @FXML
    private JFXRadioButton opcionalRadio;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		singleton = ModelFactoryController.getInstance();
		requeridaGroup = new ToggleGroup();
		obligatoriaRadio.setToggleGroup(requeridaGroup);
		opcionalRadio.setToggleGroup(requeridaGroup);
	}

	public void setEscenario(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public Actividad getActividad() {
		return actividad;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}
	
	public Actividad getActividadActualizada() {
		return actividadActualizada;
	}

	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}

	public void setRootPane(StackPane rootPane) {
		this.rootPane = rootPane;
	}

	@FXML
	private void cancelarPulsado() {
		okClicked = false;
		dialogStage.close();
		actividad = null;
		actividadActualizada = null;
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	public void actualizarCampos() {
		nombreTextField.setText(actividad.getNombre());
		descripcionTextArea.setText(actividad.getDescripcion());
		if(actividad.getRequerida() == Requerida.OBLIGATORIA) {
			obligatoriaRadio.setSelected(true);
		} else {
			opcionalRadio.setSelected(true);
		}
	}

	@FXML
	private void editarActividad() {
		if (nombreTextField.getText().isEmpty() || descripcionTextArea.getText().isEmpty() || requeridaGroup.getSelectedToggle() == null) {
			dialogStage.show();
			Aplicacion.mostrarMensaje(rootPane, rootAnchorPane, "Se deben llenar todos los campos");
		} else {
			String nombre = nombreTextField.getText();
			String descripcion = descripcionTextArea.getText();
			if(obligatoriaRadio.isSelected()) {
				actividadActualizada = new Actividad(nombre, descripcion, Requerida.OBLIGATORIA);
			} else {
				actividadActualizada = new Actividad(nombre, descripcion, Requerida.OPCIONAL);
			}
			try {
				singleton.editarActividad(proceso, actividad, actividadActualizada);
				dialogStage.close();
				okClicked = true;
			} catch (ActividadNoExisteException | YaExisteActividadException e) {
				Aplicacion.mostrarMensaje(rootPane, rootAnchorPane, e.getMessage());
			}
		}
	}
}
