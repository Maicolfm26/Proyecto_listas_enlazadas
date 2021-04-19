package co.edu.uniquindio.listas.vistas.tareas;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import co.edu.uniquindio.listas.aplicacion.Aplicacion;
import co.edu.uniquindio.listas.controller.ModelFactoryController;
import co.edu.uniquindio.listas.exceptions.ActividadNoExisteException;
import co.edu.uniquindio.listas.exceptions.DosTareasOpcionalesException;
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
import co.edu.uniquindio.listas.model.Tarea;

public class ControladorVistaEditarTareas implements Initializable {

	private ModelFactoryController singleton;
	private Stage dialogStage;
	private boolean okClicked = false;
	private Tarea tarea;
	private Tarea tareaActualizada;
	private Actividad actividad;
	private Proceso proceso;
	@FXML
	private StackPane rootPane;
	@FXML
	private AnchorPane rootAnchorPane;
	@FXML
	private JFXTextField duracionTextField;
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

	public Tarea getTareaActualizada() {
		return tareaActualizada;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}

	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}

	public void setTarea(Tarea tarea) {
		this.tarea = tarea;
	}

	public void setRootPane(StackPane rootPane) {
		this.rootPane = rootPane;
	}

	@FXML
	private void cancelarPulsado() {
		okClicked = false;
		dialogStage.close();
		tarea = null;
		tareaActualizada = null;
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	public void actualizarCampos() {
		duracionTextField.setText(tarea.getDuracion()+"");
		descripcionTextArea.setText(tarea.getDescripcion());
		if (tarea.getRequerida() == Requerida.OBLIGATORIA) {
			obligatoriaRadio.setSelected(true);
		} else {
			opcionalRadio.setSelected(true);
		}
	}

	@FXML
	private void editarActividad() {
		if (duracionTextField.getText().isEmpty() || descripcionTextArea.getText().isEmpty()
				|| requeridaGroup.getSelectedToggle() == null) {
			dialogStage.show();
			Aplicacion.mostrarMensaje(rootPane, rootAnchorPane, "Se deben llenar todos los campos");
		} else {
			try {
				int duracion = Integer.parseInt(duracionTextField.getText());
				String descripcion = descripcionTextArea.getText();
				if (obligatoriaRadio.isSelected()) {
					tareaActualizada = new Tarea(descripcion, Requerida.OBLIGATORIA, duracion);
				} else {
					tareaActualizada = new Tarea(descripcion, Requerida.OPCIONAL, duracion);
				}
				try {
					singleton.editarTarea(proceso, actividad, tarea, tareaActualizada);
					dialogStage.close();
					okClicked = true;
				} catch (DosTareasOpcionalesException e) {
					okClicked = false;
					Aplicacion.mostrarMensaje(rootPane, rootAnchorPane, e.getMessage());
				}
			} catch (NumberFormatException e) {
				okClicked = false;
				Aplicacion.mostrarMensaje(rootPane, rootAnchorPane, "Formato incorrepto en el campo de duracion");
			}

		}
	}
}
