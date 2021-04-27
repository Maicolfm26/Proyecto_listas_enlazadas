package co.edu.uniquindio.listas.vistas.actividades;

import java.util.Iterator;
import com.jfoenix.controls.JFXComboBox;
import co.edu.uniquindio.listas.aplicacion.Aplicacion;
import co.edu.uniquindio.listas.controller.ModelFactoryController;
import co.edu.uniquindio.listas.model.Actividad;
import co.edu.uniquindio.listas.model.Proceso;
import co.edu.uniquindio.listas.model.listas.ListaDoble;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ControladorSeleccionarActividad {

	private Stage dialogStage;
	private boolean okClicked;
	private Actividad actividad;
	private ModelFactoryController singleton;
	private Proceso proceso;
	
	@FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane rootAnchorPane;
	@FXML
	private JFXComboBox<Actividad> actividadesComboBox;
	
	public void setEscenario(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public boolean isOkClicked() {
		return okClicked;
	}
	
	public Actividad getActividad() {
		return actividad;
	}
	
	public void cargarActividades(ListaDoble<Actividad> listadoActividades) {
		Iterator<Actividad> it = listadoActividades.iterator();
		while(it.hasNext()) {
			actividadesComboBox.getItems().add(it.next());
		}
	}

	@FXML
	private void cancelar() {
		okClicked = false;
		dialogStage.close();
		actividad = null;
	}

	@FXML
	private void pulsadoSeleccionar() {
		if(actividadesComboBox.getSelectionModel().isEmpty()) {
			Aplicacion.mostrarMensaje(rootPane, rootAnchorPane, "Debe seleccionar una actividad");
			rootPane.setEffect(null);
		} else {
			actividad = actividadesComboBox.getSelectionModel().getSelectedItem();
			okClicked = true;
			dialogStage.close();
		}
	}
}
