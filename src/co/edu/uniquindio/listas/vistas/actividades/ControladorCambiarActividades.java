package co.edu.uniquindio.listas.vistas.actividades;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import co.edu.uniquindio.listas.aplicacion.Aplicacion;
import co.edu.uniquindio.listas.controller.ModelFactoryController;
import co.edu.uniquindio.listas.exceptions.ActividadIgualesException;
import co.edu.uniquindio.listas.exceptions.ActividadNoExisteException;
import co.edu.uniquindio.listas.model.Actividad;
import co.edu.uniquindio.listas.model.Proceso;
import co.edu.uniquindio.listas.model.listas.ListaDoble;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ControladorCambiarActividades implements Initializable {

	private Stage dialogStage;
	private boolean okClicked;
	private ModelFactoryController singleton;
	private Proceso proceso;
	
	@FXML
    private StackPane rootPane;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private JFXComboBox<Actividad> actividadesComboBox1;

    @FXML
    private JFXComboBox<Actividad> actividadesComboBox2;

    public void setEscenario(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public boolean isOkClicked() {
		return okClicked;
	}
	
	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	singleton = ModelFactoryController.getInstance();
	}
    
    public void actualizarCampos() {
    	ListaDoble<Actividad> listadoActividades = singleton.getActividadesProceso(proceso);
    	Iterator<Actividad> it = listadoActividades.iterator();
		while(it.hasNext()) {
			Actividad actividad = it.next();
			actividadesComboBox1.getItems().add(actividad);
			actividadesComboBox2.getItems().add(actividad);
		}
    }
    
    @FXML
	private void cancelar() {
		okClicked = false;
		dialogStage.close();
	}

    @FXML
    private void pulsadoCambiar() {
    	if(actividadesComboBox1.getSelectionModel().isEmpty() || actividadesComboBox2.getSelectionModel().isEmpty()) {
			Aplicacion.mostrarMensaje(rootPane, rootAnchorPane, "Debe seleccionar una actividad");
			rootPane.setEffect(null);
		} else if(actividadesComboBox1.getSelectionModel().getSelectedIndex() == actividadesComboBox2.getSelectionModel().getSelectedIndex()) {
			Aplicacion.mostrarMensaje(rootPane, rootAnchorPane, "Debe seleccionar actividades diferentes");
			rootPane.setEffect(null);
		} else {
			Actividad actividad1 = actividadesComboBox1.getSelectionModel().getSelectedItem();
			Actividad actividad2 = actividadesComboBox2.getSelectionModel().getSelectedItem();
			try {
				singleton.cambiarActividades(proceso, actividad1, actividad2);
				okClicked = true;
				dialogStage.close();
			} catch (ActividadIgualesException | ActividadNoExisteException e) {
				Aplicacion.mostrarMensaje(rootPane, rootAnchorPane, e.getMessage());
			}
			
		}
    }


}
