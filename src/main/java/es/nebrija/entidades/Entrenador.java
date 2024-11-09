package es.nebrija.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "entrenador")
public class Entrenador {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "idEntrenador")
	private int idEntrenador;

	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "contrasena")
	private String contrasena;

	public Entrenador() {
	}

	public Entrenador(String nombre) {
		this.nombre = nombre;

	}

	public int getId() {
		return idEntrenador;
	}

	public void setId(int id) {
		this.idEntrenador = id;
	}

	public String getName() {
		return nombre;
	}

	public void setName(String nombre) {
		this.nombre = nombre;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

}