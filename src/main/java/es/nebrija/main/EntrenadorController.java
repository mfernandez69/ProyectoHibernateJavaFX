package es.nebrija.main;

import java.io.IOException;

import es.nebrija.dao.DaoEntrenadorImpl;
import es.nebrija.entidades.Entrenador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class EntrenadorController {
	@FXML
	private TextField textoUsuario;
	@FXML
	private TextField textoContrasena;
	@FXML
	private TextField mensajeError;

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
	        mostrarMensaje("Éxito", "Inicio de sesión exitoso.");
	        mandarVentanaPokemons();
	    } else {
	        mostrarMensaje("Error", "Usuario o contraseña incorrectos.");
	    }
	}

	private void mostrarMensaje(String string, String string2) {
		
		
	}

	@FXML
	private void mandarVentanaPokemons() throws IOException {
		App.setRoot("pokemons");
	}
}
