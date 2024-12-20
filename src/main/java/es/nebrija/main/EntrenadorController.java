package es.nebrija.main;

import java.io.IOException;

import es.nebrija.dao.DaoEntrenadorImpl;
import es.nebrija.entidades.Entrenador;
import es.nebrija.entidades.InicioSesion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class EntrenadorController {
	@FXML
    private Button iniciarSesionButton;

    @FXML
    private Button registrarseButton;
	@FXML
	private TextField textoUsuario;
	@FXML
	private TextField textoContrasena;
	@FXML
	private Label mensajeError;
	
	@FXML
	public void initialize() {
	    mensajeError.setVisible(false);
	}
	@FXML
	public void registrarEntrenador() throws IOException {
		String usuario = textoUsuario.getText();
	    String contraseña = textoContrasena.getText();
	    
	    if (usuario.isEmpty() || contraseña.isEmpty()) {
	        mostrarMensaje("Error", "Por favor, ingrese usuario y contraseña.");
	        return;
	    }
	    Entrenador EntidadUsuario = new Entrenador();
	    EntidadUsuario.setName(usuario);
	    EntidadUsuario.setContrasena(contraseña);
	    DaoEntrenadorImpl entrenadorDAO = new DaoEntrenadorImpl();
	    Entrenador entrenador = entrenadorDAO.grabar(EntidadUsuario);
	    if (entrenador != null) {
	    	 Integer entrenadorId =entrenadorDAO.obtenerIdEntrenador(usuario);
	    	 InicioSesion.getInstancia().setEntrenadorActual(entrenador);
	        mostrarMensaje("Éxito", "Registro exitoso.");
	        mandarVentanaPokemons();
	    }
	}
	@FXML
	public void iniciarSesion() throws IOException {
	    String usuario = textoUsuario.getText();
	    String contraseña = textoContrasena.getText();

	    if (usuario.isEmpty() || contraseña.isEmpty()) {
	        mostrarMensaje("Error", "Por favor, ingrese usuario y contraseña.");
	        return;
	    }

	    DaoEntrenadorImpl entrenadorDAO = new DaoEntrenadorImpl();
	    Entrenador entrenador = entrenadorDAO.leer(usuario, contraseña);

	    if (entrenador != null) {
	    	 Integer entrenadorId =entrenadorDAO.obtenerIdEntrenador(usuario);
	    	 InicioSesion.getInstancia().setEntrenadorActual(entrenador);
	        mostrarMensaje("Éxito", "Inicio de sesión exitoso.");
	        mandarVentanaPokemons();
	    } else {
	        mostrarMensaje("Error", "Usuario o contraseña incorrectos.");
	    }
	}

	private void mostrarMensaje(String tipo, String mensaje) {
	    if (tipo.equals("Error")) {
	        mensajeError.setText(mensaje);
	        mensajeError.setVisible(true);
	    } else {
	        mensajeError.setVisible(false);
	    }
	}

	@FXML
	private void mandarVentanaPokemons() throws IOException {
		App.setRoot("pokemons");
	}
}
