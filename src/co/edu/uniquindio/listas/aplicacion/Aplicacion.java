package co.edu.uniquindio.listas.aplicacion;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import co.edu.uniquindio.listas.vistas.ControladorVistaPrincipal;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Aplicacion extends Application {

	public static Stage escenarioPrincipal;
	
	@Override
	public void start(Stage primaryStage) {
		escenarioPrincipal = primaryStage;
		escenarioPrincipal.setTitle("Listas enlazadas");
		escenarioPrincipal.	setResizable(false);
//		escenarioPrincipal.initStyle(StageStyle.UNDECORATED); 
		mostrarVistaPrincipal();
	}

	public static void main(String[] args) {
		launch(args);
		
	}
	
	public static void mostrarMensaje(String mensaje, AlertType miA, String titulo, String cabecera, String contenido) {
		Alert alert = new Alert(miA);
		alert.initOwner(escenarioPrincipal);
		alert.setTitle(titulo);
		alert.setHeaderText(cabecera);
		alert.setContentText(contenido);
		alert.showAndWait();
	}
	
	public static boolean mostrarMensajeRespuesta(AlertType miA, String titulo, String contenido) {
		// Muestra el dialogo
		boolean bandera = false;
		ButtonType boton1 = new ButtonType("Si");
		ButtonType boton2 = new ButtonType("No");
		Alert alert = new Alert(miA);
		alert.setTitle(titulo);
		alert.setContentText(contenido);
		alert.getButtonTypes().setAll(boton1, boton2);
		Optional<ButtonType> resulatdo = alert.showAndWait();
		if (resulatdo.get() == boton1) {
			bandera = true;
		}
		return bandera;
	}
	
	public static void mostrarVistaPrincipal() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Aplicacion.class.getResource("../vistas/VistaPrincipal.fxml"));
			StackPane vistaIndex = (StackPane) loader.load();
			Scene scene = new Scene(vistaIndex);
			escenarioPrincipal.setScene(scene);
			escenarioPrincipal.show();
			ControladorVistaPrincipal miControlador = loader.getController();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void mostrarVistaListasEnlazadas() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Aplicacion.class.getResource("../vistas/ListaEnlazadasVista.fxml"));
			AnchorPane vistaIndex = (AnchorPane) loader.load();
			Scene scene = new Scene(vistaIndex);
			escenarioPrincipal.setScene(scene);
			escenarioPrincipal.show();
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

//	public static void mostrarVistaAdmin() {
//		try {
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(Aplicacion.class.getResource("../view/AdminVistaPrincipal.fxml"));
//			AnchorPane vistaIndex = (AnchorPane) loader.load();
//			Scene scene = new Scene(vistaIndex);
//			escenarioPrincipal.setScene(scene);
//			escenarioPrincipal.show();
//			ControladorVistaAdmin miControlador = loader.getController();
//			miControlador.inicializarTablas();
//		} catch (IOException e) {
//			System.out.println(e.getMessage());
//		}
//	}
	
//	public static void mostrarVistaEmpleado() {
//		try {
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(Aplicacion.class.getResource("../view/EmpleadoVistaPrincipal.fxml"));
//			AnchorPane vistaIndex = (AnchorPane) loader.load();
//			Scene scene = new Scene(vistaIndex);
//			escenarioPrincipal.setScene(scene);
//			escenarioPrincipal.show();
//			ControladorVistaEmpleado miControlador = loader.getController();
//		} catch (IOException e) {
//			System.out.println(e.getMessage());
//		}
//	}

//	public static void mostrarVistaCliente() {
//		try {
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(Aplicacion.class.getResource("../view/ClienteVistaPrincipal.fxml"));
//			AnchorPane vistaIndex = (AnchorPane) loader.load();
//			Scene scene = new Scene(vistaIndex);
//			escenarioPrincipal.setScene(scene);
//			escenarioPrincipal.show();
//			ControladorVistaCliente miControlador = loader.getController();
//			miControlador.inicializarDatos();
//		} catch (IOException e) {
//			System.out.println(e.getMessage());
//		}
//	}
}
