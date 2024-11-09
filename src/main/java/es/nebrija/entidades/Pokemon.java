package es.nebrija.entidades;

import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pokemon")
public class Pokemon {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "idPokemon")
	private int idPokemon;
	@Column(name = "nombre")
	private String nombre;
	
	//Para quitar una advertencia en Set he incluido <?>
	@Column(name = "tipo")
	private Set<?> tipo;
	
	@Column(name = "habilidad")
	private Set<?> habilidad;
	
	@Column(name = "entrenador")
	private Set<?> entrenador;

	public Pokemon() {
	}

	public Pokemon(String nombre) {
		this.nombre = nombre;

	}

	public int getId() {
		return idPokemon;
	}

	public void setId(int id) {
		this.idPokemon = id;
	}

	public String getName() {
		return nombre;
	}

	public void setName(String nombre) {
		this.nombre = nombre;
	}

	public Set<?> getTipo() {
		return tipo;
	}

	public void setTipo(Set<?> tipo) {
		this.tipo = tipo;
	}

	public Set<?> getHabilidad() {
		return habilidad;
	}

	public void setHabilidad(Set<?> habilidad) {
		this.habilidad = habilidad;
	}

	public Set<?> getEntrenador() {
		return entrenador;
	}

	public void setEntrenador(Set<?> entrenador) {
		this.entrenador = entrenador;
	}
}