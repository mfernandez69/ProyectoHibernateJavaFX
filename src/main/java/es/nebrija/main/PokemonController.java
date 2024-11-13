package es.nebrija.main;

import java.util.ArrayList;
import java.util.List;

import es.nebrija.dao.DaoPokemonImpl;
import es.nebrija.entidades.Pokemon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PokemonController {

	@FXML
	private TextField nombreTexto;
	@FXML
	private TableView<Pokemon> tablaPokemons;
	@FXML
	private ComboBox<String> tipoComboBox;
	@FXML
	private ComboBox<String> habilidadComboBox;
	@FXML
	TableColumn<Pokemon, ?> idColumn;
	@FXML
	TableColumn<Pokemon, ?> nombreColumn;
	@FXML
	TableColumn<Pokemon, ?> tipoColumn;
	@FXML
	TableColumn<Pokemon, ?> habilidadColumn;
	@FXML
	TableColumn<Pokemon, ?> entrenadorColumn;

	Pokemon pokemonSeleccionado;
	int filaSeleccionada;

	DaoPokemonImpl DaoPokemonImpl;
	private List<String> tiposArray;
	private List<String> habilidadesArray;

	@FXML
	public void initialize() {
		DaoPokemonImpl = new DaoPokemonImpl();
		ArrayList<Pokemon> listadoPokemons = (ArrayList) DaoPokemonImpl.leerLista();
		tablaPokemons.setEditable(true);
		// Obtener tipos y habilidades de la base de datos
		tiposArray = DaoPokemonImpl.obtenerTipos();
		habilidadesArray = DaoPokemonImpl.obtenerHabilidades();

		// Llena los ComboBox con las opciones
		tipoComboBox.getItems().addAll(tiposArray);
		habilidadComboBox.getItems().addAll(habilidadesArray);
		// Configurar las columnas existentes
		idColumn.setCellValueFactory(new PropertyValueFactory<>("idPokemon"));
		nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombrePokemon"));
		tipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipo"));
		habilidadColumn.setCellValueFactory(new PropertyValueFactory<>("habilidad"));
		entrenadorColumn.setCellValueFactory(new PropertyValueFactory<>("entrenador"));

		// Crear una lista observable de objetos Pokemon
		ObservableList<Pokemon> pokemons = FXCollections.observableArrayList(listadoPokemons);

		// Asignar la lista a la TableView
		tablaPokemons.setItems(pokemons);

		// Asignar un listener para detectar cuando se selecciona una fila
		tablaPokemons.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				// Acción cuando se selecciona una fila
				pokemonSeleccionado = DaoPokemonImpl.leer(newValue.getId());
				nombreTexto.setText(newValue.getName());
				filaSeleccionada = tablaPokemons.getSelectionModel().getSelectedIndex();
			}
		});
	}

	@FXML
	void grabarPokemon(ActionEvent event) {
		if (nombreTexto.getText() != "") {
			Pokemon pokemon = new Pokemon(nombreTexto.getText());
			DaoPokemonImpl.grabar(pokemon);
			pokemon = DaoPokemonImpl.leer("nombre", pokemon.getName());
			tablaPokemons.getItems().add(pokemon);
		}
	}

	@FXML
	void actualizarPokemon(ActionEvent event) {
		Pokemon pokemon;
		if (pokemonSeleccionado != null) {
			pokemonSeleccionado.setName(nombreTexto.getText());
			pokemon = DaoPokemonImpl.modificar(pokemonSeleccionado);
			System.out.println(pokemon.toString());
			if (tablaPokemons.getSelectionModel().getSelectedIndex() != -1) {
				tablaPokemons.getSelectionModel().getSelectedItem().setName(pokemonSeleccionado.getName());
				tablaPokemons.refresh();
			}
		}
	}

	@FXML
	void borrarPokemon(ActionEvent event) {
		if (pokemonSeleccionado != null) {
			DaoPokemonImpl.borrar(pokemonSeleccionado);
			tablaPokemons.getItems().remove(pokemonSeleccionado);
			actualizarTabla();
		}
	}

	private void actualizarTabla() {
		ArrayList<Pokemon> listadoPokemons = (ArrayList<Pokemon>) DaoPokemonImpl.leerLista();
		ObservableList<Pokemon> pokemons = FXCollections.observableArrayList(listadoPokemons);
		tablaPokemons.setItems(pokemons);
	}
}
