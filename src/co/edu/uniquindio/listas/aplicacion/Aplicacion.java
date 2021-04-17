package co.edu.uniquindio.listas.aplicacion;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDialog.DialogTransition;
import com.jfoenix.controls.events.JFXDialogEvent;
import co.edu.uniquindio.listas.model.Proceso;
import co.edu.uniquindio.listas.vistas.procesos.ControladorVistaPrincipalProcesos;
import co.edu.uniquindio.listas.vistas.actividades.ControladorVistaPrincipalActividades;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Aplicacion extends Application {

	public static Stage escenarioPrincipal;
	
	@Override
	public void start(Stage primaryStage) {
		escenarioPrincipal = primaryStage;
		escenarioPrincipal.setTitle("Listas enlazadas");
		mostrarVistaPrincipalProcesos();
	}

	public static void main(String[] args) {
		launch(args);
		
	}
	
	public static boolean mostrarMensajeRespuesta(StackPane root, Pane rootPane, String mensaje) {
		// Muestra el dialogo
		// Ensure that the user can't close the alert.
		BoxBlur blur = new BoxBlur(3, 3, 3);
		boolean bandera = false;
		JFXAlert<String> alert = new JFXAlert<>(escenarioPrincipal);
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.setOverlayClose(false);
		
		// Create the content of the JFXAlert with JFXDialogLayout
		JFXDialogLayout layout = new JFXDialogLayout();
		layout.setBody(new Text(mensaje));
		
		// Buttons get added into the actions section of the layout.
		JFXButton button1=new JFXButton("Si");
		button1.getStylesheets().add("/co/edu/uniquindio/listas/vistas/estilos.css");
		button1.getStyleClass().add("button-red");
		button1.setDefaultButton(true);
		button1.setOnAction(addEvent -> {
			// When the button is clicked, we set the result accordingly
			alert.setResult("1");
			alert.hideWithAnimation();
			rootPane.setEffect(null);
		});
		
		JFXButton button2 = new JFXButton("No");
		button2.getStylesheets().add("/co/edu/uniquindio/listas/vistas/estilos.css");
		button2.getStyleClass().add("button-black");
		button2.setCancelButton(true);
		button2.setOnAction(closeEvent -> {
			// When the button is clicked, we set the result accordingly
			alert.setResult("0");
			alert.hideWithAnimation();
			rootPane.setEffect(null);
		});
		
		layout.setActions(button1, button2);
		alert.setContent(layout);

		rootPane.setEffect(blur);
		
		Optional<String> result = alert.showAndWait();
		if (result.isPresent()){
			if(result.get().equals("1")) {
				bandera = true;
			}
		}
		
		return bandera;
	}
	
	public static void mostrarMensaje(StackPane root, Pane rootPane, String mensaje) {
		BoxBlur blur = new BoxBlur(3, 3, 3);
		JFXDialogLayout content= new JFXDialogLayout();
		content.setBody(new Text(mensaje));
		JFXDialog dialog =new JFXDialog(root, content, JFXDialog.DialogTransition.TOP);
		JFXButton button=new JFXButton("Aceptar");
		button.getStylesheets().add("/co/edu/uniquindio/listas/vistas/estilos.css");
		button.getStyleClass().add("button-red");
		button.setOnAction(new EventHandler<ActionEvent>(){
		    @Override
		    public void handle(ActionEvent event){
		    	rootPane.setEffect(null);
		        dialog.close();
		    }
		});
		content.setActions(button);
		dialog.show();
		rootPane.setEffect(blur);
	}
	
	public static void mostrarVistaPrincipalProcesos() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Aplicacion.class.getResource("../vistas/procesos/VistaPrincipal.fxml"));
			StackPane vistaIndex = (StackPane) loader.load();
			Scene scene = new Scene(vistaIndex);
			escenarioPrincipal.setScene(scene);
			escenarioPrincipal.show();
			ControladorVistaPrincipalProcesos miControlador = loader.getController();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void mostrarVistaPrincipalActividades(Proceso proceso) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Aplicacion.class.getResource("../vistas/actividades/VistaPrincipal.fxml"));
			StackPane vistaIndex = (StackPane) loader.load();
			Scene scene = new Scene(vistaIndex);
			escenarioPrincipal.setScene(scene);
			escenarioPrincipal.show();
			ControladorVistaPrincipalActividades miControlador = loader.getController();
			miControlador.setProceso(proceso);
			miControlador.inicializarTabla();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static String mostrarFileChooserSave() {
			String ruta = "";
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("DAT", "*.dat"));
	        fileChooser.setTitle("Elegir ruta");

	        File file = fileChooser.showSaveDialog(escenarioPrincipal);
	        
	        if(file != null) {
	        	ruta = file.getAbsolutePath();
	        }
	        return ruta;
	}
	
	public static String mostrarFileChooserOpen() {
		String ruta = "";
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("DAT", "*.dat"));
        fileChooser.setTitle("Elegir ruta");

        File file = fileChooser.showOpenDialog(escenarioPrincipal);
        
        if(file != null) {
        	ruta = file.getAbsolutePath();
        }
        return ruta;
}
}
