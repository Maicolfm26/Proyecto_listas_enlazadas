package co.edu.uniquindio.listas.vistas.procesos;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import co.edu.uniquindio.listas.aplicacion.Aplicacion;
import co.edu.uniquindio.listas.controller.ModelFactoryController;
import co.edu.uniquindio.listas.exceptions.ProcesoNoExisteException;
import co.edu.uniquindio.listas.exceptions.YaExisteProcesoException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import co.edu.uniquindio.listas.model.Proceso;

public class ControladorVistaEditarProceso implements Initializable {

	private ModelFactoryController singleton;
	private Stage dialogStage;
	private boolean okClicked = false;
	private Proceso proceso;
	private Proceso procesoActualizado;
	@FXML
	private StackPane rootPane;
	@FXML
    private AnchorPane rootAnchorPane;

	@FXML
	private JFXTextField idTextField;
	@FXML
	private JFXTextField nombreTextField;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		singleton = ModelFactoryController.getInstance();
	}

	public void setEscenario(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public Proceso getProceso() {
		return proceso;
	}

	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}

	public Proceso getProcesoActualizado() {
		return procesoActualizado;
	}
	
	public void setRootPane(StackPane rootPane) {
		this.rootPane = rootPane;
	}

	@FXML
	private void cancelarPulsado() {
		okClicked = false;
		dialogStage.close();
		proceso = null;
		procesoActualizado = null;
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	public void actualizarCampos() {
		nombreTextField.setText(proceso.getNombre());
		idTextField.setText(proceso.getId());
	}

	@FXML
	private void editarProceso() {
		if (nombreTextField.getText().isEmpty() || idTextField.getText().isEmpty()) {
			dialogStage.show();
			Aplicacion.mostrarMensaje(rootPane, rootAnchorPane, "Se deben llenar todos los campos");
			rootPane.setEffect(null);
		} else {
			String nombre = nombreTextField.getText();
			String id = idTextField.getText();
			procesoActualizado = new Proceso(id, nombre);
			try {
				singleton.editarProceso(proceso, procesoActualizado);
				dialogStage.close();
				okClicked = true;
			} catch (ProcesoNoExisteException | YaExisteProcesoException e) {
				Aplicacion.mostrarMensaje(rootPane, rootAnchorPane, e.getMessage());
				rootPane.setEffect(null);
			}
		}
	}
}
