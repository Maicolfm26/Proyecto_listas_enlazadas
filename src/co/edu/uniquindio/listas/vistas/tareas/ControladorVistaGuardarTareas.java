package co.edu.uniquindio.listas.vistas.tareas;

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
import co.edu.uniquindio.listas.exceptions.DosTareasOpcionalesException;
import co.edu.uniquindio.listas.exceptions.PosicionInvalidaTareaException;
import co.edu.uniquindio.listas.exceptions.YaExisteActividadException;
import co.edu.uniquindio.listas.exceptions.YaExisteProcesoException;
import co.edu.uniquindio.listas.model.Actividad;
import co.edu.uniquindio.listas.model.Proceso;
import co.edu.uniquindio.listas.model.Requerida;
import co.edu.uniquindio.listas.model.Tarea;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControladorVistaGuardarTareas implements Initializable {

	private ModelFactoryController singleton;
	private Stage dialogStageGuardar;
	private boolean okClicked;
	private Tarea tarea;
	private Proceso proceso;
	private Actividad actividad;
	private int opcion;
	private int posicion;

	@FXML
	private StackPane rootPane;
	@FXML
	private AnchorPane rootAnchorPane;
	@FXML
	private JFXComboBox<String> formasAgregarComboBox;
	@FXML
	private JFXTextArea descripcionTextField;
	@FXML
	private JFXTextField duracionTextField;
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
		formasAgregarComboBox.getItems().add("Agregar en una posicion determinada");
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
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	public Tarea getTarea() {
		return tarea;
	}

	public void setActividad(Actividad actividad) {
		this.actividad = actividad;
	}

	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}

	public int getOpcion() {
		return opcion;
	}

	public int getPosicion() {
		return posicion;
	}

	@FXML
	private void agregarTarea() {
		if (duracionTextField.getText().isEmpty() || descripcionTextField.getText().isEmpty()
				|| requeridaGroup.getSelectedToggle() == null || formasAgregarComboBox.getSelectionModel().isEmpty()) {
			Aplicacion.mostrarMensaje(rootPane, rootAnchorPane, "Debe llenar todos los campos");
		} else {
			String descripcion = descripcionTextField.getText();

			try {
				int duracion = Integer.parseInt(duracionTextField.getText());
				if (requeridaGroup.getSelectedToggle().equals(obligatoriaRadio)) {
					tarea = new Tarea(descripcion, Requerida.OBLIGATORIA, duracion);
				} else {
					tarea = new Tarea(descripcion, Requerida.OPCIONAL, duracion);
				}

				opcion = formasAgregarComboBox.getSelectionModel().getSelectedIndex();

				if (opcion == 1) {
					mostrarCampo();
				} else {
					try {
						singleton.agregarTarea(proceso, actividad, tarea, 0, 0);
						okClicked = true;
						dialogStageGuardar.close();
					} catch (DosTareasOpcionalesException | PosicionInvalidaTareaException e) {
						okClicked = false;
						Aplicacion.mostrarMensaje(rootPane, rootAnchorPane, e.getMessage());
					}
				}
			} catch (NumberFormatException e) {
				Aplicacion.mostrarMensaje(rootPane, rootAnchorPane, "Caracteres invalidos en campo de duración");
			}
		}
	}

	private void mostrarCampo() {
		posicion = Aplicacion.mostrarMensajeCampo(rootPane, rootAnchorPane, "Ingrese la posicion");
		if (posicion != -1) {
			try {
				singleton.agregarTarea(proceso, actividad, tarea, posicion, 1);
				okClicked = true;
				dialogStageGuardar.close();
			} catch (DosTareasOpcionalesException | PosicionInvalidaTareaException e) {
				okClicked = false;
				Aplicacion.mostrarMensaje(rootPane, rootAnchorPane, e.getMessage());
			}
		}
	}

	@FXML
	private void cancelar() {
		okClicked = false;
		dialogStageGuardar.close();
		tarea = null;
	}
}
