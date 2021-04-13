package co.edu.uniquindio.listas.vistas;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import co.edu.uniquindio.listas.aplicacion.Aplicacion;
import co.edu.uniquindio.listas.controller.ModelFactoryController;
import co.edu.uniquindio.listas.exceptions.YaExisteProcesoException;
import co.edu.uniquindio.listas.model.Proceso;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ControladorVistaGuardarProceso implements Initializable {

	private ModelFactoryController singleton;
	@FXML
	private JFXTextField idTextField;
	@FXML
	private JFXTextField nombreTextField;
	private Stage dialogStage;
	private boolean okClicked;
	private Proceso proceso;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		singleton = ModelFactoryController.getInstance();
	}

	public void setEscenario(Stage dialogStage) {
		this.dialogStage = dialogStage;

	}

	public boolean isOkClicked() {
		return okClicked;
	}

	public Proceso getProceso() {
		return proceso;
	}

	@FXML
	private void agregarProceso() {
		if (idTextField.getText().isEmpty() || nombreTextField.getText().isEmpty()) {
			Aplicacion.mostrarMensaje("", AlertType.ERROR, "Error", "", "Debe llenar todos los campos");
		} else {
			String id = idTextField.getText();
			String nombre = nombreTextField.getText();
			proceso = new Proceso(id, nombre);
			try {
				singleton.agregarProceso(proceso);
				okClicked = true;
				dialogStage.close();
			} catch (YaExisteProcesoException e) {
				Aplicacion.mostrarMensaje("", AlertType.ERROR, "ERROR", "", e.getMessage());
			}
		}
	}

	@FXML
	private void cancelar() {
		okClicked = false;
		dialogStage.close();
		proceso = null;
	}
}