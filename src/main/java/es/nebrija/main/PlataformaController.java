package es.nebrija.main;

import java.util.ArrayList;

import es.nebrija.dao.DaoPlataformasImpl;
import es.nebrija.entidades.Plataforma;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PlataformaController {

	@FXML
	private TextField nombreTexto;
	@FXML
	private TableView<Plataforma> tablaPlataformas;
	@FXML
	TableColumn<Plataforma, Integer> idColumn;
	@FXML
	TableColumn<Plataforma, String> nombreColumn;

	Plataforma plataformaSeleccionada;
	int filaSeleccionada;

	DaoPlataformasImpl daoPlataformaImpl;

	@FXML
	public void initialize() {
		daoPlataformaImpl = new DaoPlataformasImpl();
		ArrayList<Plataforma> listadoPlataformas = (ArrayList) daoPlataformaImpl.leerLista();
		tablaPlataformas.setEditable(true);

		// Crear las columnas de la TableView
		idColumn = new TableColumn("ID");
		nombreColumn = new TableColumn<>("Nombre");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("idPlataforma"));
		nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombrePlataforma"));

		// Añadir las columnas a la TableView
		tablaPlataformas.getColumns().add(idColumn);
		tablaPlataformas.getColumns().add(nombreColumn);

		// Crear una lista observable de objetos Plataforma
		ObservableList<Plataforma> plataformas = FXCollections.observableArrayList(listadoPlataformas);

		// Asignar la lista a la TableView
		tablaPlataformas.setItems(plataformas);

		// Asignar un listener para detectar cuando se selecciona una fila
		tablaPlataformas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				// Acción cuando se selecciona una fila
				plataformaSeleccionada = daoPlataformaImpl.leer(newValue.getIdPlataforma());
				nombreTexto.setText(newValue.getNombrePlataforma());
				filaSeleccionada = tablaPlataformas.getSelectionModel().getSelectedIndex();
			}
		});
	}

	@FXML
	void grabarPlataforma(ActionEvent event) {
		if (nombreTexto.getText() != "") {
			Plataforma plataforma = new Plataforma(nombreTexto.getText());
			daoPlataformaImpl.grabar(plataforma);
			plataforma = daoPlataformaImpl.leer("nombre", plataforma.getNombrePlataforma());
			tablaPlataformas.getItems().add(plataforma);
		}
	}

	@FXML
	void actualizarPlataforma(ActionEvent event) {
		Plataforma plataforma;
		if (plataformaSeleccionada != null) {
			plataformaSeleccionada.setNombrePlataforma(nombreTexto.getText());
			plataforma = daoPlataformaImpl.modificar(plataformaSeleccionada);
			System.out.println(plataforma.toString());
			if (tablaPlataformas.getSelectionModel().getSelectedIndex() != -1) {
				tablaPlataformas.getSelectionModel().getSelectedItem()
						.setNombrePlataforma(plataformaSeleccionada.getNombrePlataforma());
				tablaPlataformas.refresh();
			}
		}
	}

	@FXML
	void borrarPlataforma(ActionEvent event) {
		if (plataformaSeleccionada != null) {
			daoPlataformaImpl.borrar(plataformaSeleccionada);
			tablaPlataformas.getItems().remove(plataformaSeleccionada);
			actualizarTabla();
		}
	}

	private void actualizarTabla() {
		ArrayList<Plataforma> listadoPlataformas = (ArrayList) daoPlataformaImpl.leerLista();
		ObservableList<Plataforma> plataformas = FXCollections.observableArrayList(listadoPlataformas);
		tablaPlataformas.setItems(plataformas);
	}
}
