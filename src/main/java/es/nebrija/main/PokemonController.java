package es.nebrija.main;

import java.util.ArrayList;


import es.nebrija.dao.DaoPokemonImpl;
import es.nebrija.entidades.Pokemon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

	@FXML
	public void initialize() {
		DaoPokemonImpl = new DaoPokemonImpl();
		ArrayList<Pokemon> listadoPokemons = (ArrayList) DaoPokemonImpl.leerLista();
		tablaPokemons.setEditable(true);

		// Crear las columnas de la TableView
		idColumn = new TableColumn("ID");
		nombreColumn = new TableColumn<>("Nombre");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("idPokemon"));
		nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombrePokemon"));

		// Añadir las columnas a la TableView
		tablaPokemons.getColumns().add(idColumn);
		tablaPokemons.getColumns().add(nombreColumn);
		tablaPokemons.getColumns().add(tipoColumn);
		tablaPokemons.getColumns().add(habilidadColumn);
		tablaPokemons.getColumns().add(entrenadorColumn);

		// Crear una lista observable de objetos Plataforma
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
				tablaPokemons.getSelectionModel().getSelectedItem()
						.setName(pokemonSeleccionado.getName());
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
