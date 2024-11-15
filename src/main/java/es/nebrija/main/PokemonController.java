package es.nebrija.main;

import java.util.ArrayList;
import java.util.List;

import es.nebrija.dao.DaoPokemonImpl;
import es.nebrija.entidades.*;
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
import javafx.util.StringConverter;

public class PokemonController {

	@FXML
	private TextField nombreTexto;
	@FXML
	private TableView<Pokemon> tablaPokemons;
	@FXML
	private ComboBox<Tipo> tipoComboBox;
	@FXML
	private ComboBox<Habilidad> habilidadComboBox;
	@FXML
	TableColumn<Pokemon, ?> idColumn;
	@FXML
	TableColumn<Pokemon, ?> nombreColumn;
	@FXML
	TableColumn<Pokemon, ?> tipoColumn;
	@FXML
	TableColumn<Pokemon, ?> habilidadColumn;
	@FXML
	TableColumn<Pokemon, String> entrenadorColumn;

	Pokemon pokemonSeleccionado;
	int filaSeleccionada;

	DaoPokemonImpl DaoPokemonImpl;
	private List<Tipo> tiposArray;
	private List<Habilidad> habilidadesArray;

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
		
		// Configurar cómo se muestran los elementos en los ComboBox
	    tipoComboBox.setConverter(new StringConverter<Tipo>() {
	        @Override
	        public String toString(Tipo tipo) {
	            return tipo == null ? null : tipo.toString();
	        }

	        @Override
	        public Tipo fromString(String string) {
	            return null; // No necesitamos implementar esto para este caso
	        }
	    });

	    habilidadComboBox.setConverter(new StringConverter<Habilidad>() {
	        @Override
	        public String toString(Habilidad habilidad) {
	            return habilidad == null ? null : habilidad.toString();
	        }

	        @Override
	        public Habilidad fromString(String string) {
	            return null; // No necesitamos implementar esto para este caso
	        }
	    });
	    /*
	    entrenadorColumn.setCellValueFactory(cellData -> {
	        Pokemon pokemon = cellData.getValue();
	        if (pokemon != null && pokemon.getEntrenador() != null) {
	            return new SimpleStringProperty(pokemon.getEntrenador().getName());
	        } else {
	            return new SimpleStringProperty("");
	        }
	    });
	    */
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
	    if (!nombreTexto.getText().isEmpty()) {
	        Tipo tipoSeleccionado = tipoComboBox.getValue();
	        Habilidad habilidadSeleccionada = habilidadComboBox.getValue();
	        
	        if (tipoSeleccionado != null && habilidadSeleccionada != null) {
	            Pokemon pokemon = new Pokemon(nombreTexto.getText(), tipoSeleccionado, habilidadSeleccionada);
	            pokemon = DaoPokemonImpl.grabar(pokemon);
	            if (pokemon != null) {
	                tablaPokemons.getItems().add(pokemon);
	            }
	        } else {
	            // Mostrar un mensaje de error si no se seleccionó tipo o habilidad
	        }
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
