package es.nebrija.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import es.nebrija.dao.DaoPokemonImpl;
import es.nebrija.dao.HibernateUtil;
import es.nebrija.entidades.*;
import es.nebrija.entidades.Pokemon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
	private TableColumn<Pokemon, Integer> idColumn;
	@FXML
	private TableColumn<Pokemon, String> nombreColumn;
	@FXML
	private TableColumn<Pokemon, String> tipoColumn;
	@FXML
	private TableColumn<Pokemon, String> habilidadColumn;
	@FXML
	private TableColumn<Pokemon, String> entrenadorColumn;
	@FXML
	private Label textoInfo;
	
	Pokemon pokemonSeleccionado;
	int filaSeleccionada;

	DaoPokemonImpl DaoPokemonImpl;
	private List<Tipo> tiposArray;
	private List<Habilidad> habilidadesArray;

	@FXML
	public void initialize() {
		//Hacemos que el mensaje de exportacion no se vea
		textoInfo.setVisible(false);
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
	    
		// Configurar las columnas existentes,el nombre que le pasamos en los parentesis tiene que ser el mismo que 
	    // el nombre del getter y setter de la clase pokemon
	    idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
	    nombreColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
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
		actualizarTabla();
	}

	@FXML
	void grabarPokemon(ActionEvent event) {
	    if (!nombreTexto.getText().isEmpty()) {
	    	
	        Tipo tipoSeleccionado = tipoComboBox.getValue();
	        Habilidad habilidadSeleccionada = habilidadComboBox.getValue();
	        
            
	        if (tipoSeleccionado != null && habilidadSeleccionada != null) {
	            Pokemon pokemon = new Pokemon(nombreTexto.getText(), tipoSeleccionado, habilidadSeleccionada);
	            
	            // Obtener el Entrenador actual
	            Entrenador entrenadorActual = InicioSesion.getInstancia().getEntrenadorActual();
	            
	            // Asignar el Entrenador al Pokémon
	            pokemon.setEntrenador(entrenadorActual);
	            
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
	    for (Pokemon p : listadoPokemons) {
	    	System.out.println("-----------------------------------------------------------------");
	        System.out.println("ID: " + p.getId() + ", Nombre: " + p.getName() +
	                           ", Tipo: " + (p.getTipo() != null ? p.getTipo().getName() : "null") +
	                           ", Habilidad: " + (p.getHabilidad() != null ? p.getHabilidad().getName() : "null") +
	                           ", Entrenador: " + (p.getEntrenador() != null ? p.getEntrenador().getName() : "null"));
	    }
	    ObservableList<Pokemon> pokemons = FXCollections.observableArrayList(listadoPokemons);
	    tablaPokemons.setItems(pokemons);
	}
	@FXML
	void exportarDatosCSV(ActionEvent event) {
	    // Obtener los datos de la base de datos
	    List<Pokemon> pokemons = obtenerPokemonsDesdeBD();
	    //Le decimos a java en que carpeta se encuentra el archivo .csv
	    File resourcesDir = new File("resources");  
	    // El archivo CSV donde se guardarán los datos
	    File archivo = new File(resourcesDir, "pokemons.csv");

	    // Escribir los datos en el archivo CSV
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
	        // Escribir el encabezado
	        writer.write("ID,Nombre,Habilidad,Tipo,Entrenador\n");

	        // Escribir los datos de los usuarios
	        for (Pokemon pokemon : pokemons) {
	            writer.write(pokemon.getId() + "," + pokemon.getName() + "," + pokemon.getHabilidad() + pokemon.getTipo()+ "," + pokemon.getEntrenador()+"\n");
	        }

	        mostrarMensaje("Éxito", "Los datos se han exportado correctamente:");
	        System.out.println("Los datos se han exportado correctamente:"+ archivo.getAbsolutePath());
	    } catch (IOException e) {
	        e.printStackTrace();
	        
	    }
	}
	public List<Pokemon> obtenerPokemonsDesdeBD() {
	    Session sesion = HibernateUtil.getSessionFactory().openSession();
	    List<Pokemon> pokemons = new ArrayList<>();
	    
	    try {
	        pokemons = sesion.createQuery("FROM Pokemon", Pokemon.class).getResultList();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        sesion.close();
	    }
	    
	    return pokemons;
	}
	private void mostrarMensaje(String tipo, String mensaje) {
	    if (tipo.equals("Éxito")) {
	        textoInfo.setText(mensaje);
	        textoInfo.setVisible(true);
	    } else {
	    	textoInfo.setVisible(false);
	    }
	}
}
